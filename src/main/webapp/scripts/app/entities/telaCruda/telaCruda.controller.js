'use strict';

angular.module('importcolexApp')
    .controller('TelaCrudaController', function ($scope, TelaCruda, ParseLinks) {
        $scope.telaCrudas = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TelaCruda.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.telaCrudas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TelaCruda.get({id: id}, function(result) {
                $scope.telaCruda = result;
            });
        };

        $scope.confirmDelete = function (id) {
            TelaCruda.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTelaCrudaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.telaCruda = {peso: null, ancho: null, costo: null, fechaProduccion: null, horaInicial: null, horaFinal: null, turno: null, gramaje: null, inicioParoMecanico: null, finParoMecanico: null, motivoParoMecanico: null, id: null};
        };
    });
