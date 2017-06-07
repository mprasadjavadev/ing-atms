angular.module('ingAtmsApp').service('homeService', ['$http', 'BASEURL', 'PAGESIZE', function ($http, BASEURL, PAGESIZE) {

    this.getCityATMsList = function (getCityATMsListSuccessAction, getCityATMsListErrorAction,city, pageNumber) {
        pageNumber--;

        $http({
            method: 'GET',
            url: BASEURL + 'atms/'+city+'?pageNumber=' + pageNumber + '&pageSize=' + PAGESIZE,
            headers: {'Content-Type': 'application/json'}
        }).then(function successCallback(response) {
            getCityATMsListSuccessAction(response.data);
        }, function errorCallback(error) {
            getCityATMsListErrorAction(error);
        });

    };

    this.getCityATMsListSize = function (getCityATMsSizeSuccessFunction, getCityATMsListSizeErrorFunction,city) {

        $http({
            method: 'GET',
            url: BASEURL + 'atms/size/'+city,
        }).then(function successCallback(response) {
            getCityATMsSizeSuccessFunction(response.data);
        }, function errorCallback(error) {
            getCityATMsListSizeErrorFunction(error);
        });

    };
}
]);