package com.hyjf.am.user.dao.customize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.trade.dao.mapper.auto.ROaDepartmentMapper;
import com.hyjf.am.user.dao.auto.AutoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.customize.AdminPreRegistCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.AdminUserAuthCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.BankCardManagerCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.BankOpenRecordCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.CallCenterAccountHuifuMapper;
import com.hyjf.am.user.dao.mapper.customize.CallCenterCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.ChangeLogCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.ChannelCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.EmployeeCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.EvaluationManagerCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.KeyCountCustomMapper;
import com.hyjf.am.user.dao.mapper.customize.MspConfigureCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.MyInviteCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.RegistRecordCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.SubConfigCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.TzjCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UserAdminBankAccountCheckCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UserAliasCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UserCrmInfoCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UserCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UserEntryCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UserInfoCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UserLeaveCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UserManagerCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UserPortraitManagerMapper;
import com.hyjf.am.user.dao.mapper.customize.UtmPlatCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.UtmRegCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.VipDetailListCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.VipManageCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.VipUpdateGradeListCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.WhereaboutsPageConfigCustomizeMapper;

@Service("amUserCustomizeMapper")
public class CustomizeMapper extends AutoMapper {

    @Autowired
    protected UtmRegCustomizeMapper utmRegCustomizeMapper;
    @Autowired
    protected UserManagerCustomizeMapper userManagerCustomizeMapper;
    @Autowired
	protected MspConfigureCustomizeMapper mspConfigureCustomizeMapper;
    @Autowired
    protected TzjCustomizeMapper tzjCustomizeMapper;

    @Autowired
    protected UserAliasCustomizeMapper userAliasCustomizeMapper;
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected UserInfoMapper userInfoMapper;

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
}
