'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('direccionamientoTela', {
                parent: 'entity',
                url: '/direccionamientoTelas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.direccionamientoTela.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/direccionamientoTela/direccionamientoTelas.html',
                        controller: 'DireccionamientoTelaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('direccionamientoTela');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('direccionamientoTela.detail', {
                parent: 'entity',
                url: '/direccionamientoTela/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.direccionamientoTela.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/direccionamientoTela/direccionamientoTela-detail.html',
                        controller: 'DireccionamientoTelaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('direccionamientoTela');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DireccionamientoTela', function($stateParams, DireccionamientoTela) {
                        return DireccionamientoTela.get({id : $stateParams.id});
                    }]
                }
            })
            .state('direccionamientoTela.new', {
                parent: 'direccionamientoTela',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/direccionamientoTela/direccionamientoTela-dialog.html',
                        controller: 'DireccionamientoTelaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nombre: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('direccionamientoTela', null, { reload: true });
                    }, function() {
                        $state.go('direccionamientoTela');
                    })
                }]
            })
            .state('direccionamientoTela.edit', {
                parent: 'direccionamientoTela',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/direccionamientoTela/direccionamientoTela-dialog.html',
                        controller: 'DireccionamientoTelaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DireccionamientoTela', function(DireccionamientoTela) {
                                return DireccionamientoTela.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('direccionamientoTela', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
