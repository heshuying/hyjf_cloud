package com.hyjf.am.user.dao.customize;

import com.hyjf.am.trade.dao.mapper.auto.ROaDepartmentMapper;
import com.hyjf.am.user.dao.auto.AutoMapper;
import com.hyjf.am.user.dao.mapper.customize.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("amUserCustomizeMapper")
public class CustomizeMapper extends AutoMapper {

    @Autowired
    protected UtmRegCustomizeMapper utmRegCustomizeMapper;
    @Autowired
    protected UserManagerCustomizeMapper userManagerCustomizeMapper;
    @Autowired
	protected MspConfigureCustomizeMapper mspConfigureCustomizeMapper;
    @Autowired
    protected PcChannelStatisticsJobCustomizeMapper pcChannelStatisticsJobCustomizeMapper;
    @Autowired
    protected TzjCustomizeMapper tzjCustomizeMapper;

    @Autowired
    protected UserAliasCustomizeMapper userAliasCustomizeMapper;

    @Autowired
    protected UserAdminBankAccountCheckCustomizeMapper userAdminBankAccountCheckCustomizeMapper;

    @Autowired
    protected UserEntryCustomizeMapper userEntryCustomizeMapper;

    @Autowired
    protected UserLeaveCustomizeMapper userLeaveCustomizeMapper;

    @Autowired
    protected UserCustomizeMapper userCustomizeMapper;

    @Autowired
    protected BankCardManagerCustomizeMapper bankCardManagerCustomizeMapper;

    @Autowired
    protected CallCenterCustomizeMapper callCenterCustomizeMapper;

    @Autowired
    protected CallCenterAccountHuifuMapper callCenterAccountHuifuMapper;

    @Autowired
    protected BankOpenRecordCustomizeMapper bankOpenRecordCustomizeMapper;

    @Autowired
    protected ChangeLogCustomizeMapper changeLogCustomizeMapper;

    @Autowired
    protected ChannelCustomizeMapper channelCustomizeMapper;

    @Autowired
    protected EmployeeCustomizeMapper employeeCustomizeMapper;

    @Autowired
    protected UserCrmInfoCustomizeMapper userCrmInfoCustomizeMapper;

    @Autowired
    protected UserInfoCustomizeMapper userInfoCustomizeMapper;

    @Autowired
    protected EvaluationManagerCustomizeMapper evaluationManagerCustomizeMapper;

    @Autowired
    protected KeyCountCustomMapper keyCountCustomMapper;

    @Autowired
    protected MyInviteCustomizeMapper myInviteCustomizeMapper;

    @Autowired
    protected RegistRecordCustomizeMapper registRecordCustomizeMapper;

    @Autowired
    protected SubConfigCustomizeMapper subConfigCustomizeMapper;

    @Autowired
    protected UserPortraitManagerMapper userPortraitManagerMapper;

    @Autowired
    protected UtmPlatCustomizeMapper utmPlatCustomizeMapper;

    @Autowired
    protected VipManageCustomizeMapper vipManageCustomizeMapper;

    @Autowired
    protected VipDetailListCustomizeMapper vipDetailListCustomizeMapper;

    @Autowired
    protected VipUpdateGradeListCustomizeMapper vipUpdateGradeListCustomizeMapper;

    @Autowired
    protected WhereaboutsPageConfigCustomizeMapper whereaboutsPageConfigCustomizeMapper;

    @Autowired
    protected AdminPreRegistCustomizeMapper adminPreRegistCustomizeMapper;

    @Autowired
    protected AdminUserAuthCustomizeMapper adminUserAuthCustomizeMapper;

    @Autowired
    protected ROaDepartmentMapper rOaDepartmentMapper;

    @Autowired
    protected MobileSynchronizeCustomizeMapper mobileSynchronizeCustomizeMapper;

    @Autowired
    protected  DuibaPointsCustomizeMapper duibaPointsCustomizeMapper;
}
