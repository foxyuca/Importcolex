'use strict';

angular.module('importcolexApp')
    .controller('TipoTelaDetailController', function ($scope, $rootScope, $stateParams, entity, TipoTela, DireccionamientoTela, TelaCruda) {
        $scope.tipoTela = entity;
        $scope.load = function (id) {
            TipoTela.get({id: id}, function(result) {
                $scope.tipoTela = result;
            });
        };
        $rootScope.$on('importcolexApp:tipoTelaUpdate', function(event, result) {
            $scope.tipoTela = result;
        });
    });
