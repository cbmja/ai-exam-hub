let isDragging = false;
let offsetX;
let offsetY;

$("#r-f-ar").mousedown(function(e) {
    isDragging = true;

    offsetX = null;
    offsetY = null;
    // 현재 요소의 위치 기준으로 마우스 클릭 위치 계산
    offsetX = e.clientX - $(this).offset().left;
    offsetY = e.clientY - $(this).offset().top;

    $(this).css({
        "cursor": "grabbing",
        "position": "absolute" // 이동 중에는 absolute
    });
});

$(document).mousemove(function(e) {
    if (!isDragging) return;

    $("#r-f-ar").css({
        left: e.clientX - offsetX + "px",
        top: e.clientY - offsetY + "px"
    });
});

$(document).mouseup(function() {
    if (!isDragging) return;

    isDragging = false;

    // 현재 위치를 픽스드(fixed)로 고정
    let left = e.pageX - offsetX;
    let top = e.pageY - offsetY;

    $("#r-f-ar").css({
        "cursor": "grab",
        "position": "fixed",
        "left": left + "px",
        "top": top + "px"
    });
});
