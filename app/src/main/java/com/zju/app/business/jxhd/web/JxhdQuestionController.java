package com.zju.app.business.jxhd.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zju.app.business.jxhd.service.*;
import com.zju.model.*;
import com.zju.utils.IDUtils;
import com.zju.utils.Lang;
import com.zju.utils.dwz.AjaxResponseVo;
import com.zju.utils.dwz.DwzPageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lujie on 2017/8/25.
 */
@Controller
@RequestMapping("/jxhd")
public class JxhdQuestionController {

    private static Logger logger = LoggerFactory.getLogger(JxhdQuestionController.class);
    @Autowired
    JxhdQuestionService jxhdQuestionService;

    @Autowired
    JxhdPushService jxhdPushService;

    @Autowired
    JxhdStudentAnswerService jxhdStudentAnswerService;

    @Autowired
    JxhdTeacherQuestionService jxhdTeacherQuestionService;

    @Autowired
    JxhdPushQuestionService jxhdPushQuestionService;


    /*@ModelAttribute
    public void model(@RequestParam(name = "id", required = false) Integer id, Map<String, Object> model) {
        if (!Lang.isEmpty(id)) {
            JxhdTeacherQuestionDO jxhdQuestionDO = jxhdTeacherQuestionService.findOne(id);
            //如果有id存在，首先查出数据库中的对象，避免表单中未出现的空属性覆盖掉原有的值
            model.put("jxhdQuestionDO", jxhdQuestionDO);
        }
    }*/

    @RequestMapping(value = {"/question/config"})
    public String questionList(JxhdTeacherQuestionDO jxhdTeacherQuestionDO, DwzPageVo page, Map model,HttpServletRequest request) {
        Integer type = 0;
        String typeStr = request.getParameter("type");
        if(!Lang.isEmpty(typeStr))
        {
            type=Integer.parseInt(typeStr);
        }
        Page<JxhdTeacherQuestionDO> pageLists = jxhdTeacherQuestionService.findAllQuestion(type,jxhdTeacherQuestionDO, page.getPageable());
        //Page<JxhdQuestionDO> pageLists = jxhdQuestionService.findAllQuestion(type,jxhdQuestionDO, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("jxhdQuestionDO", jxhdTeacherQuestionDO);
        model.put("page", page);
        model.put("jxhdQuestionDOs", pageLists.getContent());
        logger.info(page.getTotalCount()+"");
        model.put("type", type);
        //return "/jxhd/questionList";
        return "/jxhd/questionList1";
    }


