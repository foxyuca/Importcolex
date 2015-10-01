'use strict';

angular.module('importcolexApp')
    .controller('OperarioController', function ($scope, Operario, ParseLinks) {
        $scope.operarios = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Operario.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.operarios = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Operario.get({id: id}, function(result) {
                $scope.operario = result;
                $('#deleteOperarioConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Operario.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOperarioConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.operario = {nombre: null, turno: null, referencia: null, id: null};
        };
    });
