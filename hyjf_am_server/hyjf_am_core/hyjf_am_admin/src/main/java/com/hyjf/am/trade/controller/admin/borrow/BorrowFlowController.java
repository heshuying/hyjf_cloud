package com.hyjf.am.trade.controller.admin.borrow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBorrowFlowResponse;
import com.hyjf.am.response.admin.HjhAssetTypeResponse;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.service.admin.borrow.BorrowFlowService;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/30.
 */
@RestController
@RequestMapping("/am-trade/config/borrowflow")
public class BorrowFlowController {

    @Autowired
    private BorrowFlowService borrowFlowService;
    private static Logger logger = LoggerFactory.getLogger(BorrowFlowService.class);
    /**
     * 项目类型
     * @return
     */
    @RequestMapping("/borrowProjectTypeList/{borrowTypeCd}")
    public BorrowProjectTypeResponse selectBorrowProjectTypeList(@PathVariable String borrowTypeCd) {
        logger.info("项目类型..." + JSONObject.toJSON(borrowTypeCd));
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
        List<BorrowProjectTypeVO> vo =borrowFlowService.selectBorrowProjectTypeList(borrowTypeCd);
        if(CollectionUtils.isEmpty(vo)){
            return response;
        }
        response.setResultList(vo);
        return response;
    }
    /**
     * 资金来源
     * @return
     */
    @RequestMapping("/hjhInstConfigList")
    public HjhInstConfigResponse hjhInstConfigList(){
        List<HjhInstConfigVO> vo =borrowFlowService.hjhInstConfigList("");
        HjhInstConfigResponse response = new HjhInstConfigResponse();
        if(CollectionUtils.isEmpty(vo)){
            return response;
        }
        response.setResultList(vo);
        return response;
    }

    /**
     * 根据表的类型,期数,项目类型检索管理费件数
     * @param instCode assetType
     * @return
     */
    @RequestMapping("/countRecordByPK/{instCode}/{assetType}")
    public IntegerResponse countRecordByPK(@PathVariable String instCode, @PathVariable Integer assetType){
        IntegerResponse response=new IntegerResponse();
        int count =borrowFlowService.countRecordByPK(instCode,assetType);
        response.setResultInt(count);
        return response;
    }
    /**
     * 根据资金来源查询产品类型
     * @param instCode
     * @return
     */
    @RequestMapping("/hjhAssetTypeList/{instCode}")
    public HjhAssetTypeResponse hjhAssetTypeList(@PathVariable String instCode){
        HjhAssetTypeResponse response = new HjhAssetTypeResponse();
        List<HjhAssetTypeVO> hjhAssetTypeVOS = borrowFlowService.hjhAssetTypeList(instCode);
        if(CollectionUtils.isEmpty(hjhAssetTypeVOS)){
            return response;
        }
        response.setResultList(hjhAssetTypeVOS);
        return response;
    }
    /**
     * 分页查询
     * @param adminRequest
     * @return
     */
    @RequestMapping("/selectBorrowFlowList")
    public AdminBorrowFlowResponse selectBorrowFlowList(@RequestBody  AdminBorrowFlowRequest adminRequest){
        AdminBorrowFlowResponse response = new AdminBorrowFlowResponse();
        int total = this.borrowFlowService.countRecord(adminRequest);
        if (total > 0) {
            response.setTotal(total);
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), total,adminRequest.getPageSize() == 0?10:adminRequest.getPageSize());
            List<HjhAssetBorrowtype> recordList =
                    this.borrowFlowService.getRecordList(adminRequest, paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<HjhAssetBorrowTypeVO>  hjhAssetBorrowTypeVOS = CommonUtils.convertBeanList(recordList,HjhAssetBorrowTypeVO.class);
                response.setResultList(hjhAssetBorrowTypeVOS);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
    /**
     *详情
     * @param adminRequest
     * @return
     */
    @RequestMapping("/info")
    public AdminBorrowFlowResponse selectBorrowFlowInfo(@RequestBody  AdminBorrowFlowRequest adminRequest){
        logger.info("分账名单详情页面..." + JSONObject.toJSON(adminRequest));
        AdminBorrowFlowResponse result=new AdminBorrowFlowResponse();
        if (adminRequest.getId() != null) {
            HjhAssetBorrowtype record = this.borrowFlowService.selectBorrowFlowInfo(adminRequest.getId());
            HjhAssetBorrowTypeVO recordVo = new HjhAssetBorrowTypeVO();
            if(null != record){
                BeanUtils.copyProperties(record, recordVo);
                result.setResult(recordVo);
            }
        }
        return result;
    }
    /**
     * 添加
     * @param adminRequest
     * @return
     */
    @RequestMapping("/insertRecord")
    public AdminBorrowFlowResponse insertRecord(@RequestBody  AdminBorrowFlowRequest adminRequest){
        AdminBorrowFlowResponse result=new AdminBorrowFlowResponse();
        // 插入
        int cou= this.borrowFlowService.insertRecord(adminRequest);
        if(cou > 0){
            result.setRtn(Response.SUCCESS);
            return result;
        }
        result.setRtn(Response.FAIL);
        result.setMessage(Response.FAIL_MSG);
        return result;
    }
    /**
     * 修改
     * @param adminRequest
     * @return
     */
    @RequestMapping("/updateRecord")
    public AdminBorrowFlowResponse updateRecord(@RequestBody  AdminBorrowFlowRequest adminRequest){
        AdminBorrowFlowResponse result = new AdminBorrowFlowResponse();
        // 修改
        int cou= this.borrowFlowService.updateRecord(adminRequest);
        if(cou > 0){
            result.setRtn(Response.SUCCESS);
            return result;
        }
        result.setRtn(Response.FAIL);
        result.setMessage(Response.FAIL_MSG);
        return result;
    }
    /**
     * 删除
     * @param adminRequest
     * @return
     */
    @RequestMapping("/deleteRecord")
    public AdminBorrowFlowResponse deleteRecord(@RequestBody  AdminBorrowFlowRequest adminRequest){
        AdminBorrowFlowResponse resp = new AdminBorrowFlowResponse();
        if(adminRequest.getId() != null){
            int cou= this.borrowFlowService.deleteRecord(adminRequest.getId());
            if(cou > 0){
                resp.setRtn(Response.SUCCESS);
                return resp;
            }
            resp.setRtn(Response.FAIL);
            resp.setMessage(Response.FAIL_MSG);
            return resp;
        }
        resp.setRtn(Response.FAIL);
        resp.setMessage(Response.FAIL_MSG);
        return resp;
    }
}