    @RequestMapping(value = {"/paper/config"})
    public String paperList(JxhdQuestionDO jxhdQuestionDO, DwzPageVo page, Map model) {
        //logger.info("type: "+type);
        Page<JxhdQuestionDO> pageLists = jxhdQuestionService.findAllPaper(jxhdQuestionDO, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("jxhdQuestionDO", jxhdQuestionDO);
        model.put("page", page);
        model.put("jxhdQuestionDOs", pageLists.getContent());
        logger.info(page.getTotalCount()+"");
        //model.put("type", type);
        return "/jxhd/paperList";
    }

    /*@RequestMapping(value = {"/studentAns"})
    public String studentAnswerList(JxhdStudentAnswerDO jxhdStudentAnswerDO,DwzPageVo page, Map model)
    {
        Page<JxhdStudentAnswerDO> pageLists = jxhdStudentAnswerService.findAll(jxhdStudentAnswerDO, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("jxhdStudentAnswerDO", jxhdStudentAnswerDO);
        model.put("page", page);
        model.put("jxhdStudentAnswerDOs", pageLists.getContent());
        logger.info(page.getTotalCount()+"");
        //model.put("type", type);
        return "/jxhd/studentAnswerList";
    }*/

    @RequestMapping(value = {"/studentAns"})
    public String totalJudgeAnswerList(DwzPageVo page, Map model)
    {
        //Page<JudgeAnswerDO> pageLists = jxhdStudentAnswerService.findJudgeAnswerList(page.getPageable());
        List<JudgeAnswerDO> judgeAnswerDOs = jxhdStudentAnswerService.findJudgeAnswerList();

        for (JudgeAnswerDO judgeAnswerDO:judgeAnswerDOs
             ) {
            judgeAnswerDO.setRate(judgeAnswerDO.getCorrect()*1.0/(judgeAnswerDO.getAnswerA()
            +judgeAnswerDO.getAnswerB()+judgeAnswerDO.getAnswerC()+judgeAnswerDO.getAnswerD()));
        }
        //page.setTotalCount(pageLists.getTotalElements());
        //model.put("jxhdStudentAnswerDO", jxhdStudentAnswerDO);
        model.put("page", page);
        //model.put("judgeAnswerDOs", pageLists.getContent());
        model.put("judgeAnswerDOs", judgeAnswerDOs);

        logger.info(page.getTotalCount()+"");
        //model.put("type", type);
        return "/jxhd/totalJudgeAnswerList";
    }

    @RequestMapping(value = {"/studentAnsInOneQuestion"})
    public String getStudentAnsInOneQuestion(@RequestParam(name = "paperid")Integer paperid,JxhdStudentAnswerDO jxhdStudentAnswerDO,DwzPageVo page,Map model)
    {
        Page<JxhdStudentAnswerDO> pageLists = jxhdStudentAnswerService.findAll(paperid,jxhdStudentAnswerDO, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("jxhdStudentAnswerDO", jxhdStudentAnswerDO);
        model.put("page", page);
        model.put("jxhdStudentAnswerDOs", pageLists.getContent());
        logger.info(page.getTotalCount()+"");
        return "/jxhd/oneJudgeAnswerList";
    }

    @RequestMapping(value = {"/paper/prepareAdd"})
    public String prepareAddPaper()
    {
        //model.put("typeid",id);
        return "/jxhd/addPaper";
    }

    @RequestMapping(value = {"/question/prepareAdd"})
    public String prepareAddQuestion()
    {
        //model.put("typeid",id);
        return "/jxhd/addTeacherQuestion";
    }

    //试卷修改的功能没写
    @RequestMapping(value = {"/paper/prepareUpdate"})
    public String prepareUpdate(JxhdQuestionDO jxhdQuestionDO, Map model) {
        model.put("jxhdQuestionDO", jxhdQuestionDO);
        return "/jxhd/updatePaper";
    }

    /*@RequestMapping(value = {"/question/prepareUpdate"})
    public String prepareUpdateQuestion(JxhdQuestionDO jxhdQuestionDO, Map model) {
        model.put("jxhdQuestionDO", jxhdQuestionDO);
        return "/jxhd/question/updateTeacherQuestion";
    }*/


    @RequestMapping(value = {"/question/prepareUpdate"})
    public String prepareUpdateQuestion(@RequestParam(name = "id")Integer id, Map model) {

        if (!Lang.isEmpty(id)) {
            JxhdTeacherQuestionDO jxhdQuestionDO = jxhdTeacherQuestionService.findOne(id);
            model.put("jxhdQuestionDO", jxhdQuestionDO);
        }
        return "/jxhd/updateTeacherQuestion";
    }

    //修改教学互动中的试题
    /*@RequestMapping(value = {"/question/update"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo updateQuestion(JxhdTeacherQuestionDO jxhdQuestionDO)
    {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", "编辑", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try {
            jxhdTeacherQuestionService.save(jxhdQuestionDO);
        } catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }*/

    @RequestMapping(value = {"/question/update"},method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo questionUpdate(HttpServletRequest request)
    {
        //题目id
        String id = request.getParameter("qid");
        String topic = request.getParameter("topic");
        logger.info(topic);
        String type = request.getParameter("type");
        //试题解析
        String parsing = request.getParameter("parsing");
        //选择题正确答案
        String correctAnswer = request.getParameter("correctAnswer");
        String answerA = request.getParameter("answerA");
        String answerB = request.getParameter("answerB");
        String answerC = request.getParameter("answerC");
        String answerD = request.getParameter("answerD");
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功", "上传题目", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try{

            JxhdTeacherQuestionDO jxhdTeacherQuestionDO =jxhdTeacherQuestionService.findOne(Integer.parseInt(id));
            jxhdTeacherQuestionDO.setAnswerA(answerA);
            jxhdTeacherQuestionDO.setAnswerB(answerB);
            jxhdTeacherQuestionDO.setAnswerC(answerC);
            jxhdTeacherQuestionDO.setAnswerD(answerD);
            jxhdTeacherQuestionDO.setType(1);
            jxhdTeacherQuestionDO.setTopic(topic);
            jxhdTeacherQuestionDO.setCorrectAnswer(correctAnswer);
            jxhdTeacherQuestionDO.setParsing(parsing);
            jxhdTeacherQuestionService.save(jxhdTeacherQuestionDO);
        }catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }
    @RequestMapping(value = "uploadImg",method = RequestMethod.POST)
    @ResponseBody
    public JSON upload(@RequestParam(name = "myimgfile")MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        String oldName=multipartFile.getOriginalFilename();
        System.out.println("oldname"+oldName);
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        String newName = IDUtils.genImageName()+suffix;
        String path = request.getContextPath();
        //String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String basepath = "http://localhost:80/file/";
        System.out.print("path"+basepath);
        //String realPath = request.getServletContext().getRealPath("/");
        String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String path1 = "C:/Users/lujie/Desktop/learning-master/learning-master/imgfile/";
        //String path2 = "C:\\Users\\lujie\\Desktop\\learning-master\\learning-master\\imgfile\\";
        String path3 = "D:\\work\\file\\";
        System.out.print("realPath"+realPath);
        File file = new File(path3+newName);
        file.createNewFile();
        if (!file.exists())
        {
            file.mkdirs();
        }
        multipartFile.transferTo(file);
        JSONObject result = new JSONObject();
        result.put("url",basepath+newName);
        return result;
    }

    @RequestMapping(value = {"/paper/save"})
    @ResponseBody
    public AjaxResponseVo save(@RequestParam(name = "file")MultipartFile[] files,
                               @RequestParam(name = "uploadName")String uploadName)
    {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功",
                "上传试卷", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try {
            List<JxhdQuestionDO> jxhdQuestionDOList = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file != null) {
                    JxhdQuestionDO jxhdQuestionDO = new JxhdQuestionDO();
                    String oldname = file.getOriginalFilename();
                    jxhdQuestionDO.setPaperName(oldname);
                    String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

                    //String newFileName = "C:\\file\\" + UUID.randomUUID().toString() + suffixName;
                    String newFileName = "D:\\work\\courseWare\\" + oldname;
                    System.out.print("0000000"+newFileName);
                    FileCopyUtils.copy(file.getBytes(), new File(newFileName));
                    String url = "http://localhost/courseWare/"+oldname;
                    jxhdQuestionDO.setPaperUrl(url);
                    jxhdQuestionDO.setUploadName(uploadName);
                    jxhdQuestionDO.setCategory(1);
                    jxhdQuestionDOList.add(jxhdQuestionDO);
                }
            }
            jxhdQuestionService.save(jxhdQuestionDOList);
        }catch (Exception e){
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }

        return ajaxResponseVo;
    }

