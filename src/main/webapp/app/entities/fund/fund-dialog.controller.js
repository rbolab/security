(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('FundDialogController', FundDialogController);

    FundDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Fund'];

    function FundDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Fund) {
        var vm = this;

        vm.fund = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fund.id !== null) {
                Fund.update(vm.fund, onSaveSuccess, onSaveError);
            } else {
                Fund.save(vm.fund, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gatewayApp:fundUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
