'use strict';

angular.module('bankApp', ['ngRoute', 'ui.bootstrap'])
.config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');
    $routeProvider
        // route for the home page
        .when('/', {
            templateUrl : 'views/history.html',
            controller  : 'historyController'
        })

        // route for the operations page
        .when('/operations', {
            templateUrl : 'views/operations.html',
            controller  : 'operationsController'
        })
        $routeProvider.otherwise({redirectTo: '/'});
}]);
