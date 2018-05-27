'use strict'

var booksApp = angular.module('book', [ 'ui.bootstrap', 'book.controllers','book.services' ]);

booksApp.constant("CONSTANTS",

    {
        getBookById : "/liba/book/",
        getAllBooks : "/liba/books",
        saveBook : "/liba/books"
    });