angular.module('ingAtmsApp').controller('homeController', ['$scope', 'homeService', '$filter', 'notificationFactory', 'PAGESIZE',
    function homeController($scope, homeService, $filter, notificationFactory, PAGESIZE) {

        $scope.init = function () {
            $scope.cityATMsCurrentPage = 1;
            $scope.city = "";
            $scope.table = false;
            $scope.search = true;
        };

        $scope.init();

        function getCityATMsListSizeSuccessAction(response) {
            $scope.cityATMsPages = 0;
            if (response) {
                $scope.cityATMsSize = response;
                $scope.cityATMsPages = Math.ceil($scope.cityATMsSize / PAGESIZE);
                if($scope.cityATMsSize>0){
                $scope.getCityATMsList();
                }
            } else {
                $scope.cityATMsSize = 0;
                $scope.cityATMsPages = 0;
                $scope.cityATMs = [];
            }
        }

        function getCityATMsListSizeErrorAction(error) {
            notificationFactory.handleError($scope, error);
        }

        $scope.getCityATMsSize = function () {
            homeService.getCityATMsListSize(getCityATMsListSizeSuccessAction, getCityATMsListSizeErrorAction,  $scope.city );
        };


        function getCityATMsListSuccessAction(response) {
            $scope.table = true;
            if (response) {
                $scope.cityATMs = response;
            } else {
                $scope.cityATMs = [];
            }
        }

        function getCityATMsListErrorAction(error) {
            notificationFactory.handleError($scope, error);
        }

        $scope.getCityATMsList = function () {
            homeService.getCityATMsList(getCityATMsListSuccessAction, getCityATMsListErrorAction,  $scope.city, $scope.cityATMsCurrentPage);
        };


        $scope.getCityATMsNextPage = function () {
            $scope.cityATMsCurrentPage++;
            $scope.getCityATMsList();
        };

        $scope.getCityATMsPreviousPage = function () {
            $scope.cityATMsCurrentPage--;
            $scope.getCityATMsList();
        };

        $scope.getCityATMsFirstPage = function () {
            $scope.cityATMsCurrentPage = 1;
            $scope.getCityATMsList();
        };

        $scope.getCityATMsLastPage = function () {
            $scope.cityATMsCurrentPage = $scope.cityATMsPages;
            $scope.getCityATMsList();
        };

        $scope.getCityATMsPaginationBeforeCurrentClass = function () {
            if ($scope.cityATMsCurrentPage == 1) {
                return "inactive";
            } else {
                return "active";
            }
        };

        $scope.getCityATMsPaginationAfterCurrentClass = function () {
            if ($scope.cityATMsCurrentPage == $scope.cityATMsPages) {
                return "inactive";
            } else {
                return "active";
            }
        };

        $scope.getCityATMsClass = function () {
            if ($scope.ongoingPages == undefined || $scope.ongoingPages == 0) {
                return "hide-a";
            } else {
                return "show-a";
            }

        };

    }
]);