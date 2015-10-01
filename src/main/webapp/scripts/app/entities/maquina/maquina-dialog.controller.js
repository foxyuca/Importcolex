'use strict';

angular.module('importcolexApp').controller('MaquinaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Maquina', 'TelaCruda',
        function($scope, $stateParams, $modalInstance, entity, Maquina, TelaCruda) {

        $scope.maquina = entity;
        $scope.telacrudas = TelaCruda.query();
        $scope.load = function(id) {
            Maquina.get({id : id}, function(result) {
                $scope.maquina = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:maquinaUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.maquina.id != null) {
                Maquina.update($scope.maquina, onSaveFinished);
            } else {
                Maquina.save($scope.maquina, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
