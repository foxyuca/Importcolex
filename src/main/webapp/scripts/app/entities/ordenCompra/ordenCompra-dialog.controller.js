'use strict';

angular.module('importcolexApp').controller('OrdenCompraDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'OrdenCompra', 'Proveedores', 'Fibras',
        function($scope, $stateParams, $modalInstance, entity, OrdenCompra, Proveedores, Fibras) {

        $scope.ordenCompra = entity;
        $scope.proveedoress = Proveedores.query();
        $scope.fibrass = Fibras.query();
        $scope.load = function(id) {
            OrdenCompra.get({id : id}, function(result) {
                $scope.ordenCompra = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:ordenCompraUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.ordenCompra.id != null) {
                OrdenCompra.update($scope.ordenCompra, onSaveFinished);
            } else {
                OrdenCompra.save($scope.ordenCompra, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
