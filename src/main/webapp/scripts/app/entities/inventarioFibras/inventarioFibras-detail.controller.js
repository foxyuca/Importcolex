'use strict';

angular.module('importcolexApp')
    .controller('InventarioFibrasDetailController', function ($scope, $rootScope, $stateParams, entity, InventarioFibras, Fibras, FibrasTelaCruda) {
        $scope.inventarioFibras = entity;
        $scope.load = function (id) {
            InventarioFibras.get({id: id}, function(result) {
                $scope.inventarioFibras = result;
            });
        };
        $rootScope.$on('importcolexApp:inventarioFibrasUpdate', function(event, result) {
            $scope.inventarioFibras = result;
        });
    });
