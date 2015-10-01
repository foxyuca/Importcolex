'use strict';

angular.module('importcolexApp').controller('ClientesDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Clientes', 'TelaCruda',
        function($scope, $stateParams, $modalInstance, entity, Clientes, TelaCruda) {

        $scope.clientes = entity;
        $scope.telacrudas = TelaCruda.query();
        $scope.load = function(id) {
            Clientes.get({id : id}, function(result) {
                $scope.clientes = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:clientesUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.clientes.id != null) {
                Clientes.update($scope.clientes, onSaveFinished);
            } else {
                Clientes.save($scope.clientes, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
