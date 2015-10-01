'use strict';

angular.module('importcolexApp')
    .factory('Proveedores', function ($resource, DateUtils) {
        return $resource('api/proveedoress/:id', {}, {
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
