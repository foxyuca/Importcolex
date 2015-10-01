'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('operario', {
                parent: 'entity',
                url: '/operarios',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.operario.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/operario/operarios.html',
                        controller: 'OperarioController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('operario');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('operario.detail', {
                parent: 'entity',
                url: '/operario/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.operario.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/operario/operario-detail.html',
                        controller: 'OperarioDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('operario');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Operario', function($stateParams, Operario) {
                        return Operario.get({id : $stateParams.id});
                    }]
                }
            })
            .state('operario.new', {
                parent: 'operario',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/operario/operario-dialog.html',
                        controller: 'OperarioDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nombre: null, turno: null, referencia: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('operario', null, { reload: true });
                    }, function() {
                        $state.go('operario');
                    })
                }]
            })
            .state('operario.edit', {
                parent: 'operario',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/operario/operario-dialog.html',
                        controller: 'OperarioDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Operario', function(Operario) {
                                return Operario.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('operario', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
