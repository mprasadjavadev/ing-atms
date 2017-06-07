angular.module('ingAtmsApp', ['oc.lazyLoad', 'ui.router', 'directive.loading', 'ngCookies', 'ngExDialog'])
/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
    .config(['$ocLazyLoadProvider', function ($ocLazyLoadProvider) {
        $ocLazyLoadProvider.config({
            // global configs go here
        });
    }
    ])
    //Dialog popups default settings.
    .config(['exDialogProvider', function (exDialogProvider) {
        exDialogProvider.setDefaults({
            template: './views/tpl/commonDialog.html', //from cache
            //template: 'ngExDialog/commonDialog_0.html', //from file
            width: '330px',
            //Below items are set within the provider. Any value set here will overwrite that in the provider.
            //closeByXButton: true,
            //closeByClickOutside: true,
            //closeByEscKey: true,
            //appendToElement: '',
            //beforeCloseCallback: '',
            //grayBackground: true,
            //cacheTemplate: true,
            draggable: false,
            //animation: true,
            //messageTitle: 'Information',
            //messageIcon: 'info',
            //messageCloseButtonLabel: 'OK',
            //confirmTitle: 'Confirmation',
            //confirmIcon: 'question',
            //confirmActionButtonLabel: 'Yes',
            //confirmCloseButtonLabel: 'No'
        });
    }])
//    .constant('MAXUPLOADSIZE', 8000176)
//    .constant('BASEURL', '/web/') //for ingAtms
    .constant('BASEURL', '/') //for local or web amazon
    .constant('PAGESIZE', 10)
    .constant('RESPONSECODES', {
        notfound: 404,
        badRequest: 400,
        unAuthorized: 401,
        conflict: 409
    })
    .constant('ERORES', {
        notFoundURL: 'No ATMs available for this country.',
    })
    .service('APIInterceptor', ['$injector', '$q', '$rootScope', function ($injector, $q, $rootScope) {
        var service = this;
        service.request = function (config) {
            console.log(config.url);
            console.log(config.headers['Content-Type']);
            if ((config.method === "POST" || config.method === "PATCH") && config.headers['Content-Type'] !== undefined) {
                config.headers['Content-Type'] += "; charset=UTF-8";
            }
            console.log(config.headers['Content-Type']);
            //some config operations
            return config;
        };
        service.response = function (response) {

            return response;
        };
        service.responseError = function (response) {
            if (response.status === 401 || response.status === 403) {
                var loginService = $injector.get('loginService');
                var notificationFactory = $injector.get('notificationFactory');
                if (loginService.getLogedInRole() !== undefined) {

                    function logoutSuccessAction() {
                        $injector.get('$state').go('login');
                    }

                    function logoutErrorrAction(error) {
                        notificationFactory.handleError($rootScope, error);
                    }

                    loginService.logout(logoutSuccessAction, logoutErrorrAction);
                }
                $injector.get('$state').go('login');
            }
            return $q.reject(response);
        };
    }
    ])
    /* Setup Rounting For All Pages */
    .config(['$stateProvider', '$urlRouterProvider', '$httpProvider', function ($stateProvider, $urlRouterProvider, $httpProvider) {

        $httpProvider.interceptors.push('APIInterceptor');
        $urlRouterProvider.otherwise("/login");
        $stateProvider

            .state('login', {
                url: "/login",
                templateUrl: "./views/auth/login.html",
                data: {pageTitle: 'Login'},
                controller: "loginController"
            })
            .state('home', {
                url: "/home",
                templateUrl: "./views/home/home.html",
                data: {pageTitle: 'BackBase ING Atms | Home'},
                controller: "homeController",
                params: {
                    myParam: null
                },
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'ingAtmsApp',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                './module/services/home/homeService.js',
                                './module/controllers/home/homeController.js'
                            ]
                        });
                    }]
                }
            })
    }
    ])
    /* Init global settings and run the app */
    .run(["$rootScope", "$state", function ($rootScope, $state) {
        $rootScope.$state = $state; // state to be accessed from view
    }
    ]);