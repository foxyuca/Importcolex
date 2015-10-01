'use strict';

angular.module('importcolexApp')
    .controller('InventarioFibrasController', function ($scope, InventarioFibras, ParseLinks) {
        $scope.inventarioFibrass = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InventarioFibras.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.inventarioFibrass = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InventarioFibras.get({id: id}, function(result) {
                $scope.inventarioFibras = result;
                $('#deleteInventarioFibrasConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InventarioFibras.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInventarioFibrasConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.inventarioFibras = {lote: null, inventarioInicial: null, inventarioFinal: null, costo: null, id: null};
        };
    });
