$(document).ready(function () {
//SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS

$(document).on('click', '#q-d-close-btn', function(){
    $('#question-detail-modal').css('display' , 'none');


});


$(document).on('click', '#question-edit-btn', function(){


    let questionCode = $('#question-code').val();

    let question = $('#q-detail-question').val();
    let questionSub = $('#q-detail-question-sub').val();
    let option1 = $('#q-detail-option1').val();
    let option2 = $('#q-detail-option2').val();
    let option3 = $('#q-detail-option3').val();
    let option4 = $('#q-detail-option4').val();
    let option5 = $('#q-detail-option5').val();
    let comment = $('#q-detail-comment').val();

    $.ajax({
        url: '/ai-exam-hub/question/edit', // 서버 URL
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            extractQuestionCode : questionCode,
            question : question ,
            questionSub : questionSub,
            option1 : option1 ,
            option2 : option2,
            option3 : option3 ,
            option4 : option4,
            option5 : option5 ,
            comment: comment
        }),
        success: function(response) {

            if(response < 0){
                alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
            }else{
                alert('수정 완료');
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