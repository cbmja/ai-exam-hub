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

$(document).on('click', '#create-repository', function(){

    $('.create-repository-modal-ar').css('display' , 'flex');

    $('#create-repo-name').val("");
    $('#create-repo-comment').val("");

});

$(document).on('click', '#create-repo-quit', function(){

    $('.create-repository-modal-ar').css('display' , 'none');

});



//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});