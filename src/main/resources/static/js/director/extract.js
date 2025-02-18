let examCateCode;
let examOrgCode;

/* 시험 종류 선택시 종류별 출제 기관 목록 가져오기 */
$(document).on('change', '#exam-cate-form', function(){

    examCateCode = $('#exam-cate-form').val();

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


    examOrgCode = $('#exam-org-form').val();


});

$(document).on('change', '.extract-select', function(){

    let id = $(this).attr('id');
    let selectedText = $('#'+id+' option:selected').text();
    selectedText = ':&nbsp;&nbsp;&nbsp;'+selectedText;

    switch(id){
        case 'exam-cate-form' : $('#cate-data').empty().append(selectedText); break;
        case 'exam-org-form' : $('#org-data').empty().append(selectedText); break;
        case 'subject-form' : $('#subject-data').empty().append(selectedText); break;
        case 'year-form' : $('#year-data').empty().append(selectedText); break;
        case 'month-form' : $('#month-data').empty().append(selectedText); break;
        case 'type-form' : $('#type-data').empty().append(selectedText); break;
    }



});