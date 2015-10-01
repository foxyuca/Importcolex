'use strict';

angular.module('importcolexApp')
    .controller('ProveedoresDetailController', function ($scope, $rootScope, $stateParams, entity, Proveedores, OrdenCompra) {
        $scope.proveedores = entity;
        $scope.load = function (id) {
            Proveedores.get({id: id}, function(result) {
                $scope.proveedores = result;
            });
        };
        $rootScope.$on('importcolexApp:proveedoresUpdate', function(event, result) {
            $scope.proveedores = result;
        });
    });
