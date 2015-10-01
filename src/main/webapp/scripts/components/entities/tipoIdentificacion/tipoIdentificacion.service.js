'use strict';

angular.module('importcolexApp')
    .factory('TipoIdentificacion', function ($resource, DateUtils) {
        return $resource('api/tipoIdentificacions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
