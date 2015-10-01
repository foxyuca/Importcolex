'use strict';

angular.module('importcolexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('telaCruda', {
                parent: 'entity',
                url: '/telaCrudas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.telaCruda.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/telaCruda/telaCrudas.html',
                        controller: 'TelaCrudaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('telaCruda');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('telaCruda.detail', {
                parent: 'entity',
                url: '/telaCruda/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'importcolexApp.telaCruda.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/telaCruda/telaCruda-detail.html',
                        controller: 'TelaCrudaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('telaCruda');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TelaCruda', function($stateParams, TelaCruda) {
                        return TelaCruda.get({id : $stateParams.id});
                    }]
                }
            })
            .state('telaCruda.new', {
                parent: 'telaCruda',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/telaCruda/telaCruda-dialog.html',
                        controller: 'TelaCrudaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {peso: null, ancho: null, costo: null, fechaProduccion: null, horaInicial: null, horaFinal: null, turno: null, gramaje: null, inicioParoMecanico: null, finParoMecanico: null, motivoParoMecanico: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('telaCruda', null, { reload: true });
                    }, function() {
                        $state.go('telaCruda');
                    })
                }]
            })
            .state('telaCruda.edit', {
                parent: 'telaCruda',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/telaCruda/telaCruda-dialog.html',
                        controller: 'TelaCrudaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TelaCruda', function(TelaCruda) {
                                return TelaCruda.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('telaCruda', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
