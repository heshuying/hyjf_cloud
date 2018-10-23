/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * @author fuqiang
 * @version ProtocolsService, v0.1 2018/7/10 16:54
 */
public interface ProtocolsService {
	/**
	 * 获取协议列表
	 *
	 * @param request
	 * @return
	 */
	FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequestBean request);

	/**
	 * 添加协议列表
	 *
	 * @param requestBean
	 * @return
	 */
	FddTempletCustomizeResponse insertAction(ProtocolsRequestBean requestBean);

	/**
	 * 修改协议列表
	 *
	 * @param requestBean
	 * @return
	 */
	FddTempletCustomizeResponse updateAction(ProtocolsRequestBean requestBean);

	/**
	 * 取得新规的模板编号
	 * @param protocolType
	 * @return
	 */
    String getNewTempletId(Integer protocolType);

	/**
	 * 协议管理-画面迁移
	 *
	 * @param id
	 * @return
	 */
	FddTempletCustomizeResponse getRecordInfo(Integer id);

	/**
	 * 协议管理-获取协议类型下拉列表
	 *
	 * @param protocolType
	 * @return
	 */
	List<ParamNameVO> getParamNameList(String protocolType);

	/**
	 * 从request中取得MultipartFile列表
	 * @param multipartRequest
	 * @return
	 */
    List<MultipartFile> getMultipartFileList(MultipartHttpServletRequest multipartRequest);

	/**
	 * 上传协议模板文件到Ftp服务器
	 * @param file
	 * @param fddTemplet
	 * @param 是否删除上传目录 0：否 1：是
	 * @return
	 */
	String uploadTempletToFtp(MultipartFile file, String fddTemplet, int type);

	/**
	 * 调用发大大模板上传接口
	 * @param httpUrl
	 * @param templetId
	 * @return
	 */
	DzqzCallBean uploadtemplateDZApi(String httpUrl, String templetId, String userId);
}
