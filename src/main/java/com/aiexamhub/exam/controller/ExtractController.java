package com.aiexamhub.exam.controller;

import com.aiexamhub.exam.dto.*;
import com.aiexamhub.exam.service.*;
import com.aiexamhub.exam.util.OcrUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/ai-exam-hub")
@RequiredArgsConstructor
public class ExtractController {

    private final ExamCateService examCateService;
    private final ExamOrgService examOrgService;
    private final SubjectService subjectService;
    private final ExtractHubService extractHubService;
    private final ExtractQuestionService extractQuestionService;
    private final SubjectDetailService subjectDetailService;

    private final OcrUtil ocrUtil;

    @GetMapping("/example")
    public String example(ServletRequest servletRequest){
        return "view/director/drag-example";
    }

    // 내 저장소 목록
    @GetMapping("/repository")
    public String MyRepositories(ServletRequest servletRequest , Model model){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));

        return "view/repository/repositories";
    }

    @GetMapping("/index")
    public String index(ServletRequest servletRequest , Model model){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));
        return "view/layout/index";
    }


    @GetMapping("/{hubCode}/exam/form")
    public String testing(ServletRequest servletRequest , @PathVariable(name = "hubCode" , required = false) int hubCode,Model model){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));
        // ------------------------------------------------- 하드코딩 ------------------------------------------------- //
        String userId = "tester";
        hubCode = 1;

        ExtractHub myHub = extractHubService.selectByExtractHubCode(hubCode);

        List<ExamCate> examCateList = examCateService.selectAll();
        if(examCateList == null || examCateList.isEmpty() || examCateList.get(0).getErr().equals("err")){
            // 에러
            System.out.println("sql err");
        }


        List<ExtractQuestion> extractQList = extractQuestionService.selectByExtractHubCode(hubCode , "desc");

        for(ExtractQuestion eq : extractQList){
            if(eq.getSubjectDetailName().isEmpty()){
                eq.setSubjectDetailName("공통");
            }
            if(eq.getExamType().equals("even")){
                eq.setExamTypeName("짝수형");
            }
            if(eq.getExamType().equals("odd")){
                eq.setExamTypeName("홀수형");
            }
            if(eq.getExamType().equals("A")){
                eq.setExamTypeName("A형");
            }
            if(eq.getExamType().equals("B")){
                eq.setExamTypeName("B형");
            }
            if(eq.getExamType().equals("1")){
                eq.setExamTypeName("1형");
            }
            if(eq.getExamType().equals("2")){
                eq.setExamTypeName("1형");
            }
            if(eq.getExamType().equals("x")){
                eq.setExamTypeName("타입 없음");
            }

        }



        int currentYear = Year.now().getValue();

        // 1900년부터 현재 년도까지의 숫자를 포함하는 리스트 생성
        List<Integer> yearList = IntStream.rangeClosed(1900, currentYear)  // 1900~현재 년도 범위
                .boxed()  // IntStream을 Integer로 변환
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        model.addAttribute("extractQList" , extractQList);
        model.addAttribute("userId" , userId);
        model.addAttribute("myHub" , myHub);
        model.addAttribute("yearList" , yearList);
        model.addAttribute("examCateList" , examCateList);
        model.addAttribute("title" , "문제_추출");
        return "view/director/extract";
    }


    @GetMapping("/examInfo")
    @ResponseBody
    public Map<String , Object> getExamOrg(ServletRequest servletRequest , @RequestParam(name="examCateCode", defaultValue = "") String examCateCode,Model model){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));
        Map<String , Object> res = new HashMap<>();

        if(examCateCode.isBlank()){
            System.out.println("no request param");
        }

        // 출제 기관 리스트
        List<ExamOrg> examOrgList = examOrgService.selectByExamCateCode("."+examCateCode);
        if(examOrgList == null || examOrgList.isEmpty() || examOrgList.get(0).getErr().equals("err")){
            // 에러
            System.out.println("sql err");
        }

        // 시험 과목 리스트
        List<Subject> subjectList = subjectService.selectByExamCateCode("."+examCateCode);
        if(subjectList == null || subjectList.isEmpty() || subjectList.get(0).getErr().equals("err")){
            // 에러
            System.out.println("sql err");
        }

        res.put("examOrgList" , examOrgList);
        res.put("subjectList" , subjectList);
        return res;
    }


    @PostMapping("/naver-ocr")
    @ResponseBody
    public String img(ServletRequest servletRequest , @RequestBody Map<String , Object> form , Model model){

        try{
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));

            String base64Image = (String)form.get("image");
            int answerNo = (Integer)form.get("answerNo");

            String img64 = base64Image.replaceAll("data:image/png;base64,","");

            String ocrResult = ocrUtil.sendOCRRequest(img64);
            System.out.println("====================================================");
            System.out.println(ocrResult);

            JSONObject jsonObject = new JSONObject(ocrResult);
            JSONArray images = jsonObject.getJSONArray("images");

            StringBuilder inferTextBuilder = new StringBuilder();

            // Iterate through images and extract inferText from fields
            for (int i = 0; i < images.length(); i++) {
                JSONObject image = images.getJSONObject(i);
                JSONArray fields = image.getJSONArray("fields");

                for (int j = 0; j < fields.length(); j++) {
                    JSONObject field = fields.getJSONObject(j);
                    String inferText = field.getString("inferText");
                    inferTextBuilder.append(inferText);

                    // Add a space if lineBreak is false
                    if (!field.getBoolean("lineBreak")) {
                        inferTextBuilder.append(" ");
                    }
                }
            }

            // Output the result
            String result = inferTextBuilder.toString().trim();
            System.out.println("====================================================");
            System.out.println(result);
/*

            String[] parts = base64Image.split(",");
            String imageData = (parts.length > 1) ? parts[1] : parts[0];

            // 디코딩 후 파일 저장
            byte[] imageBytes = Base64.getDecoder().decode(imageData);

            String filePath = "C:\\Users\\jeon\\Desktop\\시험문제 추출 java\\"+answerNo+"번_선택지.png"; // 저장 경로
            if(answerNo == 0){
                filePath = "C:\\Users\\jeon\\Desktop\\시험문제 추출 java\\"+answerNo+"번_문제.png"; // 저장 경로
            }

             FileOutputStream fos = new FileOutputStream(filePath);
             fos.write(imageBytes);
             fos.close();
*/


            return result;
        }catch (Exception e){
            e.printStackTrace();
            return "err";
        }

    }


    @PostMapping("/data")
    @ResponseBody
    public int saveQuestion(ServletRequest servletRequest , @RequestBody ExtractQuestion form , Model model){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));
        int res = extractQuestionService.save(form);

        return form.getExtractQuestionCode();
    }



    @GetMapping("/subjectDetail")
    @ResponseBody
    public List<SubjectDetail> getSubjectDetails(ServletRequest servletRequest , @RequestParam(name="subjectCode", defaultValue = "") String subjectCode , Model model){
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        model.addAttribute("isLogin" , (Boolean)req.getAttribute("isLogin"));
        List<SubjectDetail> subjectDetailList = subjectDetailService.selectBySubjectCode(subjectCode);

        return subjectDetailList;
    }

}
