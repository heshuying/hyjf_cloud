package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.message.UserDeviceUniqueCodeResponse;
import com.hyjf.am.vo.UserDeviceUniqueCodeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.UserDeviceUniqueCodeEntity;
import com.hyjf.cs.message.service.userdevice.UserDeviceUniqueCodeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 保存app登录机器码记录
 * @author zhangyk
 * @date 2018/8/21 16:44
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/userdeviceuniquecode")
public class UserDeviceUniqueCodeController extends BaseController {

    @Autowired
    private UserDeviceUniqueCodeService userDeviceUniqueCodeService;

    @GetMapping("/{uniqueCode}")
    public UserDeviceUniqueCodeResponse getByUniqueCode(@PathVariable String uniqueCode){
        UserDeviceUniqueCodeResponse response = new UserDeviceUniqueCodeResponse();
        List<UserDeviceUniqueCodeEntity> list = userDeviceUniqueCodeService.selectByUniqueCode(uniqueCode);
        if (CollectionUtils.isNotEmpty(list)){
            List<UserDeviceUniqueCodeVO> result = CommonUtils.convertBeanList(list,UserDeviceUniqueCodeVO.class);
            response.setResultList(result);
        }
        return response;
    }

    @PostMapping("insert")
    public UserDeviceUniqueCodeResponse insertEntity(@RequestBody UserDeviceUniqueCodeEntity entity){
        UserDeviceUniqueCodeResponse response = new UserDeviceUniqueCodeResponse();
        userDeviceUniqueCodeService.insertEntity(entity);
        return response;
    }

    @PostMapping("update")
    public UserDeviceUniqueCodeResponse updateEntity(@RequestBody UserDeviceUniqueCodeEntity entity){
        UserDeviceUniqueCodeResponse response = new UserDeviceUniqueCodeResponse();
        userDeviceUniqueCodeService.updateEntity(entity);
        return response;
    }

}
