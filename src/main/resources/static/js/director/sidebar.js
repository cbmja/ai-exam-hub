$(document).ready(function () {
//SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS


/* 사이드바 숨김 버튼 */
$(document).on('click', '.side-bar-btn', function(){

    let siBtn = $('.side-bar-btn');
    let siAr = $('.side-ar');
    let type = siBtn.data('type');

    if(type == 'hide'){
        siBtn.css('left' , '0').data('type' , 'show');
        siAr.css('display' , 'none');

        siBtn.empty().append('>');
        siBtn.css('border' , '2px solid #0969DA');
        siBtn.css('color' , '#0969DA');
    }else{
        siBtn.css('left' , '300px').data('type' , 'hide');
        siAr.css('display' , 'flex');

        siBtn.empty().append('<');
        siBtn.css('border' , '2px solid #D1D9E0');
        siBtn.css('color' , '#D1D9E0');
    }



});


















//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
});