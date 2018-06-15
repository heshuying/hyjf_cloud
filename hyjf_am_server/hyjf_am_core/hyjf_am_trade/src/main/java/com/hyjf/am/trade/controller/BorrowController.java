/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.BorrowConfigResponse;
import com.hyjf.am.response.trade.BorrowFinmanNewChargeResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.response.trade.BorrowWithBLOBSResponse;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.service.BorrowService;
import com.hyjf.am.vo.rtbbatch.BorrowStyleVo;
import com.hyjf.am.vo.rtbbatch.BorrowWithBLOBsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.UserService;
import com.hyjf.am.vo.borrow.BorrowConfigVO;
import com.hyjf.am.vo.borrow.BorrowFinmanNewChargeVO;
import com.hyjf.am.vo.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.borrow.BorrowWithBLOBsVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangqingqing
 * @version BorrowController, v0.1 2018/5/28 12:42
 */

@Api(value = "根据机构编号检索机构信息")
@RestController
@RequestMapping("/am-trade/trade")
public class BorrowController {

    @Autowired
    UserService userService;

    @Autowired
    BorrowService borrowService;

    /**
     * @Author: zhangqingqing
     * @Desc :根据机构编号检索机构信息
     * @Param: * @param instCode
     * @Date: 9:00 2018/5/31
     * @Return: com.hyjf.am.response.user.HjhInstConfigResponse
     */
    @ApiOperation(value = " 根据机构编号检索机构信息")
    @GetMapping("/selectInstConfigByInstCode/{instCode}")
    public HjhInstConfigResponse selectInstConfigByInstCode(@PathVariable(value = "instCode") String instCode) {
        HjhInstConfigResponse response = new HjhInstConfigResponse();
        HjhInstConfig hjhInstConfig = userService.selectInstConfigByInstCode(instCode);
        if(null != hjhInstConfig){
            HjhInstConfigVO hjhInstConfigVO = new HjhInstConfigVO();
            BeanUtils.copyProperties(hjhInstConfig,hjhInstConfigVO);
            response.setResult(hjhInstConfigVO);
        }
        return response;
    }

    /**
     * 根据项目类型，期限，获取借款利率
     * @param request
     * @return
     */
    @RequestMapping("/selectBorrowApr")
    public BorrowFinmanNewChargeResponse selectBorrowApr(@RequestBody BorrowFinmanNewChargeRequest request) {
        BorrowFinmanNewChargeResponse response = new BorrowFinmanNewChargeResponse();
        BorrowFinmanNewCharge borrowFinmanNewCharge = borrowService.selectBorrowApr(request);
        if (borrowFinmanNewCharge != null) {
            BorrowFinmanNewChargeVO borrowFinmanNewChargeVO = new BorrowFinmanNewChargeVO();
            BeanUtils.copyProperties(borrowFinmanNewCharge, borrowFinmanNewChargeVO);
            response.setResult(borrowFinmanNewChargeVO);
        }
        return response;
    }

    /**
     * 获取系统配置
     * @param configCd
     * @return
     */
    @RequestMapping("/getBorrowConfig/{configCd}")
    public BorrowConfigResponse getBorrowConfig(@PathVariable String configCd) {
        BorrowConfigResponse response = new BorrowConfigResponse();
        BorrowConfig borrowConfig = borrowService.getBorrowConfigByConfigCd(configCd);
        if (borrowConfig != null) {
            BorrowConfigVO borrowConfigVO = new BorrowConfigVO();
            BeanUtils.copyProperties(borrowConfig, borrowConfigVO);
            response.setResult(borrowConfigVO);
        }
        return response;
    }

    /**
     * 借款表插入
     * @param borrow
     */
    @RequestMapping("/insertBorrow")
    public int insertBorrow(BorrowWithBLOBsVO borrow) {
        Borrow borrowWithBLOBs = new Borrow();
        if (borrow != null) {
            BeanUtils.copyProperties(borrow, borrowWithBLOBs);
            return borrowService.insertBorrow(borrowWithBLOBs);
        }
        return 0;
    }

    /**
     * 个人信息
     * @param borrowManinfoVO
     * @return
     */
    @RequestMapping("/insertBorrowManinfo")
    public int insertBorrowManinfo(BorrowManinfoVO borrowManinfoVO) {
        BorrowManinfo borrowManinfo = new BorrowManinfo();
        if (borrowManinfo != null) {
            BeanUtils.copyProperties(borrowManinfoVO, borrowManinfo);
            return borrowService.insertBorrowManinfo(borrowManinfo);
        }
        return 0;
    }

    @RequestMapping("/updateBorrowRegist")
    public int updateBorrowRegist(@RequestBody BorrowRegistRequest request) {
        return borrowService.updateBorrowRegist(request);
    }

    @GetMapping("/getBorrow/{borrowNid}")
    public BorrowWithBLOBSResponse getBorrow(String borrowNid) {
        BorrowWithBLOBSResponse response = new BorrowWithBLOBSResponse();
        Borrow borrowWithBLOBs = borrowService.getBorrow(borrowNid);
        if (borrowWithBLOBs != null) {
            BorrowWithBLOBsVo borrowWithBLOBsVo = new BorrowWithBLOBsVo();
            BeanUtils.copyProperties(borrowWithBLOBs,borrowWithBLOBsVo);
            response.setResult(borrowWithBLOBsVo);
        }
        return response;
    }

    @GetMapping("/getborrowStyleByNid/{borrowStyle}")
    public BorrowStyleResponse getborrowStyleByNid(String borrowStyle) {
        BorrowStyleResponse response = new BorrowStyleResponse();
        BorrowStyle borrowStyle1 = borrowService.getborrowStyleByNid(borrowStyle);
        if (borrowStyle1 != null) {
            BorrowStyleVo borrowStyleVo = new BorrowStyleVo();
            BeanUtils.copyProperties(borrowStyle1,borrowStyleVo);
            response.setResult(borrowStyleVo);
        }
        return response;
    }


}
