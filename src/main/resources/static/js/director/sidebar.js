/* 사이드바 숨김 버튼 */
$(document).on('click', '.side-bar-btn', function(){

    let siBtn = $('.side-bar-btn');
    let pacAr = $('.side-ar-pac');
    let siAr = $('.side-ar');
    let type = siBtn.data('type');

    if(type == 'hide'){
        pacAr.hide();
        siBtn.css('left' , '0').data('type' , 'show');
        siAr.css('border-right' , 'none');

        siBtn.empty().append('>');
    }else{
        pacAr.show();
        siBtn.css('left' , '300px').data('type' , 'hide');
        siAr.css('border-right' , '1px solid #D1D9E0');

        siBtn.empty().append('<');
    }



});