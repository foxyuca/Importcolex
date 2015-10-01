'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fibras', {
                parent: 'entity',
                url: '/fibrass',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.fibras.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fibras/fibrass.html',
                        controller: 'FibrasController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fibras');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('fibras.detail', {
                parent: 'entity',
                url: '/fibras/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.fibras.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fibras/fibras-detail.html',
                        controller: 'FibrasDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fibras');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Fibras', function($stateParams, Fibras) {
                        return Fibras.get({id : $stateParams.id});
                    }]
                }
            })
            .state('fibras.new', {
                parent: 'fibras',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fibras/fibras-dialog.html',
                        controller: 'FibrasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nombre: null, titulo: null, filamentos: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fibras', null, { reload: true });
                    }, function() {
                        $state.go('fibras');
                    })
                }]
            })
            .state('fibras.edit', {
                parent: 'fibras',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fibras/fibras-dialog.html',
                        controller: 'FibrasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Fibras', function(Fibras) {
                                return Fibras.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fibras', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
