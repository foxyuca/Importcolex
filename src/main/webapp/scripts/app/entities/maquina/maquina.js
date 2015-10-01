'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('maquina', {
                parent: 'entity',
                url: '/maquinas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.maquina.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/maquina/maquinas.html',
                        controller: 'MaquinaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('maquina');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('maquina.detail', {
                parent: 'entity',
                url: '/maquina/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.maquina.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/maquina/maquina-detail.html',
                        controller: 'MaquinaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('maquina');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Maquina', function($stateParams, Maquina) {
                        return Maquina.get({id : $stateParams.id});
                    }]
                }
            })
            .state('maquina.new', {
                parent: 'maquina',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/maquina/maquina-dialog.html',
                        controller: 'MaquinaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {marca: null, galga: null, diametro: null, rpm: null, agujas: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('maquina', null, { reload: true });
                    }, function() {
                        $state.go('maquina');
                    })
                }]
            })
            .state('maquina.edit', {
                parent: 'maquina',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/maquina/maquina-dialog.html',
                        controller: 'MaquinaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Maquina', function(Maquina) {
                                return Maquina.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('maquina', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
