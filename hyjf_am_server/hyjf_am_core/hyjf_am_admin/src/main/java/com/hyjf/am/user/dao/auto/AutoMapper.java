package com.hyjf.am.user.dao.auto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.admin.mq.producer.AppChannelStatisticsDetailProducer;
import com.hyjf.am.user.dao.mapper.auto.AccountBankMapper;
import com.hyjf.am.user.dao.mapper.auto.AccountChinapnrMapper;
import com.hyjf.am.user.dao.mapper.auto.AccountMobileSynchMapper;
import com.hyjf.am.user.dao.mapper.auto.AppointmentAuthLogMapper;
import com.hyjf.am.user.dao.mapper.auto.AppointmentRecodLogMapper;
import com.hyjf.am.user.dao.mapper.auto.BankCardLogMapper;
import com.hyjf.am.user.dao.mapper.auto.BankCardMapper;
import com.hyjf.am.user.dao.mapper.auto.BankOpenAccountLogMapper;
import com.hyjf.am.user.dao.mapper.auto.BankOpenAccountMapper;
import com.hyjf.am.user.dao.mapper.auto.BankSmsAuthCodeMapper;
import com.hyjf.am.user.dao.mapper.auto.BindUserMapper;
import com.hyjf.am.user.dao.mapper.auto.BorrowAppointMapper;
import com.hyjf.am.user.dao.mapper.auto.CallcenterServiceUsersMapper;
import com.hyjf.am.user.dao.mapper.auto.CertificateAuthorityMapper;
import com.hyjf.am.user.dao.mapper.auto.CorpOpenAccountRecordMapper;
import com.hyjf.am.user.dao.mapper.auto.EvalationMapper;
import com.hyjf.am.user.dao.mapper.auto.HjhUserAuthLogMapper;
import com.hyjf.am.user.dao.mapper.auto.HjhUserAuthMapper;
import com.hyjf.am.user.dao.mapper.auto.LabPlatformMapper;
import com.hyjf.am.user.dao.mapper.auto.LoanSubjectCertificateAuthorityMapper;
import com.hyjf.am.user.dao.mapper.auto.MspAbnormalcreditMapper;
import com.hyjf.am.user.dao.mapper.auto.MspAbnormalcreditdetailMapper;
import com.hyjf.am.user.dao.mapper.auto.MspAnliinfosMapper;
import com.hyjf.am.user.dao.mapper.auto.MspApplyMapper;
import com.hyjf.am.user.dao.mapper.auto.MspApplydetailsMapper;
import com.hyjf.am.user.dao.mapper.auto.MspBlackdataMapper;
import com.hyjf.am.user.dao.mapper.auto.MspConfigureMapper;
import com.hyjf.am.user.dao.mapper.auto.MspDegreeresultMapper;
import com.hyjf.am.user.dao.mapper.auto.MspFqzMapper;
import com.hyjf.am.user.dao.mapper.auto.MspNormalcreditdetailMapper;
import com.hyjf.am.user.dao.mapper.auto.MspQuerydetailMapper;
import com.hyjf.am.user.dao.mapper.auto.MspRegionMapper;
import com.hyjf.am.user.dao.mapper.auto.MspShixininfosMapper;
import com.hyjf.am.user.dao.mapper.auto.MspTitleMapper;
import com.hyjf.am.user.dao.mapper.auto.MspZhixinginfosMapper;
import com.hyjf.am.user.dao.mapper.auto.PreRegistMapper;
import com.hyjf.am.user.dao.mapper.auto.SmsCodeMapper;
import com.hyjf.am.user.dao.mapper.auto.SpreadsUserLogMapper;
import com.hyjf.am.user.dao.mapper.auto.SpreadsUserMapper;
import com.hyjf.am.user.dao.mapper.auto.UserAliasMapper;
import com.hyjf.am.user.dao.mapper.auto.UserBindEmailLogMapper;
import com.hyjf.am.user.dao.mapper.auto.UserChangeLogMapper;
import com.hyjf.am.user.dao.mapper.auto.UserContactMapper;
import com.hyjf.am.user.dao.mapper.auto.UserEvalationBehaviorMapper;
import com.hyjf.am.user.dao.mapper.auto.UserEvalationMapper;
import com.hyjf.am.user.dao.mapper.auto.UserEvalationResultMapper;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserLogMapper;
import com.hyjf.am.user.dao.mapper.auto.UserLoginLogMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.auto.UserPlatMapper;
import com.hyjf.am.user.dao.mapper.auto.UserPortraitMapper;
import com.hyjf.am.user.dao.mapper.auto.UtmMapper;
import com.hyjf.am.user.dao.mapper.auto.UtmPlatMapper;
import com.hyjf.am.user.dao.mapper.auto.UtmRegMapper;
import com.hyjf.am.user.dao.mapper.auto.UtmSourceMapper;
import com.hyjf.am.user.dao.mapper.auto.UtmVisitMapper;
import com.hyjf.am.user.dao.mapper.auto.VipAuthMapper;
import com.hyjf.am.user.dao.mapper.auto.VipInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.VipTransferLogMapper;
import com.hyjf.am.user.dao.mapper.auto.VipUserTenderMapper;
import com.hyjf.am.user.dao.mapper.auto.VipUserUpgradeMapper;
import com.hyjf.am.user.dao.mapper.auto.WhereaboutsPageConfigMapper;
import com.hyjf.am.user.dao.mapper.auto.WhereaboutsPagePictureMapper;

@Service("amUserAutoMapper")
public class AutoMapper {

    @Autowired
    protected AccountBankMapper accountBankMapper;

    @Autowired
    protected AccountChinapnrMapper accountChinapnrMapper;

    @Autowired
    protected AccountMobileSynchMapper accountMobileSynchMapper;

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
    protected UserChangeLogMapper usersChangeLogMapper;

    @Autowired
    protected WhereaboutsPageConfigMapper whereaboutsPageConfigMapper;

    @Autowired
    protected WhereaboutsPagePictureMapper whereaboutsPagePictureMapper;

    @Autowired
    protected UserMapper usersMapper;

    @Autowired
    protected UserInfoMapper usersInfoMapper;

    @Autowired
    protected AppChannelStatisticsDetailProducer appChannelStatisticsDetailProducer;

    @Autowired
    protected MspConfigureMapper mspConfigureMapperAuto;

    @Autowired
    protected MspQuerydetailMapper mspQueryDetailMapper;
    @Autowired
    protected MspBlackdataMapper mspBlackDataMapper;

    

}

