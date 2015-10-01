'use strict';

angular.module('importcolexApp')
    .factory('OrdenCompra', function ($resource, DateUtils) {
        return $resource('api/ordenCompras/:id', {}, {
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
