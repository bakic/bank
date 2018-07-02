angular.module("bankApp")
.service('accountService', function($http) {

    this.getAccounts = function(successCallback, errorCallback){
        $http.get('/api/account').then(successCallback).catch(errorCallback);
    },

    this.getAccount = function(accountId, successCallback, errorCallback){
        return $http.get('/api/account/' + accountId).then(successCallback).catch(errorCallback);
    }

});