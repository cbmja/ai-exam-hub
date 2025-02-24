$(document).ready(function () {
//SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS


$(document).on('click', '#repo-search-btn', function(){

    let search = $('#repo-search-input').val();
    window.location.href='/ai-exam-hub/mypage/repository?search='+search;

});


$(document).on('click', '.repo-element', function(){

    let hubCode = $(this).data('hubcode');

    window.location.href='/ai-exam-hub/mypage/repository/'+hubCode;



});

//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});