'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tipoTela', {
                parent: 'entity',
                url: '/tipoTelas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.tipoTela.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tipoTela/tipoTelas.html',
                        controller: 'TipoTelaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tipoTela');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tipoTela.detail', {
                parent: 'entity',
                url: '/tipoTela/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.tipoTela.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tipoTela/tipoTela-detail.html',
                        controller: 'TipoTelaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tipoTela');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TipoTela', function($stateParams, TipoTela) {
                        return TipoTela.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tipoTela.new', {
                parent: 'tipoTela',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tipoTela/tipoTela-dialog.html',
                        controller: 'TipoTelaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nombre: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tipoTela', null, { reload: true });
                    }, function() {
                        $state.go('tipoTela');
                    })
                }]
            })
            .state('tipoTela.edit', {
                parent: 'tipoTela',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tipoTela/tipoTela-dialog.html',
                        controller: 'TipoTelaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TipoTela', function(TipoTela) {
                                return TipoTela.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tipoTela', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
