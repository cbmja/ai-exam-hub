const pdfjsLib = window['pdfjs-dist/build/pdf'];
pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.16.105/pdf.worker.min.js';

let examCateCode;
let examOrgCode;
let examYear;
let examMonth;
let examSubject;
let examType;
let groupNm;

let examCateStr;
let examOrgStr;
let examYearStr;
let examMonthStr;
let examSubjectStr;
let examTypeStr;

/* select 변경시 상단 시험 정보 변경 */
$(document).on('change', '.extract-select', function(){

    let id = $(this).attr('id');
    let originalText = $('#'+id+' option:selected').text();
    selectedText = ':&nbsp;&nbsp;&nbsp;'+originalText;

    switch(id){
        case 'exam-cate-form' :
            examCateCode = $('#exam-cate-form').val();
            $('#cate-data').empty().append(selectedText);
            examCateStr = originalText; break;
        case 'exam-org-form' :
            examOrgCode = $('#exam-org-form').val();
            $('#org-data').empty().append(selectedText);
            examOrgStr = originalText; break;
        case 'subject-form' :
            examSubject = $('#subject-form').val();
            $('#subject-data').empty().append(selectedText);
            examSubjectStr = originalText; break;
        case 'year-form' :
            examYear = $('#year-form').val();
            $('#year-data').empty().append(selectedText);
            examYearStr = originalText; break;
        case 'month-form' :
            examMonth = $('#month-form').val();
            $('#month-data').empty().append(selectedText);
            examMonthStr = originalText; break;
        case 'type-form' :
            examType = $('#type-form').val();
            $('#type-data').empty().append(selectedText);
            examTypeStr = originalText; break;
    }

});


/* 시험 종류 선택시 출제기관목록 , 시험 과목 불러오기 */
$(document).on('change', '#exam-cate-form', function(){

    $.ajax({
        url: '/ai/extract/examInfo',
        method: 'GET',
        data: { examCateCode: examCateCode },
        success: function(res) {

            let examOrgList = res['examOrgList'];
            let subjectList = res['subjectList'];

            let examOrgForm = $('#exam-org-form').empty();

            let oOptions = '<option selected disabled>출제기관</option>';

            examOrgList.forEach((examOrg)=>{
              oOptions += `<option value=${examOrg.examOrgCode}>${examOrg.examOrgName}</option>`
            })

            examOrgForm.append(oOptions);

            let subjectForm = $('#subject-form').empty();

            let sOptions = '<option selected disabled>과목(가나다순)</option>';

            subjectList.forEach((subject)=>{
              sOptions += `<option value=${subject.subjectCode}>${subject.subjectName}</option>`
            })

            subjectForm.append(sOptions);

        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('서버 에러입니다. 잠시 후 다시 시도해 주세요.');
        }
    });

});

/* 파일 그래그 앤 드랍 */
const dropArea = $('#drop-area');


let upLoadFile;
let originalFileName;

// 드래그가 drop 영역에 들어갈 때 스타일 변경
dropArea.on('dragover', function(event) {
    event.preventDefault();
    dropArea.addClass('dragover');
});

// 드래그가 drop 영역을 벗어날 때 스타일 변경
dropArea.on('dragleave', function() {
    dropArea.removeClass('dragover');
});

// 파일을 드롭할 때 처리
dropArea.on('drop', function(event) {
    event.preventDefault();
    dropArea.removeClass('dragover');

    let files = event.originalEvent.dataTransfer.files;

    if (files.length > 0) {

        upLoadFile = files[0];
        originalFileName = upLoadFile.name;

        let fileType = upLoadFile.type;
        let fileExtension = upLoadFile.name.split('.').pop().toLowerCase();
        // PDF 파일만 허용
        if (fileType != 'application/pdf' || fileExtension != 'pdf') {
            alert('현재는 pdf 파일만 서비스 중 입니다. 죄송합니다.');
            return;
        }
    }

    dropArea.empty();
    dropArea.append(originalFileName);

    if(originalFileName && files){
        dropArea.addClass('dragover');
    }

});

/* pdf 파일 변경 버튼 */
$(document).on('click', '#edit-pdf', function(){

    files = null;
    originalFileName = null;
    dropArea.removeClass('dragover');

    dropArea.empty();
    dropArea.append('시험지 선택(드래그 하세요)');
    let navLeft = $('#nav-left-ar').empty();

});

/* 선택 완료 버튼 */
$(document).on('click', '#submit-pdf', function(){

    groupNm = $('#exam-g-nm').val();

    let navLeft = $('#nav-left-ar').empty();

    let siBtn = $('.side-bar-btn');
    let siAr = $('.side-ar');
    let type = siBtn.data('type');

    siBtn.css('left' , '0').data('type' , 'show');
    siAr.css('display' , 'none');

    siBtn.empty().append('>');

    let conAr = $('.content-ar');
    conAr.css('padding-left' , '0');
    conAr.css('width' , 'calc(100% - 315px)');
    let navStr = `<span> [ ${groupNm} ] / ${examCateStr} / ${examYearStr} / ${examMonthStr} / ${examSubjectStr} / ${examTypeStr} / ${examOrgStr} / ${originalFileName}</span>`;
    navLeft.append(navStr);

    if (upLoadFile && upLoadFile.type === 'application/pdf') {

            let pdfRenderAr = $('.extract-ar');
            let canvasAr = $('#canvas-ar');
            const containerHeight = pdfRenderAr.height();
            const containerWidth = pdfRenderAr.width();


            const fileReader = new FileReader();

            fileReader.onload = function () {
                const typedArray = new Uint8Array(this.result);

                // const pdfRenderer = $('.main-l-ar');

                // $('.main-t-ele').empty();
                // pdfRenderer.removeClass('column-c-c').addClass('pdf-view');

                pdfjsLib.getDocument(typedArray).promise.then(function (pdf) {

                    const pagePromises = [];

                    // pdf 파일 페이지 별로 canvas 생성
                    for (let pageNum = 1; pageNum <= pdf.numPages; pageNum++) {

                        pagePromises.push(
                            pdf.getPage(pageNum).then(function (page) {

                                const originalViewport = page.getViewport({ scale: 1 });
                                const originalWidth = originalViewport.width;
                                const originalHeight = originalViewport.height;

                                // 가로 길이는 containerWidth에 맞추고, 세로는 원본 비율에 맞게 계산
                                const scale = containerWidth / originalWidth;
                                let canvasHeight = originalHeight * scale;

                                // 페이지의 크기와 컨테이너의 크기를 비교하여 비율을 계산
                                const viewport = page.getViewport({ scale: scale });


                                const canvas = document.createElement('canvas');
                                const ctx = canvas.getContext('2d');
                                canvas.width = viewport.width;
                                canvas.height = viewport.height;

                                const renderContext = {
                                    canvasContext: ctx,
                                    viewport: viewport
                                };

                                return page.render(renderContext).promise.then(function () {
                                    return canvas;
                                });
                            })
                        );
                    }
                    canvasAr.empty().css('justify-content', 'flex-start');
                    canvasAr.empty().css('align-items', 'flex-start');
                    Promise.all(pagePromises).then(function (canvases) {
                        canvases.forEach(function (canvas) {
                            canvasAr.append(canvas);
                        });
                    });
                });

            };

            fileReader.readAsArrayBuffer(upLoadFile);
        } else {
            alert('현재는 pdf 파일만 서비스 중 입니다. 죄송합니다.');
        }

});

