'use strict';

angular.module('importcolexApp')
    .controller('FibrasDetailController', function ($scope, $rootScope, $stateParams, entity, Fibras, OrdenCompra, InventarioFibras) {
        $scope.fibras = entity;
        $scope.load = function (id) {
            Fibras.get({id: id}, function(result) {
                $scope.fibras = result;
            });
        };
        $rootScope.$on('importcolexApp:fibrasUpdate', function(event, result) {
            $scope.fibras = result;
        });
    });
