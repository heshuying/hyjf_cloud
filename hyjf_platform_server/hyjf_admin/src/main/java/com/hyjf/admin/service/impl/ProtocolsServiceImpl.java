/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.ProtocolsService;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;
import com.hyjf.common.file.FavFTPUtil;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author fuqiang
 * @version ProtocolsServiceImpl, v0.1 2018/7/10 16:54
 */
@Service
public class ProtocolsServiceImpl implements ProtocolsService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${hyjf.ftp.ip}")
	private String ftpIp;

	@Value("${hyjf.ftp.port}")
	private String ftpPort;

	@Value("${hyjf.ftp.basepath.pdf}")
	private String ftpBasePath;

	@Value("${hyjf.ftp.password}")
	private String ftpPassword;

	@Value("${hyjf.ftp.username}")
	private String ftpUsername;

	@Value("${hyjf.ftp.url}")
	private String ftpDomain;

	@Autowired
	private AmTradeClient amTradeClient;
	@Autowired
	private AmConfigClient amConfigClient;

	@Override
	public FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequestBean request) {
		FddTempletCustomizeResponse response = amTradeClient.selectFddTempletList(request);
		if (response != null) {
			List<FddTempletCustomizeVO> voList = response.getResultList();
			if (!CollectionUtils.isEmpty(voList)) {
				List<ParamNameVO> typeList = amConfigClient.getParamNameList("PROTOCOL_TYPE");
				for (FddTempletCustomizeVO vo : voList) {
					if (!CollectionUtils.isEmpty(typeList)) {
                        for (ParamNameVO typeVo:typeList) {
                            if (Objects.equals(typeVo.getNameCd(), vo.getProtocolType().toString())) {
                                vo.setProtocolTypeName(typeVo.getName());
                            }
                        }
					}
					List<ParamNameVO> flagList = amConfigClient.getParamNameList("CA_FLAG");
					if (!CollectionUtils.isEmpty(flagList)) {
						ParamNameVO flagVo = flagList.get(0);
						if (Objects.equals(flagVo.getNameCd(), vo.getProtocolType().toString())) {
							vo.setCaFlagName(flagVo.getName());
						}
					}
				}
			}
			response.setResultList(voList);
			return response;
		}
		return null;
	}

	@Override
	public FddTempletCustomizeResponse insertAction(ProtocolsRequestBean requestBean) {
		return amTradeClient.insertAction(requestBean);
	}

	@Override
	public FddTempletCustomizeResponse updateAction(ProtocolsRequestBean requestBean) {
		return amTradeClient.updateAction(requestBean);
	}

	@Override
	public String getNewTempletId(Integer protocolType) {
		return amTradeClient.getNewTempletId(protocolType);
	}

	/**
	 * 协议管理-画面迁移
	 *
	 * @param id
	 * @return
	 */
	@Override
	public FddTempletCustomizeResponse getRecordInfo(Integer id) {
		return amTradeClient.getRecordInfoById(id);
	}

	/**
	 * 协议管理-获取协议类型下拉列表
	 *
	 * @param protocolType
	 * @return
	 */
	@Override
	public List<ParamNameVO> getParamNameList(String protocolType) {
		return amConfigClient.getParamNameList(protocolType);
	}

	@Override
	public List<MultipartFile> getMultipartFileList(MultipartHttpServletRequest multipartRequest) {
		List<MultipartFile> multipartFileList = new ArrayList<MultipartFile>();

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			if (multipartFile != null){
				multipartFileList.add(multipartFile);
			}
		}
		return multipartFileList;
	}

	@Override
	public String uploadTempletToFtp(MultipartFile multipartFile, String fddTemplet, int type) {
		String ftpIP = ftpIp;
		String port = ftpPort;
		String basePath = ftpBasePath;
		String password = ftpPassword;
		String username = ftpUsername;
		String domain = ftpDomain;
		String ftpPath = "ftp://" + ftpIP + ":" + port + basePath + "/" + fddTemplet;
		String httpPath = domain + basePath + "/" + fddTemplet;
		String httpUrl = null;
		try {
			logger.info("----------待上传目录：" + multipartFile.getOriginalFilename());
			File paraentDir = multipartToFile(multipartFile);
			String upParaFile = paraentDir.getParent();
			if(paraentDir.isDirectory()){

				logger.info("----------待删除目录：" + upParaFile);
				File[] files = paraentDir.listFiles();
				for (File file : files) {
					String fileName = file.getName();
					logger.info("--------循环目录，开始上传文件：" + fileName);
					FileInputStream in = new FileInputStream(file);
					boolean flag = FavFTPUtil.uploadFile(ftpIP, Integer.valueOf(port), username, password,
							basePath, fddTemplet, fileName, in);
					if (!flag){
						throw new RuntimeException("上传失败!fileName:" + fileName);
					}
				}
			}else{
				String fileName = multipartFile.getOriginalFilename();
				logger.info("--------开始上传文件：" + fileName + "  上传到：" + httpPath );
				FileInputStream in = new FileInputStream(paraentDir);
				boolean flag = FavFTPUtil.uploadFile(ftpIP, Integer.valueOf(port), username, password,
						basePath, fddTemplet, fileName, in);
				if (!flag){
					throw new RuntimeException("上传失败!fileName:" + fileName);
				}
				httpUrl = httpPath + "/" + fileName;
			}
			if(type == 1){
				//删除原目录
				FileUtil.deltree(upParaFile);
				logger.info("--------删除原目录：" + upParaFile );
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			return null;
		}
		return httpUrl;
	}

	@Override
	public DzqzCallBean uploadtemplateDZApi(String httpUrl, String templetId, String userId) {
		//参数生成
		DzqzCallBean bean = new DzqzCallBean();
		bean.setUserId(Integer.parseInt(userId));
		bean.setTxCode("uploadtemplate");
		bean.setApp_id(DzqzConstant.HYJF_FDD_APP_ID);
		bean.setV(DzqzConstant.HYJF_FDD_VERSION);
		bean.setTimestamp(GetDate.getDate("yyyyMMddHHmmss"));
		bean.setTemplate_id(templetId);
		bean.setFile(null);
		bean.setDoc_url(httpUrl);
		//调用接口
		return DzqzCallUtil.callApiBg(bean);
	}

	/**
	 * MultipartFile 转换成File
	 *
	 * @param multfile 原文件类型
	 * @return File
	 * @throws IOException
	 */
	private File multipartToFile(MultipartFile multfile) throws IOException {
		CommonsMultipartFile cf = (CommonsMultipartFile)multfile;
		//这个myfile是MultipartFile的
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File file = fi.getStoreLocation();
		//手动创建临时文件private
		if(file.length() < 2024){
			File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
					file.getName());
			multfile.transferTo(tmpFile);
			return tmpFile;
		}
		return file;
	}
}
