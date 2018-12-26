/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.EvaluationBorrowLevelConfigVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author liuyang
 * @version EvaluationBorrowLevelConfigResponseBean, v0.1 2018/12/25 14:14
 */
public class EvaluationBorrowLevelConfigResponseBean extends Response<EvaluationBorrowLevelConfigVO> {
    @ApiModelProperty(value = "FORM")
    private EvaluationBorrowLevelConfigVO form;

    private List<DropDownVO> investLevel;

    public EvaluationBorrowLevelConfigVO getForm() {
        return form;
    }

    public void setForm(EvaluationBorrowLevelConfigVO form) {
        this.form = form;
    }

    public List<DropDownVO> getInvestLevel() {
        return investLevel;
    }

    public void setInvestLevel(List<DropDownVO> investLevel) {
        this.investLevel = investLevel;
    }
}
