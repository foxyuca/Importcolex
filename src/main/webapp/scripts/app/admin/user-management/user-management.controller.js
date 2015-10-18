'use strict';

angular.module('importcolexApp')
    .controller('UserManagementController', function ($scope, $rootScope, User, Authority, ParseLinks, Language) {
        $scope.users = [];
        $scope.authorities = [];
        Language.getAll().then(function (languages) {
            $scope.languages = languages;
        });

        $scope.page = 0;
        $scope.loadAll = function () {
            User.query({page: $scope.page, per_page: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.users = result;
            });
        };
        
        $scope.loadAllAuthorities = function () {
        	Authority.query({page: $scope.page, per_page: 20}, function (result, headers) {
                $scope.authorities = result;
            });
        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
        $scope.loadAllAuthorities();
        
        $scope.setActive = function (user, isActivated) {
            user.activated = isActivated;
            User.update(user, function () {
                $scope.loadAll();
                $scope.clear();
            });
        };

        $scope.showUpdate = function (login) {
            User.get({login: login}, function (result) {
                $scope.user = result;
                $('#saveUserModal').modal('show');
            });
        };

        $scope.save = function () {
            User.update($scope.user,
                function () {
                    $scope.refresh();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveUserModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.user = {
                id: null, login: null, firstName: null, lastName: null, email: null,
                activated: null, langKey: null, createdBy: null, createdDate: null,
                lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                resetKey: null, authorities: null
            };
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
