'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('proveedores', {
                parent: 'entity',
                url: '/proveedoress',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.proveedores.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/proveedores/proveedoress.html',
                        controller: 'ProveedoresController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('proveedores');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('proveedores.detail', {
                parent: 'entity',
                url: '/proveedores/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.proveedores.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/proveedores/proveedores-detail.html',
                        controller: 'ProveedoresDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('proveedores');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Proveedores', function($stateParams, Proveedores) {
                        return Proveedores.get({id : $stateParams.id});
                    }]
                }
            })
            .state('proveedores.new', {
                parent: 'proveedores',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/proveedores/proveedores-dialog.html',
                        controller: 'ProveedoresDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nombre: null, identificacion: null, direccion: null, telefono: null, email: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('proveedores', null, { reload: true });
                    }, function() {
                        $state.go('proveedores');
                    })
                }]
            })
            .state('proveedores.edit', {
                parent: 'proveedores',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/proveedores/proveedores-dialog.html',
                        controller: 'ProveedoresDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Proveedores', function(Proveedores) {
                                return Proveedores.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('proveedores', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
