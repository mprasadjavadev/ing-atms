angular.module('ingAtmsApp').service('loginService', ['$http', '$cookies', 'BASEURL', function ($http, $cookies, BASEURL) {

    this.login = function (userName, password, loginSuccessAction, loginErrorrAction) {
        $http({
            method: 'POST',
            url: BASEURL + 'login/authenticate',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: {"username": userName, "password": password}
        }).then(function successCallback(response) {
            $cookies.put("ROLE", response.data.ROLE);
            $cookies.put("USERNAME", response.data.userName);
            loginSuccessAction(response);
        }, function errorCallback(error) {
            loginErrorrAction(error);
        });
    };

    this.getLogedInRole = function () {
        return $cookies.get('ROLE');
    };

    this.getLogedInUserName = function () {

        return $cookies.get('USERNAME');
    };

    this.logout = function (logoutSuccessAction, logoutErrorrAction) {
        $http({
            method: 'POST',
            url: BASEURL + 'logout',
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response) {
            $cookies.remove('ROLE');
            $cookies.remove('USERNAME');
            logoutSuccessAction();
        }, function errorCallback(error) {
            logoutErrorrAction(error);
        });
    };

}
]);