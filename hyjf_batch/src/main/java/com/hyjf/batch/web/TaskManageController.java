package com.hyjf.batch.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyjf.batch.exception.ServiceException;
import com.hyjf.batch.json.ResultInfo;
import com.hyjf.batch.pojo.TaskInfo;
import com.hyjf.batch.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * @author xiasq
 * @version TaskManageController, v0.1 2018/1/20 22:12
 */
@Controller
public class TaskManageController {
    private static final Logger logger = LoggerFactory.getLogger(TaskManageController.class);

    @Autowired
    private TaskService taskService;


    /**
     * 首页
     * @return
     */
    @RequestMapping(value = { "", "/", "index" })
    public String info() {
        return "index.jsp";
    }

    /**
     * 任务列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String list() {
        Map<String, Object> map = new HashMap<>();
        List<TaskInfo> infos = taskService.list();
        map.put("rows", infos);
        map.put("total", infos.size());
        return JSON.toJSONString(map);
    }

    /**
     * 保存定时任务
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save", produces = "application/json; charset=UTF-8")
    public String save(TaskInfo taskInfo) {
        logger.info("保存任务, taskInfo is :{}", JSONObject.toJSONString(taskInfo));
        try {
            if (taskInfo.getId() == 0) {
                taskService.addJob(taskInfo);
            } else {
                taskService.edit(taskInfo);
            }
        } catch (ServiceException e) {
            return ResultInfo.error(-1, e.getMessage());
        }
        return ResultInfo.success();
    }

    /**
     * 删除定时任务
     * @param jobName
     * @param jobGroup
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete/{jobName}/{jobGroup}", produces = "application/json; charset=UTF-8")
    public String delete(@PathVariable String jobName, @PathVariable String jobGroup) {
        logger.info("删除任务, jobName is :{}", jobName);
        try {
            taskService.delete(jobName, jobGroup);
        } catch (ServiceException e) {
            return ResultInfo.error(-1, e.getMessage());
        }
        return ResultInfo.success();
    }

    /**
     * 暂停任务
     * @param jobName
     * @param jobGroup
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pause/{jobName}/{jobGroup}", produces = "application/json; charset=UTF-8")
    public String pause(@PathVariable String jobName, @PathVariable String jobGroup) {
        logger.info("暂停任务, jobName is :{}", jobName);
        try {
            taskService.pause(jobName, jobGroup);
        } catch (ServiceException e) {
            return ResultInfo.error(-1, e.getMessage());
        }
        return ResultInfo.success();
    }

    /**
     * 重新开始任务
     * @param jobName
     * @param jobGroup
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "resume/{jobName}/{jobGroup}", produces = "application/json; charset=UTF-8")
    public String resume(@PathVariable String jobName, @PathVariable String jobGroup) {
        logger.info("开始任务, jobName is :{}", jobName);
        try {
            taskService.resume(jobName, jobGroup);
        } catch (ServiceException e) {
            return ResultInfo.error(-1, e.getMessage());
        }
        return ResultInfo.success();
    }

    /**
     * 运行一次任务
     * @param jobName
     * @param jobGroup
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "runOnce/{jobName}/{jobGroup}", produces = "application/json; charset=UTF-8")
    public String runOnceTask(@PathVariable String jobName, @PathVariable String jobGroup) {
        logger.info("运行一次, jobName is :{}", jobName);
        try {
            taskService.runOnce(jobName, jobGroup);
        } catch (ServiceException e) {
            return ResultInfo.error(-1, e.getMessage());
        }
        return ResultInfo.success();
    }

}
