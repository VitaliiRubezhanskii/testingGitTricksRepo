'use strict'

angular.module('book.services', []).factory('bookService',
    [ "$http", "CONSTANTS", function($http, CONSTANTS) {
        var service = {};

        service.getBookById = function(bookId) {
            var url = CONSTANTS.getBookById + bookId;
            return $http.get(url);
        }

        service.getAllBooks = function() {
            return $http.get(CONSTANTS.getAllBooks);
        }

        service.saveBook = function(book) {
            return $http.post(CONSTANTS.saveBook, book);
        }
        return service;
    } ]);