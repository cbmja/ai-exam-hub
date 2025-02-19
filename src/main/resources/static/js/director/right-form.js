let isDragging = false;
let offsetX, offsetY;

$("#r-f-ar").mousedown(function(e) {
    isDragging = true;

    // 현재 요소의 위치 기준으로 마우스 클릭 위치 계산 (스크롤 고려)
    offsetX = e.clientX;
    offsetY = e.clientY;

    $(this).css({
        "cursor": "grabbing",
        "position": "absolute" // 이동 중에는 absolute
    });
});

$(document).mousemove(function(e) {
    if (!isDragging) return;

    $("#r-f-ar").css({
        left: e.pageX + "px",
        top: e.pageY + "px"
    });
});

$(document).mouseup(function(e) {
    if (!isDragging) return;
    isDragging = false;

    // 현재 뷰포트 기준 좌표로 변환 (스크롤 고려)
    let left = e.clientX;
    let top = e.clientY;

    $("#r-f-ar").css({
        "cursor": "grab",
        "position": "fixed", // 뷰포트 기준 고정
        "left": left + "px",
        "top": top + "px"
    });
});
