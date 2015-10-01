'use strict';

angular.module('importcolexApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


