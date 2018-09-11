package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.resquest.trade.BorrowProjectTypeRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowProjectRepayMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowProjectTypeMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowStyleMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhAssetTypeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowProjectTypeCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.borrow.BorrowProjectTypeService;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  项目类型
 * @author zhangyk
 * @date 2018/6/25 11:25
 */
@Service
public class BorrowProjectTypeImpl implements BorrowProjectTypeService {

    @Autowired
    BorrowProjectTypeMapper  borrowProjectTypeMapper;
    @Autowired
    BorrowProjectRepayMapper borrowProjectRepayMapper;
    @Autowired
    BorrowStyleMapper borrowStyleMapper;
    @Autowired
    HjhAssetTypeMapper hjhAssetTypeMapper;
    @Autowired
    BorrowProjectTypeCustomizeMapper borrowProjectTypeCustomizeMapper;
    @Override
    public BorrowProjectType getProjectTypeByBorrowNid(String borrowNid) {
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(CustomConstants.FALG_NOR).andBorrowClassEqualTo(borrowNid.substring(0, 3));
        List<BorrowProjectType> borrowProjectTypes = this.borrowProjectTypeMapper.selectByExample(example);
        BorrowProjectType borrowProjectType = new BorrowProjectType();
        if (borrowProjectTypes != null && borrowProjectTypes.size() > 0) {
            borrowProjectType = borrowProjectTypes.get(0);
        }
        return borrowProjectType;
    }
    /*
    * 分页查询分项目类型
    * */
    @Override
    public List<BorrowProjectTypeVO>  selectProjectTypeList(BorrowProjectTypeVO borrowProjectTypeVO){
        return borrowProjectTypeCustomizeMapper.selectProjectTypeList(borrowProjectTypeVO);
    }

    /*
    * 根据id查询项目类型
    * */
    @Override
    public BorrowProjectType selectProjectTypeRecord(Integer id){
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
//        cra.andBorrowCdEqualTo(record.getBorrowCd());
        List<BorrowProjectType> BorrowTypeList = borrowProjectTypeMapper.selectByExample(example);
        if (BorrowTypeList != null && BorrowTypeList.size() > 0) {
            return BorrowTypeList.get(0);
        }
        return new BorrowProjectType();
    }


    /**
     * 根据主键判断汇直投项目类型维护中数据是否存在
     * @return
     */
    @Override
    public boolean isExistsRecord(BorrowProjectTypeVO record){
        if (StringUtils.isEmpty(record.getBorrowCd())) {
            return false;
        }
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andBorrowCdEqualTo(record.getBorrowCd());
        List<BorrowProjectType> BorrowTypeList = borrowProjectTypeMapper.selectByExample(example);
        if (BorrowTypeList != null && BorrowTypeList.size() > 0) {
            return true;
        }
        return false;
    }
    /**
     * 获取单个汇直投项目类型维护
     * @return
     */
    @Override
    public BorrowProjectTypeVO getRecord(BorrowProjectTypeVO record){
        BorrowProjectTypeVO borrowProjectType = borrowProjectTypeCustomizeMapper.selectByBorrowCd(record);
        if (borrowProjectType != null ) {
            return borrowProjectType;
        }
        return null;
    }
    /**
     * 根据项目编号查询还款方式
     * @param str
     */
    @Override
    public List<BorrowProjectRepayVO> selectRepay(String str){
        List<BorrowProjectRepayVO> borrowProjectRepayVOS = new ArrayList<>();
        // 封装查询条件
        BorrowProjectRepayExample example = new BorrowProjectRepayExample();
        example.setLimitStart(-1);
        example.setLimitEnd(-1);
        BorrowProjectRepayExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowClassEqualTo(str);
        List<BorrowProjectRepay> borrowProjectRepayS=borrowProjectRepayMapper.selectByExample(example);
        borrowProjectRepayVOS =CommonUtils.convertBeanList(borrowProjectRepayS,BorrowProjectRepayVO.class);
        return borrowProjectRepayVOS;
    }
    /**
     * 查询类型表
     */
    @Override
    public List<BorrowStyleVO> selectStyles(){
        // 封装查询条件
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(0);
        example.setLimitStart(-1);
        example.setLimitEnd(-1);
        List<BorrowStyle> borrowStyleS =this.borrowStyleMapper.selectByExample(example);
        List<BorrowStyleVO> borrowStyleVOS = CommonUtils.convertBeanList(borrowStyleS,BorrowStyleVO.class);
        return borrowStyleVOS;
    }
    /**
     * 汇直投项目类型维护插入
     * @param form
     */
    @Override
    public BorrowProjectTypeResponse insertRecord(BorrowProjectTypeRequest form){
        Date sysDate = new Date();
//        String userId = ShiroUtil.getLoginUserId();
        BorrowProjectType record = new BorrowProjectType();
        BeanUtils.copyProperties(form, record);
        // 插入用户表
        record.setBorrowCd(form.getBorrowCd());
        record.setBorrowProjectType(form.getBorrowProjectType());
        record.setStatus(0);
        record.setCreateTime(sysDate);
        record.setCreateUserId(Integer.valueOf(form.getCreateUserId()));//测试用，联调需改 todo
//        record.setCreateGroupId(userId);//测试用，联调需改 todo
        record.setUpdateTime(sysDate);
        record.setUpdateUserId(Integer.valueOf(form.getCreateUserId()));//测试用，联调需改 todo
        // record.setUpdateGroupId(userId)(userId);//测试用，联调需改 todo
        borrowProjectTypeMapper.insertSelective(record);
        String  methodName = form.getMethodName();
        // 直接插入
        this.insertRepay(record,methodName);
		/*--------------------------add by LSY START-----------------------------------*/
        // 直接插入asset表
        this.insertAsset(form);
		/*--------------------------add by LSY END-----------------------------------*/
		return new BorrowProjectTypeResponse();
    }
    public void insertRepay(BorrowProjectType form,String methodName) {
        BorrowProjectRepay repay = new BorrowProjectRepay();
        String[] split = methodName.split(",");
        for (String str : split) {
            repay.setRepayMethod(str);
            repay.setBorrowClass(form.getBorrowClass());
            // 查询存取
            BorrowStyleExample example = new BorrowStyleExample();
            BorrowStyleExample.Criteria criteria = example.createCriteria();
            criteria.andNidEqualTo(str);
            List<BorrowStyle> list = this.borrowStyleMapper.selectByExample(example);
            repay.setMethodName(list.get(0).getName());
            repay.setDelFlag(0);
            // 插入数据
            borrowProjectRepayMapper.insertSelective(repay);
        }
    }

