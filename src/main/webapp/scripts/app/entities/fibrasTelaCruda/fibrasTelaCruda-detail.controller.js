'use strict';

angular.module('importcolexApp')
    .controller('FibrasTelaCrudaDetailController', function ($scope, $rootScope, $stateParams, entity, FibrasTelaCruda, InventarioFibras, TelaCruda) {
        $scope.fibrasTelaCruda = entity;
        $scope.load = function (id) {
            FibrasTelaCruda.get({id: id}, function(result) {
                $scope.fibrasTelaCruda = result;
            });
        };
        $rootScope.$on('importcolexApp:fibrasTelaCrudaUpdate', function(event, result) {
            $scope.fibrasTelaCruda = result;
        });
    });
