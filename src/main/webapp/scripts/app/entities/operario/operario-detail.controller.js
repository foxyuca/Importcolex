'use strict';

angular.module('importcolexApp')
    .controller('OperarioDetailController', function ($scope, $rootScope, $stateParams, entity, Operario, TelaCruda) {
        $scope.operario = entity;
        $scope.load = function (id) {
            Operario.get({id: id}, function(result) {
                $scope.operario = result;
            });
        };
        $rootScope.$on('importcolexApp:operarioUpdate', function(event, result) {
            $scope.operario = result;
        });
    });
