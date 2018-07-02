describe('History controller tests', function(){
    beforeEach(module('bankApp'));

    var $controller;
    var operations = {"total":4,"operations":[{"operationDate":"2018-07-01T00:00:00","amount":3.0,"type":"DEPOSIT"},{"operationDate":"2018-07-01T00:00:00","amount":20.0,"type":"DEPOSIT"},{"operationDate":"2018-07-01T00:00:00","amount":7.0,"type":"DEPOSIT"},{"operationDate":"2018-07-01T00:00:00","amount":5.0,"type":"DEPOSIT"}]};

    var accountService = {
        getAccounts : function(success, error){
            success({data: {}});
        },
    
        getAccount : function(accountId, success, error){
            success({data: {}});
        }
    };

    var accountServiceFail = {
        getAccounts : function(success, error){
            error();
        },
    
        getAccount : function(accountId, success, error){
            error();
        }
    };

    var operationsService = {
        getOperations : function(accountId, success, error){
            success({data: operations});
        },
    
        deposit : function(accountId, params, success, error){
            success();
        },
    
        withdraw : function(accountId, params, success, error){
            success();
        }
    };


    beforeEach(inject(function(_$controller_){
        $controller = _$controller_;
    }));

    describe('Alert', function () {
		it('Alert should be closed', function () {
            var $scope = {};
            var controller = $controller('historyController', 
                { 
                    $scope: $scope,
                    accountService: accountServiceFail,
                    operationsService: operationsService
                }
            );
            $scope.displayedAlert = { type: 'danger', msg: '' };
            
            $scope.closeAlert();

			expect($scope.displayedAlert).toBeUndefined();
		});	
    });

    describe('Account', function () {
		it('Fetch account should fail', function () {
            var $scope = {};
            var controller = $controller('historyController', 
                { 
                    $scope: $scope,
                    accountService: accountServiceFail,
                    operationsService: operationsService
                }
            );
            $scope.selectedAccountId = 1;
            $scope.accountChange();
            
			expect($scope.displayedAlert.msg).toBe('An error occurred while fetching account');
		});
    });

    describe('Account', function () {
		it('Fetch account should succeed', function () {
            var $scope = {};
            var controller = $controller('historyController', 
                { 
                    $scope: $scope,
                    accountService: accountService,
                    operationsService: operationsService
                }
            );
            $scope.selectedAccountId = 1;
            $scope.accountChange();
            
            expect($scope.history).toBeDefined();
            expect($scope.history.total).toBe(4);
            expect($scope.history.operations).toBeDefined();
            expect($scope.history.operations.length).toBe(4);
		});
    });
});