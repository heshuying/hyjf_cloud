/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.UserParamExceptionService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.trade.BorrowTenderUpdRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author nxl
 * @version UserParamExceptionServiceImpl, v0.1 2018/8/9 11:40
 */
@Service
public class UserParamExceptionServiceImpl extends BaseServiceImpl implements UserParamExceptionService {

    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 更新userInfo的主单与非主单信息
     */
    @Override
    public void updateUserParam(Integer userId) {

        UserVO users = amUserClient.findUserById(userId);
        if (users != null) {
            UserInfoVO userInfo = amUserClient.findUserInfoById(userId);
            if (null != userInfo) {
                // 如果userinfo不为空
                userInfo.setAttribute(0);// 默认为无主单
                {
                    // 从oa表中查询线上线下部门属性
                    UserUpdateParamCustomizeResponse userUpdateParamCustomizeResponse = amUserClient.queryUserAndDepartment(userInfo.getUserId());
                    if (null != userUpdateParamCustomizeResponse && Response.isSuccess(userUpdateParamCustomizeResponse)) {
                        List<UserUpdateParamCustomizeVO> userUpdateParamList = userUpdateParamCustomizeResponse.getResultList();
                        if (userUpdateParamList != null && userUpdateParamList.size() > 0) {
                            if (userUpdateParamList.get(0).getCuttype() != null) {
                                if (userUpdateParamList.get(0).getCuttype().equals("1")) {
                                    // 线上
                                    userInfo.setAttribute(3);
                                }
                                if (userUpdateParamList.get(0).getCuttype().equals("2")) {
                                    // 线下
                                    userInfo.setAttribute(2);
                                }
                            }
                        }
                    }
                }
                {
                    // 更新attribute
                    if (userInfo.getAttribute() != 2 && userInfo.getAttribute() != 3) {
                        SpreadsUserVO spreadsUserVO = amUserClient.searchSpreadsUserByUserId(userId);
                        if (null != spreadsUserVO && null != spreadsUserVO.getSpreadsUserId()) {
                            UserInfoVO parentInfo =  amUserClient.findUserInfoById(spreadsUserVO.getSpreadsUserId());
                            if (null != parentInfo) {
                                // 如果该用户的上级不为空
                                if (Validator.isNotNull(parentInfo) && Validator.isNotNull(parentInfo.getAttribute())) {
                                    if (Validator.equals(parentInfo.getAttribute(), new Integer(2))
                                            || Validator.equals(parentInfo.getAttribute(), new Integer(3))) {
                                        // 有推荐人且推荐人为员工(Attribute=2或3)时才设置为有主单
                                        userInfo.setAttribute(1);
                                    }
                                }
                            }
                        }
                    }
                }
                int intUpdFlg = amUserClient.updateUserInfoByUserInfo(userInfo);
                if (intUpdFlg > 0) {
                    logger.info("用户信息修改成功!");
                } else {
                    logger.info("用户信息修改失败!");
                }
            }
        }
    }

    /**
     * 查找所用用户
     *
     * @return
     */
    @Override
    public List<UserVO> getAllUser() {
        UserResponse userResponse = amUserClient.selectAllUser();
        if (null != userResponse && Response.isSuccess(userResponse)) {
            return userResponse.getResultList();
        }
        return null;
    }

