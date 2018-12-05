package com.hyjf.cs.trade.service.aems.assetpush.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.base.service.BaseServiceImpl;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.RabbitMQConstants;
import com.hyjf.mybatis.model.auto.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资产推送 jijun 20180914
 */
@Service
public class AemsPushServiceImpl extends BaseServiceImpl implements AemsPushService {


    @Autowired
    @Qualifier("myAmqpTemplate")
    private RabbitTemplate rabbitTemplate;

    /**
     * 插入资产表
     */
    @Override
    public int insertAssert(HjhPlanAsset record, Map<String, String> params) {

        return this.hjhPlanAssetMapper.insertSelective(record);
    }

    /**
     *获取开户的数据
     */
    @Override
    public BankOpenAccount selectBankAccountById(int userId) {
        BankOpenAccountExample example = new BankOpenAccountExample();
        BankOpenAccountExample.Criteria crt = example.createCriteria();
        crt.andUserIdEqualTo(userId);
        List<BankOpenAccount> list = this.bankOpenAccountMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else return null;
    }

    /**
     *获取用户信息
     */
    @Override
    public UsersInfo selectUserInfoByNameAndCard(String trueName, String idCrad) {
        UsersInfoExample example = new UsersInfoExample();
        UsersInfoExample.Criteria crt = example.createCriteria();
        crt.andTruenameEqualTo(trueName);
        crt.andIdcardEqualTo(idCrad);
        List<UsersInfo> list = usersInfoMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     *获取用户的数据
     */
    @Override
    public Users selectUsersById(int userId) {
        UsersExample example = new UsersExample();
        UsersExample.Criteria crt = example.createCriteria();
        crt.andUserIdEqualTo(userId);
        List<Users> list = usersMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     *获取机构信息
     */
    @Override
    public HjhAssetBorrowType selectAssetBorrowType(String instCode, int assetType) {
        HjhAssetBorrowTypeExample example = new HjhAssetBorrowTypeExample();
        HjhAssetBorrowTypeExample.Criteria crt = example.createCriteria();
        crt.andInstCodeEqualTo(instCode);
        crt.andAssetTypeEqualTo(assetType);
        List<HjhAssetBorrowType> list = hjhAssetBorrowTypeMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }


    /**
     * 根据项目类型去还款方式
     * @param borrowcCd
     * @return
     */
    public List<BorrowProjectRepay> selectProjectRepay(String borrowcCd){
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria crt = example.createCriteria();
        crt.andBorrowCdEqualTo(borrowcCd);
        crt.andStatusEqualTo("0");
        List<BorrowProjectType> list = this.borrowProjectTypeMapper.selectByExample(example);
        String brrowClass = null;
        if(list.size() > 0){
            brrowClass = list.get(0).getBorrowClass();

            BorrowProjectRepayExample example2 = new BorrowProjectRepayExample();
            BorrowProjectRepayExample.Criteria crt2 = example2.createCriteria();
            crt2.andBorrowClassEqualTo(brrowClass);
            crt2.andDelFlagEqualTo("0");
            List<BorrowProjectRepay> list2 = this.borrowProjectRepayMapper.selectByExample(example2);

            return list2;

        }else{
            return null;
        }
    }

    /**
     *获取受托支付电子账户列表
     */
    @Override
    public STZHWhiteList selectStzfWhiteList(String instCode, String stAccountid) {
        STZHWhiteListExample example = new STZHWhiteListExample();
        STZHWhiteListExample.Criteria crt = example.createCriteria();
        crt.andStAccountidEqualTo(stAccountid);
        crt.andInstcodeEqualTo(instCode);
        crt.andDelFlgEqualTo(0);
        crt.andStateEqualTo(1);
        List<STZHWhiteList> list = this.sTZHWhiteListMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 推送消息到MQ
     */
    @Override
    public void sendToMQ(HjhPlanAsset hjhPlanAsset){

        // 加入到消息队列
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("assetId", hjhPlanAsset.getAssetId());
        params.put("instCode", hjhPlanAsset.getInstCode());

        rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_COUPON, RabbitMQConstants.ROUTINGKEY_BORROW_SEND, JSONObject.toJSONString(params));

    }

    /**
     * 通过用户名获得用户的详细信息
     */
    @Override
    public Users selectUserInfoByUsername(String userName){
        UsersExample example = new UsersExample();
        UsersExample.Criteria crt = example.createCriteria();
        crt.andUsernameEqualTo(userName);
        //usersMapper.updateByUsername(userName);
        List<Users> list = this.usersMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     *通过机构编号获得机构信息
     */
    @Override
    public CorpOpenAccountRecord selectUserBusiNameByUsername(String userName) {
        CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
        CorpOpenAccountRecordExample.Criteria crt = example.createCriteria();
        crt.andUsernameEqualTo(userName);
        List<CorpOpenAccountRecord> list = corpOpenAccountRecordMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 通过用户id获得用户真实姓名和身份证号
     * @param userId
     * @return
     */
    @Override
    public UsersInfo selectUserInfoByUserId(Integer userId) {
        UsersInfoExample example = new UsersInfoExample();
        UsersInfoExample.Criteria crt = example.createCriteria();
        crt.andUserIdEqualTo(userId);
        List<UsersInfo> list = usersInfoMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 检查是否存在重复资产
     * @param assetId
     * @return
     */
    @Override
    public HjhPlanAsset checkDuplicateAssetId(String assetId) {
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        HjhPlanAssetExample.Criteria crt = example.createCriteria();
        crt.andAssetIdEqualTo(assetId);
        List<HjhPlanAsset> list = hjhPlanAssetMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 获取保证金配置
     * @param instCode
     * @return
     */
    @Override
    public HjhBailConfig getBailConfig(String instCode){
        HjhBailConfigExample example = new HjhBailConfigExample();
        example.createCriteria().andInstCodeEqualTo(instCode).andDelFlgEqualTo(0);

        List<HjhBailConfig> list = hjhBailConfigMapper.selectByExample(example);
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }

        return null;
    }

    /**
     * 根据id获取资产count
     * @param assetId
     * @return
     */
    @Override
    public int selectAssertCountByAssetId(String assetId) {
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        HjhPlanAssetExample.Criteria crt = example.createCriteria();
        crt.andAssetIdEqualTo(assetId);
        return hjhPlanAssetMapper.countByExample(example);
    }
}
