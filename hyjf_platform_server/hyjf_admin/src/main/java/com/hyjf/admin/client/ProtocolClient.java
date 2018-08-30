package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AdminProtocolResponse;
import com.hyjf.am.resquest.admin.AdminProtocolRequest;
import com.hyjf.am.vo.admin.ProtocolTemplateCommonVO;
import com.hyjf.am.vo.admin.ProtocolVersionVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/8  16:50
 */
public interface ProtocolClient {

    /**
     * 统计全部个数
     *
     * @return
     */
    Integer countRecord(AdminProtocolRequest request);

    List<ProtocolTemplateCommonVO> getRecordList(AdminProtocolRequest request);

    /**
     * 根据协议id查询协议和版本
     *
     * @return
     */
    ProtocolTemplateCommonVO getProtocolTemplateById(AdminProtocolRequest request);

    /**
     * 查询协议模板数量
     *
     * @return
     */
    Integer getProtocolTemplateNum(AdminProtocolRequest request);

    /**
     * 判断删除的协议中是否存在当前协议模板名称Agreement006
     *
     * @return
     */
    ProtocolTemplateVO getProtocolTemplateByProtocolName(AdminProtocolRequest request);

    /**
     * 保存 协议模板、协议版本、协议日志
     *
     * @return
     */
    Integer insert(AdminProtocolRequest request);

    /**
     * 修改 协议模板
     *
     * @return
     */
    Integer updateProtocolTemplate(AdminProtocolRequest request);

    /**
     * 修改 之前的版本的启用状态改成不启用
     *
     * @return
     */
    Integer updateDisplayFlag(AdminProtocolRequest request);

    /**
     * 删除协议模板
     *
     * @return
     */
    AdminProtocolResponse deleteProtocolTemplate(AdminProtocolRequest request);

    List<ProtocolTemplateVO> getNewInfo();

    ProtocolVersionVO byIdProtocolVersion(Integer id);

    ProtocolTemplateVO byIdTemplateBy(String protocolId);

    int getProtocolVersionSize(AdminProtocolRequest adminProtocolRequest);
}