    @RequestMapping(value = {"/paper/delete"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo delete(JxhdQuestionDO jxhdQuestionDO) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "已经作废", "上传试卷");
        try {
            jxhdQuestionDO.setDeleteFlag(true);
            jxhdQuestionService.save(jxhdQuestionDO);
        } catch (Exception e) {
            logger.error("删除试卷失败:{}", e.getMessage());
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
        }
        return ajaxResponseVo;
    }


    @RequestMapping(value = {"/push"})
    @ResponseBody
    public Integer paperPush(@RequestParam(name = "id")Integer id)
    {
       try {
           if (!Lang.isEmpty(id)) {
               JxhdQuestionDO jxhdQuestionDO= jxhdQuestionService.findOne(id);
               if(jxhdQuestionDO.getVersion()==1)
               {
                   JxhdPushDO jxhdPushDO = new JxhdPushDO();
                   Integer paperId = jxhdQuestionDO.getId();
                   String paperName = jxhdQuestionDO.getPaperName();
                   String question = jxhdQuestionDO.getQuestion();
                   String paperUrl = jxhdQuestionDO.getPaperUrl();
                   Integer category = jxhdQuestionDO.getCategory();
                   String xztAnswer = jxhdQuestionDO.getXztAnswer();
                   String uploadName = jxhdQuestionDO.getUploadName();
                   Integer type = jxhdQuestionDO.getType();
                   String answer = jxhdQuestionDO.getAnswer();

                   jxhdPushDO.setAnswer(answer);
                   jxhdPushDO.setCategory(category);
                   jxhdPushDO.setPaperId(paperId);
                   jxhdPushDO.setPaperName(paperName);
                   jxhdPushDO.setQuestion(question);
                   jxhdPushDO.setPaperUrl(paperUrl);
                   jxhdPushDO.setXztAnswer(xztAnswer);
                   jxhdPushDO.setType(type);
                   jxhdPushDO.setUploadName(uploadName);
                   jxhdPushService.save(jxhdPushDO);

                   jxhdQuestionDO.setVersion(2);
                   jxhdQuestionService.save(jxhdQuestionDO);
               }

           }
           return 1;
       }catch (Exception e)
       {
           e.printStackTrace();
       }
       return 0;
    }

