package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.bifa.UserIdAccountSumBean;
import com.hyjf.am.trade.service.front.borrow.BorrowUserService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.bifa.BifaBorrowUserInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 借款人公司信息
 * @Author zhangyk upd by liushouyi
 */
@Service
public class BorrowUserServiceImpl extends BaseServiceImpl implements BorrowUserService {

	/**
	 * 根据借款编号获取借款人公司信息
	 *
	 * @param borrowNid
	 * @return
	 */
	@Override
	public BorrowUser getBorrowUserByNid(String borrowNid) {
		BorrowUserExample example = new BorrowUserExample();
		BorrowUserExample.Criteria cri = example.createCriteria();
		cri.andBorrowNidEqualTo(borrowNid);
		List<BorrowUser> list = borrowUserMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
            return null;
        }
		return list.get(0);
	}

	/**
	 * 北互金获取借款人信息
	 * @param borrowNid
	 * @param companyOrPersonal
	 * @return
	 */
	@Override
	public BifaBorrowUserInfoVO getBifaBorrowUserInfo(String borrowNid, String companyOrPersonal) {
		BifaBorrowUserInfoVO result = new BifaBorrowUserInfoVO();
		if ("1".equals(companyOrPersonal)) {
			//公司
			BorrowUserExample example = new BorrowUserExample();
			BorrowUserExample.Criteria criteria = example.createCriteria();
			criteria.andBorrowNidEqualTo(borrowNid);
			List<BorrowUser> borrowUsers=this.borrowUserMapper.selectByExample(example);
			result.setTrueName("");
			result.setSex(this.convertSex(9));
			String idCard = borrowUsers.get(0).getSocialCreditCode();
			if (StringUtils.isEmpty(idCard)){
				idCard = borrowUsers.get(0).getRegistCode();
			}
			result.setIdCard(idCard);
		}else if ("2".equals(companyOrPersonal)) {
			//个人
			BorrowManinfoExample example = new BorrowManinfoExample();
			BorrowManinfoExample.Criteria criteria = example.createCriteria();
			criteria.andBorrowNidEqualTo(borrowNid);
			List<BorrowManinfo> borrowManinfos=this.borrowManinfoMapper.selectByExample(example);
			result.setTrueName(borrowManinfos.get(0).getName());
			result.setSex(this.convertSex(borrowManinfos.get(0).getSex()));
			result.setIdCard(borrowManinfos.get(0).getCardNo());
		}
		return result;
	}

	/**
	 * 散标转让服务费
	 * @param creditNid
	 * @return
	 */
	@Override
	public BigDecimal getBorrowCreditFeeSumByCreditNid(String creditNid) {
		return bifaCreditTenderCustomizeMapper.getBorrowCreditFeeSumByCreditNid(creditNid);
	}

	/**
	 * 智投转让服务费
	 * @param creditNid
	 * @return
	 */
	@Override
	public BigDecimal getHjhCreditFeeSumByCreditNid(String creditNid) {
		return bifaHjhDebtCreditTenderCustomizeMapper.getHjhCreditFeeSumByCreditNid(creditNid);
	}

	/**
	 * 获取智投数
	 * @param planNid
	 * @return
	 */
	@Override
	public int countHjhPlan(String planNid) {
		HjhPlanExample example = new HjhPlanExample();
		HjhPlanExample.Criteria cri = example.createCriteria();
		cri.andPlanNidEqualTo(planNid);
		int count = this.hjhPlanMapper.countByExample(example);
		return count;
	}

	/**
	 * 获取智投列表
	 * @return
	 */
	@Override
	public List<HjhPlan> selectHjhPlanInfoList() {
		HjhPlanExample example = new HjhPlanExample();
		List<HjhPlan> list = this.hjhPlanMapper.selectByExample(example);
		return list;
	}

	/**
	 * 已开户且出借>0的用户
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public List<UserIdAccountSumBean> getBorrowTenderAccountSum(Integer startDate, Integer endDate) {
		return bifaBorrowTenderInfoCustomizeMapper.getBorrowTenderAccountSum(startDate,endDate);
	}

	/**
	 * 借款人信息
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public List<BorrowAndInfoVO> selectBorrowUserInfo(Integer startDate, Integer endDate) {
		return bifaBorrowCustomizeMapper.selectBorrowUserInfo(startDate,endDate);
	}

	@Override
	public int getLoanNum(Date time) {
		return operationReportJobCustomizeMapper.getLoanNum(time);
	}

	/**
	 * 北互金累计借贷余额
	 * @return
	 */
	@Override
	public BigDecimal getWillPayMoney() {
		return operationReportJobCustomizeMapper.getWillPayMoney();
	}

	/**
	 * 累计借贷余额笔数
	 * @return
	 */
	@Override
	public int getTotalLoanBalanceNum() {
		return bifaOperationReportJobCustomizeMapper.getLoanBalanceNum();
	}

	/**
	 * 累计借款人
	 * @return
	 */
	@Override
	public int countBorrowUser() {
		return borrowUserStatisticsMapper.countBorrowUser();
	}

	/**
	 * 累计投资人数
	 * @param date
	 * @return
	 */
	@Override
	public int getTenderCount(Date date) {
		return operationReportJobCustomizeMapper.getTenderCount(date);
	}

	/**
	 * 当前借款人
	 * @return
	 */
	@Override
	public int countCurrentBorrowUser() {
		return borrowUserStatisticsMapper.countCurrentBorrowUser();
	}

	/**
	 * 当前出借人
	 * @return
	 */
	@Override
	public int countCurrentTenderUser() {
		return borrowUserStatisticsMapper.countCurrentTenderUser();
	}

	/**
	 * 平台前十大融资人融资待还余额占比
	 * @return
	 */
	@Override
	public BigDecimal sumBorrowUserMoneyTopTen() {
		return borrowUserStatisticsMapper.sumBorrowUserMoneyTopTen();
	}

	/**
	 * 代还总金额
	 * @return
	 */
	@Override
	public BigDecimal sumBorrowUserMoney() {
		Calendar calendar = Calendar.getInstance();
		// 要统计前一个月的数据，所以月份要减一
		calendar.add(Calendar.MONTH, -1);
		return borrowUserStatisticsMapper.sumBorrowUserMoney(getLastDay(calendar));
	}

	/**
	 * 平台单一融资人最大融资待还余额占比
	 * @return
	 */
	@Override
	public BigDecimal sumBorrowUserMoneyTopOne() {
		return borrowUserStatisticsMapper.sumBorrowUserMoneyTopOne();
	}

    @Override
    public List<BorrowUserVO> getBorrowUserList(List<String> borrowNids) {
        return borrowCustomizeMapper.getBorrowUserList(borrowNids);
    }

    /**
	 * 通过输入的日期，获取这个日期所在月份的最后一天
	 * @param cal
	 * @return
	 */
	private Date getLastDay(Calendar cal) {
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 北互金性别转换
	 * @param sex
	 * @return
	 */
	private String convertSex(int sex) {
		switch (sex) {
			case 0:
				return "未知";
			case 1:
				return "男";
			case 2:
				return "女";
			default:
				return "";
		}
	}
}
