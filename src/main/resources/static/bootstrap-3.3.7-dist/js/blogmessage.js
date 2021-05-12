$(function () {
    function add() {
        $.ajax({
            url: "/addcomment",
            sucess :function (data) {
                console.log(data);
                alert("经过了这里");
            },
            error: function (e) {
                console.log(e);
                alert("经过了这里1");
            }
        })

    }


})