$(document).ready(function () {
//SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS


    $(window).scroll(function() {
        if ($(this).scrollTop() > 0) {
            $('.navbar-ar').css('top', '0');
        } else {
            $('.navbar-ar').css('top', '40px');
        }
    });


//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});