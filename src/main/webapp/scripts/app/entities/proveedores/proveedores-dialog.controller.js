'use strict';

angular.module('importcolexApp').controller('ProveedoresDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Proveedores', 'OrdenCompra',
        function($scope, $stateParams, $modalInstance, entity, Proveedores, OrdenCompra) {

        $scope.proveedores = entity;
        $scope.ordencompras = OrdenCompra.query();
        $scope.load = function(id) {
            Proveedores.get({id : id}, function(result) {
                $scope.proveedores = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:proveedoresUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.proveedores.id != null) {
                Proveedores.update($scope.proveedores, onSaveFinished);
            } else {
                Proveedores.save($scope.proveedores, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
