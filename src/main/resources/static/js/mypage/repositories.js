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


    if(!confirm("현재 작성된 내용은 저장되지 않습니다.")){
        return;
    }
    $('.create-repository-modal-ar').css('display' , 'none');


});

$(document).on('click', '#create-repo-submit', function(){

        let extractHubName = $('#create-repo-name').val();
        let comment = $('#create-repo-comment').val();


        $.ajax({
            url: '/ai-exam-hub/repository/add', // 서버 URL
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                extractHubName : extractHubName,
                comment : comment
            }),
            success: function(response) {

                if(response < 0){
                    alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
                }else{
                    alert('생성 완료');
                    location.reload();
                }

            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
            }
            });

});

//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});