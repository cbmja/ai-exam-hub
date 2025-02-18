let examCateCode;
let examOrgCode;

/* 시험 종류 선택시 종류별 출제 기관 목록 가져오기 */
$(document).on('change', '#exam-cate-form', function(){

    examCateCode = $('#exam-cate-form').val();

    $.ajax({
        url: '/ai/extract/examOrg',
        method: 'GET',
        data: { examCateCode: examCateCode },
        success: function(examOrgList) {

            let examOrgForm = $('#exam-org-form').empty();

            let options = '<option selected disabled>출제기관</option>';

            examOrgList.forEach((examOrg)=>{
              options += `<option value=${examOrg.examOrgCode}>${examOrg.examOrgName}</option>`
            })

            examOrgForm.append(options);

        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('서버 에러입니다. 잠시 후 다시 시도해 주세요.');
        }
    });


    examOrgCode = $('#exam-org-form').val();

    $.ajax({
        url: '/ai/extract/examSubject',
        method: 'GET',
        data: { examOrgCode: examOrgCode },
        success: function(subjectList) {

            let subjectForm = $('#subject-form').empty();

            let options = '<option selected disabled>과목</option>';

            subjectList.forEach((subject)=>{
              options += `<option value=${subject.subjectCode}>${subject.subjectName}</option>`
            })

            subjectForm.append(options);

        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('서버 에러입니다. 잠시 후 다시 시도해 주세요.');
        }
    });

});

