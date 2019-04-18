package com.hyjf.admin.service.workflow.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.workflow.WorkFlowConfigService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.AdminRoleResponse;
import com.hyjf.am.response.admin.WorkFlowConfigResponse;
import com.hyjf.am.response.admin.WorkFlowUserResponse;
import com.hyjf.am.response.config.AdminUserResponse;
import com.hyjf.am.resquest.admin.AdminUserWorkFlowRequest;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.resquest.config.AdminRequest;
import com.hyjf.am.vo.admin.AdminVO;
import com.hyjf.am.vo.admin.WorkFlowUserVO;
import com.hyjf.am.vo.admin.WorkFlowVO;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xiehuili on 2019/4/12.
 */
@Service
public class WorkFlowConfigServiceImpl implements WorkFlowConfigService {

    private static final Logger logger = LoggerFactory.getLogger(WorkFlowConfigServiceImpl.class);

    @Autowired
    private AmAdminClient adminClient;

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 查询工作流配置
     * @param adminRequest
     * @return
     */
    @Override
    public WorkFlowConfigResponse selectWorkFlowConfigList(WorkFlowConfigRequest adminRequest) {
        return adminClient.selectWorkFlowConfigList(adminRequest);
    }
    /**
     * 添加工作流配置
     * @param workFlowVO
     * @return
     */
    @Override
    public BooleanResponse insertWorkFlowConfig(WorkFlowVO workFlowVO){
        return adminClient.insertWorkFlowConfig(workFlowVO);
    }
    /**
     * 查询业务流程详情页面
     * @param id
     * @return
     */
    @Override
    public WorkFlowConfigResponse selectWorkFlowConfigInfo(int id){
        return adminClient.selectWorkFlowConfigInfo(id);
    }
    /**
     * 校验业务id是否存在
     * @param businessId
     * @return
     */
    @Override
    public BooleanResponse selectWorkFlowConfigByBussinessId(int businessId){
        return adminClient.selectWorkFlowConfigByBussinessId(businessId);
    }
    /**
     * 修改工作流配置
     * @param workFlowVO
     * @return
     */
    @Override
    public BooleanResponse updateWorkFlowConfig(WorkFlowVO workFlowVO){
        return adminClient.updateWorkFlowConfig(workFlowVO);
    }
    /**
     *  删除工作流配置业务流程
     * @param id
     * @return
     */
    @Override
    public BooleanResponse deleteWorkFlowConfigById(int id){
        return adminClient.deleteWorkFlowConfigById(id);
    }
    /**
     *  查询邮件预警通知人
     * @param workFlowUserVO
     * @return
     */
    @Override
    public WorkFlowUserResponse selectUser(WorkFlowUserVO workFlowUserVO){
        return adminClient.selectUser(workFlowUserVO);
    }

    /**
     * 处理业务流程配置删除用户异常
     * @param adminUserId
     */
    public void disableAdminUser(Integer[] adminUserId){
        AdminUserWorkFlowRequest request = new AdminUserWorkFlowRequest();
        request.setAdminuserId(adminUserId);
        List<AdminVO> adminVOList = amConfigClient.getAdminUser(request);
        List<String> userIdList = new ArrayList<>();
        Map<Integer,WorkFlowUserVO> workMap = new HashMap<>();
        Map<Integer,String> trueNameMap = new HashMap<>();
        if(adminVOList != null ){

            for(AdminVO vo : adminVOList){
                userIdList.add(vo.getId()+","+vo.getTruename());
            }
        }

//        //将后台用户与业务流程用户匹配
//        List<WorkFlowVO> workFlowVOList = adminClient.updateStatusBusinessName();
//        String trueName = "";
//        if(workFlowVOList != null ){
//
//            for(String user: userIdList){
//
//                //匹配邮件预警人员
//                for(WorkFlowVO vo : workFlowVOList){
//                    if(vo.getMailWarningUser().contains(user)){
//                        workMap.put(vo.getId(),vo);
//
//                        //trueNameMap 放入 删除或禁用的用户姓名
//                        trueName = user.substring(user.lastIndexOf(",")+1);
//                        if(trueNameMap.containsKey(vo.getId())){
//                            trueName = trueNameMap.get(vo.getId()) + "、" + trueName;
//                            trueNameMap.put(vo.getId(),trueName);
//                        }else{
//                            trueNameMap.put(vo.getId(),trueName);
//                        }
//
//                    }
//
//                }
//            }
//        }

        //将后台用户与流程节点用户匹配
        List<WorkFlowUserVO> workFlowUserVOList = adminClient.findWorkFlowNodeUserEmailAll();
        if(workFlowUserVOList != null ){

            for(AdminVO user: adminVOList){

                //匹配邮件预警人员
                for(WorkFlowUserVO vo : workFlowUserVOList){
                    if(vo.getUserid().equals(user.getId())){
                        workMap.put(vo.getWorkflowid(),vo);

                        //trueNameMap 放入 删除或禁用的用户姓名
                        if(trueNameMap.containsKey(vo.getWorkflowid())){
                            trueNameMap.put(vo.getId(),trueNameMap.get(vo.getWorkflowid()) + "、" + vo.getTruename());
                        }else{
                            trueNameMap.put(vo.getId(),vo.getTruename());
                        }

                    }

                }
            }
        }


        logger.info("【工作流引擎】 后台用户与流程节点用户匹配条数："+workMap.size());
        //将匹配上的业务流程配置改为异常
        Set<Integer> set = workMap.keySet();
        for (Integer key : set) {
            WorkFlowUserVO workFlowVO = workMap.get(key);
            adminClient.updateFlowStatus(key);

            //发送邮件
            String title = "【工作流引擎】"+workFlowVO.getWorkname()+"业务名称流程异常";
            StringBuffer msg = new StringBuffer();
            msg.append("因").append(trueNameMap.get(key)).append("账号删除或禁用");
            msg.append("导致").append(workFlowVO.getWorkname()).append("业务名称流程异常,");
            msg.append("请修改审核流程！");

            try {
                String [] toMail = getMail(workFlowVO.getMailWarningUser());
                if (toMail == null) {
                    throw new Exception("错误收件人没有配置。");
                }
                MailMessage mailmessage = new MailMessage(null, null,
                        title, msg.toString(), null, toMail,
                        null, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
                commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC,"WORKFLOW_TAG", UUID.randomUUID().toString(), mailmessage));
            } catch (Exception e2) {
                logger.error("发送邮件失败..", e2);
            }
            logger.info("【工作流引擎】"+workFlowVO.getWorkname()+"修改审核流程邮件发送完成");
        }

    }

    /**
     * 邮箱拆分
     * @param mailWarningUser
     * @return
     */
    private String[] getMail(String mailWarningUser){
        // mailWarningUser  = 12,张三,1364384067@qq.com;13,李四,65464@qq.com
        List<String> listMail = new ArrayList<>();
        String singleMail = "";
        String[] multiMail = mailWarningUser.split(";");
        for(String arg : multiMail){
            singleMail = arg.substring(arg.lastIndexOf(",")+1);
            if(StringUtils.isNotBlank(singleMail)){
                listMail.add(singleMail);
            }
        }

        return listMail.toArray(new String[listMail.size()]);
    }

    /**
     * 工作流查询所有用户角色
     * @return
     */
    @Override
    public AdminRoleResponse selectWorkFlowRoleList(){
        return adminClient.selectWorkFlowRoleList();
    }
}
