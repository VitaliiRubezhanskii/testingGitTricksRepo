app.service('bookService',['$http',function ($http) {

    this.getBooks=function getBppks() {
        return $http({

            method: 'GET',
            url: 'http://localhost:8080/liba/books'
        });
    }



    this.saveBook=function saveBook() {
        return $http({
            method: 'POST',
            url:'http://localhost:8080/liba/books'


            })
    }
}])