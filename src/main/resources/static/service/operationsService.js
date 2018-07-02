angular.module("bankApp")
.service('operationsService', function($http) {

    this.getOperations = function(accountId, successCallback, errorCallback){
        return $http.get('/api/history?accountId=' + accountId).then(successCallback).catch(errorCallback);;
    },

    this.deposit = function(accountId, params, successCallback, errorCallback){
        return $http.post('/api/deposit/' + accountId, params).then(successCallback).catch(errorCallback);;
    },

    this.withdraw = function(accountId, params, successCallback, errorCallback){
        return $http.post('/api/retrieve/' + accountId, params).then(successCallback).catch(errorCallback);;
    }

});