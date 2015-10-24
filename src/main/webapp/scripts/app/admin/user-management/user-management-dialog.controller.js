'use strict';

angular.module('importcolexApp').controller('UserManagementDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'User', 'Authority','Language',
        function($scope, $stateParams, $modalInstance, entity, User, Authority,Language) {

        $scope.user = entity;
        $scope.authority = Authority.query();
        $scope.language = Language.query();
        $scope.load = function(id) {
        	User.get({id : id}, function(result) {
                $scope.user = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('importcolexApp:user-managementUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.user.id != null) {
            	User.update($scope.user, onSaveFinished);
            } else {
            	User.save($scope.user, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
