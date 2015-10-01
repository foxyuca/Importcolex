'use strict';

angular.module('importcolexApp')
    .controller('OrdenCompraController', function ($scope, OrdenCompra, ParseLinks) {
        $scope.ordenCompras = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            OrdenCompra.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.ordenCompras = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            OrdenCompra.get({id: id}, function(result) {
                $scope.ordenCompra = result;
                $('#deleteOrdenCompraConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrdenCompra.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrdenCompraConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.ordenCompra = {ticket: null, cantidad: null, costo: null, aprovada: null, ordenada: null, recibida: null, id: null};
        };
    });
