'use strict';

angular.module('importcolexApp').controller('TelaCrudaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TelaCruda', 'FibrasTelaCruda', 'TipoTela', 'Maquina', 'Operario', 'Clientes',
        function($scope, $stateParams, $modalInstance, entity, TelaCruda, FibrasTelaCruda, TipoTela, Maquina, Operario, Clientes) {

        $scope.telaCruda = entity;
        $scope.fibrastelacrudas = FibrasTelaCruda.query();
        $scope.tipotelas = TipoTela.query();
        $scope.maquinas = Maquina.query();
        $scope.operarios = Operario.query();
        $scope.clientess = Clientes.query();
        $scope.load = function(id) {
            TelaCruda.get({id : id}, function(result) {
                $scope.telaCruda = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:telaCrudaUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.telaCruda.id != null) {
                TelaCruda.update($scope.telaCruda, onSaveFinished);
            } else {
                TelaCruda.save($scope.telaCruda, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
