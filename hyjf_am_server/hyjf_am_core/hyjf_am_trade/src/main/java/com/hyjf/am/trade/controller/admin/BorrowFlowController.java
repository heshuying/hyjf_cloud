package com.hyjf.am.trade.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBorrowFlowResponse;
import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.service.admin.BorrowFlowService;
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
    private static Logger logger = LoggerFactory.getLogger(InstConfigController.class);
    /**
     * 项目类型
     * @return
     */
    @RequestMapping("/borrowProjectTypeList/{borrowTypeCd}")
    public List<BorrowProjectTypeVO> selectBorrowProjectTypeList(@PathVariable String borrowTypeCd) {
        logger.info("项目类型..." + JSONObject.toJSON(borrowTypeCd));
        return borrowFlowService.selectBorrowProjectTypeList(borrowTypeCd);
    }
    /**
     * 资金来源
     * @param instCode
     * @return
     */
    @RequestMapping("/hjhInstConfigList/{instCode}")
    public List<HjhInstConfigVO> hjhInstConfigList(@PathVariable String instCode){
        return borrowFlowService.hjhInstConfigList(instCode);
    }

    /**
     * 根据表的类型,期数,项目类型检索管理费件数
     * @param instCode assetType
     * @return
     */
    @RequestMapping("/countRecordByPK/{instCode}/{assetType}")
    public int countRecordByPK(@PathVariable String instCode, @PathVariable Integer assetType){
        return borrowFlowService.countRecordByPK(instCode,assetType);
    }
    /**
     * 根据资金来源查询产品类型
     * @param instCode
     * @return
     */
    @RequestMapping("/hjhAssetTypeList/{instCode}")
    public List<HjhAssetTypeVO> hjhAssetTypeList(@PathVariable String instCode){
        return borrowFlowService.hjhAssetTypeList(instCode);
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
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), total);
            List<HjhAssetBorrowtype> recordList =
                    this.borrowFlowService.getRecordList(adminRequest, paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<HjhAssetBorrowTypeVO>  hjhAssetBorrowTypeVOS = CommonUtils.convertBeanList(recordList,HjhAssetBorrowTypeVO.class);
                response.setResultList(hjhAssetBorrowTypeVOS);
                response.setRtn(Response.SUCCESS);
                return response;
            }
            return null;
        }
        return null;
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
                result.setRtn(Response.SUCCESS);
            }
            return result;
        }
        return null;
    }
    /**
     * 添加
     * @param adminRequest
     * @return
     */
    @RequestMapping("/insertRecord")
    public AdminBorrowFlowResponse insertRecord(@RequestBody  AdminBorrowFlowRequest adminRequest){
        AdminBorrowFlowResponse result=new AdminBorrowFlowResponse();
        try{
            // 插入
            this.borrowFlowService.insertRecord(adminRequest);
            result.setRtn(Response.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 修改
     * @param adminRequest
     * @return
     */
    @RequestMapping("/updateRecord")
    public AdminBorrowFlowResponse updateRecord(@RequestBody  AdminBorrowFlowRequest adminRequest){
        AdminBorrowFlowResponse resp = new AdminBorrowFlowResponse();
        try{
            // 修改
            this.borrowFlowService.updateRecord(adminRequest);
            resp.setRtn(Response.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resp;
    }
    /**
     * 删除
     * @param adminRequest
     * @return
     */
    @RequestMapping("/deleteRecord")
    public AdminBorrowFlowResponse deleteRecord(@RequestBody  AdminBorrowFlowRequest adminRequest){
        AdminBorrowFlowResponse resp = new AdminBorrowFlowResponse();
        try{
            if(adminRequest.getId() != null){
                this.borrowFlowService.deleteRecord(adminRequest.getId());
                resp.setRtn(Response.SUCCESS);
            }
            resp.setRtn(Response.FAIL);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resp;
    }
}
