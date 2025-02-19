let examCateCode;
let examOrgCode;
let examYear;
let examMonth;
let examSubject;
let examType;

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
let files;
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

    files = null;
    files = event.originalEvent.dataTransfer.files;



    if (files.length > 0) {

        const file = files[0];
        originalFileName = file.name;

        let fileType = file.type;
        let fileExtension = file.name.split('.').pop().toLowerCase();
        // PDF 파일만 허용
        if (fileType != 'application/pdf' || fileExtension != 'pdf') {
            alert('현재는 pdf 파일만 가능합니다. 죄송합니다.');
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

});

/* 선택 완료 버튼 */
$(document).on('click', '#submit-pdf', function(){

    let navLeft = $('#nav-left-ar').empty();

    let navStr = `<span>${examCateStr} / ${examYearStr} / ${examMonthStr} / ${examSubjectStr} / ${examTypeStr} / ${examOrgStr} / ${originalFileName}</span>`;
    navLeft.append(navStr);

});

