/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.excel.ReadExcel;
import com.hyjf.admin.service.UserPortraitService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.user.UserPortraitCustomizeRequest;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.vo.user.UserPortraitVO;
import com.hyjf.common.file.UploadFileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author nxl
 * @version LoanCoverServiceImpl, v0.1 2018/6/26 17:11
 */
@Service
public class UserPortraitServiceImpl implements UserPortraitService {
    private static Logger logger = LoggerFactory.getLogger(UserPortraitServiceImpl.class);
    @Autowired
    private AmUserClient userPortraitClient;
    @Value("${file.upload.path}")
    private String FILEUPLOADPATH;

    @Value("${file.physical.path}")
    private String PHYSICAL_PATH;

    @Value("${admin.front.host}")
    private String ADMIN_HOST;

    /**
     * 根据参数查询用户画像信息
     *
     * @return
     */
    @Override
    public UserPortraitResponse selectRecordList(UserPortraitRequest userPortraitRequest) {
        UserPortraitResponse response = userPortraitClient.selectRecordList(userPortraitRequest);
        return response;
    }

    /**
     * 导出 根据参数查询用户画像信息
     *
     * @return
     */
    @Override
    public UserPortraitResponse exportRecordList(UserPortraitRequest userPortraitRequest) {
        UserPortraitResponse response = userPortraitClient.exportRecordList(userPortraitRequest);
        return response;
    }

    @Override
    public StringResponse importBatch(HttpServletRequest request) {
        StringResponse response = new StringResponse();
        String errorMessage = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String filePhysicalPath = PHYSICAL_PATH + FILEUPLOADPATH;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(date);

        String logoRealPathDir = filePhysicalPath + "/" + today;
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }
        Iterator<String> itr = multipartRequest.getFileNames();
        MultipartFile multipartFile = null;
        String fileRealName = null;
        while (itr.hasNext()) {
            multipartFile = multipartRequest.getFile(itr.next());
            fileRealName = String.valueOf(System.currentTimeMillis() / 1000);
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = UploadFileUtils.getSuffix(originalFilename);
            if (StringUtils.equals(suffix, ".xlsx")) {
                fileRealName = fileRealName + suffix;
                try {
                    errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);
                    logger.info("文件上传状态：" + errorMessage);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            } else {
                response.setRtn(Response.FAIL);
                response.setMessage("上传失败，请上传Excel(.xlsx)文件");
                return response;
            }
        }

        String filePath = logoRealPathDir + "/" + fileRealName;
        Map<String, String> map = new HashMap<>();
        map.put("userName", "userName");
        map.put("currentOwner", "currentOwner");

        Map<String, String> maps = new HashMap<>();
        maps.put("用户名", "userName");
        maps.put("当前拥有人", "currentOwner");
        ReadExcel readExcel = new ReadExcel();
        List<JSONObject> list = new ArrayList<>();
        try {
            list = readExcel.readExcel(filePath, map, maps);
        } catch (IOException e) {
            logger.error("导入当前拥有人失败，解析Excel：" + filePath + "失败！", e);
        }
        try {
            Integer flag = batchImport(list);
            response.setMessage("导入" + list.size() + "条数据，成功" + (list.size() - flag) + "条，失败" + flag + "条！");
        } catch (Exception e) {
            response.setMessage("导入失败！");
        }
        return response;
    }

    /**
     * 批量导入
     *
     * @return
     */
    private Integer batchImport(List<JSONObject> list) {
        Integer totalCount;
        try {
            final List<JSONObject> lists = list;
            ExecutorService executorService = new ThreadPoolExecutor(3, 10,
                    20L, TimeUnit.MINUTES,
                    new LinkedBlockingQueue<Runnable>(512), Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
            Future<Integer> t = executorService.submit(() -> {
                // run方法具体重写
                logger.info("批量导入当前拥有人开始： 预计" + lists.size() + "条");
                Integer totalCount1 = 0;
                List<UserPortraitVO> usersPortraits = new ArrayList<>();
                List<String> userNames = new ArrayList<>();
                UserPortraitCustomizeRequest request = new UserPortraitCustomizeRequest();

                for (JSONObject jsonObject : lists) {
                    UserPortraitVO usersPortrait = JSONObject.toJavaObject(jsonObject, UserPortraitVO.class);
                    if (StringUtils.isBlank(usersPortrait.getUserName())) {
                        totalCount1++;
                        continue;
                    }
                    if (usersPortrait.getCurrentOwner() == null) {
                        usersPortrait.setCurrentOwner("");
                    }

                    userNames.add(usersPortrait.getUserName());
                    usersPortraits.add(usersPortrait);
                    if (usersPortraits.size() > 1000) {
                        request.setUserPortraitVOS(usersPortraits);
                        request.setUserNames(userNames);
                        int result = userPortraitClient.countUserNames(request);
                        if (result > 0) {
                            batchInsertOwner(request);
                        }
                        totalCount1 = totalCount1 + (userNames.size() - result);
                        usersPortraits.clear();
                        userNames.clear();
                    }
                }
                if (!CollectionUtils.isEmpty(usersPortraits)) {
                    request.setUserPortraitVOS(usersPortraits);
                    request.setUserNames(userNames);
                    int result = userPortraitClient.countUserNames(request);
                    if (result > 0) {
                        batchInsertOwner(request);
                    }
                    totalCount1 = totalCount1 + (userNames.size() - result);
                }
                logger.info("批量导入当前拥有人失败数，共：" + totalCount1 + "条");
                return totalCount1;
            });
            totalCount = t.get(50L, TimeUnit.MINUTES);
            executorService.shutdown();
        } catch (Exception e) {
            logger.error("导入当前拥有人异常", e);
            return 0;
        }
        return totalCount;
    }

    public boolean batchInsertOwner(UserPortraitCustomizeRequest request) {
        // 导入
        try {
            this.importData(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return true;
    }

    private synchronized int importData(UserPortraitCustomizeRequest request) {
        int count = 0;
        userPortraitClient.updateBatch(request);
        return count;
    }

    /**
     * 根据用户id查找用户画像
     *
     * @param userId
     * @return
     */
    @Override
    public UserPortraitVO selectUsersPortraitByUserId(Integer userId) {
        UserPortraitVO userPortraitVO = userPortraitClient.selectUsersPortraitByUserId(userId);
        return userPortraitVO;
    }

    /**
     * 修改用户画像
     */
    @Override
    public int updateUserPortrait(UserPortraitRequest userPortraitRequest) {
        int updFlg = userPortraitClient.updateUserPortrait(userPortraitRequest);
        return updFlg;
    }

}
