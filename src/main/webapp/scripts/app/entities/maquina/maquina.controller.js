'use strict';

angular.module('importcolexApp')
    .controller('MaquinaController', function ($scope, Maquina, ParseLinks) {
        $scope.maquinas = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Maquina.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.maquinas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Maquina.get({id: id}, function(result) {
                $scope.maquina = result;
                $('#deleteMaquinaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Maquina.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMaquinaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.maquina = {marca: null, galga: null, diametro: null, rpm: null, agujas: null, id: null};
        };
    });
