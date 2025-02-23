$(document).ready(function () {
//SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS


/* 로그인 모달 띄우기 */
$(document).on('click', '#login-btn', function(){

    $('#login-id').val('');
    $('#login-pw').val('');

    $('.login-form-ar').css('display' , 'flex');
});

/* 로그인 모달 숨기기 */
$(document).on('click', '#login-close-btn', function(){
    $('.login-form-ar').css('display' , 'none');
});

/* 로그인 */
$(document).on('click', '#login-submit', function(){

    let loginId = $('#login-id').val();
    let loginPw = $('#login-pw').val();

    $.ajax({
            url: '/ai-exam-hub/login', // 서버 URL
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                memberId : loginId,
                memberPw : loginPw
            }),
            success: function(response) {

                console.log(response);
                switch (response){

                    case 'success': alert('로그인 성공');

                    $('#login-btn').css('display' , 'none');
                    $('#mypage-btn').css('display' , 'flex');
                    $('.login-form-ar').css('display' , 'none');


                    break;
                    case 'wrongId': alert('존재하지 않는 아이디입니다.'); break;
                    case 'wrongPw': alert('비밀번호가 틀렸습니다.'); break;
                    case 'err': alert('서버 에러입니다.'); break;
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









