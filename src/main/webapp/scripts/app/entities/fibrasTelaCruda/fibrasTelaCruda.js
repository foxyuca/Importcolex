'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fibrasTelaCruda', {
                parent: 'entity',
                url: '/fibrasTelaCrudas',
                data: {
                    authorities: ['ROLE_OPERARIO'],
                    pageTitle: 'importcolexApp.fibrasTelaCruda.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fibrasTelaCruda/fibrasTelaCrudas.html',
                        controller: 'FibrasTelaCrudaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fibrasTelaCruda');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('fibrasTelaCruda.detail', {
                parent: 'entity',
                url: '/fibrasTelaCruda/{id}',
                data: {
                    authorities: ['ROLE_OPERARIO'],
                    pageTitle: 'importcolexApp.fibrasTelaCruda.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fibrasTelaCruda/fibrasTelaCruda-detail.html',
                        controller: 'FibrasTelaCrudaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fibrasTelaCruda');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'FibrasTelaCruda', function($stateParams, FibrasTelaCruda) {
                        return FibrasTelaCruda.get({id : $stateParams.id});
                    }]
                }
            })
            .state('fibrasTelaCruda.new', {
                parent: 'fibrasTelaCruda',
                url: '/new',
                data: {
                    authorities: ['ROLE_OPERARIO'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fibrasTelaCruda/fibrasTelaCruda-dialog.html',
                        controller: 'FibrasTelaCrudaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {referencia: null, peso: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fibrasTelaCruda', null, { reload: true });
                    }, function() {
                        $state.go('fibrasTelaCruda');
                    })
                }]
            })
            .state('fibrasTelaCruda.edit', {
                parent: 'fibrasTelaCruda',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_OPERARIO'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fibrasTelaCruda/fibrasTelaCruda-dialog.html',
                        controller: 'FibrasTelaCrudaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['FibrasTelaCruda', function(FibrasTelaCruda) {
                                return FibrasTelaCruda.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fibrasTelaCruda', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
