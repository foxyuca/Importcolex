'use strict';

angular.module('importcolexApp')
    .factory('Operario', function ($resource, DateUtils) {
        return $resource('api/operarios/:id', {}, {
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
