'use strict';

angular.module('importcolexApp')
    .controller('ClientesController', function ($scope, Clientes, ParseLinks) {
        $scope.clientess = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Clientes.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.clientess = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Clientes.get({id: id}, function(result) {
                $scope.clientes = result;
                $('#deleteClientesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Clientes.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteClientesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.clientes = {nombre: null, identificacion: null, telefono: null, correo: null, direccion: null, id: null};
        };
    });
