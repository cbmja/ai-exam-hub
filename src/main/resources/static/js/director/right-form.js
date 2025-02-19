let isDragging = false;
let offsetX, offsetY;

$("#r-f-ar").mousedown(function(e) {
    isDragging = true;

    // 현재 요소의 위치 기준으로 마우스 클릭 위치 계산 (스크롤 고려)
    offsetX = e.clientX - $(this).offset().left;
    offsetY = e.clientY - $(this).offset().top + $(window).scrollTop();

    $(this).css({
        "cursor": "grabbing",
        "position": "absolute" // 이동 중에는 absolute
    });
});

$(document).mousemove(function(e) {
    if (!isDragging) return;

    $("#r-f-ar").css({
        left: e.pageX - offsetX + "px",
        top: e.pageY - offsetY + "px"
    });
});

$(document).mouseup(function(e) {
    if (!isDragging) return;
    isDragging = false;

    let left = $("#r-f-ar").offset().left;
    let top = $("#r-f-ar").offset().top - $(window).scrollTop();

    let windowWidth = $(window).width();  // 현재 브라우저 창 너비
    let windowHeight = $(window).height(); // 현재 브라우저 창 높이
    let elementWidth = $("#r-f-ar").outerWidth(); // 요소의 너비
    let elementHeight = $("#r-f-ar").outerHeight(); // 요소의 높이

    // 요소가 화면을 벗어나면 0으로 설정
    if (left < 0) left = 0;
    if (top < 0) top = 0;

    // 요소가 오른쪽 화면을 벗어나면 최대 값으로 설정
    if (left + elementWidth > windowWidth) {
        left = windowWidth - elementWidth;
    }

    // 요소가 아래쪽 화면을 벗어나면 최대 값으로 설정
    if (top + elementHeight > windowHeight) {
        top = windowHeight - elementHeight;
    }

    $("#r-f-ar").css({
        "cursor": "grab",
        "position": "fixed", // 뷰포트 기준 고정
        "left": left + "px",
        "top": top + "px"
    });
});
