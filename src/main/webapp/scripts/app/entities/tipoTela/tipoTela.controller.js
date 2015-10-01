'use strict';

angular.module('importcolexApp')
    .controller('TipoTelaController', function ($scope, TipoTela, ParseLinks) {
        $scope.tipoTelas = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TipoTela.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tipoTelas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TipoTela.get({id: id}, function(result) {
                $scope.tipoTela = result;
                $('#deleteTipoTelaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TipoTela.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTipoTelaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.tipoTela = {nombre: null, id: null};
        };
    });
