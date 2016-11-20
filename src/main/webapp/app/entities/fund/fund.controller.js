(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('FundController', FundController);

    FundController.$inject = ['$scope', '$state', 'Fund'];

    function FundController ($scope, $state, Fund) {
        var vm = this;
        
        vm.funds = [];

        loadAll();

        function loadAll() {
            Fund.query(function(result) {
                vm.funds = result;
            });
        }
    }
})();
