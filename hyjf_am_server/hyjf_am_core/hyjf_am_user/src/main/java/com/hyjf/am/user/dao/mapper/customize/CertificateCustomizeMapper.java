package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.vo.user.CertificateAuthorityVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @Author: yinhui
 * @Date: 2019/6/3 16:19
 * @Version 1.0
 */
public interface CertificateCustomizeMapper {

    /**
     * 通过用户id查询 ca客户编号
     * @param userId
     * @return
     */
    List<CertificateAuthorityVO> queryCustomerId(@Param("set") Set<Integer> userId);

    List<CertificateAuthorityVO> queryCustomerIds(@Param("list") List<Integer> userIds);
}
