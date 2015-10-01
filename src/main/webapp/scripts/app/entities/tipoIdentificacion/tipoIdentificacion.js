'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tipoIdentificacion', {
                parent: 'entity',
                url: '/tipoIdentificacions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.tipoIdentificacion.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tipoIdentificacion/tipoIdentificacions.html',
                        controller: 'TipoIdentificacionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tipoIdentificacion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tipoIdentificacion.detail', {
                parent: 'entity',
                url: '/tipoIdentificacion/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.tipoIdentificacion.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tipoIdentificacion/tipoIdentificacion-detail.html',
                        controller: 'TipoIdentificacionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tipoIdentificacion');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TipoIdentificacion', function($stateParams, TipoIdentificacion) {
                        return TipoIdentificacion.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tipoIdentificacion.new', {
                parent: 'tipoIdentificacion',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tipoIdentificacion/tipoIdentificacion-dialog.html',
                        controller: 'TipoIdentificacionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nombre: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tipoIdentificacion', null, { reload: true });
                    }, function() {
                        $state.go('tipoIdentificacion');
                    })
                }]
            })
            .state('tipoIdentificacion.edit', {
                parent: 'tipoIdentificacion',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tipoIdentificacion/tipoIdentificacion-dialog.html',
                        controller: 'TipoIdentificacionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TipoIdentificacion', function(TipoIdentificacion) {
                                return TipoIdentificacion.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tipoIdentificacion', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
