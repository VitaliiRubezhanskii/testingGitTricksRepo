$(document).ready(function () {
    $("#customerForm").submit(function (event) {
        event.preventDefault();
        ajaxPost();
    })

    function ajaxPost() {
        //prepare data
        var book={
            title : $("#title").val(),
            author : $("#author").val(),
            isbn : $("#isbn").val(),
            genre : $("#genre").val()
        }
        $.ajax({
            type:"POST",
            contentType:"application/json",
            url: "http://localhost:8080/liba/save",
            data: JSON.stringify(book),
            dataType: 'json',
            success:function (response) {
                if (response.status=="Ok"){

                    $("#postResultDiv").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" +
                        "Post Successfully! <br>" +
                        "---> Book Info: Title = " +
                        response.status + " ,Author = " + response.books + "</p>")
                } else {
                    $("#postResultDiv").html("<strong>Error</strong>")
                }
                console.log(response);
            },
            error: function (error) {
                alert(error);
                console.log(error);
            }



        });

        //reset();

    }
    function reset() {
        $("#title").val(""),
            $("#author").val(""),
            $("#isbn").val(""),
            $("#genre").val("")
    }

})