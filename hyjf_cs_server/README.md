#组合层服务
### 业务和系统异常处理方式
#### 简单错误信息返回
    方式一（组合层都可用）
        CheckUtil.check(false,MsgEnum.ERR_USER_NOT_LOGIN);
    方式二（组合层都可用）
        if（false）{
            throw new CheckException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
    方式三（组合层的controller可用）
        if(false){
            _log.error("XXXXX");
            return new BaseResult<>(MsgEnum.ERR_USER_NOT_LOGIN.getCode(), MsgEnum.ERR_USER_NOT_LOGIN.getMsg());
        }
    方式四（组合层的controller可用）
        if(false){
            _log.error("XXXXX");
            BaseResult<?> result = new BaseResult<>();
            result.setStatus(MsgEnum.ERR_USER_NOT_LOGIN.getCode());
            result.setStatusDesc(MsgEnum.ERR_USER_NOT_LOGIN.getMsg());
            return result;
        }
#### 带参数错误信息返回
    方式一（组合层都可用）
        CheckUtil.check(false,MsgEnum.ERR_OBJECT_REQUIRED,"姓名");
    方式二（组合层都可用）
        if（false）{
            throw new CheckException(MsgEnum.ERR_USER_NOT_LOGIN, StringUtil.getEnumMessage(MsgEnum.ERR_USER_NOT_LOGIN, "姓名"));
        }
    方式三（组合层的controller可用）
        if(false){
            _log.error("XXXXX");
            return new BaseResult<>(MsgEnum.ERR_USER_NOT_LOGIN, StringUtil.getEnumMessage(MsgEnum.ERR_USER_NOT_LOGIN, "姓名"));
        }
#### 带数据错误信息返回
    方式一（组合层都可用）
        CheckUtil.setData(数据Bean对象);
        CheckUtil.check(false,MsgEnum.ERR_OBJECT_REQUIRED,"姓名");
    方式二（组合层都可用）
        if（false）{
            throw new CheckException(MsgEnum.ERR_USER_NOT_LOGIN, StringUtil.getEnumMessage(MsgEnum.ERR_USER_NOT_LOGIN, "姓名"), 数据Bean对象);
        }
    方式三（组合层的controller可用）
        if(false){
            _log.error("XXXXX");
            BaseResult<?> result = new BaseResult<>(数据Bean对象);
            result.setStatusInfo(MsgEnum.ERR_USER_NOT_LOGIN, StringUtil.getEnumMessage(MsgEnum.ERR_USER_NOT_LOGIN, "姓名"));
            return result;
        }
### Controller
#### 父类继承
    组合层controller继承类使用(按照不同业务区分)
        cs_market       --      BaseMarketController
        cs_message      --      BaseMessageController
        cs_trade        --      BaseTradeController
        cs_user         --      BaseUserController
    例子：public class WebLoginController extends BaseUserController {
    
#### 返回对象
    组合层controller返回前端结果类使用(按照不同前端区分)
        api     --      ApiResult<T>
        app     --      AppResult<T>
        web     --      WebResult<T>
        wechat  --      WechatResult<T>
    例子：public WebResult<String> loginout(@RequestHeader(value = "token") String token){
    
### Service
#### 父类继承
    组合层Service继承接口/类使用(按照不同业务区分)
        cs_market       --      BaseMarketService/BaseMarketServiceImpl
        cs_message      --      BaseMessageService/BaseMessageServiceImpl
        cs_trade        --      BaseTradeService/BaseTradeServiceImpl
        cs_user         --      BaseUserService/BaseUserServiceImpl
    例子：public interface LoginService extends BaseUserService {
         public class LoginServiceImpl extends BaseUserServiceImpl implements LoginService {