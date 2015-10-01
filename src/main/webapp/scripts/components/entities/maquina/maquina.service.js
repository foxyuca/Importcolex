'use strict';

angular.module('importcolexApp')
    .factory('Maquina', function ($resource, DateUtils) {
        return $resource('api/maquinas/:id', {}, {
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
