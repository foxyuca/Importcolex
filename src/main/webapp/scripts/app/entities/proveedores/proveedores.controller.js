'use strict';

angular.module('importcolexApp')
    .controller('ProveedoresController', function ($scope, Proveedores, ParseLinks) {
        $scope.proveedoress = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Proveedores.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.proveedoress = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Proveedores.get({id: id}, function(result) {
                $scope.proveedores = result;
                $('#deleteProveedoresConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Proveedores.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProveedoresConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.proveedores = {nombre: null, identificacion: null, direccion: null, telefono: null, email: null, id: null};
        };
    });
