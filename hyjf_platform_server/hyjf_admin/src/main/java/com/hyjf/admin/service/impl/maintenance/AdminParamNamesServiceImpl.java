/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.maintenance;

import com.hyjf.admin.service.impl.BaseAdminServiceImpl;
import com.hyjf.admin.service.maintenance.AdminParamNamesService;
import com.hyjf.am.resquest.admin.AdminParamNameRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: AdminParamNamesServiceImpl, v0.1 2018/9/6 11:03
 */
@Service
public class AdminParamNamesServiceImpl extends BaseAdminServiceImpl implements AdminParamNamesService {

    /**
     * 查询数据字典条数
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getParamNamesCount(AdminParamNameRequest request) {
        return amAdminClient.getParamNamesCount(request);
    }

    /**
     * 查询数据字典列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<ParamNameVO> searchParamNamesList(AdminParamNameRequest request) {
        return amAdminClient.searchParamNamesList(request);
    }

    /**
     * 添加数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean insertParamName(ParamNameVO paramNameVO) {
        this.checkParams(paramNameVO);
        boolean isExistsParamName = amAdminClient.isExistsParamName(paramNameVO);
        logger.info("isExistsParamName:[{}]",isExistsParamName);
        CheckUtil.check(isExistsParamName!=true,MsgEnum.ERR_OBJECT_EXISTS,"数据字典");
        if (StringUtils.isBlank(paramNameVO.getOther1())) {
            paramNameVO.setOther1(StringUtils.EMPTY);
        }
        if (StringUtils.isBlank(paramNameVO.getOther2())) {
            paramNameVO.setOther2(StringUtils.EMPTY);
        }
        if (StringUtils.isBlank(paramNameVO.getOther3())) {
            paramNameVO.setOther3(StringUtils.EMPTY);
        }
        paramNameVO.setDelFlag(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        Date nowTime = new Date();
        paramNameVO.setCreateTime(nowTime);
        paramNameVO.setUpdateTime(nowTime);
        boolean isSuccess = amAdminClient.insertParamName(paramNameVO)>0;
        if(isSuccess){
            String result = RedisUtils.hget(RedisConstants.CACHE_PARAM_NAME + paramNameVO.getNameClass(),paramNameVO.getNameCd());
            logger.info("redis result:[{}]",result);
            if(StringUtils.isBlank(result)){
                RedisUtils.hset(RedisConstants.CACHE_PARAM_NAME + paramNameVO.getNameClass(),paramNameVO.getNameCd(),paramNameVO.getName());
                return true;
            }else{
                logger.info("插入数据库成功，但插入redis失败。原因:redis已存在该值");
                throw new CheckException("99","插入数据库成功，但插入redis失败。原因:redis已存在该值");
            }
        }
        return isSuccess;
    }

    /**
     * 修改数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean updateParamName(ParamNameVO paramNameVO) {
        this.checkParams(paramNameVO);
        boolean isSuccess = amAdminClient.updateParamName(paramNameVO)>0;
        if(isSuccess){
            String result = RedisUtils.hget(RedisConstants.CACHE_PARAM_NAME + paramNameVO.getNameClass(),paramNameVO.getNameCd());
            logger.info("redis result:[{}]",result);
            if(StringUtils.isBlank(result)){
                throw new CheckException("99","数据不存在");
            }else{
                RedisUtils.hset(RedisConstants.CACHE_PARAM_NAME + paramNameVO.getNameClass(),paramNameVO.getNameCd(),paramNameVO.getName());
                return true;
            }
        }
        return isSuccess;
    }

    /**
     * 检查参数
     * @auth sunpeikai
     * @param
     * @return
     */
    private void checkParams(ParamNameVO paramNameVO){
        CheckUtil.check(StringUtils.isNotBlank(paramNameVO.getNameClass()),MsgEnum.ERR_OBJECT_REQUIRED,"字典区分");
        CheckUtil.check(StringUtils.isNotBlank(paramNameVO.getNameCd()),MsgEnum.ERR_OBJECT_REQUIRED,"字典编号");
        CheckUtil.check(StringUtils.isNotBlank(paramNameVO.getName()),MsgEnum.ERR_OBJECT_REQUIRED,"字典名称");
        CheckUtil.check(paramNameVO.getNameClass().length()<=20,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"字典区分");
        CheckUtil.check(paramNameVO.getNameCd().length()<=6,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"字典编号");
        CheckUtil.check(paramNameVO.getName().length()<=100,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"字典名称");
        CheckUtil.check(paramNameVO.getOther1().length()<=255,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"扩展项目1");
        CheckUtil.check(paramNameVO.getOther2().length()<=255,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"扩展项目2");
        CheckUtil.check(paramNameVO.getOther3().length()<=255,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"扩展项目3");
        CheckUtil.check(paramNameVO.getSort()<=100,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"排序");
    }

    /**
     * 删除数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean deleteParamName(ParamNameVO paramNameVO) {
        //检查删除所需要的联合主键参数
        CheckUtil.check(StringUtils.isNotBlank(paramNameVO.getNameClass()),MsgEnum.ERR_OBJECT_REQUIRED,"字典区分");
        CheckUtil.check(StringUtils.isNotBlank(paramNameVO.getNameCd()),MsgEnum.ERR_OBJECT_REQUIRED,"字典编号");
        ParamNameVO exist = amAdminClient.searchParamNameByKey(paramNameVO);
        if(exist != null){
            boolean isSuccess = amAdminClient.deleteParamName(paramNameVO)>0;
            if(isSuccess){
                return RedisUtils.hdel(RedisConstants.CACHE_PARAM_NAME + paramNameVO.getNameClass(),paramNameVO.getNameCd())>0;
            }
            return isSuccess;
        }else{
            throw new CheckException("99","数据不存在");
        }
    }

    /**
     * 根据联合主键查询一条数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public ParamNameVO selectParamName(ParamNameVO paramNameVO) {
        //检查联合主键参数
        CheckUtil.check(StringUtils.isNotBlank(paramNameVO.getNameClass()),MsgEnum.ERR_OBJECT_REQUIRED,"字典区分");
        CheckUtil.check(StringUtils.isNotBlank(paramNameVO.getNameCd()),MsgEnum.ERR_OBJECT_REQUIRED,"字典编号");
        return amAdminClient.searchParamNameByKey(paramNameVO);
    }
    /**
     * 同步数据字典至redis
     * @auth wgx
     * @return
     */
    @Override
    public boolean syncParam() {
        return amAdminClient.syncParam();
    }
}
