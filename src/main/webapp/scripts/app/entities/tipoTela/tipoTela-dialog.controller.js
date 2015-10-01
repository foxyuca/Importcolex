'use strict';

angular.module('importcolexApp').controller('TipoTelaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TipoTela', 'DireccionamientoTela', 'TelaCruda',
        function($scope, $stateParams, $modalInstance, entity, TipoTela, DireccionamientoTela, TelaCruda) {

        $scope.tipoTela = entity;
        $scope.direccionamientotelas = DireccionamientoTela.query();
        $scope.telacrudas = TelaCruda.query();
        $scope.load = function(id) {
            TipoTela.get({id : id}, function(result) {
                $scope.tipoTela = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:tipoTelaUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.tipoTela.id != null) {
                TipoTela.update($scope.tipoTela, onSaveFinished);
            } else {
                TipoTela.save($scope.tipoTela, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
