/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.UserCenterClient;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserManagerVO;
//import com.hyjf.cs.common.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nixiaoling
 * @version UserCenterServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class UserCenterServiceImpl implements UserCenterService {


    @Autowired
    private UserCenterClient userCenterClient;


    private static Logger logger = LoggerFactory.getLogger(UserCenterServiceImpl.class);

    /**
     *查找用户信息
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> selectUserMemberList(UserManagerRequest request) {
        Map<String, Object> mapReturn = new HashMap<String, Object>();
        String status = "0";//0->成功,99->失败
        // 初始化分页参数，并组合到请求参数
       /* Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        // 查询count
        int userCount = userCenterClient.countRecordTotal(request);
        page.setTotal(userCount);*/
        // 关联hyjf_trade库的ht_hjh_inst_config表
        List<HjhInstConfigVO> listHjhInstConfig = userCenterClient.selectHjhInstConfigListByInstCode(null);
        // 查询列表
        List<UserManagerVO> listUserMember = userCenterClient.selectUserMemberList(request);
        if (!CollectionUtils.isEmpty(listUserMember)) {
            for (UserManagerVO userManager : listUserMember) {
                for (HjhInstConfigVO hjhinst : listHjhInstConfig) {
                    if (hjhinst.getInstCode().equals(userManager.getInstCode())) {
                        userManager.setInstName(hjhinst.getInstName());
                    }
                }
            }
            //页数信息
           // mapReturn.put("page", page);
            //用户列表信息
            mapReturn.put("data", listHjhInstConfig);
        } else {
            // 暂无数据
            status = "99";
        }
        mapReturn.put("status", status);
        return mapReturn;
    }

    /**
     * 根据机构编号获取机构列表
     * @param instCode
     * @return
     */
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigList(String instCode) {
        List<HjhInstConfigVO> listHjhInstConfig = userCenterClient.selectHjhInstConfigListByInstCode(instCode);
        return listHjhInstConfig;
    }

}
