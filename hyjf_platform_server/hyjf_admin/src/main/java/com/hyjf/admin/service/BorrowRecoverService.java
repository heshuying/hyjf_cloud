package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowRecoverBean;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRecoverService, v0.1 2018/7/2 10:16
 */
public interface BorrowRecoverService {
    List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode);

    void searchAction(JSONObject jsonObject, BorrowRecoverBean form);
}
