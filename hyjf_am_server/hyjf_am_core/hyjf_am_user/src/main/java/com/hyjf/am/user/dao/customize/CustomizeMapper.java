package com.hyjf.am.user.dao.customize;

import com.hyjf.am.user.dao.auto.AutoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.customize.*;
import com.hyjf.am.user.dao.mapper.customize.hgreportdata.bifa.BifaUserCustomizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    protected BifaUserCustomizeMapper bifaUserCustomizeMapper;

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
    protected AdminPreRegistCustomizeMapper adminPreRegistCustomizeMapper;

    @Autowired
    protected AdminUserAuthCustomizeMapper adminUserAuthCustomizeMapper;

    @Autowired
    protected AccountChinapnrCustomizeMapper accountChinapnrCustomizeMapper;

    @Autowired
    protected BankOpenAccountCustomizeMapper bankOpenAccountCustomerMapper;

    @Autowired
    protected UserDepartmentInfoCustomizeMapper userDepartmentInfoCustomizeMapper;

    @Autowired
    protected DuiBaCustomizeMapper duiBaCustomizeMapper;
}
