angular.module("bankApp")
.controller('historyController', function($scope, accountService, operationsService){

    $scope.accounts = [];
    $scope.selectedAccountId = '-1';
    $scope.selectedAccount = undefined;
    $scope.history = [];

    var errorAlert = { type: 'danger', msg: '' };
    $scope.displayedAlert = undefined;
    /**
     * Init method.
     */
    var init = function(){
        var success = function(response){
            $scope.accounts = response.data;
        };
        
        var error = function(error){
            errorAlert.msg = 'An error occurred while fetching accounts';
            $scope.displayedAlert = errorAlert;
        }
        accountService.getAccounts(success, error);
    }

    /**
     * Method called when an account is chosen from the list.
     */
    $scope.accountChange = function(){
        fetchAccount($scope.selectedAccountId);
    };

    var listOperations = function(){
        // Look for the operations
        var success = function(response){
            $scope.history = response.data;
        };
        var error = function(error){
            errorAlert.msg = 'An error occurred';
            $scope.displayedAlert = errorAlert;
        };
        operationsService.getOperations($scope.selectedAccountId, success, error);
    }


    /**
     * Get the account object by its id
     * @param {*} id 
     */
    var fetchAccount = function(accountId){
        if(!accountId || accountId == -1) return;
        var success = function(response){
            $scope.selectedAccount = response.data;
            listOperations();
        };
        var error = function(error){
            errorAlert.msg = 'An error occurred while fetching account';
            $scope.displayedAlert = errorAlert;
        };
        accountService.getAccount(accountId, success, error);
    }

    init();

    /**
     * Close the alert box.
     */
    $scope.closeAlert = function() {
        $scope.displayedAlert = undefined;
    };

});