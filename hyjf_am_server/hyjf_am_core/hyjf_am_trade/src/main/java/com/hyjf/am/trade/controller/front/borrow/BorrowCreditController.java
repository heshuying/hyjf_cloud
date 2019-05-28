/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.borrow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.AdminBorrowCreditInfoResponse;
import com.hyjf.am.response.admin.AdminBorrowCreditResponse;
import com.hyjf.am.response.trade.BorrowCreditDetailResponse;
import com.hyjf.am.response.trade.BorrowCreditResponse;
import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowCreditCustomize;
import com.hyjf.am.trade.service.front.borrow.BorrowCreditService;
import com.hyjf.am.trade.service.front.borrow.ProjectListService;
import com.hyjf.am.vo.admin.BorrowCreditInfoSumVO;
import com.hyjf.am.vo.admin.BorrowCreditInfoVO;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.FormatRateUtil;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditController, v0.1 2018/6/24 10:48
 */
@Api(value = "债转信息")
@RestController
@RequestMapping("/am-trade/borrowCredit")
public class BorrowCreditController extends BaseController {

    @Autowired
    BorrowCreditService borrowCreditService;
    @Autowired
    private ProjectListService projectListService;

    /**
     * 查询债转状态为0的数据
     * @return
     */
    @RequestMapping("/selectBorrowCreditList")
    public BorrowCreditResponse selectBorrowCreditList() {
        BorrowCreditResponse response = new BorrowCreditResponse();
        List<BorrowCredit> borrowCredits = borrowCreditService.selectBorrowCreditList();
        if(borrowCredits != null){
            List<BorrowCreditVO> borrowCreditVOS = CommonUtils.convertBeanList(borrowCredits,BorrowCreditVO.class);
            response.setResultList(borrowCreditVOS);
        }
        return response;
    }

    /**
     * 更新债转状态为1
     *
     * @param borrowCreditVO
     * @return
     */
    @RequestMapping("/updateBorrowCredit")
    public Integer updateCreditCredit(@RequestBody @Valid BorrowCreditVO borrowCreditVO) {
        return this.borrowCreditService.updateBorrowCredit(borrowCreditVO);
    }



    @GetMapping("/borrowCreditDetail/{creditNid}")
    public BorrowCreditDetailResponse updateCreditCredit(@PathVariable @Valid String creditNid) {
        BorrowCreditDetailResponse response = new BorrowCreditDetailResponse();
        BorrowCreditDetailVO detailVO = borrowCreditService.getBorrowCreditDetail(creditNid);
        if(null!=detailVO){
            //平台所有利率（参考年回报率，历史年回报率，折让率，加息利率）
            // 全部统一为：小数点后一位（除非后台配置为小数点后两位且不为0时，则展示小数点后两位）
            // mod by nxl 20190409 start
            //历史年回报率
            String fromatBorr= FormatRateUtil.formatBorrowApr(detailVO.getBorrowApr());
            detailVO.setBorrowApr(fromatBorr);
            // mod by nxl 20190409 end
        }
        response.setResult(detailVO);
        return response;
    }


    @PostMapping("/getBorrowCreditList")
    public BorrowCreditResponse getBorrowCreditList(@RequestBody BorrowCreditRequest request1) {
    	BorrowCreditResponse response = new BorrowCreditResponse();
    	List<BorrowCredit> borrowCredits =borrowCreditService.getBorrowCreditList(request1);
    	if(CollectionUtils.isNotEmpty(borrowCredits)) {
    		response.setResultList(CommonUtils.convertBeanList(borrowCredits, BorrowCreditVO.class));
    	}
    	return response;
    }

    /**
     * admin: 查询汇转让列表
     * @author zhangyk
     * @date 2018/7/9 16:50
     */
    @PostMapping("/getBorrowCreditList4admin")
    public AdminBorrowCreditResponse getBorrowCreditList4admin(@RequestBody BorrowCreditAmRequest request){
        AdminBorrowCreditResponse response = new AdminBorrowCreditResponse();
        List<AdminBorrowCreditCustomize> list = borrowCreditService.getBorrowCreditList4Admin(request);
        if (CollectionUtils.isNotEmpty(list)){
            List<com.hyjf.am.vo.admin.BorrowCreditVO> res = CommonUtils.convertBeanList(list, com.hyjf.am.vo.admin.BorrowCreditVO.class);
            response.setResultList(res);
        }
        return response;
    }


