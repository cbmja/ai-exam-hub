let examCateCode;
let examOrgCode;
let examYear;
let examMonth;
let examSubject;
let examType;

/* select 변경시 상단 시험 정보 변경 */
$(document).on('change', '.extract-select', function(){

    let id = $(this).attr('id');
    let selectedText = $('#'+id+' option:selected').text();
    selectedText = ':&nbsp;&nbsp;&nbsp;'+selectedText;

    switch(id){
        case 'exam-cate-form' :
            examCateCode = $('#exam-cate-form').val();
            $('#cate-data').empty().append(selectedText); break;

        case 'exam-org-form' :
            examOrgCode = $('#exam-org-form').val();
            $('#org-data').empty().append(selectedText); break;

        case 'subject-form' :
            examSubject = $('#subject-form').val();
            $('#subject-data').empty().append(selectedText); break;

        case 'year-form' :
            examYear = $('#year-form').val();
            $('#year-data').empty().append(selectedText); break;

        case 'month-form' :
            examMonth = $('#month-form').val();
            $('#month-data').empty().append(selectedText); break;

        case 'type-form' :
            examType = $('#type-form').val();
            $('#type-data').empty().append(selectedText); break;
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

            let sOptions = '<option selected disabled>과목</option>';

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

