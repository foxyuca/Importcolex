'use strict';

angular.module('importcolexApp')
    .factory('FibrasTelaCruda', function ($resource, DateUtils) {
        return $resource('api/fibrasTelaCrudas/:id', {}, {
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
