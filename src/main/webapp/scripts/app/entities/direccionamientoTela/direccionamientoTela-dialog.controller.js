'use strict';

angular.module('importcolexApp').controller('DireccionamientoTelaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DireccionamientoTela', 'TipoTela',
        function($scope, $stateParams, $modalInstance, entity, DireccionamientoTela, TipoTela) {

        $scope.direccionamientoTela = entity;
        $scope.tipotelas = TipoTela.query();
        $scope.load = function(id) {
            DireccionamientoTela.get({id : id}, function(result) {
                $scope.direccionamientoTela = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:direccionamientoTelaUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.direccionamientoTela.id != null) {
                DireccionamientoTela.update($scope.direccionamientoTela, onSaveFinished);
            } else {
                DireccionamientoTela.save($scope.direccionamientoTela, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
