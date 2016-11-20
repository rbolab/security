(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fund', {
            parent: 'entity',
            url: '/fund',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Funds'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fund/funds.html',
                    controller: 'FundController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('fund-detail', {
            parent: 'entity',
            url: '/fund/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fund'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fund/fund-detail.html',
                    controller: 'FundDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Fund', function($stateParams, Fund) {
                    return Fund.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fund',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fund-detail.edit', {
            parent: 'fund-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fund/fund-dialog.html',
                    controller: 'FundDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fund', function(Fund) {
                            return Fund.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fund.new', {
            parent: 'fund',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fund/fund-dialog.html',
                    controller: 'FundDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                fees: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fund', null, { reload: 'fund' });
                }, function() {
                    $state.go('fund');
                });
            }]
        })
        .state('fund.edit', {
            parent: 'fund',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fund/fund-dialog.html',
                    controller: 'FundDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fund', function(Fund) {
                            return Fund.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fund', null, { reload: 'fund' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fund.delete', {
            parent: 'fund',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fund/fund-delete-dialog.html',
                    controller: 'FundDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Fund', function(Fund) {
                            return Fund.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fund', null, { reload: 'fund' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
