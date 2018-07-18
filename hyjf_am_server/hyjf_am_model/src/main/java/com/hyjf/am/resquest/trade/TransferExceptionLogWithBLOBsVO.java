package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.trade.TransferExceptionLogVO;

import java.io.Serializable;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/18 09:50
 * @Description: TransferExceptionLogWithBLOBsVO
 */
public class TransferExceptionLogWithBLOBsVO extends TransferExceptionLogVO implements Serializable {
    private String content;

    private String result;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }
}
