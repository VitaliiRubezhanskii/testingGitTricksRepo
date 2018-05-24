$(document).ready(function () {

    // DELETE REQUEST
    $("#deleteCustomerId").click(function(event){
        event.preventDefault();
        ajaxDelete();
    });
    function ajaxDelete() {

        $.ajax({
            type : "DELETE",
            url : "http://localhost:8080/liba/book/2",
            success: function(response){
                if(response.status=="Ok" ){
                    $('#getResultDiv ul').empty();
                    var custList = "";

                    $.each(response.books, function(i, book){
                        var book = "- Deleted = " + i + ", firstname = " + book.title + "<br>";
                        $('#getResultDiv .list-group').append(book)

                    });
                    console.log("Success: ", response);
                }else{
                    $("#getResultDiv").html("<strong>Error</strong>");
                    console.log("Fail: ", response);
                }
            },

            error : function(e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }





        })

    }

})