'use strict';

angular.module('importcolexApp')
    .factory('TelaCruda', function ($resource, DateUtils) {
        return $resource('api/telaCrudas/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fechaProduccion = DateUtils.convertDateTimeFromServer(data.fechaProduccion);
                    data.horaInicial = DateUtils.convertDateTimeFromServer(data.horaInicial);
                    data.horaFinal = DateUtils.convertDateTimeFromServer(data.horaFinal);
                    data.inicioParoMecanico = DateUtils.convertDateTimeFromServer(data.inicioParoMecanico);
                    data.finParoMecanico = DateUtils.convertDateTimeFromServer(data.finParoMecanico);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
