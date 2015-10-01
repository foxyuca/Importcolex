'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('inventarioFibras', {
                parent: 'entity',
                url: '/inventarioFibrass',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.inventarioFibras.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventarioFibras/inventarioFibrass.html',
                        controller: 'InventarioFibrasController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventarioFibras');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('inventarioFibras.detail', {
                parent: 'entity',
                url: '/inventarioFibras/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.inventarioFibras.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventarioFibras/inventarioFibras-detail.html',
                        controller: 'InventarioFibrasDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventarioFibras');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InventarioFibras', function($stateParams, InventarioFibras) {
                        return InventarioFibras.get({id : $stateParams.id});
                    }]
                }
            })
            .state('inventarioFibras.new', {
                parent: 'inventarioFibras',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/inventarioFibras/inventarioFibras-dialog.html',
                        controller: 'InventarioFibrasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {lote: null, inventarioInicial: null, inventarioFinal: null, costo: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('inventarioFibras', null, { reload: true });
                    }, function() {
                        $state.go('inventarioFibras');
                    })
                }]
            })
            .state('inventarioFibras.edit', {
                parent: 'inventarioFibras',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/inventarioFibras/inventarioFibras-dialog.html',
                        controller: 'InventarioFibrasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InventarioFibras', function(InventarioFibras) {
                                return InventarioFibras.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('inventarioFibras', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
