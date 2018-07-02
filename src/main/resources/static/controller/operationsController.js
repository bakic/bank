angular.module("bankApp")
.controller('operationsController', function($scope, accountService, operationsService){


    $scope.accounts = [];
    $scope.selectedAccountId = '';
    $scope.selectedOperation = '';
    $scope.operations = ['WITHDRAW', 'DEPOSIT'];

    $scope.params = {
        amount: 0
    };

    var errorAlert = { type: 'danger', msg: '' };
    var successAlert = { type: 'success', msg: '' };
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

    init();

    /**
     * Call the withdrawal money service.
     */
    var callWithdraw = function(){

        var success = function(response){
            successAlert.msg = 'The money was retrieved successfully';
            $scope.displayedAlert = successAlert;
        };
        
        var error = function(error){
            if(error.status == '400'){
                errorAlert.msg = 'The amount to retrieve is bigger than the current balance';
            } else{
                errorAlert.msg = 'An error occurred';
            }
            $scope.displayedAlert = errorAlert;
        }

        operationsService.withdraw($scope.selectedAccountId, $scope.params, success, error);
    };

    /**
     * Call the deposit service
     */
    var callDeposit = function(){

        var success = function(response){
            successAlert.msg = 'The money was saved successfully';
            $scope.displayedAlert = successAlert;
        };
        
        var error = function(error){
            errorAlert.msg = 'An error occurred';
            $scope.displayedAlert = errorAlert;
        }

        operationsService.deposit($scope.selectedAccountId, $scope.params, success, error);
    };

    /**
     * Excecute the operation of withdrawal or deposit.
     */
    $scope.executeOperation = function(){

        if($scope.params.amount<= 0){
            errorAlert.msg = 'The amount should be a positive number';
            $scope.displayedAlert = errorAlert;
        } else {
            if($scope.selectedOperation === 'WITHDRAW'){
                callWithdraw();
            } else if($scope.selectedOperation === 'DEPOSIT'){
                callDeposit()
            } else {
                errorAlert.msg = 'Operation not supported';
                $scope.displayedAlert = errorAlert;
            }
        }
    };

    /**
     * Close the alert box.
     */
    $scope.closeAlert = function() {
        $scope.displayedAlert = undefined;
    };

});