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



            $('#q-detail-question').empty().val(response.question);
            $('#q-detail-question-sub').empty().val(response.questionSub);
            $('#q-detail-option1').empty().val(response.option1);
            $('#q-detail-option2').empty().val(response.option2);
            $('#q-detail-option3').empty().val(response.option3);
            $('#q-detail-option4').empty().val(response.option4);
            $('#q-detail-option5').empty().val(response.option5);

            $('#question-detail-modal').css('display' , 'flex');


        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
        }
    });

});



//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});