    @RequestMapping(value = {"/question/push"})
    @ResponseBody
    public Integer questionPush(@RequestParam(name = "id")Integer id)
    {
        try {
            if (!Lang.isEmpty(id)) {
                JxhdTeacherQuestionDO jxhdTeacherQuestionDO= jxhdTeacherQuestionService.findOne(id);
                if(jxhdTeacherQuestionDO.getVersion()==1)
                {
                    JxhdPushQuestionDO jxhdPushQuestionDO = new JxhdPushQuestionDO();
                    Integer questionId = jxhdTeacherQuestionDO.getId();
                    String topic = jxhdTeacherQuestionDO.getTopic();
                    String answerA = jxhdTeacherQuestionDO.getAnswerA();
                    String answerB = jxhdTeacherQuestionDO.getAnswerB();
                    String answerC = jxhdTeacherQuestionDO.getAnswerC();
                    String answerD = jxhdTeacherQuestionDO.getAnswerD();
                    String correctAnswer = jxhdTeacherQuestionDO.getCorrectAnswer();
                    String parsing = jxhdTeacherQuestionDO.getParsing();
                    Integer type = jxhdTeacherQuestionDO.getType();
                    String image = jxhdTeacherQuestionDO.getImage();

                    jxhdPushQuestionDO.setQuestionId(questionId);
                    jxhdPushQuestionDO.setAnswerA(answerA);
                    jxhdPushQuestionDO.setAnswerB(answerB);
                    jxhdPushQuestionDO.setAnswerC(answerC);
                    jxhdPushQuestionDO.setAnswerD(answerD);
                    jxhdPushQuestionDO.setCorrectAnswer(correctAnswer);
                    jxhdPushQuestionDO.setParsing(parsing);
                    jxhdPushQuestionDO.setImage(image);
                    jxhdPushQuestionDO.setType(type);
                    jxhdPushQuestionDO.setTopic(topic);
                    jxhdPushQuestionService.save(jxhdPushQuestionDO);

                    jxhdTeacherQuestionDO.setVersion(2);
                    jxhdTeacherQuestionService.save(jxhdTeacherQuestionDO);
                }

            }
            return 1;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    /*@RequestMapping(value = {"/question/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo save(HttpServletRequest request)
    {
        String question = request.getParameter("question");
        logger.info(question);
        String type = request.getParameter("type");
        Integer typeInt = 1;
        if (!Lang.isEmpty(type))
        {
            typeInt = Integer.parseInt(type);
        }
        String answer = request.getParameter("answer");
        String xztAnswer = request.getParameter("xztAnswer");
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功", "上传题目", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try{
            JxhdQuestionDO jxhdQuestionDO = new JxhdQuestionDO();
            jxhdQuestionDO.setAnswer(answer);
            jxhdQuestionDO.setXztAnswer(xztAnswer);
            jxhdQuestionDO.setQuestion(question);
            jxhdQuestionDO.setType(typeInt);
            //category 用于试题或者试卷的分类 1，2
            jxhdQuestionDO.setCategory(2);
            jxhdQuestionService.save(jxhdQuestionDO);
        }catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }*/

    @RequestMapping(value = {"/question/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo save(HttpServletRequest request)
    {
        String question = request.getParameter("question");
        logger.info("question"+question);
        String type = request.getParameter("type");
        Integer typeInt = 1;
        if (!Lang.isEmpty(type))
        {
            typeInt = Integer.parseInt(type);
        }
        //试题解析
        String parsing = request.getParameter("answer");
        //选择题正确答案
        String correctAnswer = request.getParameter("correctAnswer");
        //以当前时间为文件名
        Date now=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName=dateFormat.format(now)+".html";
        String filePathContent = "D:\\work\\question\\"+"content"+fileName;
        String contentUrl = "http://localhost/question/"+"content"+fileName;

       /* String filePathAnswer = "D:\\work\\question\\"+"answer"+fileName;
        String contentAnswer = "http://localhost/question/"+"answer"+fileName;*/
        String answerA = request.getParameter("answerA");
        String answerB = request.getParameter("answerB");
        String answerC = request.getParameter("answerC");
        String answerD = request.getParameter("answerD");
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功", "上传题目", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try{
            JxhdTeacherQuestionDO jxhdTeacherQuestionDO = new JxhdTeacherQuestionDO();
            jxhdTeacherQuestionDO.setAnswerA(answerA);
            jxhdTeacherQuestionDO.setAnswerB(answerB);
            jxhdTeacherQuestionDO.setAnswerC(answerC);
            jxhdTeacherQuestionDO.setAnswerD(answerD);
            jxhdTeacherQuestionDO.setType(typeInt);
            jxhdTeacherQuestionDO.setTopic(question);
            jxhdTeacherQuestionDO.setCorrectAnswer(correctAnswer);
            WriteStringToFile(filePathContent,question);
            jxhdTeacherQuestionDO.setContentUrl(contentUrl);
            jxhdTeacherQuestionDO.setParsing(parsing);
            jxhdTeacherQuestionService.save(jxhdTeacherQuestionDO);
        }catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }

    //把题目的答案和解析以文件形式保存，为了传给手机，保证文本不出现乱码
    public void WriteStringToFile(String filePath,String content) {
        try{
            File file = new File(filePath);
            file.createNewFile();
            FileWriter fw = new FileWriter(filePath, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @RequestMapping(value = {"/save"})
//    @ResponseBody
//    public AjaxResponseVo save(HttpServletRequest request)
//    {
//        String content = request.getParameter("content");
//        logger.info(content);
//        String type = request.getParameter("type");
//        String answer = request.getParameter("answer");
//        String judgeAnswer = request.getParameter("judgeAnswer");
//        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功",
// "题目列表", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
//        try{
//            QuestionDO questionDO = new QuestionDO();
//            questionDO.setType(Integer.parseInt(type));
//            questionDO.setContent(content);
//            questionDO.setAnswer(answer);
//            questionDO.setJudgeAnswer(judgeAnswer);
//            questionService.save(questionDO);
//        }catch (Exception e) {
//            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
//            ajaxResponseVo.setMessage("操作失败!");
//            ajaxResponseVo.setCallbackType(null);
//            logger.error(e.getMessage(), e);
//        }
//        return ajaxResponseVo;
//    }

}
