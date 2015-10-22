'use strict';

angular.module('importcolexApp').controller('InventarioFibrasDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InventarioFibras', 'Fibras', 'FibrasTelaCruda','OrdenCompra',
        function($scope, $stateParams, $modalInstance, entity, InventarioFibras, Fibras, FibrasTelaCruda,OrdenCompra) {

        $scope.inventarioFibras = entity;
        $scope.fibrass = Fibras.query();
        $scope.ordenCompra = OrdenCompra.query();
        $scope.fibrastelacrudas = FibrasTelaCruda.query();
        $scope.load = function(id) {
            InventarioFibras.get({id : id}, function(result) {
                $scope.inventarioFibras = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:inventarioFibrasUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.inventarioFibras.id != null) {
                InventarioFibras.update($scope.inventarioFibras, onSaveFinished);
            } else {
                InventarioFibras.save($scope.inventarioFibras, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
