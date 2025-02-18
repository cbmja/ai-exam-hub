package com.aiexamhub.exam;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AiExamHubApplicationTests {

	@Test
	void contextLoads() {

/*
		String sql = "INSERT INTO exam_org (exam_org_code, exam_org_name, exam_cate_code) VALUES\n";

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// JSON 파일 읽기
			java.util.List<School> schools = objectMapper.readValue(new File("C:\\Users\\jeon\\Desktop\\시험_사이트\\서울시 고등학교 기본정보.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, School.class));

			// 읽은 데이터 출력
			for (int i = 0; i < schools.size(); i++) {
				School school = schools.get(i);
				sql += "('" + school.getSd_schul_code() + "', '" + school.getSchul_nm() + "', 'SCHOOL')";
				// 마지막 항목 뒤에 쉼표를 추가하지 않음
				if (i < schools.size() - 1) {
					sql += ",";
				}
				sql += "\n";
			}
			sql += ";";

		} catch (IOException e) {
			e.printStackTrace();
		}

		try (FileWriter writer = new FileWriter("C:\\Users\\jeon\\Desktop\\시험_사이트\\학교_insert_sql.txt")) {
			writer.write(sql);  // String을 output.txt 파일로 작성
		} catch (IOException e) {
			e.printStackTrace();
		}




@Data
public class School {

    private String schul_knd_sc_nm;   // 고등학교 종류
    private String fond_ymd;          // 설립일
    private String atpt_ofcdc_sc_code;
    private String indst_specl_ccccl_exst_yn;
    private String dght_sc_nm;        // 주간/야간
    private String org_faxno;         // 학교 팩스 번호
    private String org_rdnma;         // 학교 주소 (도로명)
    private String coedu_sc_nm;       // 남여공학 여부
    private String org_telno;         // 학교 전화번호
    private String org_rdnzc;         // 주소 (우편번호)
    private String hs_sc_nm;          // 특성화 고등학교 종류
    private String schul_nm;          // 학교 이름
    private String ju_org_nm;         // 주관 기관
    private String foas_memrd;        // 등록일
    private String ene_bfe_sehf_sc_nm;
    private String eng_schul_nm;      // 영어 학교 이름
    private String fond_sc_nm;       // 설립 상태
    private String lctn_sc_nm;       // 위치
    private String hmpg_adres;       // 홈페이지 주소
    private String spcly_purps_hs_ord_nm;
    private String org_rdnda;         // 추가 주소
    private String load_dtm;         // 로딩 시간
    private String sd_schul_code;    // 학교 코드
    private String atpt_ofcdc_sc_nm; // 주관 기관 이름
    private String hs_gnrl_busns_sc_nm; // 전문계 여부

}
*/




	}





}
