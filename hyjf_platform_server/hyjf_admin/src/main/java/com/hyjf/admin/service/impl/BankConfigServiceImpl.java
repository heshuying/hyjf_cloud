package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.BankConfigClient;
import com.hyjf.admin.service.BankConfigService;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/11.
 */
@Service
public class BankConfigServiceImpl implements BankConfigService {

    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 查询银行配置列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse bankConfigInit(AdminBankConfigRequest adminRequest){
        return amConfigClient.bankConfigInit(adminRequest);
    }

    /**
     * 根据id查询银行配置
     * @param bankId
     * @return
     */
    @Override
    public AdminBankConfigResponse selectBankConfigById(Integer bankId){
        return amConfigClient.selectBankConfigById(bankId);
    }

    /**
     * 根据银行名称查询银行配置
     * @return
     */
    @Override
    public List<BankConfigVO> getBankConfigRecordList(BankConfigVO bank){
        return amConfigClient.getBankConfigRecordList(bank.getName());
    }

    /**
     * 添加银行配置
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse insertBankConfigRecord(AdminBankConfigRequest adminRequest){
        return amConfigClient.insertBankConfigRecord(adminRequest);
    }


    /**
     * 修改银行配置
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse updateBankConfigRecord(AdminBankConfigRequest adminRequest){
        return amConfigClient.updateBankConfigRecord(adminRequest);
    }
    /**
     * 删除银行配置
     * @param id
     * @return
     */
    @Override
    public AdminBankConfigResponse deleteBankConfigById(Integer id){
        return amConfigClient.deleteBankConfigById(id);
    }

    /**
     * 上传文件
     * @param request
     * @param response
     * @return
     */
    @Override
    public AdminBankConfigResponse uploadFile(HttpServletRequest request, HttpServletResponse response){
//        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
//        String fileDomainUrl = UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
//        String filePhysicalPath = UploadFileUtils.getDoPath(PropUtils.getSystem("file.physical.path"));
//        String fileUploadTempPath = UploadFileUtils.getDoPath(PropUtils.getSystem("file.upload.temp.path"));
//
//        String logoRealPathDir = filePhysicalPath + fileUploadTempPath;
//
//        File logoSaveFile = new File(logoRealPathDir);
//        if (!logoSaveFile.exists()) {
//            logoSaveFile.mkdirs();
//        }
//
//        BorrowCommonImage fileMeta = null;
//        LinkedList<BorrowCommonImage> files = new LinkedList<BorrowCommonImage>();
//
//        Iterator<String> itr = multipartRequest.getFileNames();
//        MultipartFile multipartFile = null;
//
//        while (itr.hasNext()) {
//            multipartFile = multipartRequest.getFile(itr.next());
//            String fileRealName = String.valueOf(new Date().getTime());
//            String originalFilename = multipartFile.getOriginalFilename();
//            fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
//
//            // 文件大小
//            String errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);
//
//            fileMeta = new BorrowCommonImage();
//            int index = originalFilename.lastIndexOf(".");
//            if (index != -1) {
//                fileMeta.setImageName(originalFilename.substring(0, index));
//            } else {
//                fileMeta.setImageName(originalFilename);
//            }
//
//            fileMeta.setImageRealName(fileRealName);
//            fileMeta.setImageSize(multipartFile.getSize() / 1024 + "");// KB
//            fileMeta.setImageType(multipartFile.getContentType());
//            fileMeta.setErrorMessage(errorMessage);
//            // 获取文件路径
//            fileMeta.setImagePath(fileUploadTempPath + fileRealName);
//            fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
//            files.add(fileMeta);
//        }
//        return JSONObject.toJSONString(files, true);
        return null;
    }


    /**
     * 保存之前的去重校验
     * @param adminBankConfigRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse validateBeforeAction(AdminBankConfigRequest adminBankConfigRequest){
        return amConfigClient.validateBeforeAction(adminBankConfigRequest);
    }


}