    /**
     * 执行前每个方法前需要添加BusinessDesc描述
     * @param form
     * @author PC-LIUSHOUYI
     */
    public void insertAsset(BorrowProjectTypeRequest form) {
        HjhAssetType record = new HjhAssetType();
        record.setCreateUser(Integer.parseInt(form.getCreateUserId()));
        //汇赢金服的类型
        record.setInstCode(CustomConstants.INST_CODE_HYJF);
        //项目类型里编号存放string类型、tinyint类型最大值存放127
        record.setAssetType(Integer.parseInt(form.getBorrowCd()));
        //名称
        record.setAssetTypeName(form.getBorrowName());
        //状态
        record.setStatus(CustomConstants.FLAG_STATUS_ENABLE);
        //时间戳是integer类型
        record.setCreateTime(new Date());
        record.setDelFlag(CustomConstants.FLAG_STATUS_ENABLE);

        hjhAssetTypeMapper.insertSelective(record);
    }
    /**
     * 汇直投项目类型维护插入
     *
     * @param form
     */
    @Override
    public  BorrowProjectTypeResponse updateRecord(BorrowProjectTypeRequest form){
        BorrowProjectType record = new BorrowProjectType();
        BeanUtils.copyProperties(form, record);
        Date sysDate = new Date();
        record.setUpdateUserId(Integer.valueOf(form.getUpdateUserId()));
        record.setUpdateTime(sysDate);
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andBorrowCdEqualTo(record.getBorrowCd());
        if(record.getInterestCoupon()==null|| "".equals(record.getInterestCoupon())){
            record.setInterestCoupon(0);
        }
        if(record.getTasteMoney()==null|| "".equals(record.getTasteMoney())){
            record.setTasteMoney(0);
        }
        this.borrowProjectTypeMapper.updateByExampleSelective(record, example);
        // 先删除再插入数据
        this.delectRepay(record.getBorrowClass());
        String  methodName = form.getMethodName();
        this.insertRepay(record,methodName);
		/*--------------------------add by LSY START-----------------------------------*/
        //asset表更新处理
        this.updateAsset(record);
		/*--------------------------add by LSY END-----------------------------------*/
		return new BorrowProjectTypeResponse();
    }

    public void delectRepay(String borrowClass) {
        BorrowProjectRepayExample example = new BorrowProjectRepayExample();
        BorrowProjectRepayExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowClassEqualTo(borrowClass);
        borrowProjectRepayMapper.deleteByExample(example);
    }
    /**
     * 执行前每个方法前需要添加BusinessDesc描述
     * @param form
     * @author PC-LIUSHOUYI
     */

    public void updateAsset(BorrowProjectType form) {
        HjhAssetTypeExample example = new HjhAssetTypeExample();
        HjhAssetTypeExample.Criteria cra = example.createCriteria();
        cra.andAssetTypeEqualTo(Integer.parseInt(form.getBorrowCd()));
        cra.andInstCodeEqualTo(CustomConstants.INST_CODE_HYJF);

        HjhAssetType record = new HjhAssetType();
        //更新用户id
        record.setUpdateUser(form.getUpdateUserId());
        //更新时间
        record.setUpdateTime(new Date());
        //名称
        record.setAssetTypeName(form.getBorrowName());
        //状态
        record.setStatus(CustomConstants.FLAG_STATUS_ENABLE);
        //删除flag
//        record.setDelFlg(0);

        hjhAssetTypeMapper.updateByExampleSelective(record, example);

    }
    /**
     * 汇直投项目类型维护删除
     * @param borrowCd
     */
    @Override
    public void deleteProjectType(String borrowCd){
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andBorrowCdEqualTo(borrowCd);
        borrowProjectTypeMapper.deleteByExample(example);
    }
    /**
     * 汇直投项目类型维护删除
     * @param assetType
     */
    @Override
    public void deleteAsset(Integer assetType){
        HjhAssetTypeExample example = new HjhAssetTypeExample();
        HjhAssetTypeExample.Criteria cra = example.createCriteria();
        cra.andAssetTypeEqualTo(assetType);
        cra.andInstCodeEqualTo(CustomConstants.INST_CODE_HYJF);
        hjhAssetTypeMapper.deleteByExample(example);
    }
    /**
     * 检查项目名称唯一性
     * @param borrowCd
     * @return
     */
    @Override
    public int borrowCdIsExists( String borrowCd){
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowCdEqualTo(borrowCd);
        return borrowProjectTypeMapper.countByExample(example);
    }
}