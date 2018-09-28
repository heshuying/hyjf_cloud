/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.resquest.admin.BailConfigAddRequest;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigService, v0.1 2018/9/26 16:36
 */
public interface BailConfigService extends BaseService {

    /**
     * 获取保证金配置总数
     *
     * @param request
     * @return
     */
    Integer selectBailConfigCount(BailConfigRequest request);

    /**
     * 获取保证金配置列表
     *
     * @param request
     * @return
     */
    List<BailConfigCustomizeVO> selectRecordList(BailConfigRequest request);

    /**
     * 根据主键获取保证金配置
     *
     * @param id
     * @return
     */
    BailConfigInfoCustomizeVO selectBailConfigById(Integer id);

    /**
     * 未配置保证金的机构编号
     *
     * @return
     */
    List<HjhInstConfigVO> selectNoUsedInstConfigList();

    /**
     * 添加保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    boolean insertBailConfig(BailConfigAddRequest bailConfigAddRequest);

    /**
     * 周期内发标已发额度
     *
     * @param bailConfigAddRequest
     * @return
     */
    String selectSendedAccountByCyc(BailConfigAddRequest bailConfigAddRequest);

    /**
     * 根据该机构可用还款方式更新可用授信方式
     *
     * @param instCode
     * @return
     */
    boolean updateBailInfoDelFlg(String instCode);

    /**
     * 更新保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    boolean updateBailConfig(BailConfigAddRequest bailConfigAddRequest);

    /**
     * 删除保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    boolean deleteBailConfig(BailConfigAddRequest bailConfigAddRequest);

    /**
     * 获取当前机构可用还款方式
     *
     * @param instCode
     * @return
     */
    List<String> selectRepayMethod(String instCode);
}
