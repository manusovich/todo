angular.module('starter.controllers', [])

    .controller('TodoListCtrl', function ($scope, $location, $ionicLoading) {
        $scope.data = {};

        $scope.newTodoPage = function () {
            $location.path("/new-todo");
        };

        $scope.clearSearch = function() {
            $scope.data.searchQuery = '';
        };

        $ionicLoading.show({
            template: 'Loading...'
        });

        $.ajax({
            type: "GET",
            url: "/api/todo/",
            headers: {
                Accept: "application/json; charset=utf-8"
            },
            success: function (data) {
                $ionicLoading.hide();
                $scope.todoList = data;
                $scope.$apply(function () {
                    $location.path("/todo-list");
                });
            },
            error: function (data) {
                $ionicLoading.hide();
                alert(data.responseText);
            }
        });
    })

    .controller('DetailedTodoCtrl', function ($scope, $location, $stateParams, $ionicLoading) {
        $scope.update = function() {
            $ionicLoading.show({
                template: 'Update...'
            });
            $.ajax({
                type: "PUT",
                url: "/api/todo/",
                contentType: "application/json; charset=utf-8",
                data: angular.toJson($scope.todo),
                success: function () {
                    $ionicLoading.hide();
                    $scope.$apply(function () {
                        $location.path("/todo-list");
                    });
                },
                error: function (data) {
                    $ionicLoading.hide();
                    alert(data.responseText);
                }
            });
        };

        $scope.remove = function() {
            $ionicLoading.show({
                template: 'Delete...'
            });
            $.ajax({
                type: "DELETE",
                url: "/api/todo/" + $stateParams.todoId,
                success: function () {
                    $ionicLoading.hide();
                    $scope.$apply(function () {
                        $location.path("/todo-list");
                    });
                },
                error: function (data) {
                    $ionicLoading.hide();
                    alert(data.responseText);
                }
            });
        };

        $.ajax({
            type: "GET",
            url: "/api/todo/" + $stateParams.todoId,
            headers: {
                Accept: "application/json; charset=utf-8"
            },
            success: function (data) {
                $scope.todo = data;
                $scope.$apply(function () {
                    $location.path("/todo-detail/" + $stateParams.todoId);
                });
            },
            error: function (data) {
                alert(data.responseText);
            }
        });
    })

    .controller('SignOnCtrl', function ($scope, $location) {
        $scope.auth = {"phone": readCookie("MASHAPE_TODO_SESSION_PHONE")};
        $scope.submitQuery = function () {
            if ($scope.auth !== null) {
                var signOnParams = {
                    "phone": $scope.auth.phone
                };
                $.ajax({
                    type: "POST",
                    url: "/api/session/",
                    contentType: "application/json; charset=utf-8",
                    data: angular.toJson(signOnParams),
                    success: function () {
                        $scope.$apply(function () {
                            $location.path("/todo-list");
                        });
                    },
                    error: function (data) {
                        alert(data.responseText);
                    }
                });
            }
        };
        $scope.doAuth = $scope.submitQuery;
    })


    .controller('NewTodoCtrl', function ($scope, $location, $ionicLoading) {
        $scope.todo = {};
        $scope.doCreateNew = function () {
            $ionicLoading.show({
                template: 'Create...'
            });
            var params = {
                "title": $scope.todo.title,
                "body": $scope.todo.body
            };
            $.ajax({
                type: "POST",
                url: "/api/todo/",
                contentType: "application/json; charset=utf-8",
                data: angular.toJson(params),
                success: function () {
                    $ionicLoading.hide();
                    $scope.$apply(function () {
                        $location.path("/todo-list");
                    });
                },
                error: function (data) {
                    $ionicLoading.hide();
                    alert(data.responseText);
                }
            });
        }
    });

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

