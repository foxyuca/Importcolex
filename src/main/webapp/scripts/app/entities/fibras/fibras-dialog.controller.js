'use strict';

angular.module('importcolexApp').controller('FibrasDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Fibras', 'OrdenCompra', 'InventarioFibras',
        function($scope, $stateParams, $modalInstance, entity, Fibras, OrdenCompra, InventarioFibras) {

        $scope.fibras = entity;
        $scope.ordencompras = OrdenCompra.query();
        $scope.inventariofibrass = InventarioFibras.query();
        $scope.load = function(id) {
            Fibras.get({id : id}, function(result) {
                $scope.fibras = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:fibrasUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.fibras.id != null) {
                Fibras.update($scope.fibras, onSaveFinished);
            } else {
                Fibras.save($scope.fibras, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
