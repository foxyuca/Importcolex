'use strict';

angular.module('importcolexApp')
    .controller('DireccionamientoTelaDetailController', function ($scope, $rootScope, $stateParams, entity, DireccionamientoTela, TipoTela) {
        $scope.direccionamientoTela = entity;
        $scope.load = function (id) {
            DireccionamientoTela.get({id: id}, function(result) {
                $scope.direccionamientoTela = result;
            });
        };
        $rootScope.$on('importcolexApp:direccionamientoTelaUpdate', function(event, result) {
            $scope.direccionamientoTela = result;
        });
    });
