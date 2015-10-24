'use strict';

angular.module('importcolexApp')
    .controller('FibrasTelaCrudaController', function ($scope, FibrasTelaCruda, ParseLinks) {
        $scope.fibrasTelaCrudas = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            FibrasTelaCruda.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.fibrasTelaCrudas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            FibrasTelaCruda.get({id: id}, function(result) {
                $scope.fibrasTelaCruda = result;
                $('#deleteFibrasTelaCrudaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            FibrasTelaCruda.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFibrasTelaCrudaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.fibrasTelaCruda = {referencia: null, peso: null, id: null, cantidadUsada: null};
        };
    });
