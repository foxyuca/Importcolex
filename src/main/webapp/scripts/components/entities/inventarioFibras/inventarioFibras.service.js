'use strict';

angular.module('importcolexApp')
    .factory('InventarioFibras', function ($resource, DateUtils) {
        return $resource('api/inventarioFibrass/:id', {}, {
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
