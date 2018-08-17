package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.BankInterfaceMapper;
import com.hyjf.am.config.dao.mapper.customize.BankInterfaceCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.BankInterface;
import com.hyjf.am.config.dao.model.auto.BankInterfaceExample;
import com.hyjf.am.config.service.BankInterfaceService;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BankInterfaceServiceImpl, v0.1 2018/6/22 14:34
 */
@Service
public class BankInterfaceServiceImpl implements BankInterfaceService {
    @Autowired
    protected BankInterfaceMapper bankInterfaceMapper;
    @Autowired
    private BankInterfaceCustomizeMapper bankInterfaceCustomizeMapper;
    @Override
    public Integer getBanksConfigByBankId(String type) {
        BankInterfaceExample bankInterfaceExample = new BankInterfaceExample();
        BankInterfaceExample.Criteria criteria= bankInterfaceExample.createCriteria();
        criteria.andInterfaceTypeEqualTo(type);
        criteria.andIsDeleteEqualTo(0);
        criteria.andIsUsableEqualTo(1);
        bankInterfaceExample.setLimitStart(0);
        bankInterfaceExample.setLimitEnd(1);
        bankInterfaceExample.setOrderByClause("`update_time` DESC");
        List<BankInterface> bankInterfaces = bankInterfaceMapper.selectByExample(bankInterfaceExample);
        if(!bankInterfaces.isEmpty()){
            //返回接口类型
            return bankInterfaces.get(0).getInterfaceStatus();
        }else {
            //默认绑卡旧接口
            return 0;
        }
    }

    /**
     * 查询接口切换列表条数
     * @param paraMap
     * @return
     */
    @Override
    public Integer selectBankInterfaceListCount(Map<String, Object> paraMap){
        return bankInterfaceCustomizeMapper.selectBankInterfaceListCount(paraMap);
    }

    /**
     * 查询接口切换列表
     * @param paraMap
     * @return
     */
    @Override
    public List<BankInterface> selectBankInterfaceListByPage(Map<String, Object> paraMap){
        return bankInterfaceCustomizeMapper.selectBankInterfaceListByPage(paraMap);
    }

    /**
     * 查询接口切换详情
     * @param id
     * @return
     */
    @Override
    public BankInterface selectBankInterfaceInfo(Integer id){
        return bankInterfaceMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改接口切换
     * @param bankInterfaceVO
     */
    @Override
    public void updateBankInterfaceInfo(BankInterfaceVO bankInterfaceVO){
        BankInterface bankInterface =new BankInterface();
        BeanUtils.copyProperties(bankInterfaceVO,bankInterface);
        this.bankInterfaceMapper.updateByPrimaryKeySelective(bankInterface);
    }


}
