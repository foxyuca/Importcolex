'use strict';

angular.module('importcolexApp').controller('TipoIdentificacionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TipoIdentificacion', 'Proveedores',
        function($scope, $stateParams, $modalInstance, entity, TipoIdentificacion, Proveedores) {

        $scope.tipoIdentificacion = entity;
        $scope.proveedoress = Proveedores.query();
        $scope.load = function(id) {
            TipoIdentificacion.get({id : id}, function(result) {
                $scope.tipoIdentificacion = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:tipoIdentificacionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.tipoIdentificacion.id != null) {
                TipoIdentificacion.update($scope.tipoIdentificacion, onSaveFinished);
            } else {
                TipoIdentificacion.save($scope.tipoIdentificacion, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
