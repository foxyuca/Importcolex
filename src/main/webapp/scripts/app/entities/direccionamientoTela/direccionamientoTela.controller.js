'use strict';

angular.module('importcolexApp')
    .controller('DireccionamientoTelaController', function ($scope, DireccionamientoTela, ParseLinks) {
        $scope.direccionamientoTelas = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DireccionamientoTela.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.direccionamientoTelas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DireccionamientoTela.get({id: id}, function(result) {
                $scope.direccionamientoTela = result;
                $('#deleteDireccionamientoTelaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DireccionamientoTela.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDireccionamientoTelaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.direccionamientoTela = {nombre: null, id: null};
        };
    });
