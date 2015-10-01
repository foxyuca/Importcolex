 'use strict';

angular.module('importcolexApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-importcolexApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-importcolexApp-params')});
                }
                return response;
            },
        };
    });