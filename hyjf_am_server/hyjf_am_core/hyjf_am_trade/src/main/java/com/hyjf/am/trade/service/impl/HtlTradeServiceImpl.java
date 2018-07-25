/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.user.HtlTradeRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.ProductIntoRecordCustomize;
import com.hyjf.am.trade.dao.model.customize.ProductRedeemCustomize;
import com.hyjf.am.trade.service.HtlTradeService;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: HtlTradeServiceImpl, v0.1 2018/7/25 15:57
 */
@Service
public class HtlTradeServiceImpl extends BaseServiceImpl implements HtlTradeService {
    @Override
    public Integer countHtlIntoRecord(HtlTradeRequest htlTradeRequest) {
        ProductListExample example = convertProductListExample(htlTradeRequest);
        return productListMapper.countByExample(example);
    }

    @Override
    public List<ProductIntoRecordCustomize> getIntoRecordList(HtlTradeRequest htlTradeRequest) {
        ProductListExample example = convertProductListExample(htlTradeRequest);
        List<ProductList> productLists = productListMapper.selectByExample(example);
        List<ProductIntoRecordCustomize> productIntoRecordCustomizes = CommonUtils.convertBeanList(productLists,ProductIntoRecordCustomize.class);
        for(ProductIntoRecordCustomize productIntoRecordCustomize:productIntoRecordCustomizes){
            logger.info("数据库获取并转换bean以后的时间戳[{}]",productIntoRecordCustomize.getInvestTime());
            productIntoRecordCustomize.setInvestTime(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(Integer.valueOf(productIntoRecordCustomize.getInvestTime())));
            // 获取部门名称
            String[] department = getDepartment(productIntoRecordCustomize.getDepartment());
            productIntoRecordCustomize.setDepartmentName(department[0]);
            productIntoRecordCustomize.setBranceName(department[1]);
            productIntoRecordCustomize.setRegionName(department[2]);
        }
        return productIntoRecordCustomizes;
    }

    @Override
    public Integer countProductRedeemRecord(HtlTradeRequest htlTradeRequest) {
        ProductRedeemExample example = convertProductRedeemExample(htlTradeRequest);
        return productRedeemMapper.countByExample(example);
    }

    @Override
    public List<ProductRedeemCustomize> getRedeemRecordList(HtlTradeRequest htlTradeRequest) {
        ProductRedeemExample example = convertProductRedeemExample(htlTradeRequest);
        List<ProductRedeem> productRedeems = productRedeemMapper.selectByExample(example);
        List<ProductRedeemCustomize> productIntoRecordCustomizes = CommonUtils.convertBeanList(productRedeems,ProductRedeemCustomize.class);
        for(ProductRedeemCustomize productRedeemCustomize:productIntoRecordCustomizes){
            logger.info("数据库获取并转换bean以后的时间戳[{}]",productRedeemCustomize.getRedeemTime());
            productRedeemCustomize.setRedeemTime(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(Integer.valueOf(productRedeemCustomize.getRedeemTime())));
            // 获取部门名称
            String[] department = getDepartment(productRedeemCustomize.getDepartment());
            productRedeemCustomize.setDepartmentName(department[0]);
            productRedeemCustomize.setBranceName(department[1]);
            productRedeemCustomize.setRegionName(department[2]);
        }
        return productIntoRecordCustomizes;
    }

    private String[] getDepartment(Integer id){
        String[] department = new String[3];
        ROaDepartmentExample departmentExample = new ROaDepartmentExample();
        ROaDepartmentExample.Criteria departmentExampleCriteria = departmentExample.createCriteria();
        departmentExampleCriteria.andIdEqualTo(id);
        List<ROaDepartment> departmentList = rOaDepartmentMapper.selectByExample(departmentExample);
        if(!CollectionUtils.isEmpty(departmentList)){
            department[0] = departmentList.get(0).getName();

            ROaDepartmentExample branceExample = new ROaDepartmentExample();
            ROaDepartmentExample.Criteria branceExampleCriteria = branceExample.createCriteria();
            branceExampleCriteria.andParentidEqualTo(id);
            List<ROaDepartment> branceList = rOaDepartmentMapper.selectByExample(branceExample);
            if(!CollectionUtils.isEmpty(branceList)) {
                department[1] = branceList.get(0).getName();

                ROaDepartmentExample regionExample = new ROaDepartmentExample();
                ROaDepartmentExample.Criteria regionCriteria = regionExample.createCriteria();
                regionCriteria.andParentidEqualTo(id);
                List<ROaDepartment> regionList = rOaDepartmentMapper.selectByExample(regionExample);
                if(!CollectionUtils.isEmpty(branceList)) {
                    department[2] = regionList.get(0).getName();
                }
            }
        }
        return department;
    }

    /**
     * request转example
     * @auth sunpeikai
     * @param
     * @return
     */
    private ProductListExample convertProductListExample(HtlTradeRequest request){
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        if(null != request.getUserId()){
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if(StringUtils.isNotBlank(request.getClient())){
            criteria.andClientEqualTo(Integer.valueOf(request.getClient()));
        }
        if(request.getStatus() != null){
            criteria.andInvestStatusEqualTo(request.getStatus());
        }
        // 添加时间开始
        if (StringUtils.isNotEmpty(request.getTimeStartSrch())) {

            criteria.andInvestTimeGreaterThanOrEqualTo(GetDate.strYYYYMMDD2Timestamp2(request.getTimeStartSrch()));
        }
        // 添加时间结束
        if (StringUtils.isNotEmpty(request.getTimeEndSrch())) {
            criteria.andInvestTimeLessThan(GetDate.strYYYYMMDD2Timestamp2(request.getTimeEndSrch()));
        }
        example.setOrderByClause("invest_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        return example;
    }
    /**
     * request转example
     * @auth sunpeikai
     * @param
     * @return
     */
    private ProductRedeemExample convertProductRedeemExample(HtlTradeRequest request){
        ProductRedeemExample example = new ProductRedeemExample();
        ProductRedeemExample.Criteria criteria = example.createCriteria();

        if(null != request.getUserId()){
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if(StringUtils.isNotBlank(request.getClient())){
            criteria.andClientEqualTo(Integer.valueOf(request.getClient()));
        }
        if(request.getStatus() != null){
            criteria.andStatusEqualTo(request.getStatus());
        }
        // 添加时间开始
        if (StringUtils.isNotEmpty(request.getTimeStartSrch())) {

            criteria.andRedeemTimeGreaterThanOrEqualTo(GetDate.strYYYYMMDD2Timestamp2(request.getTimeStartSrch()));
        }
        // 添加时间结束
        if (StringUtils.isNotEmpty(request.getTimeEndSrch())) {
            criteria.andRedeemTimeLessThan(GetDate.strYYYYMMDD2Timestamp2(request.getTimeEndSrch()));
        }
        example.setOrderByClause("invest_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }

        return example;
    }
}
