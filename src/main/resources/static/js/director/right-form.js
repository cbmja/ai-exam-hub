$(document).ready(function () {
//SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS

    let isDragging = false;
    let offsetX, offsetY;
    let ar = $("#r-f-ar");

    $('.modal-move-btn').mousedown(function (e) {
        isDragging = true;

        // 요소의 현재 위치에서 마우스 클릭 위치까지의 거리 저장
        offsetX = e.clientX - ar.position().left;
        offsetY = e.clientY - ar.position().top;

        ar.css({
            "cursor": "grabbing",
            "position": "fixed", // 항상 뷰포트 기준으로 이동
            "z-index": 9999
        });

        e.preventDefault(); // 기본 이벤트 방지
    });

    $(document).mousemove(function (e) {
        if (!isDragging) return;

        let left = e.clientX - offsetX;
        let top = e.clientY - offsetY;

        let windowWidth = $(window).width();
        let windowHeight = $(window).height();
        let elementWidth = ar.outerWidth();
        let elementHeight = ar.outerHeight();

        // 뷰포트 범위를 벗어나지 않도록 제한
        if (left < 0) left = 0;
        if (top < 0) top = 0;
        if (left + elementWidth > windowWidth) left = windowWidth - elementWidth;
        if (top + elementHeight > windowHeight) top = windowHeight - elementHeight;

        ar.css({
            left: left + "px",
            top: top + "px"
        });
    });

    $(document).mouseup(function () {
        if (!isDragging) return;
        isDragging = false;

        ar.css({
            "cursor": "default"
        });
    });


    /* 모달 숨기기*/
    $(document).on('click', '.modal-hide-btn', function(){
        $('#r-f-ar').css('display' , 'none');

        let sBtn = $('.modal-show-btn');
        sBtn.css('display' , 'flex');

    });

    /* 모달 나타내기*/
    $(document).on('click', '.modal-show-btn', function(){
        $('#r-f-ar').css('display' , 'flex');
        $('.modal-show-btn').css('display' , 'none');
    });



/* 캡쳐
1. 캡쳐 아닌 상황
	1. 아무거나 캡쳐 시작
	2. 캡쳐 종료 클릭

2. 캡쳐중인상황
	1. 현재 캡쳐중인것 다시 클릭
	2. 다른 캡쳐 클릭
	3. 캡쳐 종료 클릭
*/

let isCapturing = false; // 현재 캡쳐 활성화 중인지 여부
let startX, startY, endX, endY; // 캡쳐 박스의 네 모서리 좌표
let capType; // 현재 캡쳐 타입
const $captureArea = $("#capture-area"); // 캡쳐 시작시 생성되는 캡쳐박스

$(document).on('click', '.cap-btn', function () {
    let thisEle = $(this);
    let clickType = thisEle.data('id'); // 현재 클릭한 캡쳐 타입

    $captureArea.hide();

    if (!isCapturing) { // 1-1 상황
        $('[data-id="' + capType + '"]').parent().css('border', 'none'); // 기존 테두리 제거
        //console.log('캡쳐 시작');
        isCapturing = true; // 캡쳐 활성화
        thisEle.parent().css('border', '1px solid #00C471'); // 현재 캡쳐 영역 테두리
        $('#canvas-ar').css('cursor', 'crosshair'); // 마우스 십자가
        capType = clickType; // 캡쳐 타입 갱신
    } else if (isCapturing && capType === clickType) { // 2-1 상황
        //console.log('캡쳐 종료');
        isCapturing = false; // 캡쳐 비활성화
        thisEle.parent().css('border', 'none'); // 현재 캡쳐 영역 테두리
        $('#canvas-ar').css('cursor', 'default'); // 마우스 십자가
    } else if (isCapturing && capType !== clickType) { // 2-2 상황
        //console.log('현재 캡쳐 종료 -> 새 캡쳐 시작');
        $('[data-id="' + capType + '"]').parent().css('border', 'none'); // 기존 테두리 제거
        thisEle.parent().css('border', '1px solid #00C471'); // 현재 캡쳐 영역 테두리
        capType = clickType; // 캡쳐 타입 갱신
    }

    //console.log(`캡쳐중인지:${isCapturing} / 현재 캡쳐 타입 :${capType}`);
    //console.log(`${startX} / ${startY} / ${endX} / ${endY}`);
}); // ok

$(document).on("mousedown", function (e) {
    if (!isCapturing) return;

    e.preventDefault();

    startX = e.clientX;
    startY = e.clientY + window.scrollY; // 스크롤 값 보정

    $captureArea.css({
        left: startX + "px",
        top: startY + "px",
        width: "0px",
        height: "0px",
        display: "block",
    });
}); // ok

$(document).on("mousemove", function (e) {
    if (!isCapturing) return;

    e.preventDefault();

    endX = e.clientX;
    endY = e.clientY + window.scrollY; // 스크롤 값 보정

    const width = Math.abs(endX - startX);
    const height = Math.abs(endY - startY);

    $captureArea.css({
        width: width + "px",
        height: height + "px",
        left: Math.min(startX, endX) + "px",
        top: Math.min(startY, endY) + "px", // 스크롤 보정된 top 값 사용
    });
}); // ok

$(document).on("mouseup", function () {
    if (!isCapturing) return;

    isCapturing = false; // 캡쳐 비활성화
    $('#canvas-ar').css('cursor', 'default'); // 커서 기본으로

    startX = undefined;
    startY = undefined;
    endX = undefined;
    endY = undefined;

    //console.log('====================캡쳐 끝==================');
    //console.log(`캡쳐 활성화 여부: ${isCapturing}`);
    //console.log(`현재 캡쳐 타입: ${capType}`);
    //console.log();

    const rect = $captureArea[0].getBoundingClientRect();

    html2canvas(document.body, {
        x: rect.left,
        y: rect.top + window.scrollY,
        width: rect.width,
        height: rect.height,
    }).then((canvas) => {

        const imageData = canvas.toDataURL("image/png");

        // 새 창에서 이미지 보기
        // const newWindow = window.open();
        // newWindow.document.write('<img src="' + imageData + '" />');

        $.ajax({
            url: "/ai/extract/naver-ocr",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({ image : imageData , answerNo : 0}),
            success: function (response) {
                if(response == 'err'){
                    alert("서버 에러");
                    return;
                }

                let resStr = response.trim();

                $('#'+capType).val(resStr);

            },
            error: function (xhr, status, error) {
                alert("서버 에러");
            },
        });

    });



}); //ok


/* 문제 저장 버튼*/
$(document).on('click', '.extract-submit-btn', function(){

    let cateCode = $('#cateCode').val();
    let orgCode = $('#orgCode').val();
    let year = $('#year').val();
    let month = $('#month').val();
    let subject = $('#subjectCode').val();
    let type = $('#type').val();
    //let userId = $('#userId').val();
    let hubCode = $('#hubCode').val();

    let qnum = $('#question-num').val();
    let question = $('#question').val();
    let questionSub = $('#question-sub').val();
    let option1 = $('#option-1').val();
    let option2 = $('#option-2').val();
    let option3 = $('#option-3').val();
    let option4 = $('#option-4').val();
    let option5 = $('#option-5').val();


    $.ajax({
        url: "/ai/extract/data",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            examCateCode: cateCode,
            examOrgCode: orgCode,
            examYear:year,
            examMonth: month,
            subjectCode: subject,
            examType: type,
            extractHubCode: hubCode,
            questionNo: qnum,
            question: question,
            questionSub: questionSub,
            option1: option1,
            option2: option2,
            option3: option3,
            option4: option4,
            option5: option5
        }),
        success: function (response) {

            if(response === 1){
                alert('저장');
            }else{
                alert('서버 에러입니다.');
            }

        },
        error: function (xhr, status, error) {
            alert("서버 에러");
        },
    });



    console.log(`카테코드 : ${cateCode} / 그룹코드 : ${orgCode} / 년도 : ${year} / 달 : ${month} / 과목코드 : ${subject} / 타입 : ${type} / 저장소코드 : ${hubCode}`);
    console.log('----------------------------------------------------------');
    console.log(`문제번호 : ${qnum}`);console.log('---------------------------------------------------------');
    console.log(`문제 텍스트 : ${question}`);console.log('----------------------------------------------------------');
    console.log(`문제 보기 : ${questionSub}`);console.log('----------------------------------------------------------');
    console.log(`선택지1 : ${option1}`);console.log('----------------------------------------------------------');
    console.log(`선택지2 : ${option2}`);console.log('----------------------------------------------------------');
    console.log(`선택지3 : ${option3}`);console.log('----------------------------------------------------------');
    console.log(`선택지4 : ${option4}`);console.log('----------------------------------------------------------');
    console.log(`선택지5 : ${option5}`);console.log('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++');


});











//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});
