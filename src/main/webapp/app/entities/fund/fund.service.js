(function() {
    'use strict';
    angular
        .module('gatewayApp')
        .factory('Fund', Fund);

    Fund.$inject = ['$resource'];

    function Fund ($resource) {
        var resourceUrl =  'api/funds/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
