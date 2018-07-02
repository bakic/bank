describe('operations controller tests', function(){

    beforeEach(module('bankApp'));

    var $controller;

    var accountService = {
        getAccounts : function(success, error){
            return {};
        },
    
        getAccount : function(accountId, success, error){
            return {};
        }
    };

    var operationsService = {
        getOperations : function(accountId, success, error){
            success();
        },
    
        deposit : function(accountId, params, success, error){
            success();
        },
    
        withdraw : function(accountId, params, success, error){
            success();
        }
    };

    var operationsServiceFail = {
        getOperations : function(accountId, success, error){
            error();
        },
    
        deposit : function(accountId, params, success, error){
            error();
        },
    
        withdraw : function(accountId, params, success, error){
            error({status: 409});
        }
    };

    beforeEach(inject(function(_$controller_){
        $controller = _$controller_;
    }));

    describe('Alert', function () {
		it('Alert should be closed', function () {
            var $scope = {};
            var controller = $controller('operationsController', 
                { 
                    $scope: $scope,
                    accountService: accountService,
                    operationsService: operationsService
                }
            );
            $scope.displayedAlert = { type: 'danger', msg: '' };
            
            $scope.closeAlert();

			expect($scope.displayedAlert).toBeUndefined();
		});	
    });
    
    describe('Operations', function(){
        it('Operation with negative amount should fail', function(){
            var $scope = {};
            var controller = $controller('operationsController', 
                { 
                    $scope: $scope,
                    accountService: accountService,
                    operationsService: operationsService
                }
            );
            $scope.params= {amount: -6};

            $scope.executeOperation();

            expect($scope.displayedAlert.msg).toBe('The amount should be a positive number');
        });

        it('Operation with undefined type should fail', function(){
            var $scope = {};
            var controller = $controller('operationsController', 
                { 
                    $scope: $scope,
                    accountService: accountService,
                    operationsService: operationsService
                }
            );
            $scope.params= {amount: 10};
            $scope.selectedOperation = 'UNDEF';

            $scope.executeOperation();

            expect($scope.displayedAlert.msg).toBe('Operation not supported');
        });

        it('Deposit should succeed', function(){
            var $scope = {};
            var controller = $controller('operationsController', 
                { 
                    $scope: $scope,
                    accountService: accountService,
                    operationsService: operationsService
                }
            );
            $scope.params= {amount: 10};
            $scope.selectedOperation = 'DEPOSIT';

            $scope.executeOperation();

            expect($scope.displayedAlert.msg).toBe('The money was saved successfully');
        });

        it('Deposit should fail', function(){
            var $scope = {};
            
            var controller = $controller('operationsController', 
                { 
                    $scope: $scope,
                    accountService: accountService,
                    operationsService: operationsServiceFail
                }
            );
            $scope.params= {amount: 10};
            $scope.selectedOperation = 'DEPOSIT';

            $scope.executeOperation();

            expect($scope.displayedAlert.msg).toBe('An error occurred');
        });

        it('Withdrawal should succeed', function(){
            var $scope = {};
            var controller = $controller('operationsController', 
                { 
                    $scope: $scope,
                    accountService: accountService,
                    operationsService: operationsService
                }
            );
            $scope.params= {amount: 10};
            $scope.selectedOperation = 'WITHDRAW';

            $scope.executeOperation();

            expect($scope.displayedAlert.msg).toBe('The money was retrieved successfully');
        });

        it('Withdrawal should fail', function(){
            var $scope = {};
            var controller = $controller('operationsController', 
                { 
                    $scope: $scope,
                    accountService: accountService,
                    operationsService: operationsServiceFail
                }
            );
            $scope.params= {amount: 10};
            $scope.selectedOperation = 'WITHDRAW';

            $scope.executeOperation();

            expect($scope.displayedAlert.msg).toBe('An error occurred');
        });
        
    })
})