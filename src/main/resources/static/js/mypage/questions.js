$(document).ready(function () {
//SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS


$(document).on('click', '.question-ele', function(){

    let questionCode = $(this).data('questioncode');
    let questiontitle = $(this).data('questiontitle');


    $.ajax({
        url: '/ai-exam-hub/question/'+questionCode, // 서버 URL
        method: 'GET',
        success: function(response) {

            console.log(response);

        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
        }
    });

});



//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});