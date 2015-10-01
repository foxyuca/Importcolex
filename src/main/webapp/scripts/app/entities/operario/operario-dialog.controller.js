'use strict';

angular.module('importcolexApp').controller('OperarioDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Operario', 'TelaCruda',
        function($scope, $stateParams, $modalInstance, entity, Operario, TelaCruda) {

        $scope.operario = entity;
        $scope.telacrudas = TelaCruda.query();
        $scope.load = function(id) {
            Operario.get({id : id}, function(result) {
                $scope.operario = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:operarioUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.operario.id != null) {
                Operario.update($scope.operario, onSaveFinished);
            } else {
                Operario.save($scope.operario, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
