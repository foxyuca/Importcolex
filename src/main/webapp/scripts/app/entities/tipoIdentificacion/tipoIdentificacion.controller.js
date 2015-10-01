'use strict';

angular.module('importcolexApp')
    .controller('TipoIdentificacionController', function ($scope, TipoIdentificacion, ParseLinks) {
        $scope.tipoIdentificacions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TipoIdentificacion.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tipoIdentificacions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TipoIdentificacion.get({id: id}, function(result) {
                $scope.tipoIdentificacion = result;
                $('#deleteTipoIdentificacionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TipoIdentificacion.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTipoIdentificacionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.tipoIdentificacion = {nombre: null, id: null};
        };
    });