    /**
     * 查询固定时间间隔的用户出借列表
     *
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    @Override
    public List<BorrowTenderVO> selectBorrowTenderListByDate(String repairStartDate, String repairEndDate) {
        return amTradeClient.selectBorrowTenderListByDate(repairStartDate, repairEndDate);
    }

    /**
     * 更新用户的出借记录
     *
     * @param borrowTender
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    @Override
    public void updateUserTender(BorrowTenderVO borrowTender, String repairStartDate, String repairEndDate) {
        // 获取出借用户信息
        int userId = borrowTender.getUserId();
        // 出借人信息
        UserVO users = amUserClient.findUserById(userId);
        if (users != null) {
            // 出借用户名
            borrowTender.setUserName(users.getUsername());
            // 获取出借人属性
            UserInfoVO userInfo = amUserClient.findUserInfoById(userId);
            // 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
            Integer attribute = null;
            if (userInfo != null) {
                // 获取出借用户的用户属性
                attribute = userInfo.getAttribute();
                if (attribute != null) {
                    attribute = getUserAttribute(userId, attribute, borrowTender.getCreateTime(), repairStartDate,
                            repairEndDate);
                    // 出借人用户属性
                    borrowTender.setTenderUserAttribute(attribute);
                    // 如果是线上员工或线下员工，推荐人的userId和username不插
                    if (attribute == 2 || attribute == 3) {
                        EmployeeCustomizeResponse employeeCustomizeResponse = amUserClient.selectEmployeeInfoByUserId(userId);
                        if (null != employeeCustomizeResponse && Response.isSuccess(employeeCustomizeResponse)) {
                            EmployeeCustomizeVO employeeCustomize = employeeCustomizeResponse.getResult();
                            if (employeeCustomize != null) {
                                borrowTender.setInviteRegionId(employeeCustomize.getRegionId());
                                borrowTender.setInviteRegionName(employeeCustomize.getRegionName());
                                borrowTender.setInviteBranchId(employeeCustomize.getBranchId());
                                borrowTender.setInviteBranchName(employeeCustomize.getBranchName());
                                borrowTender.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                                borrowTender.setInviteDepartmentName(employeeCustomize.getDepartmentName());
                            } else {
                                borrowTender.setInviteRegionId(0);
                                borrowTender.setInviteRegionName("");
                                borrowTender.setInviteBranchId(0);
                                borrowTender.setInviteBranchName("");
                                borrowTender.setInviteDepartmentId(0);
                                borrowTender.setInviteDepartmentName("");
                            }
                            borrowTender.setInviteUserId(0);
                            borrowTender.setInviteUserName("");
                            borrowTender.setInviteUserAttribute(0);
                        }
                    } else if (attribute == 1) {
                        SpreadsUserVO spreadsUserVO = amUserClient.searchSpreadsUserByUserId(userId);
                        if (null != spreadsUserVO) {
                            int refUserId = spreadsUserVO.getSpreadsUserId();
                            refUserId = getUserRefererId(userId, refUserId, borrowTender.getCreateTime(), repairStartDate,
                                    repairEndDate);
                            // 查找用户推荐人
                            UserVO userss = amUserClient.findUserById(refUserId);
                            if (userss != null) {
                                borrowTender.setInviteUserId(userss.getUserId());
                                borrowTender.setInviteUserName(userss.getUsername());
                            } else {
                                borrowTender.setInviteUserId(0);
                                borrowTender.setInviteUserName("");
                            }
                            // 推荐人信息
                            UserInfoVO refUsers = amUserClient.findUsersInfoById(refUserId);
                            // 推荐人用户属性
                            if (refUsers != null) {
                                int refererAttribute = refUsers.getAttribute();
                                refererAttribute = getUserAttribute(refUserId, refererAttribute,
                                        borrowTender.getCreateTime(), repairStartDate, repairEndDate);
                                borrowTender.setInviteUserAttribute(refererAttribute);
                            } else {
                                borrowTender.setInviteUserAttribute(0);
                            }
                            // 查找用户推荐人部门
                            EmployeeCustomizeResponse employeeCustomizeResponse = amUserClient.selectEmployeeInfoByUserId(refUserId);
                            if (null != employeeCustomizeResponse && Response.isSuccess(employeeCustomizeResponse)) {
                                EmployeeCustomizeVO employeeCustomize = employeeCustomizeResponse.getResult();
                                if (employeeCustomize != null) {
                                    borrowTender.setInviteRegionId(employeeCustomize.getRegionId());
                                    borrowTender.setInviteRegionName(employeeCustomize.getRegionName());
                                    borrowTender.setInviteBranchId(employeeCustomize.getBranchId());
                                    borrowTender.setInviteBranchName(employeeCustomize.getBranchName());
                                    borrowTender.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                                    borrowTender.setInviteDepartmentName(employeeCustomize.getDepartmentName());
                                } else {
                                    borrowTender.setInviteRegionId(0);
                                    borrowTender.setInviteRegionName("");
                                    borrowTender.setInviteBranchId(0);
                                    borrowTender.setInviteBranchName("");
                                    borrowTender.setInviteDepartmentId(0);
                                    borrowTender.setInviteDepartmentName("");
                                }
                            }
                        } else {
                            int refUserId = getUserRefererId(userId, 0, borrowTender.getCreateTime(), repairStartDate,
                                    repairEndDate);
                            // 查找用户推荐人
                            UserVO userss = amUserClient.findUserById(refUserId);
                            if (userss != null) {
                                borrowTender.setInviteUserId(userss.getUserId());
                                borrowTender.setInviteUserName(userss.getUsername());
                            } else {
                                borrowTender.setInviteUserId(0);
                                borrowTender.setInviteUserName("");
                            }
                            // 推荐人信息
                            UserInfoVO refUsers = amUserClient.findUsersInfoById(refUserId);
                            // 推荐人用户属性
                            if (refUsers != null) {
                                int refererAttribute = refUsers.getAttribute();
                                refererAttribute = getUserAttribute(refUserId, refererAttribute,
                                        borrowTender.getCreateTime(), repairStartDate, repairEndDate);
                                borrowTender.setInviteUserAttribute(refererAttribute);
                            } else {
                                borrowTender.setInviteUserAttribute(0);
                            }
                            // 查找用户推荐人部门
                            EmployeeCustomizeResponse employeeCustomizeResponse = amUserClient.selectEmployeeInfoByUserId(refUserId);
                            if (null != employeeCustomizeResponse && Response.isSuccess(employeeCustomizeResponse)) {
                                EmployeeCustomizeVO employeeCustomize = employeeCustomizeResponse.getResult();
                                if (employeeCustomize != null) {
                                    borrowTender.setInviteRegionId(employeeCustomize.getRegionId());
                                    borrowTender.setInviteRegionName(employeeCustomize.getRegionName());
                                    borrowTender.setInviteBranchId(employeeCustomize.getBranchId());
                                    borrowTender.setInviteBranchName(employeeCustomize.getBranchName());
                                    borrowTender.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                                    borrowTender.setInviteDepartmentName(employeeCustomize.getDepartmentName());
                                } else {
                                    borrowTender.setInviteRegionId(0);
                                    borrowTender.setInviteRegionName("");
                                    borrowTender.setInviteBranchId(0);
                                    borrowTender.setInviteBranchName("");
                                    borrowTender.setInviteDepartmentId(0);
                                    borrowTender.setInviteDepartmentName("");
                                }
                            }
                        }
                    } else if (attribute == 0) {
                        SpreadsUserVO spreadsUserVO = amUserClient.searchSpreadsUserByUserId(userId);
                        if (null != spreadsUserVO) {
                            int refUserId = spreadsUserVO.getSpreadsUserId();
                            refUserId = getUserRefererId(userId, refUserId, borrowTender.getCreateTime(), repairStartDate,
                                    repairEndDate);
                            // 查找推荐人
                            UserVO userss = amUserClient.findUserById(refUserId);
                            if (userss != null) {
                                borrowTender.setInviteUserId(userss.getUserId());
                                borrowTender.setInviteUserName(userss.getUsername());
                            } else {
                                borrowTender.setInviteUserId(0);
                                borrowTender.setInviteUserName("");
                            }
                            // 推荐人信息
                            UserInfoVO refUsers = amUserClient.findUsersInfoById(refUserId);
                            // 推荐人用户属性
                            if (refUsers != null) {
                                int refererAttribute = refUsers.getAttribute();
                                refererAttribute = getUserAttribute(refUserId, refererAttribute,
                                        borrowTender.getCreateTime(), repairStartDate, repairEndDate);
                                borrowTender.setInviteUserAttribute(refererAttribute);
                            } else {
                                borrowTender.setInviteUserAttribute(0);
                            }
                            // 更新其他的属性
                            borrowTender.setInviteRegionId(0);
                            borrowTender.setInviteRegionName("");
                            borrowTender.setInviteBranchId(0);
                            borrowTender.setInviteBranchName("");
                            borrowTender.setInviteDepartmentId(0);
                            borrowTender.setInviteDepartmentName("");
                        } else {
                            int refUserId = getUserRefererId(userId, 0, borrowTender.getCreateTime(), repairStartDate,
                                    repairEndDate);
                            // 查找推荐人
                            UserVO userss = amUserClient.findUserById(refUserId);
                            if (userss != null) {
                                borrowTender.setInviteUserId(userss.getUserId());
                                borrowTender.setInviteUserName(userss.getUsername());
                            } else {
                                borrowTender.setInviteUserId(0);
                                borrowTender.setInviteUserName("");
                            }
                            // 推荐人信息
                            UserInfoVO refUsers = amUserClient.findUsersInfoById(refUserId);
                            // 推荐人用户属性
                            if (refUsers != null) {
                                int refererAttribute = refUsers.getAttribute();
                                refererAttribute = getUserAttribute(refUserId, refererAttribute,
                                        borrowTender.getCreateTime(), repairStartDate, repairEndDate);
                                borrowTender.setInviteUserAttribute(refererAttribute);
                            } else {
                                borrowTender.setInviteUserAttribute(0);
                            }
                            // 更新其他的属性
                            borrowTender.setInviteRegionId(0);
                            borrowTender.setInviteRegionName("");
                            borrowTender.setInviteBranchId(0);
                            borrowTender.setInviteBranchName("");
                            borrowTender.setInviteDepartmentId(0);
                            borrowTender.setInviteDepartmentName("");
                        }
                    }
                }
            }
            BorrowTenderUpdRequest borrowTenderUpdRequest = new BorrowTenderUpdRequest();
            BeanUtils.copyProperties(borrowTender, borrowTenderUpdRequest);
            amTradeClient.updateBorrowTender(borrowTenderUpdRequest);
        }
    }

    /**
     * 获取用户的历史属性
     *
     * @param attribute
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    private int getUserAttribute(int userId, int attribute, Date addTime, String repairStartDate, String repairEndDate) {
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SpreadsUserVO sList = amUserClient.searchSpreadsUserByUserId(userId);
        // 用户现在有推荐人
        try {
            if (null != sList) {
                // 获取现在的推荐人id
                int refererId = sList.getSpreadsUserId();
                // 获取用户的历史推荐人
                refererId = this.getUserRefererId(userId, refererId, addTime, repairStartDate, repairEndDate);
                if (refererId == 0) {
                    // 假设用户是员工，查询用户的离职记录
                    AdminEmployeeLeaveCustomizeResponse adminEmployeeLeaveCustomizeResponse = amUserClient.selectUserLeaveByUserId(userId);
                    if (null != adminEmployeeLeaveCustomizeResponse && Response.isSuccess(adminEmployeeLeaveCustomizeResponse)) {
                        AdminEmployeeLeaveCustomizeVO userLeaveTemp = adminEmployeeLeaveCustomizeResponse.getResult();
                        // 用户的入职时间
                        String entryDateStr = userLeaveTemp.getEntryDate() + " 00:00:01";

                        // 入职时间
                        Date dateEntry = smp.parse(entryDateStr);
                        // 用户出借的时间之后用户入职
                        if (dateEntry.compareTo(addTime) >= 0) {
                            attribute = 0;
                        } else {
                            // 用户的推荐人在此段时间内没有离职记录
                            if (StringUtils.isNotBlank(userLeaveTemp.getEndTime())) {
                                // 用户的离职时间
                                String leaveEndTimeStartStr = userLeaveTemp.getEndTime();
                                Date leaveEndTimeStart = smp.parse(leaveEndTimeStartStr);
//                                int leaveEndTimeStart = GetDate.strYYYYMMDD2Timestamp2(leaveEndTimeStartStr);
                                // 用户出借的时间之后用户发生过离职
                                if (leaveEndTimeStart.compareTo(addTime) >= 0) {
//                                if (addTime <= leaveEndTimeStart) {
                                    int staffType = Integer.parseInt(userLeaveTemp.getStaffType());
                                    if (staffType == 1) {
                                        // 线上
                                        attribute = 3;
                                    } else if (staffType == 2) {
                                        // 线下
                                        attribute = 2;
                                    }
                                } else {
                                    // 用户出借的时间之前用户发生过离职
                                    attribute = 0;
                                }
                            } else {
                                int staffType = Integer.parseInt(userLeaveTemp.getStaffType());
                                if (staffType == 1) {
                                    // 线上
                                    attribute = 3;
                                } else if (staffType == 2) {
                                    // 线下
                                    attribute = 2;
                                }
                            }
                        }

                    } else {
                        attribute = 0;
                    }
                } else {
                    // 查询用户的离职记录
                    AdminEmployeeLeaveCustomizeResponse adminEmployeeLeaveCustomizeResponse = amUserClient.selectUserLeaveByUserId(refererId);
                    if (null != adminEmployeeLeaveCustomizeResponse && Response.isSuccess(adminEmployeeLeaveCustomizeResponse)) {
                        AdminEmployeeLeaveCustomizeVO userRefererLeaveTemp = adminEmployeeLeaveCustomizeResponse.getResult();
                        // 用户的离职时间
//                    String entryDateStr = userRefererLeaveTemp.getEntryDate();
                        // 入职时间
//                    int entryTime = GetDate.strYYYYMMDD2Timestamp2(entryDateStr);
                        // 用户的入职时间
                        String entryDateStr = userRefererLeaveTemp.getEntryDate() + " 00:00:01";
                        Date dateEntryDate = smp.parse(entryDateStr);
                        // 用户出借的时间之后用户入职
                        if (dateEntryDate.compareTo(addTime) >= 0) {
//                        if (addTime <= entryTime) {
                            attribute = 0;
                        } else {
                            // 用户的推荐人在此段时间内没有离职记录
                            if (StringUtils.isNotBlank(userRefererLeaveTemp.getEndTime())) {
                                // 用户的推荐人的离职时间
                                String leaveEndTimeStartStr = userRefererLeaveTemp.getEndTime();
                                Date leaveEndTimeStart = smp.parse(leaveEndTimeStartStr);
//                                int leaveEndTimeStart = GetDate.strYYYYMMDD2Timestamp2(leaveEndTimeStartStr);
                                // 用户出借的时间之后用户的推荐人发生过离职
                                if (leaveEndTimeStart.compareTo(addTime) >= 0) {
//                                if (addTime <= leaveEndTimeStart) {
                                    // 用户为有主单
                                    attribute = 1;
                                } else {
                                    // 用户出借的时间之前用户的推荐人发生过离职
                                    attribute = 0;
                                }
                            } else {
                                // 用户未有离职
                                attribute = 1;
                            }
                        }

                    } else {
                        // 用户的推荐人不是员工
                        attribute = 0;
                    }
                }
            } else {
                // 获取用户的推荐人
                int refererId = this.getUserRefererId(userId, 0, addTime, repairStartDate, repairEndDate);
                if (refererId == 0) {
                    // 假设用户是员工，查询用户的离职记录
                    AdminEmployeeLeaveCustomizeResponse adminEmployeeLeaveCustomizeResponse = amUserClient.selectUserLeaveByUserId(userId);
                    if (null != adminEmployeeLeaveCustomizeResponse && Response.isSuccess(adminEmployeeLeaveCustomizeResponse)) {
                        AdminEmployeeLeaveCustomizeVO userLeaveTemp = adminEmployeeLeaveCustomizeResponse.getResult();
                        // 用户的离职时间
//                        String entryDateStr = userLeaveTemp.getEntryDate();
//                        // 入职时间
//                        int entryTime = GetDate.strYYYYMMDD2Timestamp2(entryDateStr);
                        // 用户出借的时间之后用户入职
                        String entryDateStr = userLeaveTemp.getEntryDate() + " 00:00:01";
                        Date dateEntryDate = smp.parse(entryDateStr);
//                        if (addTime <= entryTime) {
                        if (dateEntryDate.compareTo(addTime) >= 0) {
                            attribute = 0;
                        } else {
                            // 用户的推荐人在此段时间内没有离职记录
                            if (StringUtils.isNotBlank(userLeaveTemp.getEndTime())) {
                                // 用户的离职时间
                                String leaveEndTimeStartStr = userLeaveTemp.getEndTime();
                                Date leaveEndTimeStart = smp.parse(leaveEndTimeStartStr);
                                // 用户出借的时间之后用户发生过离职
//                                if (addTime <= leaveEndTimeStart) {
                                if (leaveEndTimeStart.compareTo(addTime) >= 0) {
                                    int staffType = Integer.parseInt(userLeaveTemp.getStaffType());
                                    if (staffType == 1) {
                                        // 线上
                                        attribute = 3;
                                    } else if (staffType == 2) {
                                        // 线下
                                        attribute = 2;
                                    }
                                } else {
                                    // 用户出借的时间之前用户发生过离职
                                    attribute = 0;

                                }
                            } else {
                                int staffType = Integer.parseInt(userLeaveTemp.getStaffType());
                                if (staffType == 1) {
                                    // 线上
                                    attribute = 3;
                                } else if (staffType == 2) {
                                    // 线下
                                    attribute = 2;
                                }
                            }
                        }
                    }
                } else {
                    // 查询用户的离职记录
                    AdminEmployeeLeaveCustomizeResponse adminEmployeeLeaveCustomizeResponse = amUserClient.selectUserLeaveByUserId(refererId);
                    if (null != adminEmployeeLeaveCustomizeResponse && Response.isSuccess(adminEmployeeLeaveCustomizeResponse)) {
                        AdminEmployeeLeaveCustomizeVO userRefererLeaveTemp = adminEmployeeLeaveCustomizeResponse.getResult();
                        // 用户的入职时间
//                        String entryDateStr = userRefererLeaveTemp.getEntryDate();
//                        // 入职时间
//                        int entryTime = GetDate.strYYYYMMDD2Timestamp2(entryDateStr);
                        String entryDateStr = userRefererLeaveTemp.getEntryDate() + " 00:00:01";
                        Date dateEntryDate = smp.parse(entryDateStr);
                        // 用户出借的时间之后用户入职
//                        if (addTime <= entryTime) {
                        if (dateEntryDate.compareTo(addTime) >= 0) {
                            attribute = 0;
                        } else {
                            // 用户的推荐人在此段时间内没有离职记录
                            if (StringUtils.isNotBlank(userRefererLeaveTemp.getEndTime())) {
                                // 用户的推荐人的离职时间
                                String leaveEndTimeStartStr = userRefererLeaveTemp.getEndTime();
                               /* int leaveEndTimeStart = GetDate.strYYYYMMDD2Timestamp2(leaveEndTimeStartStr);
                                // 用户出借的时间之后用户的推荐人发生过离职
                                if (addTime <= leaveEndTimeStart) {*/
                                Date leaveEndTimeStart = smp.parse(leaveEndTimeStartStr);
                                if (leaveEndTimeStart.compareTo(addTime) >= 0) {
                                    // 用户为有主单
                                    attribute = 1;
                                } else {
                                    // 用户出借的时间之前用户的推荐人发生过离职
                                    attribute = 0;

                                }
                            } else {
                                // 用户的推荐人未有离职
                                attribute = 1;
                            }
                        }
                    } else {
                        // 用户的推荐人不是员工
                        attribute = 0;
                    }
                }
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return attribute;
    }

    /**
     * 获取用户的历史推荐人
     *
     * @param spreadUserId
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    public int getUserRefererId(int userId, int spreadUserId, Date addTime, String repairStartDate,
                                String repairEndDate) {
        // 获取用户的推荐人修改记录
        SpreadsUserLogResponse spreadsUserLogResponse = amUserClient.searchSpreadUsersLogByDate(userId, repairStartDate, repairEndDate);
        if (null != spreadsUserLogResponse && Response.isSuccess(spreadsUserLogResponse)) {
            List<SpreadsUserLogVO> spreadsUsersLogList = spreadsUserLogResponse.getResultList();
            if (spreadsUsersLogList != null && spreadsUsersLogList.size() > 0) {
                for (int i = 0; i < spreadsUsersLogList.size(); i++) {
                    // 下一个推荐人修改记录
                    SpreadsUserLogVO spreadUsersLog = spreadsUsersLogList.get(i);
                    Date modTimeStart = spreadUsersLog.getCreateTime();
                    // 获取用户的推荐人的首次修改时间
//                    int modTimeStart = Integer.parseInt(spreadUsersLog.getCreateTime());
//                    spreadUsersLog.getCreateTime();
                    // 用户出借的时间之前用户的推荐人被修改过
//                    if (addTime <= modTimeStart) {
                    if (modTimeStart.compareTo(addTime) >= 0) {
                        spreadUserId = spreadUsersLog.getOldSpreadsUserId() == null ? 0
                                : spreadUsersLog.getOldSpreadsUserId();
                        break;
                    } else {
                        if ((i + 1) == spreadsUsersLogList.size()) {
                            spreadUserId = spreadUsersLog.getSpreadsUserId() == null ? 0
                                    : spreadUsersLog.getSpreadsUserId();
                        }
                    }
                }
            }
        }


        return spreadUserId;
    }

}
