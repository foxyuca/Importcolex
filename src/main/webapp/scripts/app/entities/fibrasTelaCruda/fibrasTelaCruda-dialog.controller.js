'use strict';

angular.module('importcolexApp').controller('FibrasTelaCrudaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FibrasTelaCruda', 'InventarioFibras', 'TelaCruda',
        function($scope, $stateParams, $modalInstance, entity, FibrasTelaCruda, InventarioFibras, TelaCruda) {

        $scope.fibrasTelaCruda = entity;
        $scope.inventariofibrass = InventarioFibras.query();
        $scope.telacrudas = TelaCruda.query();
        $scope.load = function(id) {
            FibrasTelaCruda.get({id : id}, function(result) {
                $scope.fibrasTelaCruda = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:fibrasTelaCrudaUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.fibrasTelaCruda.id != null) {
                FibrasTelaCruda.update($scope.fibrasTelaCruda, onSaveFinished);
            } else {
                FibrasTelaCruda.save($scope.fibrasTelaCruda, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
