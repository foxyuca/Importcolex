'use strict';

angular.module('importcolexApp')
    .controller('OrdenCompraDetailController', function ($scope, $rootScope, $stateParams, entity, OrdenCompra, Proveedores, Fibras) {
        $scope.ordenCompra = entity;
        $scope.load = function (id) {
            OrdenCompra.get({id: id}, function(result) {
                $scope.ordenCompra = result;
            });
        };
        $rootScope.$on('importcolexApp:ordenCompraUpdate', function(event, result) {
            $scope.ordenCompra = result;
        });
    });
