package com.hyjf.admin.controller.manager.borrow.credit;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.Utils.ConvertUtils;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.HjhDebtCreditService;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.vo.admin.AccountDetailVO;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/3
 * @Description: 汇计划-转让记录
 */
@Api(value = "汇计划-转让记录")
@RestController
@RequestMapping("/hyjf-admin/hjhDebtCredit")
public class HjhDebtCreditController extends BaseController{

    @Autowired
    private HjhDebtCreditService hjhDebtCreditService;

    @ApiOperation(value = "汇计划-转让记录页面初始化", notes = "页面初始化")
    @PostMapping(value = "/hjhDebtCreditInit")
    @ResponseBody
    public JSONObject hjhDebtCreditInit() {
        JSONObject jsonObject = null;
        return jsonObject;
    }

    @ApiOperation(value = "汇计划-转让记录页面列表显示", notes = "页面列表显示")
    @PostMapping(value = "/queryHjhDebtCreditDetail")

    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    public JSONObject queryHjhDebtCreditDetail(@RequestBody HjhDebtCreditListRequest request) {
        JSONObject jsonObject = null;
        HjhDebtCreditReponse hjhDebtCreditReponse = hjhDebtCreditService.queryHjhDebtCreditList(request);
        List<HjhDebtCreditVo> hjhDebtCreditVoList = new ArrayList<HjhDebtCreditVo>();
        if (null != hjhDebtCreditReponse) {
            List<HjhDebtCreditVo> listAccountDetail = hjhDebtCreditReponse.getResultList();
            Integer recordCount = hjhDebtCreditReponse.getRecordTotal();
            if (null != listAccountDetail && listAccountDetail.size() > 0) {
                hjhDebtCreditVoList.addAll(listAccountDetail);
            }
            if (null != hjhDebtCreditVoList) {
                jsonObject = this.success(String.valueOf(recordCount), hjhDebtCreditVoList);
            } else {
                jsonObject = this.fail("暂无符合条件数据");
            }
        }
        return jsonObject;
    }
}
