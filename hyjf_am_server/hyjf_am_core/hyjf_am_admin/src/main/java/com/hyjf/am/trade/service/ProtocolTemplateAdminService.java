package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.admin.AdminProtocolRequest;
import com.hyjf.am.resquest.admin.ProtocolLogRequest;
import com.hyjf.am.vo.admin.ProtocolLogVO;
import com.hyjf.am.vo.admin.ProtocolTemplateCommonVO;
import com.hyjf.am.vo.admin.ProtocolVersionVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;

import java.util.List;
import java.util.Map;

/**
 * @author：yinhui
 * @Date: 2018/10/8  14:53
 */
public interface ProtocolTemplateAdminService {

    /**
     * 查询协议模板
     * @date 2018/7/4 15:38
     */
    List<ProtocolTemplateVO> getProtocolTemplateVOByDisplayName(String displayName);

    /**
     * 统计全部个数
     *
     * @return
     */
    Integer countRecord(AdminProtocolRequest request);

    int startUseExistProtocol(AdminProtocolRequest request);

    List<ProtocolTemplateCommonVO> getRecordList(AdminProtocolRequest request);

    /**
     * 根据协议id查询协议和版本
     *
     * @return
     */
    ProtocolTemplateCommonVO getProtocolTemplateById(Integer id);

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
    ProtocolTemplateCommonVO getProtocolTemplateByProtocolName(AdminProtocolRequest request);

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


    List<ProtocolTemplateVO>  protocolGetnewinfo();

    /**
     * 修改 之前的版本的启用状态改成不启用
     *
     * @return
     */
    Integer updateDisplayFlag(AdminProtocolRequest request);

    /**
     * 删除协议模板
     * @param request
     * @return
     */
    ProtocolTemplateCommonVO deleteProtocolTemplate(AdminProtocolRequest request);

    /**
     *  统计模板日志
     *
     * @return
     */
    Integer countRecordLog(ProtocolLogRequest request);

    /**
     * 查询所有协议日志
     * @date 2018/7/4 15:38
     */
    List<ProtocolLogVO> getProtocolLogVOAll(ProtocolLogRequest request);

    ProtocolVersionVO byIdProtocolVersion(Integer id);

    ProtocolTemplateVO byIdTemplateBy(String protocolId);

    int updateProtocolVersionSize(ProtocolTemplateVO protocolTemplate);

    Map<String,Object> validatorFieldCheck(String protocolName, String versionNumber, String displayName, String protocolUrl, String protocolType, String oldDisplayName, String flagT);
}
