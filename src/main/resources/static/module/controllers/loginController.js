angular.module('ingAtmsApp').controller('loginController', ['$scope', '$rootScope', 'loginService', '$state', 'notificationFactory',
    function loginController($scope, $rootScope, loginService, $state, notificationFactory) {

        function loginSuccessAction(response) {
            if (response.status === 401) {
                $scope.loginFailureAction("User name or passord are wrong due to: " + response);
            } else {
                $rootScope.logedInUserName = loginService.getLogedInUserName();
                if (response.ROLE !== "") {
                    switch (response.data.ROLE) {
                        case "ADMIN":
                            $state.go("home");
                            console.log("$rootScope.logedInUserName");
                            break;
                        default:
                            $state.go("login");
                    }
                }
            }
        }

        function loginErrorrAction(error) {
            notificationFactory.handleError($scope, error);
        }

        function logoutSuccessAction() {
            $state.go("login");
        }

        function logoutErrorrAction(error) {
            notificationFactory.handleError($scope, error);
        }

        $scope.login = function () {
            loginService.login($scope.userName, $scope.password, loginSuccessAction, loginErrorrAction);
        };

        $scope.logout = function () {
            loginService.logout(logoutSuccessAction, logoutErrorrAction);
        };

    }
]);