'use strict';

angular.module('importcolexApp')
    .controller('ClientesDetailController', function ($scope, $rootScope, $stateParams, entity, Clientes, TelaCruda) {
        $scope.clientes = entity;
        $scope.load = function (id) {
            Clientes.get({id: id}, function(result) {
                $scope.clientes = result;
            });
        };
        $rootScope.$on('importcolexApp:clientesUpdate', function(event, result) {
            $scope.clientes = result;
        });
    });
