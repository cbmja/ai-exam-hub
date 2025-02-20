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








//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});
