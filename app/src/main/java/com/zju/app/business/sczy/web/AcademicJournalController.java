package com.zju.app.business.sczy.web;

import com.zju.app.business.sczy.service.AcademicJournalService;
import com.zju.model.AcademicJournalDO;
import com.zju.utils.Lang;
import com.zju.utils.dwz.AjaxResponseVo;
import com.zju.utils.dwz.DwzPageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lujie on 2017/8/16.
 */
@Controller
@RequestMapping("/sczy/academicJournal")
public class AcademicJournalController {

    private static Logger logger = LoggerFactory.getLogger(AcademicJournalController.class);
    @Autowired
    AcademicJournalService academicJournalService;

    @ModelAttribute
    public void model(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> model) {
        if (!Lang.isEmpty(id)) {
            AcademicJournalDO academicJournalDO = academicJournalService.findOne(id);
            //如果有id存在，首先查出数据库中的对象，避免表单中未出现的空属性覆盖掉原有的值
            model.put("academicJournalDO", academicJournalDO);
        }
    }
    @RequestMapping(value = "/config")
    public String list(AcademicJournalDO academicJournalDO, DwzPageVo page, Map model)
    {
        Page<AcademicJournalDO> pageLists = academicJournalService.findAll(academicJournalDO,page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        //logger.info(pageLists.getContent().toString());
        model.put("academicJournalDO", academicJournalDO);
        model.put("page", page);
        model.put("academicJournalDOs", pageLists.getContent());
        return "/academicJournal/list";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResponseVo save(@RequestParam(name = "name")String name,@RequestParam(name = "url")String url)
    {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", "学科期刊", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try{
            AcademicJournalDO academicJournalDO = new AcademicJournalDO();
            academicJournalDO.setName(name);
            academicJournalDO.setUrl(url);
            academicJournalService.save(academicJournalDO);
        }catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;

    }
    @RequestMapping(value = {"/prepareUpdate"})
    public String update(AcademicJournalDO academicJournalDO,Map model)
    {
        model.put("academicJournalDO", academicJournalDO);
        return "/academicJournal/update";
    }
    @RequestMapping(value ={"/add"})
    public String add()
    {
        return "/academicJournal/add";
    }

    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo update(AcademicJournalDO academicJournalDO) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功",
                "学科期刊", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try {
           academicJournalService.save(academicJournalDO);
        } catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo delete(AcademicJournalDO academicJournalDO) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "已经作废", "学科期刊");
        try {
            academicJournalDO.setDeleteFlag(true);
            academicJournalService.save(academicJournalDO);
        } catch (Exception e) {
            logger.error("删除课件失败:{}", e.getMessage());
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
        }
        return ajaxResponseVo;
    }
}
