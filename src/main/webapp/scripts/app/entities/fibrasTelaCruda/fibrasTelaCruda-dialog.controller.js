'use strict';

angular.module('importcolexApp').controller('FibrasTelaCrudaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FibrasTelaCruda', 'InventarioFibras', 'TelaCruda','AlertService',
        function($scope, $stateParams, $modalInstance, entity, FibrasTelaCruda, InventarioFibras, TelaCruda,AlertService) {

        $scope.fibrasTelaCruda = entity;
        $scope.inventariofibrass = InventarioFibras.query();
        $scope.telacrudas = TelaCruda.query();
        $scope.load = function(id) {
            FibrasTelaCruda.get({id : id}, function(result) {
                $scope.fibrasTelaCruda = result;
            });
        };
        $scope.selectedInventary = function () {
        	InventarioFibras.get({id: $scope.fibrasTelaCruda.inventarioFibrasId}, function(result) {
                $scope.inventarioSelected = result;
                AlertService.warning("global.messages.warning.verifyQuantityUsed",{param: $scope.fibrasTelaCruda.cantidadUsada});
            });
        };
        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:fibrasTelaCrudaUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
        	if($scope.fibrasTelaCruda.cantidadUsada > $scope.inventarioSelected.inventarioFinal){
        		addErrorAlert("global.messages.error.usedQuantityGreater",{param: $scope.fibrasTelaCruda.cantidadUsada})
        		$modalInstance.close($scope.fibrasTelaCruda.cantidadUsada);
        	}else{
        		if ($scope.fibrasTelaCruda.id != null) {
                    FibrasTelaCruda.update($scope.fibrasTelaCruda, onSaveFinished);
                } else {
                    FibrasTelaCruda.save($scope.fibrasTelaCruda, onSaveFinished);
                }
        	}
            
        };
        
        var addErrorAlert = function (keyMessage, param) {
            AlertService.error(keyMessage, param);
        }

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
  
    
    }]);


