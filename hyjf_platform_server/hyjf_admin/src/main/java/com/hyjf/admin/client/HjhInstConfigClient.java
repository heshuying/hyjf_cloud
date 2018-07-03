package com.hyjf.admin.client;

import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version HjhInstConfigClient, v0.1 2018/7/2 10:43
 */
public interface HjhInstConfigClient {
    List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode);
}
