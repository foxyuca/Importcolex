'use strict';

angular.module('importcolexApp')
    .controller('TelaCrudaDetailController', function ($scope, $rootScope, $stateParams, entity, TelaCruda, FibrasTelaCruda, TipoTela, Maquina, Operario, Clientes) {
        $scope.telaCruda = entity;
        $scope.load = function (id) {
            TelaCruda.get({id: id}, function(result) {
                $scope.telaCruda = result;
            });
        };
        $rootScope.$on('importcolexApp:telaCrudaUpdate', function(event, result) {
            $scope.telaCruda = result;
        });
    });