    /**
     * admin: 查询汇转让count
     * @author zhangyk
     * @date 2018/7/9 16:50
     */
    @PostMapping("/countBorrowCreditList4admin")
    public AdminBorrowCreditResponse countBorrowCreditList4admin(@RequestBody BorrowCreditAmRequest request){
        AdminBorrowCreditResponse response = new AdminBorrowCreditResponse();
        Integer count = borrowCreditService.countBorrowCreditList4Admin(request);
        response.setRecordTotal(count);
        return response;
    }

    /**
     * admin: 查询汇转让合计行
     * @author zhangyk
     * @date 2018/7/10 17:36
     */
    @PostMapping("/getBorrowCreditTotalCount")
    public  AdminBorrowCreditResponse getBorrowCreditTotalCount(@RequestBody BorrowCreditAmRequest request){
        AdminBorrowCreditResponse response = new AdminBorrowCreditResponse();
        BorrowCreditSumVO sumVO = borrowCreditService.getBorrowCreditTotalCount(request);
        response.setSumVO(sumVO);
        return response;
    }


    /**
     * admin: 查询汇转让明细count
     * @author zhangyk
     * @date 2018/7/10 17:37
     */
    @PostMapping("/countBorrowCreditInfo4admin")
    public AdminBorrowCreditInfoResponse countBorrowCreditInfo4admin(@RequestBody BorrowCreditAmRequest request){
        AdminBorrowCreditInfoResponse response = new AdminBorrowCreditInfoResponse();
        Integer count = borrowCreditService.countBorrowCreditInfo4Admin(request);
        response.setCount(count);
        return response;
    }

    /**
     * admin: 查询汇转让明细list
     * @author zhangyk
     * @date 2018/7/10 19:17
     */
    @PostMapping("/searchBorrowCreditInfo4admin")
    public AdminBorrowCreditInfoResponse searchBorrowCreditInfo4admin(@RequestBody BorrowCreditAmRequest request){
        AdminBorrowCreditInfoResponse response = new AdminBorrowCreditInfoResponse();
        List<AdminBorrowCreditCustomize> list = borrowCreditService.getBorrowCreditInfo4Admin(request);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,BorrowCreditInfoVO.class));
        }
        return response;
    }


    /**
     * admin: 查询汇转让明细list
     * @author zhangyk
     * @date 2018/7/10 19:17
     */
    @PostMapping("/sumBorrowCreditInfo4admin")
    public AdminBorrowCreditInfoResponse sumBorrowCreditInfo4admin(@RequestBody BorrowCreditAmRequest request){
        AdminBorrowCreditInfoResponse response = new AdminBorrowCreditInfoResponse();
        BorrowCreditInfoSumVO sumVO = borrowCreditService.sumBorrowCreditInfoData(request);
        response.setSumData(sumVO);
        return response;
    }


    /**
     * 根据userid和tenderNid查询出借列表
     * @author zhangyk
     * @date 2018/8/30 11:27
     */
    @GetMapping("/getBorrowCreditListByUserIdAndTenderNid/{userId}/{tenderNid}")
    public BorrowCreditResponse getBorrowCreditList(@PathVariable String userId, @PathVariable String tenderNid) {
        BorrowCreditResponse response = new BorrowCreditResponse();
        List<BorrowCredit> borrowCredits =borrowCreditService.getBorrowCreditList(userId,tenderNid);
        if(CollectionUtils.isNotEmpty(borrowCredits)) {
            response.setResultList(CommonUtils.convertBeanList(borrowCredits, BorrowCreditVO.class));
        }
        return response;
    }


    /**
     * 根据credit查询出借列表
     * @author zhangyk
     * @date 2018/8/30 13:27
     */
    @GetMapping("/getBorrowCreditListByCreditNid/{creditNid}")
    public BorrowCreditResponse getBorrowCreditList( @PathVariable String creditNid) {
        BorrowCreditResponse response = new BorrowCreditResponse();
        List<BorrowCredit> borrowCredits =borrowCreditService.getBorrowCreditListByCreditNid(creditNid);
        if(CollectionUtils.isNotEmpty(borrowCredits)) {
            response.setResultList(CommonUtils.convertBeanList(borrowCredits, BorrowCreditVO.class));
        }
        return response;
    }



    
}
