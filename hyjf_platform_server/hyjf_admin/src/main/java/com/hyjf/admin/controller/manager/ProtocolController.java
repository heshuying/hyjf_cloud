package com.hyjf.admin.controller.manager;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.ProtocolService;
import com.hyjf.am.response.admin.AdminProtocolResponse;
import com.hyjf.am.resquest.admin.AdminProtocolRequest;
import com.hyjf.am.resquest.admin.AdminProtocolVersionRequest;
import com.hyjf.am.vo.admin.ProtocolTemplateCommonVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.ProtocolEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;

/**
 * 协议模板管理
 * @author：yinhui
 * @Date: 2018/8/8  15:10
 */
@Api(tags ="配置中心-协议模板管理")
@RestController
@RequestMapping("/hyjf-admin/manager/protocol")
public class ProtocolController extends BaseController{

    @Autowired
    private ProtocolService protocolService;

    /** 权限 */
    public static final String PERMISSIONS = "protocolView";

    /**
     * 分页显示
     * @param request
     * @return
     */
    @ApiOperation(value = "分页显示", notes = "分页显示")
    @PostMapping("/search")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_VIEW })
    public AdminResult<ListResult<ProtocolTemplateCommonVO>> search(@RequestBody AdminProtocolRequest request){
        AdminProtocolResponse response = new AdminProtocolResponse();
        response = protocolService.searchPage(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        return new AdminResult<ListResult<ProtocolTemplateCommonVO>>(ListResult.build(response.getResultList(), response.getCount())) ;
    }

    /**
     * 迁移到查看详细画面
     * @param request
     * @return
     */
    @ApiOperation(value = "根据id查询显示修改界面", notes = "根据id查询显示修改界面")
    @PostMapping("/infoInfoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW )
    public AdminResult<ProtocolTemplateCommonVO> infoInfoAction(@RequestBody  AdminProtocolRequest request){
        AdminProtocolResponse response = null;
        response = protocolService.getProtocolTemplateById(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        return new AdminResult(response.getResult()) ;
    }

    /**
     * 添加协议模板
     * @param request
     * @return
     */
    @ApiOperation(value = "添加协议模板", notes = "添加协议模板")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD )
    public AdminResult insertAction(@RequestBody AdminProtocolRequest request, HttpServletRequest httpServletRequest){
        AdminProtocolResponse response = new AdminProtocolResponse();
        AdminSystemVO user = getUser(httpServletRequest);
        String userId = user.getId();
//        String userId = "1";

        protocolService.insertProtocolTemplate(request,userId);

        //更新redis
        RedisUtils.del(RedisConstants.PROTOCOL_PARAMS);
        List<ProtocolTemplateVO> list = protocolService.getNewInfo();
        logger.info("添加协议模板,查询所有模板list大小,list="+list);
        JSONObject jsonObject = new JSONObject();
        HashMap<String, Object> map = new HashMap<String, Object>();
        //是否在枚举中有定义
        for (ProtocolTemplateVO p : list) {
            String protocolType = p.getProtocolType();
            String alia = ProtocolEnum.getAlias(protocolType);
            if (alia != null){
                map.put(alia, p.getDisplayName());
            }
        }
        jsonObject.put("status","000");
        jsonObject.put("statusDesc","成功");
        jsonObject.put("displayName",map);
        RedisUtils.set(RedisConstants.PROTOCOL_PARAMS,jsonObject.toString());


        return new AdminResult();
    }

    /**
     * 修改已经存在的协议模板
     * @param request
     * @return
     */
    @ApiOperation(value = "修改协议模板", notes = "修改协议模板")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY )
    public AdminResult updateAction(@RequestBody AdminProtocolRequest request, HttpServletRequest httpServletRequest){
        AdminProtocolResponse response = new AdminProtocolResponse();
        HashMap<String, Object> map = new HashMap<String, Object>();
        AdminSystemVO user = getUser(httpServletRequest);
        String userId = user.getId();
//        String userId = "1";
        protocolService.updateProtocolTemplate(request,userId);

        //更新redis
        RedisUtils.del(RedisConstants.PROTOCOL_PARAMS);
        List<ProtocolTemplateVO> list = protocolService.getNewInfo();
        JSONObject jsonObject = new JSONObject();
        //是否在枚举中有定义
        for (ProtocolTemplateVO p : list) {
            String protocolType = p.getProtocolType();
            String alia = ProtocolEnum.getAlias(protocolType);
            if (alia != null){
                map.put(alia, p.getDisplayName());
            }
        }
        jsonObject.put("status","000");
        jsonObject.put("statusDesc","成功");
        jsonObject.put("displayName",map);
        RedisUtils.set(RedisConstants.PROTOCOL_PARAMS,jsonObject.toString());

        return new AdminResult();
    }

    /**
     * 修改已经存在的协议模板
     * @param request
     * @return
     */
    @ApiOperation(value = "修改已经存在的协议模板", notes = "修改已经存在的协议模板")
    @PostMapping("/updateExistProtocol")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY )
    public AdminResult updateExistProtocol(@RequestBody AdminProtocolVersionRequest request, HttpServletRequest httpServletRequest){
        AdminProtocolResponse response = new AdminProtocolResponse();
        HashMap<String, Object> map = new HashMap<String, Object>();
        AdminSystemVO user = getUser(httpServletRequest);
        String userId = user.getId();
//        String userId = "1";
        protocolService.updateExistAction(request,userId);

        //更新redis
        RedisUtils.del(RedisConstants.PROTOCOL_PARAMS);
        List<ProtocolTemplateVO> list = protocolService.getNewInfo();
        JSONObject jsonObject = new JSONObject();
        //是否在枚举中有定义
        for (ProtocolTemplateVO p : list) {
            String protocolType = p.getProtocolType();
            String alia = ProtocolEnum.getAlias(protocolType);
            if (alia != null){
                map.put(alia, p.getDisplayName());
            }
        }
        jsonObject.put("status","000");
        jsonObject.put("statusDesc","成功");
        jsonObject.put("displayName",map);
        RedisUtils.set(RedisConstants.PROTOCOL_PARAMS,jsonObject.toString());

        return new AdminResult();
    }

    /**
     * 删除协议模板
     * @param request
     * @return
     */
    @ApiOperation(value = "删除协议模板", notes = "删除协议模板")
    @RequestMapping(value="/deleteAction",method = RequestMethod.DELETE)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE )
    public AdminResult deleteAction(@RequestBody  AdminProtocolRequest request, HttpServletRequest httpServletRequest){
        HashMap<String, Object> map = new HashMap<String, Object>();
        AdminSystemVO user = getUser(httpServletRequest);
        String userId = user.getId();
        protocolService.deleteProtocolTemplate(request,userId);

        //删除redis相应记录
        RedisUtils.del(RedisConstants.PROTOCOL_PARAMS);
        //在redis中添加最新记录
        List<ProtocolTemplateVO> list = protocolService.getNewInfo();
        JSONObject jsonObject = new JSONObject();
        //是否在枚举中有定义
        for (ProtocolTemplateVO p : list) {
            String protocolType = p.getProtocolType();
            String alia = ProtocolEnum.getAlias(protocolType);
            if (alia != null){
                map.put(alia, p.getDisplayName());
            }
        }
        jsonObject.put("status","000");
        jsonObject.put("statusDesc","成功");
        jsonObject.put("displayName",map);
        RedisUtils.set(RedisConstants.PROTOCOL_PARAMS,jsonObject.toString());

        return new AdminResult();
    }

    /**
     * 资料上传
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "pdf文件上传", notes = "pdf文件上传")
    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
        try {
            LinkedList<BorrowCommonImage> borrowCommonImages = protocolService.uploadFile(request, response);
            adminResult.setData(borrowCommonImages);
            adminResult.setStatus(SUCCESS);
            adminResult.setStatusDesc(SUCCESS_DESC);
            return adminResult;
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    /**
     * 校验表单字段是否唯一
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "校验字段是否唯一", notes = "校验传给后台的值字段是否唯一")
    @RequestMapping(value = "validationProtocolNameAction", method = RequestMethod.POST)
    public Map<String,String> validationProtocolNameAction(@RequestBody  AdminProtocolRequest request) throws Exception {
        //获取校验参数
        String protocolName = request.getProtocolName();//协议模板名称
        String protocolType = request.getProtocolType();//协议类别
        String versionNumber = request.getVersionNumber();//版本号
        String displayName = request.getDisplayName();//前台展示名称
        String protocolUrl = request.getProtocolUrl();//协议上传路径
        String oldDisplayName = request.getOldDisplayName();//原前台展示名称
        String flag = request.getFlag();//原前台展示名称
        Map<String,String> map = protocolService.validatorFieldCheck(request,protocolName,versionNumber,displayName,protocolUrl,protocolType,oldDisplayName,flag);
        return map;
    }

    /**
     * 获取下拉框
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "协议名称下拉框", notes = "协议名称下拉框")
    @RequestMapping(value = "selectlist", method = RequestMethod.GET)
    public AdminResult selectlist() throws Exception {
        //下拉框初始化
        List<ProtocolSelectBean> selectList = initSelect();
        return new AdminResult<>(ListResult.build(selectList, selectList.size()));
    }

    public List<ProtocolSelectBean> initSelect() {
        List<ProtocolSelectBean> selectList = new ArrayList<ProtocolSelectBean>();
        ProtocolSelectBean bean1 = new ProtocolSelectBean();
        bean1.setCode("《注册协议》");
        bean1.setName("《注册协议》");
        selectList.add(bean1);
        ProtocolSelectBean bean2 = new ProtocolSelectBean();
        bean2.setCode("《平台隐私条款》");
        bean2.setName("《平台隐私条款》");
        selectList.add(bean2);
        ProtocolSelectBean bean3 = new ProtocolSelectBean();
        bean3.setCode("《出借咨询与管理服务协议》");
        bean3.setName("《出借咨询与管理服务协议》");
        selectList.add(bean3);
        ProtocolSelectBean bean4 = new ProtocolSelectBean();
        bean4.setCode("《用户授权协议》");
        bean4.setName("《用户授权协议》");
        selectList.add(bean4);
        ProtocolSelectBean bean5 = new ProtocolSelectBean();
        bean5.setCode("《居间服务借款协议》");
        bean5.setName("《居间服务借款协议》");
        selectList.add(bean5);
        ProtocolSelectBean bean6 = new ProtocolSelectBean();
        bean6.setCode("《散标风险揭示书》");
        bean6.setName("《散标风险揭示书》");
        selectList.add(bean6);
        ProtocolSelectBean bean7 = new ProtocolSelectBean();
        bean7.setCode("《债权转让协议》");
        bean7.setName("《债权转让协议》");
        selectList.add(bean7);
        ProtocolSelectBean bean9 = new ProtocolSelectBean();
        bean9.setCode("《开户协议》");
        bean9.setName("《开户协议》");
        selectList.add(bean9);
        ProtocolSelectBean bean10 = new ProtocolSelectBean();
        bean10.setCode("《智投服务协议》");
        bean10.setName("《智投服务协议》");
        selectList.add(bean10);
        ProtocolSelectBean bean11 = new ProtocolSelectBean();
        bean11.setCode("《智投风险揭示书》");
        bean11.setName("《智投风险揭示书》");
        selectList.add(bean11);
        ProtocolSelectBean bean12 = new ProtocolSelectBean();
        bean12.setCode("《自动投标授权》");
        bean12.setName("《自动投标授权》");
        selectList.add(bean12);
        ProtocolSelectBean bean13 = new ProtocolSelectBean();
        bean13.setCode("《自动债转授权》");
        bean13.setName("《自动债转授权》");
        selectList.add(bean13);
        ProtocolSelectBean bean14 = new ProtocolSelectBean();
        bean14.setCode("《缴费授权》");
        bean14.setName("《缴费授权》");
        selectList.add(bean14);
        ProtocolSelectBean bean15 = new ProtocolSelectBean();
        bean15.setCode("《还款授权》");
        bean15.setName("《还款授权》");
        selectList.add(bean15);
        return selectList;
    }


    /**
     * 协议模板下拉框
     */
    public class ProtocolSelectBean implements Serializable {

        private  String code;
        private  String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
