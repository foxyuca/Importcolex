'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('clientes', {
                parent: 'entity',
                url: '/clientess',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.clientes.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clientes/clientess.html',
                        controller: 'ClientesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clientes');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('clientes.detail', {
                parent: 'entity',
                url: '/clientes/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.clientes.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clientes/clientes-detail.html',
                        controller: 'ClientesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clientes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Clientes', function($stateParams, Clientes) {
                        return Clientes.get({id : $stateParams.id});
                    }]
                }
            })
            .state('clientes.new', {
                parent: 'clientes',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/clientes/clientes-dialog.html',
                        controller: 'ClientesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nombre: null, identificacion: null, telefono: null, correo: null, direccion: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('clientes', null, { reload: true });
                    }, function() {
                        $state.go('clientes');
                    })
                }]
            })
            .state('clientes.edit', {
                parent: 'clientes',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/clientes/clientes-dialog.html',
                        controller: 'ClientesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Clientes', function(Clientes) {
                                return Clientes.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('clientes', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
