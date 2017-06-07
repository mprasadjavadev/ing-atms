angular.module('ingAtmsApp').factory('notificationFactory', ['exDialog',
    'RESPONSECODES', 'ERORES',
    function (exDialog,
              RESPONSECODES, ERORES) {
        var notificationFactory = {};

        notificationFactory.handleError = function (scope, error) {
            console.log("error" + JSON.stringify(error));
            var responseError = "";
            if (error.status === undefined) {
                responseError = error;
            }else if (error.status === RESPONSECODES.unAuthorized) {
                responseError = error.status + ": " + error.data.errorMessage;
            } else if (error.status === RESPONSECODES.notfound) {
                responseError = error.status + ": " + ERORES.notFoundURL;
            } else if (error.data.error !== undefined) {
                responseError = error.status + ": " + error.data.error;
            } else if (error.data.message !== undefined) {
                responseError = error.status + ": " + error.data.message;
            }
            exDialog.openMessage(scope, responseError, "Error", "error");
        };

        notificationFactory.handleWarning = function (scope, warning) {

            exDialog.openMessage(scope, warning, "Warning", "warning");
        };

        notificationFactory.handleInfo = function (scope, info) {
            exDialog.openMessage(scope, info);
        };

        notificationFactory.handleConfirm = function (scope, info, confirmFunctionAcction) {
            exDialog.openConfirm(scope, info).then(function (value) {
                confirmFunctionAcction();
            });
        };


        return notificationFactory;
    }
]);