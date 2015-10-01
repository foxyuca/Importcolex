'use strict';

angular.module('importcolexApp')
    .factory('Clientes', function ($resource, DateUtils) {
        return $resource('api/clientess/:id', {}, {
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
