'use strict'

var module = angular.module('book.controllers', []);

module.controller("bookController", [ "$scope", "bookService",

    function($scope, bookService) {

        $scope.book= {
            id : null,
            title : null,
            author: null,
            isbn: null,
            genre: null
        };

        $scope.skills = [];


        bookService.getBookById(1).then(function(value) {
            console.log(value.data);
        }, function(reason) {
            console.log("error occured");
        }, function(value) {
            console.log("no callback");
        });

        $scope.saveUser = function() {
            $scope.book={
                id : null,
                title : null,
                author: null,
                isbn: null,
                genre: null
            }

            bookService.saveUser($scope.book).then(function() {
                console.log("works");
                bookService.getAllBooks().then(function(value) {
                    $scope.allBooks= value.data;
                }, function(reason) {
                    console.log("error occured");
                }, function(value) {
                    console.log("no callback");
                });

                $scope.books = [];
                $scope.book = {
                    id : null,
                    title : null,
                    author: null,
                    isbn: null,
                    genre: null
                };
            }, function(reason) {
                console.log("error occured");
            }, function(value) {
                console.log("no callback");
            });
        }
    } ]);