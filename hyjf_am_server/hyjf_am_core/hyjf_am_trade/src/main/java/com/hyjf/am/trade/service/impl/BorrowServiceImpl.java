/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BorrowService;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version BorrowServiceImpl, v0.1 2018/6/13 18:53
 */
@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowFinmanNewChargeMapper borrowFinmanNewChargeMapper;

    @Autowired
    private BorrowConfigMapper borrowConfigMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BorrowManinfoMapper borrowManinfoMapper;
    @Autowired
    private BorrowStyleMapper borrowStyleMapper;

    @Override
    public BorrowFinmanNewCharge selectBorrowApr(BorrowFinmanNewChargeRequest request) {
        BorrowFinmanNewChargeExample example = new BorrowFinmanNewChargeExample();
        BorrowFinmanNewChargeExample.Criteria cra = example.createCriteria();
        cra.andProjectTypeEqualTo(request.getBorrowClass());
        cra.andInstCodeEqualTo(request.getInstCode());
        cra.andAssetTypeEqualTo(request.getAssetType());
        cra.andManChargeTimeTypeEqualTo(request.getQueryBorrowStyle());
        cra.andManChargeTimeEqualTo(request.getBorrowPeriod());
        cra.andStatusEqualTo(0);

        List<BorrowFinmanNewCharge> list = this.borrowFinmanNewChargeMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Borrow getBorrow(String borrowNid) {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<Borrow> list = this.borrowMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public BorrowStyle getborrowStyleByNid(String borrowStyle) {
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cri = example.createCriteria();
        cri.andNidEqualTo(borrowStyle);
        List<BorrowStyle> style = borrowStyleMapper.selectByExample(example);
        return style.get(0);
    }

    public BorrowConfig getBorrowConfigByConfigCd(String configCd) {
        BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
        return borrowConfig;
    }

    @Override
    public int insertBorrow(Borrow borrow) {
        return borrowMapper.insertSelective(borrow);
    }

    @Override
    public int insertBorrowManinfo(BorrowManinfo borrowManinfo) {
         return borrowManinfoMapper.insertSelective(borrowManinfo);
    }

    @Override
    public int updateBorrowRegist(BorrowRegistRequest request) {
        BorrowVO borrowVO = request.getBorrowVO();
        int status = request.getStatus();
        int registStatus = request.getRegistStatus();
        Date nowDate = new Date();
//		AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrowVO.getId()).andStatusEqualTo(borrowVO.getStatus()).andRegistStatusEqualTo(borrowVO.getRegistStatus());
        borrowVO.setRegistStatus(registStatus);
        borrowVO.setStatus(status);
        borrowVO.setRegistUserId(1);//TODO:id写死1
        borrowVO.setRegistUserName("AutoRecord");
        borrowVO.setRegistTime(nowDate);
        Borrow borrow = new Borrow();
        BeanUtils.copyProperties(borrowVO, borrow);
        return borrowMapper.updateByExampleSelective(borrow, example);
    }

    /**
     * 检索正在还款中的标的
     * @return
     */
    @Override
    public List<Borrow> selectBorrowList() {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(3);
        cra.andRepayFullStatusEqualTo(0);
        List<Borrow> list = this.borrowMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

}
