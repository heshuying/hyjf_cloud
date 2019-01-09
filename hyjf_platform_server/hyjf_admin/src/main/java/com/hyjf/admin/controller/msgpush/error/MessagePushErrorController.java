/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush.error;

import com.hyjf.admin.beans.request.MessagePushErrorRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.controller.manager.banksetting.BankSettingController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.MessagePushErrorService;
import com.hyjf.admin.service.MessagePushHistoryService;
import com.hyjf.am.response.admin.MessagePushErrorResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.AdminAccountDetailDataRepairVO;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorController, v0.1 2018/8/14 21:37
 */
@Api(description = "消息中心-APP消息推送 异常处理", tags = "消息中心-APP消息推送 异常处理")
@RestController
@RequestMapping("/hyjf-admin/msgpush/error")
public class MessagePushErrorController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BankSettingController.class);
    @Value("file.domain.url")
    private String FILE_DOMAIN_URL;
    /**
     * 权限名称
     */
    public static final String PERMISSIONS = "msgpusherror";

    @Autowired
    private MessagePushErrorService messagePushErrorService;
    @Autowired
    private MessagePushHistoryService messagePushHistoryService;
    @Autowired
    private CommonProducer commonProducer;


    @PostMapping("/getListByConditions")
    @ApiParam(required = false, name = "requestBean", value = "查询条件")
    @ApiOperation(value = "(条件)查询 APP消息推送 异常处理 列表", httpMethod = "POST", notes = "(条件)查询 APP消息推送 异常处理 列表")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_VIEW, ShiroConstants.PERMISSION_SEARCH})
    public AdminResult getListByConditions(@RequestBody MessagePushErrorRequestBean requestBean) {
        logger.info(this.getClass().getName(), "(条件)查询 APP消息推送 异常处理 列表 start", requestBean.toString(), "/hyjf-admin/msgpush/error/getListByConditions");
        MessagePushErrorRequest request = new MessagePushErrorRequest();
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        try {
            BeanUtils.copyProperties(requestBean, request);
            Integer count = this.messagePushErrorService.getRecordCount(request);
            if(count > 0){
                Paginator paginator = new Paginator(request.getCurrPage(), count, request.getPageSize() == 0 ? 10 : request.getPageSize());
                request.setPaginator(paginator);
                List<MessagePushMsgHistoryVO> recordList = this.messagePushErrorService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
                request.setRecordList(recordList);
                String fileDomainUrl = UploadFileUtils.getDoPath(FILE_DOMAIN_URL);
                response.setFileDomainUrl(fileDomainUrl);
            }
            // 标签
            List<MessagePushTagVO> tagList =  messagePushHistoryService.getAllPushTagList();
            response.setTagList(tagList);
            // 发送状态
            List<ParamNameVO> messagesSendStatus = this.messagePushErrorService.getParamNameList("MSG_PUSH_SEND_STATUS");
            response.setMessagesSendStatus(messagesSendStatus);
            response.setMsgErrorForm(request);
            //返回状态
            response.setCount(count);
        }catch (Exception e){
            logger.info(this.getClass().getName(), "(条件)查询 APP消息推送 异常处理 列表 异常!", "/hyjf-admin/msgpush/error/getListByConditions",
                    e);
            return new AdminResult(FAIL, FAIL_DESC);
        }
        logger.info(this.getClass().getName(), "(条件)查询 APP消息推送 异常处理 列表 end", "/hyjf-admin/msgpush/error/getListByConditions");
        return new AdminResult(response);
    }

    @PutMapping("/update")
    @ApiOperation(value = "重发 APP消息推送 异常处理", httpMethod = "PUT", notes = "重发 APP消息推送 异常处理")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult update(@RequestBody AdminAccountDetailDataRepairVO requestParam) {
		logger.info(this.getClass().getName(), "(条件)查询 APP消息推送 异常处理 列表 start", requestParam.toString(),
				"/hyjf-admin/msgpush/error/getListByConditions");
		MessagePushErrorResponse response = new MessagePushErrorResponse();
		try {
			// 重发此消息
			AppMsMessage appMsMessage = new AppMsMessage(MessageConstant.APP_MS_SEND_REPEAT, requestParam.getId());
			try {
				commonProducer.messageSend(
						new MessageContent(MQConstant.APP_MESSAGE_TOPIC, requestParam.getId(), appMsMessage));
			} catch (MQException e) {
				logger.error("app push send error...", e);
			}
		} catch (Exception e) {
			logger.info(this.getClass().getName(), "(条件)查询 APP消息推送 异常处理 列表 异常!",
					"/hyjf-admin/msgpush/error/getListByConditions", e);
			return new AdminResult(FAIL, FAIL_DESC);
		}
		logger.info(this.getClass().getName(), "(条件)查询 APP消息推送 异常处理 列表 end",
				"/hyjf-admin/msgpush/error/getListByConditions");
		return new AdminResult(response);
	}
}
