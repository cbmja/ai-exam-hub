$(document).ready(function () {
//SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS

// 02/25 1차 ok-----------------------------------------------------------------------------------------------------
$(document).on('click', '.question-ele', function(){

    let questionCode = $(this).data('questioncode');
    let questiontitle = $(this).data('questiontitle');


    $.ajax({
        url: '/ai-exam-hub/question/'+questionCode, // 서버 URL
        method: 'GET',
        success: function(response) {

            console.log(response);

            if(response.err == 'err'){
                alert('서버 에러입니다.');
                return;
            }

            $('#question-code').val(questionCode);
            $('#question-detail-title').empty().append(questiontitle);


            $('#q-detail-question').val(response.question);
            $('#q-detail-question-sub').val(response.questionSub);
            $('#q-detail-option1').val(response.option1);
            $('#q-detail-option2').val(response.option2);
            $('#q-detail-option3').val(response.option3);
            $('#q-detail-option4').val(response.option4);
            $('#q-detail-option5').val(response.option5);
            $('#q-detail-comment').val(response.comment);
            $('#question-detail-modal').css('display' , 'flex');

            $('.question-detail-modal').animate({ scrollTop: 0 }, 'fast');


        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
        }
    });

});


$(document).on('click', '#add-question-btn', function(){

    let hubCode = $('#exhcode').val();

    window.location.href='/ai-exam-hub/extract/form/'+hubCode;

});



//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});