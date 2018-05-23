$( document ).ready(function() {

    // GET REQUEST
    $("#getAllCustomerId").click(function(event){
        event.preventDefault();
        ajaxGet();
    });

    // DO GET
    function ajaxGet(){
        $.ajax({
            type : "GET",
            url : window.location + "liba/books",
            success: function(response){
                if(response.status=="Ok" ){
                    $('#getResultDiv ul').empty();
                    var custList = "";

                    $.each(response.books, function(i, book){
                        var book = "- Customer with Id = " + i + ", firstname = " + book.title + "<br>";
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
        });
    }
})