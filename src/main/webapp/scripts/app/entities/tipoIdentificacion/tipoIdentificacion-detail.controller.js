'use strict';

angular.module('importcolexApp')
    .controller('TipoIdentificacionDetailController', function ($scope, $rootScope, $stateParams, entity, TipoIdentificacion, Proveedores) {
        $scope.tipoIdentificacion = entity;
        $scope.load = function (id) {
            TipoIdentificacion.get({id: id}, function(result) {
                $scope.tipoIdentificacion = result;
            });
        };
        $rootScope.$on('importcolexApp:tipoIdentificacionUpdate', function(event, result) {
            $scope.tipoIdentificacion = result;
        });
    });
