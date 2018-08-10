package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/8  15:17
 */
public class ProtocolTemplateCommonVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    //协议模板
    private ProtocolTemplateVO protocolTemplateVO;

    //协议日志
    private List<ProtocolLogVO> protocolLog;

    //协议版本
    private List<ProtocolVersionVO> protocolVersion;

    //修改时间
    private String updateTime;

    public ProtocolTemplateVO getProtocolTemplateVO() {
        return protocolTemplateVO;
    }

    public void setProtocolTemplateVO(ProtocolTemplateVO protocolTemplateVO) {
        this.protocolTemplateVO = protocolTemplateVO;
    }

    public List<ProtocolLogVO> getProtocolLog() {
        return protocolLog;
    }

    public void setProtocolLog(List<ProtocolLogVO> protocolLog) {
        this.protocolLog = protocolLog;
    }

    public List<ProtocolVersionVO> getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(List<ProtocolVersionVO> protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
