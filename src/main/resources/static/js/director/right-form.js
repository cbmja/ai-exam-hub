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



    /* 캡쳐 버튼 클릭 */
    $(document).on('click', '.cap-btn', function(){

        let type = $(this).data('id');
        console.log(`선택 : ${type}`);

    });









        let isCapturing = false; // 현재 캡쳐 활성화 중인지 여부
        let startX, startY, endX, endY;

        const $captureArea = $("#capture-area");

        $(document).on('click', '.capture-btn', function () {
            console.log($(this).data('selected'));

            if($(this).data('selected') == 0){
                $(this).data('selected' , '1');
                $('body').css('cursor', 'crosshair');
                $(this).addClass('selected-btn');
                isCapturing = true;
                $('.modal-box').val('');
                $('.modal-box').removeClass('filled');
                $('.capture-ar').empty();
                $('.text-modal').css('visibility' , 'hidden');
                console.log('선택해제 -> 선택');
            }else{
                $(this).data('selected' , '0');
                $('body').css('cursor', 'default');
                $(this).removeClass('selected-btn');
                isCapturing = false;
                console.log('선택 -> 선택해제');
            }

        });


        $(document).on("mousedown", function (e) {
            if (!isCapturing) return;

            startX = e.clientX;
            startY = e.clientY;

            $captureArea.css({
                left: startX + "px",
                top: startY + "px",
                width: "0px",
                height: "0px",
                display: "block",
            });
        });


        $(document).on("mousemove", function (e) {
            if (!isCapturing) return;

            endX = e.clientX;
            endY = e.clientY;

            const width = Math.abs(endX - startX);
            const height = Math.abs(endY - startY);

            $captureArea.css({
                width: width + "px",
                height: height + "px",
                left: Math.min(startX, endX) + "px",
                top: Math.min(startY, endY) + "px",
            });
        });


        $(document).on("mouseup", function () {
            if (!isCapturing) return;

            $("body").css("cursor", "default");
            isCapturing = false;

            const rect = $captureArea[0].getBoundingClientRect();

            $captureArea.hide();
            // 화면 캡처 실행

            html2canvas(document.body, {
                x: rect.left,
                y: rect.top,
                width: rect.width,
                height: rect.height,
            }).then((canvas) => {
                // 기존 캔버스를 그대로 사용
                canvas.style.maxWidth = "100%";
                canvas.style.height = "auto";

                // 캡처된 canvas 출력
                $('.capture-ar').empty().append(canvas); // <canvas> 삽입
                $captureArea.hide();
            });



            $('.capture-btn').data('selected' , '0');
            $('body').css('cursor', 'default');
            $('.capture-btn').removeClass('selected-btn');
            isCapturing = false;

            $('.text-modal').css('visibility' , 'visible');
        });



        $(document).on('click', '.modal-close', function () {

                $('.capture-ar').empty();
                $('.capture-btn').data('selected' , '0');
                $('body').css('cursor', 'default');
                $('.capture-btn').removeClass('selected-btn');
                isCapturing = false;
                $('.modal-box').val('');
                $('.modal-box').removeClass('filled');
                $('.text-modal').css('visibility' , 'hidden');
        });


//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});
