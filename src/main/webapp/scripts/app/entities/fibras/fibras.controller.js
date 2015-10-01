'use strict';

angular.module('importcolexApp')
    .controller('FibrasController', function ($scope, Fibras, ParseLinks) {
        $scope.fibrass = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Fibras.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.fibrass = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Fibras.get({id: id}, function(result) {
                $scope.fibras = result;
                $('#deleteFibrasConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Fibras.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFibrasConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.fibras = {nombre: null, titulo: null, filamentos: null, id: null};
        };
    });
