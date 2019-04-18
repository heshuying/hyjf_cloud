package com.hyjf.am.user.dao.auto;

import com.hyjf.am.user.dao.mapper.auto.*;
import com.hyjf.am.user.dao.mapper.customize.AdminAccountCustomizeQuiryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("amUserAutoMapper")
public class AutoMapper {

    @Autowired
    protected AccountBankMapper accountBankMapper;

    @Autowired
    protected AccountChinapnrMapper accountChinapnrMapper;

    @Autowired
    protected AccountMobileSynchMapper accountMobileSynchMapper;

    @Autowired
    protected AdminAccountCustomizeQuiryMapper adminAccountCustomizeQuiryMapper;

    @Autowired
    protected AppointmentAuthLogMapper appointmentAuthLogMapper;

    @Autowired
    protected AppointmentRecodLogMapper appointmentRecodLogMapper;

    @Autowired
    protected BankCardMapper bankCardMapper;

    @Autowired
    protected BankCardLogMapper bankCardLogMapper;

    @Autowired
    protected BankOpenAccountMapper bankOpenAccountMapper;

    @Autowired
    protected BankOpenAccountLogMapper bankOpenAccountLogMapper;

    @Autowired
    protected BankSmsAuthCodeMapper bankSmsAuthCodeMapper;

    @Autowired
    protected BindUserMapper bindUserMapper;

    @Autowired
    protected BorrowAppointMapper borrowAppointMapper;

    @Autowired
    protected CallcenterServiceUsersMapper callcenterServiceUsersMapper;

    @Autowired
    protected CertificateAuthorityMapper certificateAuthorityMapper;

    @Autowired
    protected CorpOpenAccountRecordMapper corpOpenAccountRecordMapper;

    @Autowired
    protected EvalationMapper evalationMapper;

    @Autowired
    protected HjhUserAuthMapper hjhUserAuthMapper;

    @Autowired
    protected HjhUserAuthLogMapper hjhUserAuthLogMapper;

    @Autowired
    protected LabPlatformMapper labPlatformMapper;

    @Autowired
    protected LoanSubjectCertificateAuthorityMapper loanSubjectCertificateAuthorityMapper;

    @Autowired
    protected MspAbnormalcreditMapper mspAbnormalcreditMapper;

    @Autowired
    protected MspAbnormalcreditdetailMapper mspAbnormalcreditdetailMapper;

    @Autowired
    protected MspAnliinfosMapper mspAnliinfosMapper;

    @Autowired
    protected MspApplyMapper mspApplyMapper;

    @Autowired
    protected MspApplydetailsMapper mspApplydetailsMapper;

    @Autowired
    protected MspBlackdataMapper mspBlackdataMapper;

    @Autowired
    protected MspConfigureMapper mspConfigureMapper;

    @Autowired
    protected MspDegreeresultMapper mspDegreeresultMapper;

    @Autowired
    protected MspFqzMapper mspFqzMapper;

    @Autowired
    protected MspNormalcreditdetailMapper mspNormalcreditdetailMapper;

    @Autowired
    protected MspQuerydetailMapper mspQuerydetailMapper;

    @Autowired
    protected MspRegionMapper mspRegionMapper;

    @Autowired
    protected MspShixininfosMapper mspShixininfosMapper;

    @Autowired
    protected MspTitleMapper mspTitleMapper;

    @Autowired
    protected MspZhixinginfosMapper mspZhixinginfosMapper;

    @Autowired
    protected PreRegistMapper preRegistMapper;

    @Autowired
    protected SmsCodeMapper smsCodeMapper;

    @Autowired
    protected SpreadsUserMapper spreadsUserMapper;

    @Autowired
    protected SpreadsUserLogMapper spreadsUserLogMapper;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected UserAliasMapper userAliasMapper;

    @Autowired
    protected UserBindEmailLogMapper userBindEmailLogMapper;

    @Autowired
    protected UserChangeLogMapper userChangeLogMapper;

    @Autowired
    protected UserContactMapper userContactMapper;

    @Autowired
    protected UserEvalationMapper userEvalationMapper;

    @Autowired
    protected UserEvalationBehaviorMapper userEvalationBehaviorMapper;

    @Autowired
    protected UserEvalationResultMapper userEvalationResultMapper;

    @Autowired
    protected UserInfoMapper userInfoMapper;

    @Autowired
    protected UserLogMapper userLogMapper;

    @Autowired
    protected UserLoginLogMapper userLoginLogMapper;

    @Autowired
    protected UserPlatMapper userPlatMapper;

    @Autowired
    protected UserPortraitMapper userPortraitMapper;

    @Autowired
    protected UtmMapper utmMapper;

    @Autowired
    protected UtmPlatMapper utmPlatMapper;

    @Autowired
    protected UtmRegMapper utmRegMapper;

    @Autowired
    protected UtmSourceMapper utmSourceMapper;

    @Autowired
    protected UtmVisitMapper utmVisitMapper;

    @Autowired
    protected VipAuthMapper vipAuthMapper;

    @Autowired
    protected VipInfoMapper vipInfoMapper;

    @Autowired
    protected VipTransferLogMapper vipTransferLogMapper;

    @Autowired
    protected VipUserTenderMapper vipUserTenderMapper;

    @Autowired
    protected VipUserUpgradeMapper vipUserUpgradeMapper;

    @Autowired
    protected WhereaboutsPageConfigMapper whereaboutsPageConfigMapper;

    @Autowired
    protected WhereaboutsPagePictureMapper whereaboutsPagePictureMapper;

    @Autowired
    protected  BankCancellationAccountMapper bankCancellationAccountMapper;

}

