'use strict';

angular.module('importcolexApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
