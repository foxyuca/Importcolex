'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ordenCompra', {
                parent: 'entity',
                url: '/ordenCompras',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.ordenCompra.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ordenCompra/ordenCompras.html',
                        controller: 'OrdenCompraController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ordenCompra');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ordenCompra.detail', {
                parent: 'entity',
                url: '/ordenCompra/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.ordenCompra.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ordenCompra/ordenCompra-detail.html',
                        controller: 'OrdenCompraDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ordenCompra');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OrdenCompra', function($stateParams, OrdenCompra) {
                        return OrdenCompra.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ordenCompra.new', {
                parent: 'ordenCompra',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ordenCompra/ordenCompra-dialog.html',
                        controller: 'OrdenCompraDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {ticket: null, cantidad: null, costo: null, aprovada: null, ordenada: null, recibida: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ordenCompra', null, { reload: true });
                    }, function() {
                        $state.go('ordenCompra');
                    })
                }]
            })
            .state('ordenCompra.edit', {
                parent: 'ordenCompra',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ordenCompra/ordenCompra-dialog.html',
                        controller: 'OrdenCompraDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OrdenCompra', function(OrdenCompra) {
                                return OrdenCompra.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ordenCompra', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
