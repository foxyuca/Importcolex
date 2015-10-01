'use strict';

angular.module('importcolexApp')
    .controller('MaquinaDetailController', function ($scope, $rootScope, $stateParams, entity, Maquina, TelaCruda) {
        $scope.maquina = entity;
        $scope.load = function (id) {
            Maquina.get({id: id}, function(result) {
                $scope.maquina = result;
            });
        };
        $rootScope.$on('importcolexApp:maquinaUpdate', function(event, result) {
            $scope.maquina = result;
        });
    });
