var as = angular.module('searchApp', []);
as.controller('SearchController', function ($scope, $rootScope, $http, $location) {

    $scope.search = 'message:*yolo*';

//    $scope.addAssignment = function () {
//
//        console.log('adding assignment: ' + $scope.newTeam + ' role: ' + $scope.newRole);
//
//        //todo: this url can be generated using the createLink tag (well, the gsp part)
//        $http.post('/PDR/user/addAssignment/${userInstance.id}?newTeam=' + $scope.newTeam + '&newRole=' + $scope.newRole)
//            .success(function (data, status, headers, config) {
//                var dataString = JSON.stringify(data);
//                if (dataString.indexOf('Error:') == 1) {
//                    $scope.errorString = dataString.substring(7).replace(/"/g, '');
//                }
//                else {
//                    load();
//
//                }
//            }).error(function (data, status, headers, config) {
//                $scope.errorString = "There was a problem adding the assignment.";
//            });
//    };
//
//    $scope.removeAssignment = function (assignmentId) {
//
//        console.log('remove assignment: ' + assignmentId);
//
//        $http.post('/PDR/user/removeAssignment/' + assignmentId)
//            .success(function (data, status, headers, config) {
//                load();
//            }).error(function (data, status, headers, config) {
//                $scope.errorString = "There was a problem adding the assignment.";
//            });
//    };

    $scope.performSearch = function () {
        console.log('search: ' + $scope.search);
        $http.get('/kafka-project/tweet/doSearchTweets/?message=' + $scope.search)
            .success(function (data, status, headers, config) {
                $scope.tweetResults = data
            });
        $scope.errorString = '';
    }

    var doSearch = function () {

        console.log('search: ' + $scope.search);
        if ($scope.search) {
            $http.get('/kafka-project/tweet/doSearchTweets/?message=' + $scope.search)
                .success(function (data, status, headers, config) {
                    $scope.tweetResults = data
                });
            $scope.errorString = '';
        }
        else {
            $http.get('/kafka-project/tweet/doSearchTweets/')
                .success(function (data, status, headers, config) {
                    $scope.tweetResults = data
                });
            $scope.errorString = '';
        }
    };


    doSearch();


});
