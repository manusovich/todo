angular.module('starter.services', [])

    .factory('TodoList', function () {
        var todoRecords = [];

        return {
            set: function (data) {
                todoRecords = data;
            },
            all: function () {
                return todoRecords;
            },
            get: function (todoId) {
                // Simple index lookup
                return todoRecords[todoId];
            }
        }
    });
