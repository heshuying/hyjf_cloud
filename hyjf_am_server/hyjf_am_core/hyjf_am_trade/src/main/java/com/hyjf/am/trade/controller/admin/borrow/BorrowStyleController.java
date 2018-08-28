package com.hyjf.am.trade.controller.admin.borrow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBorrowStyleResponse;
import com.hyjf.am.response.admin.AdminInstConfigListResponse;
import com.hyjf.am.resquest.admin.AdminBorrowStyleRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleWithBLOBs;
import com.hyjf.am.trade.service.front.borrow.BorrowStyleService;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/12.
 */
@RestController
@RequestMapping("/am-trade/config/borrowstyle")
public class BorrowStyleController {

    @Autowired
    private BorrowStyleService borrowStyleService;
    private static Logger logger = LoggerFactory.getLogger(BorrowStyleController.class);
    /**
     * 分页查询配置中心还款方式列表
     * @return
     */
    @RequestMapping("/selectBorrowStyleListByPage")
    public AdminBorrowStyleResponse selectBorrowStyleListByPage(@RequestBody AdminBorrowStyleRequest adminRequest) {
        logger.info("还款方式列表..." + JSONObject.toJSON(adminRequest));
        AdminBorrowStyleResponse  response =new AdminBorrowStyleResponse();
        //查询保证金配置条数
        int recordTotal = this.borrowStyleService.getBorrowStyleCount();
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(adminRequest.getCurrPage(),recordTotal, adminRequest.getPageSize() == 0?10:adminRequest.getPageSize());
            //查询记录
            List<BorrowStyleWithBLOBs> recordList =borrowStyleService.selectBorrowStyleListByPage(adminRequest,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<BorrowStyleVO> hicv = CommonUtils.convertBeanList(recordList, BorrowStyleVO.class);
                response.setResultList(hicv);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
                return response;
            }
            response.setRtn(Response.SUCCESS);
            response.setMessage("查询的数据为空！");
            return response;
        }
        return null;
    }
    /**
     * 查询配置中心还款方式详情页面
     * @return
     */
    @RequestMapping("/searchBorrowStyleInfo")
    public AdminBorrowStyleResponse searchBorrowStyleInfo(@RequestBody  AdminBorrowStyleRequest adminRequest) {
        logger.info("还款方式详情页面..." + JSONObject.toJSON(adminRequest));
        AdminBorrowStyleResponse  response =new AdminBorrowStyleResponse();
        if (adminRequest.getId() != null&&adminRequest.getId().intValue() >0) {
            BorrowStyleWithBLOBs record = this.borrowStyleService.searchBorrowStyleInfoById(adminRequest.getId());
            if(null != record){
                BorrowStyleVO res = new BorrowStyleVO();
                BeanUtils.copyProperties(record,res);
                response.setResult(res);
                response.setRtn(Response.SUCCESS);
                return response;
            }
            response.setRtn(Response.SUCCESS);
            response.setMessage("查询的数据为空！");
            return response;
        }
       return null;
    }

    /**
     * 添加配置中心还款方式
     * @return
     */
    @RequestMapping("/insertBorrowStyle")
    public AdminBorrowStyleResponse insertBorrowStyle(@RequestBody  AdminBorrowStyleRequest adminRequest) {
        logger.info("添加还款方式..." + JSONObject.toJSON(adminRequest));
        AdminBorrowStyleResponse  response =new AdminBorrowStyleResponse();
        this.borrowStyleService.insertBorrowStyle(adminRequest);
        //查询保证金配置条数
        int recordTotal = this.borrowStyleService.getBorrowStyleCount();
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(adminRequest.getCurrPage(),recordTotal, adminRequest.getPageSize());
            //查询记录
            List<BorrowStyleWithBLOBs> recordList =borrowStyleService.selectBorrowStyleListByPage(adminRequest,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<BorrowStyleVO> hicv = CommonUtils.convertBeanList(recordList, BorrowStyleVO.class);
                response.setResultList(hicv);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
                return response;
            }
            response.setRtn(Response.SUCCESS);
            response.setMessage("查询的数据为空！");
            return response;
        }
        return null;
    }
    /**
     * 修改配置中心还款方式
     * @return
     */
    @RequestMapping("/updateBorrowStyle")
    public AdminBorrowStyleResponse updateBorrowStyle(@RequestBody  AdminBorrowStyleRequest adminRequest) {
        logger.info("修改还款方式..." + JSONObject.toJSON(adminRequest));
        AdminBorrowStyleResponse  response =new AdminBorrowStyleResponse();
        this.borrowStyleService.updateBorrowStyleById(adminRequest);
        //查询保证金配置条数
        int recordTotal = this.borrowStyleService.getBorrowStyleCount();
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(adminRequest.getCurrPage(),recordTotal, adminRequest.getPageSize());
            //查询记录
            List<BorrowStyleWithBLOBs> recordList =borrowStyleService.selectBorrowStyleListByPage(adminRequest,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<BorrowStyleVO> hicv = CommonUtils.convertBeanList(recordList, BorrowStyleVO.class);
                response.setResultList(hicv);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
                return response;
            }
            response.setRtn(Response.SUCCESS);
            response.setMessage("查询的数据为空！");
            return response;
        }
        return null;
    }
    /**
     * 删除还款方式
     * @param id
     */
    @RequestMapping("/deleteBorrowStyle")
    public AdminInstConfigListResponse deleteBorrowStyle(@RequestBody Integer id) {
        AdminInstConfigListResponse resp = new AdminInstConfigListResponse();
        this.borrowStyleService.deleteBorrowStyleById(id);
        resp.setRtn(Response.SUCCESS);
        return  resp;
    }
    /**
     * 修改还款方式
     * @param id
     */
    @RequestMapping("/modifyBorrowStyle")
    public AdminBorrowStyleResponse modifyBorrowStyle(@RequestBody Integer id) {
        AdminBorrowStyleResponse  response =new AdminBorrowStyleResponse();
        BorrowStyleWithBLOBs record =this.borrowStyleService.searchBorrowStyleInfoById(id);
        if (record.getStatus() == 1) {
            record.setStatus(0);
        } else {
            record.setStatus(1);
        }
        AdminBorrowStyleRequest req = new AdminBorrowStyleRequest();
        BeanUtils.copyProperties(record,req);
        this.borrowStyleService.updateBorrowStyleById(req);
        response.setRtn(Response.SUCCESS);
        return  response;
    }
    /**
     * 根据主键判断权限维护中权限是否存在
     *
     * @return
     */
    @RequestMapping("/validatorFieldCheck")
    public BooleanResponse isExistsPermission(BorrowStyle record){
        BooleanResponse response = new BooleanResponse();
        Boolean b=borrowStyleService.isExistsPermission(record);
        response.setResultBoolean(b);
       return response;
    }

}
