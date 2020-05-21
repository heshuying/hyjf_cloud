/*
Navicat MySQL Data Transfer

Source Server         : beta2_47.105.206.3
Source Server Version : 50725
Source Host           : 47.105.206.3:33306
Source Database       : hyjf_config

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-07-24 15:41:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ht_admin
-- ----------------------------
DROP TABLE IF EXISTS `ht_admin`;
CREATE TABLE `ht_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `truename` varchar(10) DEFAULT NULL COMMENT '真实姓名',
  `department_id` int(4) DEFAULT NULL COMMENT '部门ID',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别 0:男,1:女',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `phone` varchar(30) DEFAULT NULL COMMENT '电话',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `fax` varchar(50) DEFAULT NULL COMMENT '传真',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `state` varchar(10) DEFAULT 'disable' COMMENT '状态',
  `login_num` int(11) DEFAULT '0' COMMENT '登录次数',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(20) DEFAULT NULL COMMENT '最后登录IP',
  `operator` varchar(10) DEFAULT NULL COMMENT '操作者',
  `role` varchar(10) DEFAULT 'user' COMMENT '权限',
  `status` tinyint(1) DEFAULT '0',
  `posttime` int(11) DEFAULT '0',
  `group_id` int(11) DEFAULT NULL COMMENT '作废，每个用户可以有多个角色,关联hyjf_admin_and_role',
  `accredit` varchar(255) DEFAULT NULL COMMENT '???',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除FLAG',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=568 DEFAULT CHARSET=utf8 COMMENT='管理用户';

-- ----------------------------
-- Table structure for ht_admin_and_role
-- ----------------------------
DROP TABLE IF EXISTS `ht_admin_and_role`;
CREATE TABLE `ht_admin_and_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL,
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=352 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_admin_group
-- ----------------------------
DROP TABLE IF EXISTS `ht_admin_group`;
CREATE TABLE `ht_admin_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(20) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `access` varchar(2000) DEFAULT NULL,
  `open_status` tinyint(4) DEFAULT '0',
  `sort` int(5) DEFAULT '0',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='管理员权限表';

-- ----------------------------
-- Table structure for ht_admin_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_admin_log`;
CREATE TABLE `ht_admin_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `event` varchar(20) DEFAULT NULL COMMENT '事件',
  `content` varchar(3000) DEFAULT NULL COMMENT '日志内容',
  `create_ip` varchar(15) DEFAULT NULL COMMENT 'IP地址',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员日志表';

-- ----------------------------
-- Table structure for ht_admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `ht_admin_menu`;
CREATE TABLE `ht_admin_menu` (
  `menu_uuid` varchar(36) NOT NULL COMMENT '文档ID',
  `menu_puuid` varchar(36) NOT NULL DEFAULT '0' COMMENT '上级分类ID',
  `menu_ctrl` varchar(20) DEFAULT NULL,
  `menu_icon` varchar(50) DEFAULT NULL,
  `menu_name` varchar(50) DEFAULT '' COMMENT '标题',
  `menu_sort` int(10) unsigned DEFAULT '0' COMMENT '排序（同级有效）',
  `menu_url` varchar(255) DEFAULT '' COMMENT '链接地址',
  `menu_hide` tinyint(1) unsigned DEFAULT '0' COMMENT '是否隐藏',
  `menu_tip` varchar(255) DEFAULT '' COMMENT '提示',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除FLAG',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`menu_uuid`),
  KEY `pid` (`menu_puuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_admin_menu_permssions
-- ----------------------------
DROP TABLE IF EXISTS `ht_admin_menu_permssions`;
CREATE TABLE `ht_admin_menu_permssions` (
  `menu_id` varchar(36) NOT NULL,
  `permission_id` varchar(36) NOT NULL,
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`menu_id`,`permission_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_admin_permissions
-- ----------------------------
DROP TABLE IF EXISTS `ht_admin_permissions`;
CREATE TABLE `ht_admin_permissions` (
  `permission_uuid` varchar(36) NOT NULL DEFAULT '' COMMENT '权限编号',
  `permission` varchar(20) DEFAULT NULL COMMENT '权限按钮ID',
  `permission_name` varchar(20) DEFAULT NULL COMMENT '权限名称',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除FLAG',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`permission_uuid`),
  KEY `pid` (`permission_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for ht_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `ht_admin_role`;
CREATE TABLE `ht_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  `sort` int(5) DEFAULT '0' COMMENT '排序',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除FLAG',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ht_admin_role_menu_permissions
-- ----------------------------
DROP TABLE IF EXISTS `ht_admin_role_menu_permissions`;
CREATE TABLE `ht_admin_role_menu_permissions` (
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `menu_uuid` varchar(36) NOT NULL COMMENT '菜单编号',
  `permission_uuid` varchar(36) NOT NULL COMMENT '权限编号',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除FLAG',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`,`menu_uuid`,`permission_uuid`),
  KEY `pid` (`menu_uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ht_admin_utm_read_permissions
-- ----------------------------
DROP TABLE IF EXISTS `ht_admin_utm_read_permissions`;
CREATE TABLE `ht_admin_utm_read_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `admin_user_id` int(11) DEFAULT NULL COMMENT '系统用户id',
  `admin_user_name` varchar(20) DEFAULT NULL COMMENT '系统用户名',
  `utm_ids` varchar(200) DEFAULT NULL COMMENT '渠道id',
  `key_code` varchar(20) DEFAULT NULL COMMENT '关键字',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='渠道账号管理表';

-- ----------------------------
-- Table structure for ht_answer
-- ----------------------------
DROP TABLE IF EXISTS `ht_answer`;
CREATE TABLE `ht_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `question_id` int(11) NOT NULL COMMENT '问题id',
  `answer` varchar(250) NOT NULL COMMENT '答案',
  `score` tinyint(2) unsigned NOT NULL COMMENT '评分',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态0是可用，1是不可用',
  `sort` tinyint(3) unsigned DEFAULT NULL,
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='问卷答案表';

-- ----------------------------
-- Table structure for ht_app_borrow_image
-- ----------------------------
DROP TABLE IF EXISTS `ht_app_borrow_image`;
CREATE TABLE `ht_app_borrow_image` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `borrow_image` varchar(20) NOT NULL COMMENT '主键',
  `borrow_image_title` varchar(100) DEFAULT NULL,
  `borrow_image_name` varchar(1000) DEFAULT NULL,
  `borrow_image_realname` varchar(20) DEFAULT NULL,
  `borrow_image_url` varchar(1000) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `sort` tinyint(1) DEFAULT NULL COMMENT '排序',
  `page_url` varchar(200) DEFAULT NULL,
  `page_type` varchar(2) DEFAULT NULL,
  `version` varchar(10) DEFAULT NULL,
  `status` tinyint(3) unsigned DEFAULT '0' COMMENT '配置状态：0启用 1禁用',
  `version_max` varchar(10) DEFAULT NULL COMMENT '最大版本号',
  `jump_name` varchar(10) DEFAULT NULL COMMENT '跳转标示',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='app首页链接图片表';

-- ----------------------------
-- Table structure for ht_bank_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_config`;
CREATE TABLE `ht_bank_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `code` varchar(10) DEFAULT NULL COMMENT '银行代号',
  `app_logo` varchar(200) DEFAULT NULL COMMENT 'appLOGO',
  `logo` varchar(200) DEFAULT NULL COMMENT 'LOGO',
  `personal_ebank` tinyint(1) unsigned DEFAULT '0' COMMENT '个人银行',
  `enterprise_ebank` tinyint(1) unsigned DEFAULT '0' COMMENT '企业银行',
  `quick_payment` tinyint(1) unsigned DEFAULT '0' COMMENT '快捷支付',
  `immediately_withdraw` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '即时提现1是,0否',
  `quick_withdraw` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '快速提现1是,0否',
  `normal_withdraw` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '一般提现1是,0否',
  `withdraw_defaulttype` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '默认提现方式,0一般提现,1快速提现,2即时提现,默认0',
  `status` tinyint(1) unsigned DEFAULT '1' COMMENT '使用状态',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='银行配置表';

-- ----------------------------
-- Table structure for ht_bank_interface
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_interface`;
CREATE TABLE `ht_bank_interface` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `interface_type` varchar(30) NOT NULL COMMENT '银行接口类型',
  `interface_name` varchar(30) NOT NULL COMMENT '银行接口类型名称',
  `interface_status` tinyint(1) NOT NULL COMMENT '银行接口使用状态(0:老接口，1:新接口)',
  `create_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建用户名',
  `update_time` datetime(3) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `is_usable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用 0否 1是',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0否 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='银行接口配置表';

-- ----------------------------
-- Table structure for ht_bank_recharge_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_recharge_config`;
CREATE TABLE `ht_bank_recharge_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bank_id` int(11) unsigned DEFAULT NULL COMMENT '银行id（bank_config）',
  `access_code` tinyint(1) unsigned DEFAULT '0' COMMENT '接入方式  0全国',
  `bank_type` tinyint(1) unsigned DEFAULT '0' COMMENT '银行卡类型 0借记卡',
  `single_quota` decimal(13,2) DEFAULT NULL COMMENT '单笔充值限额',
  `single_card_quota` decimal(13,2) DEFAULT NULL COMMENT '单卡单日累计充值限额',
  `status` tinyint(1) unsigned DEFAULT '0' COMMENT '状态 0 启用 1禁用',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='银行卡充值限额配置表';

-- ----------------------------
-- Table structure for ht_bank_return_code_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_return_code_config`;
CREATE TABLE `ht_bank_return_code_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `tx_code` varchar(20) DEFAULT '' COMMENT '接口代码',
  `method_name` varchar(20) DEFAULT '' COMMENT '接口方法名',
  `ret_code` varchar(20) NOT NULL DEFAULT '' COMMENT '银行返回码',
  `ret_msg` varchar(100) NOT NULL DEFAULT '' COMMENT '银行返回描述',
  `error_msg` varchar(100) NOT NULL DEFAULT '' COMMENT '平台错误描述',
  `page_key` varchar(20) DEFAULT '' COMMENT '保留字段：page标记',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0 禁用；1 启用',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_ret_code` (`ret_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3911 DEFAULT CHARSET=utf8 COMMENT='银行返回码配置表';

-- ----------------------------
-- Table structure for ht_card_bin
-- ----------------------------
DROP TABLE IF EXISTS `ht_card_bin`;
CREATE TABLE `ht_card_bin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `issuing_bank_name` varchar(30) DEFAULT NULL COMMENT '发卡行名称及机构代码',
  `card_name` varchar(30) DEFAULT NULL COMMENT '卡名',
  `card_length` int(10) DEFAULT '0' COMMENT '卡号长度',
  `card_num_format` varchar(30) DEFAULT NULL COMMENT '卡号格式',
  `bin_length` int(10) DEFAULT '0' COMMENT 'BIN长度',
  `bin_value` varchar(30) DEFAULT NULL COMMENT '取值',
  `card_type` varchar(10) DEFAULT NULL COMMENT '卡种',
  `bank_id` varchar(10) DEFAULT NULL COMMENT '银行Id',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_bin_value` (`bin_value`)
) ENGINE=InnoDB AUTO_INCREMENT=3955 DEFAULT CHARSET=utf8 COMMENT='卡BIN配置表';

-- ----------------------------
-- Table structure for ht_category
-- ----------------------------
DROP TABLE IF EXISTS `ht_category`;
CREATE TABLE `ht_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '文档ID',
  `title` varchar(50) NOT NULL DEFAULT '' COMMENT '标题',
  `pid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '上级分类ID',
  `code` varchar(200) DEFAULT NULL,
  `group` varchar(20) DEFAULT NULL COMMENT '分组',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序（同级有效）',
  `hide` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否隐藏',
  `tip` varchar(255) DEFAULT '' COMMENT '提示',
  `level` int(11) DEFAULT '0',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=180 DEFAULT CHARSET=utf8 COMMENT='分类管理，前台树状图';

-- ----------------------------
-- Table structure for ht_cert_err_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_cert_err_log`;
CREATE TABLE `ht_cert_err_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_ord_id` varchar(100) NOT NULL COMMENT 'mongo里面的订单号',
  `inf_type` int(11) DEFAULT NULL COMMENT '接口类型  见 CertCallConstant 请求类型 ',
  `send_time` int(11) DEFAULT NULL COMMENT '上传时间',
  `send_status` tinyint(1) DEFAULT '0' COMMENT '上报结果 0初始，1成功，9失败 99无响应',
  `send_count` tinyint(1) DEFAULT '0' COMMENT '上送次数 最多三次',
  `result_code` varchar(50) DEFAULT '0' COMMENT '上报返回状态',
  `result_msg` varchar(500) DEFAULT '0' COMMENT '上报返回描述',
  `query_result` tinyint(1) DEFAULT '0' COMMENT '查询结果 0初始，1成功，9失败 99无响应',
  `query_msg` varchar(500) DEFAULT '' COMMENT '查询结果返回描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_send_status` (`send_status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3370 DEFAULT CHARSET=utf8 COMMENT='国家互联网应急中心错误日志表';

-- ----------------------------
-- Table structure for ht_cert_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_cert_log`;
CREATE TABLE `ht_cert_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mq_content` varchar(500) DEFAULT '' COMMENT 'mq内容',
  `log_ord_id` varchar(100) NOT NULL COMMENT 'mongo里面的订单号',
  `inf_type` int(11) DEFAULT NULL COMMENT '接口类型  见 CertCallConstant 请求类型 ',
  `send_time` int(11) DEFAULT NULL COMMENT '上传时间',
  `send_status` tinyint(1) DEFAULT '0' COMMENT '上报结果 0初始，1成功，9失败 99无响应',
  `result_code` varchar(50) DEFAULT '0' COMMENT '上报返回状态',
  `result_msg` varchar(500) DEFAULT '0' COMMENT '上报返回描述',
  `query_result` tinyint(1) DEFAULT '0' COMMENT '查询结果 0初始，1成功，9失败 99无响应',
  `query_msg` varchar(500) DEFAULT '' COMMENT '查询结果返回描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_send_status` (`send_status`) USING BTREE,
  KEY `idx_inf_type` (`inf_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12140 DEFAULT CHARSET=utf8 COMMENT='国家互联网应急中心日志表';

-- ----------------------------
-- Table structure for ht_company_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_company_info`;
CREATE TABLE `ht_company_info` (
  `id` mediumint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '分公司id',
  `name` varchar(30) DEFAULT '' COMMENT '公司名称',
  `provence` varchar(20) DEFAULT '' COMMENT '所属省',
  `city` varchar(20) DEFAULT '' COMMENT '所属市',
  `registration_time` varchar(20) DEFAULT '' COMMENT '公司注册时间',
  `brief` varchar(1000) DEFAULT '' COMMENT '公司简介',
  `tel` varchar(15) DEFAULT '' COMMENT '联系电话',
  `banner` varchar(255) DEFAULT '' COMMENT '公司banner图',
  `reg_img1` varchar(255) DEFAULT '' COMMENT '注册图片1',
  `reg_img2` varchar(255) DEFAULT '' COMMENT '注册图片2',
  `reg_img3` varchar(255) DEFAULT '' COMMENT '注册图片3',
  `reg_img4` varchar(255) DEFAULT NULL,
  `reg_img5` varchar(255) DEFAULT NULL,
  `reg_img6` varchar(255) DEFAULT NULL,
  `QQ` int(30) DEFAULT NULL,
  `weibo` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `weixin` varchar(255) DEFAULT NULL,
  `status` tinyint(2) unsigned DEFAULT '1' COMMENT '1启用 0停用',
  `reg_money` varchar(20) DEFAULT '' COMMENT '注册资金',
  `sort` int(10) NOT NULL DEFAULT '0' COMMENT '排序,大的在前',
  `list_img` varchar(255) DEFAULT NULL COMMENT '列表页图片',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='公司管理';

-- ----------------------------
-- Table structure for ht_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_config`;
CREATE TABLE `ht_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '配置名称',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '配置类型',
  `title` varchar(50) NOT NULL DEFAULT '' COMMENT '配置说明',
  `group` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '配置分组',
  `extra` varchar(255) NOT NULL DEFAULT '' COMMENT '配置值',
  `remark` varchar(100) NOT NULL COMMENT '配置说明',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `value` varchar(2000) NOT NULL COMMENT '配置值',
  `sort` smallint(3) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`),
  KEY `type` (`type`),
  KEY `group` (`group`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='配置管理';

-- ----------------------------
-- Table structure for ht_config_applicant
-- ----------------------------
DROP TABLE IF EXISTS `ht_config_applicant`;
CREATE TABLE `ht_config_applicant` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `applicant` varchar(20) NOT NULL COMMENT '项目申请人',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `applicant_index` (`applicant`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2076 DEFAULT CHARSET=utf8 COMMENT='项目申请人配置';

-- ----------------------------
-- Table structure for ht_content
-- ----------------------------
DROP TABLE IF EXISTS `ht_content`;
CREATE TABLE `ht_content` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) unsigned DEFAULT '0' COMMENT '是否单页1单页，0非单页',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `cate_id` int(11) unsigned DEFAULT '0' COMMENT '分类ID',
  `code` varchar(20) DEFAULT NULL COMMENT '别名',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '文章状态',
  `source` varchar(50) DEFAULT NULL COMMENT '文章来源',
  `author` varchar(50) DEFAULT NULL COMMENT '作者',
  `thumb` varchar(200) DEFAULT NULL COMMENT '缩略图',
  `summary` varchar(255) DEFAULT NULL COMMENT '简要介绍',
  `content` text COMMENT '内容',
  `seo_title` varchar(200) DEFAULT NULL,
  `seo_keyword` varchar(200) DEFAULT NULL,
  `seo_description` varchar(255) DEFAULT NULL,
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `hits` int(11) DEFAULT '0' COMMENT '点击数',
  `out_link` varchar(100) NOT NULL,
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `name` (`title`),
  KEY `cate_id` (`cate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=880 DEFAULT CHARSET=utf8 COMMENT='内容管理';

-- ----------------------------
-- Table structure for ht_content_article
-- ----------------------------
DROP TABLE IF EXISTS `ht_content_article`;
CREATE TABLE `ht_content_article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '文章管理主键',
  `type` varchar(50) NOT NULL COMMENT '文章分类',
  `title` varchar(50) NOT NULL COMMENT '文章标题',
  `status` tinyint(1) unsigned NOT NULL COMMENT '状态0关闭，1启用',
  `author` varchar(50) NOT NULL COMMENT '文章作者',
  `imgUrl` varchar(100) DEFAULT '' COMMENT '文章图片',
  `summary` varchar(1000) DEFAULT NULL COMMENT '简介',
  `click` int(11) DEFAULT NULL COMMENT '点击率',
  `content` varchar(20000) NOT NULL COMMENT '文章内容',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1996 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_content_environment
-- ----------------------------
DROP TABLE IF EXISTS `ht_content_environment`;
CREATE TABLE `ht_content_environment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '办公环境主键',
  `name` varchar(50) NOT NULL COMMENT '各个公司名',
  `img_url` varchar(255) NOT NULL COMMENT '图片地址',
  `img_type` int(1) NOT NULL COMMENT '图片类型：0横图，1竖图',
  `order_id` int(11) NOT NULL COMMENT '排序数字',
  `status` int(1) NOT NULL COMMENT '状态：0关闭，1启用',
  `describe` varchar(255) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_content_help
-- ----------------------------
DROP TABLE IF EXISTS `ht_content_help`;
CREATE TABLE `ht_content_help` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pcate_id` int(10) DEFAULT NULL,
  `cate_id` int(10) DEFAULT '0' COMMENT '分类ID',
  `title` varchar(100) DEFAULT NULL COMMENT '问题标题',
  `content` varchar(20000) DEFAULT NULL COMMENT '内容',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态 0：启用 1：开启 2：常见问题',
  `type` int(1) DEFAULT '0' COMMENT '是否单页1单页，0非单页',
  `code` varchar(200) DEFAULT NULL COMMENT '别名',
  `source` varchar(50) DEFAULT NULL COMMENT '文章来源',
  `author` varchar(50) DEFAULT NULL COMMENT '作者',
  `thumb` varchar(200) DEFAULT NULL COMMENT '缩略图',
  `summary` varchar(255) DEFAULT NULL COMMENT '简要介绍',
  `seo_title` varchar(200) DEFAULT NULL,
  `seo_keyword` varchar(200) DEFAULT NULL,
  `seo_description` varchar(255) DEFAULT NULL,
  `hits` int(11) DEFAULT '0' COMMENT '点击数',
  `out_link` varchar(100) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `zhichi_status` tinyint(2) unsigned DEFAULT '0' COMMENT '智齿客服状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=999 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_content_qualify
-- ----------------------------
DROP TABLE IF EXISTS `ht_content_qualify`;
CREATE TABLE `ht_content_qualify` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资质荣誉id',
  `name` varchar(50) NOT NULL COMMENT '资质荣誉名',
  `imgUrl` varchar(255) NOT NULL COMMENT '证书图片路径',
  `describe` varchar(255) DEFAULT NULL COMMENT '资质描述',
  `order_num` int(11) NOT NULL COMMENT '排序数字',
  `status` tinyint(1) unsigned NOT NULL COMMENT '使用状态：0关闭，1启用',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_coupon_check
-- ----------------------------
DROP TABLE IF EXISTS `ht_coupon_check`;
CREATE TABLE `ht_coupon_check` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(30) NOT NULL COMMENT '文件名',
  `file_path` varchar(100) NOT NULL COMMENT '文件路径',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '发放状态 待审核:1,审核通过:2,审核不通过:3',
  `remark` varchar(20) DEFAULT NULL COMMENT '审核意见备注',
  `de_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记 已删除:1,未删除:0',
  `create_name` varchar(10) DEFAULT NULL COMMENT '添加人',
  `update_name` varchar(10) DEFAULT NULL COMMENT '更新人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_debt_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_config`;
CREATE TABLE `ht_debt_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `attorn_rate` decimal(5,2) DEFAULT NULL COMMENT '服务费率',
  `concession_rate_up` decimal(5,2) DEFAULT NULL COMMENT '折让率上限',
  `concession_rate_down` decimal(5,2) DEFAULT NULL COMMENT '折让率下限',
  `toggle` tinyint(1) DEFAULT '1' COMMENT '散标债转开关',
  `close_des` varchar(1024) DEFAULT NULL COMMENT '关闭提示',
  `config_type` tinyint(1) DEFAULT '1' COMMENT '类型1:债转配置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='债转配置表';

-- ----------------------------
-- Table structure for ht_debt_config_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_config_log`;
CREATE TABLE `ht_debt_config_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hyjf_debt_config_id` int(11) NOT NULL COMMENT '债转配置主键',
  `attorn_rate` decimal(5,2) DEFAULT NULL COMMENT '服务费率',
  `concession_rate_up` decimal(5,2) DEFAULT NULL COMMENT '折让率上限',
  `concession_rate_down` decimal(5,2) DEFAULT NULL COMMENT '折让率下限',
  `toggle` tinyint(1) DEFAULT '1' COMMENT '散标债转开关',
  `close_des` varchar(1024) DEFAULT NULL COMMENT '关闭提示',
  `update_user` int(11) DEFAULT NULL COMMENT '修改人',
  `update_username` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `ip_address` varchar(64) DEFAULT NULL COMMENT 'ip地址',
  `mac_address` varchar(64) DEFAULT NULL COMMENT 'MAC地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='债转配置日志表';

-- ----------------------------
-- Table structure for ht_event
-- ----------------------------
DROP TABLE IF EXISTS `ht_event`;
CREATE TABLE `ht_event` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `event_time` varchar(30) DEFAULT NULL COMMENT '纪事发生的时间',
  `title` varchar(80) NOT NULL COMMENT '纪事标题',
  `content` varchar(20000) NOT NULL COMMENT '纪事内容',
  `event_year` int(11) DEFAULT NULL COMMENT '纪事所在年份(用于归类)',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '纪事状态: 0:关闭,1:启用',
  `add_admin` varchar(255) DEFAULT NULL COMMENT '管理员',
  `act_time` int(11) DEFAULT NULL COMMENT '纪事实际时间戳',
  `thumb` varchar(200) DEFAULT NULL COMMENT '缩略图',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='公司纪事表';

-- ----------------------------
-- Table structure for ht_fee_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_fee_config`;
CREATE TABLE `ht_fee_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bank_code` varchar(11) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '银行名称',
  `personal_credit` varchar(10) NOT NULL COMMENT '个人网银充值',
  `enterprise_credit` varchar(10) NOT NULL COMMENT '企业网银充值',
  `quick_payment` varchar(10) NOT NULL COMMENT '快捷支付充值',
  `direct_takeout` varchar(10) NOT NULL COMMENT '即时提现',
  `direct_takeout_percent` varchar(10) DEFAULT NULL COMMENT '即时提现、千分比',
  `quick_takeout` varchar(10) NOT NULL COMMENT '快速提现',
  `quick_takeout_percent` varchar(10) DEFAULT NULL COMMENT '快速提现、千分比',
  `normal_takeout` varchar(10) NOT NULL COMMENT '普通提现',
  `remark` varchar(100) NOT NULL DEFAULT 'NULL' COMMENT '说明',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_feerate_modify_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_feerate_modify_log`;
CREATE TABLE `ht_feerate_modify_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '变更日志id',
  `inst_code` varchar(20) NOT NULL COMMENT '机构编号-- 资产来源：10000001 关联 hyjf_hjh_asset_type 查询',
  `asset_type` tinyint(2) unsigned NOT NULL COMMENT '产品类型：1 关联 hyjf_hjh_asset_type 查询',
  `borrow_period` int(10) DEFAULT '0' COMMENT '借款期限 60 或 3 ',
  `borrow_style` varchar(100) DEFAULT '0' COMMENT '还款方式 - endmonth',
  `borrow_apr` decimal(10,2) DEFAULT '0.00' COMMENT '自动发标利率',
  `service_fee` varchar(100) DEFAULT NULL COMMENT '服务费',
  `manage_fee` varchar(100) DEFAULT NULL COMMENT '管理费',
  `revenue_diff_rate` varchar(100) DEFAULT NULL COMMENT '收益差率',
  `late_interest_rate` varchar(13) DEFAULT NULL COMMENT '逾期利率(汇计划用)',
  `late_free_days` tinyint(2) unsigned DEFAULT '0' COMMENT '逾期免息天数(汇计划用)',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态：0 启用，1 禁用',
  `modify_type` tinyint(1) unsigned NOT NULL COMMENT '修改类型 0:全部 1：增加 2:修改 3:删除',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=402 DEFAULT CHARSET=utf8 COMMENT='费率配置变更日志表';

-- ----------------------------
-- Table structure for ht_gateway_api_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_gateway_api_config`;
CREATE TABLE `ht_gateway_api_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `path` varchar(255) NOT NULL,
  `service_id` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `retryable` tinyint(1) DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `strip_prefix` int(11) DEFAULT '1',
  `secure_visit_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '安全访问控制标识，  0-无需登陆访问 1-需要登陆访问',
  `api_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `path_UNIQUE` (`path`)
) ENGINE=InnoDB AUTO_INCREMENT=10793 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_hjh_user_auth_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_user_auth_config`;
CREATE TABLE `ht_hjh_user_auth_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `auth_type` tinyint(1) DEFAULT NULL COMMENT '授权类型 1缴费授权 2还款授权 3自动投标 4自动债转',
  `personal_max_amount` int(4) DEFAULT NULL COMMENT '个人最高金额(元)',
  `enterprise_max_amount` int(4) DEFAULT NULL COMMENT '企业最高金额(元)',
  `auth_period` tinyint(2) DEFAULT NULL COMMENT '授权期限(年)',
  `enabled_status` tinyint(1) DEFAULT NULL COMMENT '启用状态 0未启用 1启用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='授权配置表';

-- ----------------------------
-- Table structure for ht_hjh_user_auth_config_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_user_auth_config_log`;
CREATE TABLE `ht_hjh_user_auth_config_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `auth_config_id` int(11) NOT NULL COMMENT '关联的授权配置id',
  `auth_type` tinyint(1) DEFAULT NULL COMMENT '授权类型 1缴费授权 2还款授权 3自动投标 4自动债转',
  `personal_max_amount` int(8) DEFAULT NULL COMMENT '个人最高金额(元)',
  `enterprise_max_amount` int(8) DEFAULT NULL COMMENT '企业最高金额(元)',
  `auth_period` tinyint(2) DEFAULT NULL COMMENT '授权期限(年)',
  `enabled_status` tinyint(1) DEFAULT NULL COMMENT '启用状态 0未启用 1启用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `ip` varchar(15) DEFAULT NULL COMMENT 'ip地址',
  `mac` varchar(48) DEFAULT NULL COMMENT 'mac地址',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=utf8 COMMENT='授权配置表操作记录';

-- ----------------------------
-- Table structure for ht_holidays_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_holidays_config`;
CREATE TABLE `ht_holidays_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `year` varchar(20) NOT NULL COMMENT '年份',
  `events_name` varchar(50) NOT NULL COMMENT '假日名称',
  `statr_time` varchar(20) NOT NULL COMMENT '开始时间',
  `end_time` varchar(20) NOT NULL COMMENT '结束时间',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='节假日配置表';

-- ----------------------------
-- Table structure for ht_holidays_config_new
-- ----------------------------
DROP TABLE IF EXISTS `ht_holidays_config_new`;
CREATE TABLE `ht_holidays_config_new` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `day_time` date NOT NULL COMMENT 'yyyymmdd',
  `holiday_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '节假日标识: 工作日-0, 休息日-1, 节假日-2',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_DAY_INDEX` (`day_time`)
) ENGINE=InnoDB AUTO_INCREMENT=100230 DEFAULT CHARSET=utf8 COMMENT='节假日配置表';

-- ----------------------------
-- Table structure for ht_idcard
-- ----------------------------
DROP TABLE IF EXISTS `ht_idcard`;
CREATE TABLE `ht_idcard` (
  `bm` varchar(20) NOT NULL DEFAULT '' COMMENT '身份证前6位',
  `area` varchar(100) DEFAULT NULL COMMENT '地区',
  PRIMARY KEY (`bm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_invite
-- ----------------------------
DROP TABLE IF EXISTS `ht_invite`;
CREATE TABLE `ht_invite` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` varchar(20000) NOT NULL COMMENT '纪事内容',
  `img` varchar(200) DEFAULT NULL COMMENT '图片地址',
  `link_url` varchar(200) NOT NULL COMMENT 'URL链接',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='邀请好友配置表';

-- ----------------------------
-- Table structure for ht_job
-- ----------------------------
DROP TABLE IF EXISTS `ht_job`;
CREATE TABLE `ht_job` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `office_name` varchar(20) DEFAULT NULL COMMENT '职位名称',
  `place` varchar(30) DEFAULT NULL COMMENT '工作地点',
  `content` varchar(20000) NOT NULL COMMENT '招聘内容',
  `status` tinyint(2) DEFAULT '1' COMMENT '是否显示（1：显示，0不显示）',
  `add_admin` varchar(10) DEFAULT NULL COMMENT '管理员',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `persons` int(11) DEFAULT NULL COMMENT '招聘人数',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='招聘表';

-- ----------------------------
-- Table structure for ht_jx_bank_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_jx_bank_config`;
CREATE TABLE `ht_jx_bank_config` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bank_id` int(10) NOT NULL DEFAULT '0' COMMENT '银行ID',
  `bank_name` varchar(30) DEFAULT NULL COMMENT '银行名称',
  `pay_alliance_code` varchar(30) DEFAULT NULL COMMENT '银行总行的行联号',
  `bank_code` varchar(20) DEFAULT '' COMMENT '银行代码',
  `bank_icon` varchar(200) DEFAULT NULL COMMENT '银行icon',
  `bank_logo` varchar(200) DEFAULT NULL COMMENT '银行logo',
  `quick_payment` tinyint(1) DEFAULT '1' COMMENT '支持快捷支付：0 不支持  1 支持',
  `single_quota` decimal(12,2) DEFAULT '0.00' COMMENT '单笔限额',
  `single_card_quota` decimal(12,2) DEFAULT '0.00' COMMENT '单卡单日限额',
  `fee_withdraw` decimal(10,2) DEFAULT '1.00' COMMENT '提现手续费',
  `sort_id` smallint(5) unsigned DEFAULT '1' COMMENT '排序id',
  `remark` varchar(100) DEFAULT '' COMMENT '备注说明',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `month_card_quota` decimal(12,2) DEFAULT '0.00' COMMENT '单月限额',
  `android_market` varchar(255) DEFAULT '' COMMENT '银行android应用市场链接',
  `ios_market` varchar(255) DEFAULT '' COMMENT '银行ios应用市场链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='江西银行的银行卡配置表';

-- ----------------------------
-- Table structure for ht_landing_page
-- ----------------------------
DROP TABLE IF EXISTS `ht_landing_page`;
CREATE TABLE `ht_landing_page` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `page_name` varchar(20) DEFAULT NULL COMMENT '着落页名称',
  `channel_name` varchar(20) DEFAULT NULL,
  `page_url` varchar(200) DEFAULT NULL COMMENT '链接地址',
  `code_url` varchar(200) DEFAULT NULL COMMENT '二维码地址',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_link
-- ----------------------------
DROP TABLE IF EXISTS `ht_link`;
CREATE TABLE `ht_link` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) DEFAULT NULL COMMENT '连接类型1友情连接2合作伙伴',
  `status` tinyint(2) unsigned DEFAULT '0',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `url` varchar(60) DEFAULT '',
  `webname` varchar(30) DEFAULT '',
  `summary` varchar(1000) DEFAULT NULL COMMENT '机构介绍',
  `summary2` varchar(1000) DEFAULT NULL COMMENT '机构介绍2-用于展示',
  `control_measures` varchar(1000) DEFAULT NULL COMMENT '风控措施',
  `operating_process` varchar(1000) DEFAULT NULL COMMENT '操作流程',
  `logo` varchar(255) DEFAULT NULL,
  `province` char(10) DEFAULT '',
  `city` char(10) DEFAULT '',
  `area` char(10) DEFAULT '',
  `phone` char(15) NOT NULL,
  `address` varchar(50) NOT NULL,
  `setup_time` varchar(20) NOT NULL,
  `cooperation_time` varchar(20) NOT NULL,
  `logo1` varchar(255) DEFAULT NULL COMMENT '新网站投资显示logo',
  `approval_by` varchar(50) DEFAULT NULL COMMENT '审批机构',
  `register_capital` int(11) DEFAULT NULL COMMENT '注册资本',
  `partner_type` tinyint(2) NOT NULL,
  `hits` int(10) DEFAULT '0',
  `isindex` tinyint(4) DEFAULT '0' COMMENT '0不显示在首页，1显示首页',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_message_push_tag
-- ----------------------------
DROP TABLE IF EXISTS `ht_message_push_tag`;
CREATE TABLE `ht_message_push_tag` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_name` varchar(10) DEFAULT NULL COMMENT '标签名称',
  `tag_code` varchar(40) DEFAULT NULL COMMENT '标签编码',
  `introduction` varchar(200) DEFAULT NULL COMMENT '简介',
  `icon_url` varchar(200) DEFAULT NULL COMMENT 'icon图标',
  `is_login` tinyint(1) DEFAULT '0' COMMENT '是否登录查看  0登录查看 1不登录查看',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态 0 新建 1启用 2 禁用',
  `sort_id` int(10) DEFAULT '0' COMMENT '排序',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人用户id',
  `create_user_name` varchar(30) DEFAULT '' COMMENT '创建人用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '最后修改人用户id',
  `update_user_name` varchar(30) DEFAULT '' COMMENT '最后修改人用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='消息推送-标签表';

-- ----------------------------
-- Table structure for ht_message_push_template
-- ----------------------------
DROP TABLE IF EXISTS `ht_message_push_template`;
CREATE TABLE `ht_message_push_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_id` int(11) NOT NULL COMMENT '消息标签,外键,消息标签表的id',
  `tag_code` varchar(20) NOT NULL COMMENT '消息标签编码,消息标签表的编码',
  `template_code` varchar(40) NOT NULL DEFAULT '' COMMENT '消息编码',
  `template_title` varchar(40) NOT NULL DEFAULT '' COMMENT '消息标题',
  `template_image_url` varchar(200) DEFAULT '' COMMENT '图片url',
  `template_content` varchar(4000) NOT NULL DEFAULT '' COMMENT '消息内容',
  `template_terminal` varchar(20) DEFAULT '' COMMENT '推送终端',
  `template_action` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '后续动作,0打开APP,1打开H5页面,2指定原生页面',
  `template_action_url` varchar(200) DEFAULT '' COMMENT '后续动作url,后续动作为0时此字段无效,1时为填写的url,2时为原生页面的url',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态 0 新建 1启用 2 禁用',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_template_code` (`template_code`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='消息推送-消息模版表';

-- ----------------------------
-- Table structure for ht_param_name
-- ----------------------------
DROP TABLE IF EXISTS `ht_param_name`;
CREATE TABLE `ht_param_name` (
  `name_class` varchar(30) NOT NULL DEFAULT '',
  `name_cd` varchar(6) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `other1` varchar(255) DEFAULT NULL,
  `other2` varchar(255) DEFAULT NULL,
  `other3` varchar(255) DEFAULT NULL,
  `sort` smallint(5) DEFAULT '0',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除FLAG',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`name_class`,`name_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_partner_type
-- ----------------------------
DROP TABLE IF EXISTS `ht_partner_type`;
CREATE TABLE `ht_partner_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `typename` varchar(30) DEFAULT NULL COMMENT '类别名称',
  `state` tinyint(4) DEFAULT '1' COMMENT '是否显示（1显示，0不显示）',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='合作机构类别表';

-- ----------------------------
-- Table structure for ht_payment
-- ----------------------------
DROP TABLE IF EXISTS `ht_payment`;
CREATE TABLE `ht_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `code` varchar(10) DEFAULT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `fee` decimal(10,2) DEFAULT '0.00',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `content` text,
  `status` tinyint(4) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='充值银行列表';

-- ----------------------------
-- Table structure for ht_question
-- ----------------------------
DROP TABLE IF EXISTS `ht_question`;
CREATE TABLE `ht_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(40) NOT NULL DEFAULT '0' COMMENT '类型.0问卷问题',
  `question` varchar(250) NOT NULL COMMENT '问题',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态0是启用，1是禁用',
  `sort` smallint(3) DEFAULT NULL COMMENT '排序',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='问卷问题表';

-- ----------------------------
-- Table structure for ht_site_setting
-- ----------------------------
DROP TABLE IF EXISTS `ht_site_setting`;
CREATE TABLE `ht_site_setting` (
  `id` int(11) NOT NULL COMMENT '主键',
  `company` varchar(100) DEFAULT NULL,
  `site_name` varchar(50) DEFAULT NULL,
  `site_domain` varchar(50) DEFAULT NULL,
  `site_logo` varchar(50) DEFAULT NULL,
  `site_icp` varchar(50) DEFAULT NULL,
  `site_terms` varchar(2000) DEFAULT NULL,
  `site_stats` varchar(200) DEFAULT NULL,
  `site_footer` varchar(500) DEFAULT NULL,
  `site_status` tinyint(1) unsigned DEFAULT '1',
  `site_close_reason` varchar(200) DEFAULT NULL,
  `site_keyword` varchar(200) DEFAULT NULL,
  `site_description` varchar(200) DEFAULT NULL,
  `site_theme_path` varchar(100) DEFAULT NULL,
  `site_theme` varchar(20) DEFAULT NULL,
  `smtp_server` varchar(100) DEFAULT NULL,
  `smtp_reply` varchar(100) DEFAULT NULL,
  `smtp_username` varchar(100) DEFAULT NULL,
  `smtp_password` varchar(30) DEFAULT NULL,
  `smtp_verify` tinyint(1) DEFAULT NULL,
  `smtp_port` varchar(6) DEFAULT NULL,
  `smtp_ssl` tinyint(1) unsigned DEFAULT NULL,
  `smtp_from_name` varchar(30) DEFAULT NULL,
  `attachment_url` varchar(50) DEFAULT NULL,
  `attachment_dir` varchar(20) DEFAULT NULL,
  `attachment_type` varchar(50) DEFAULT NULL,
  `attachment_maxupload` varchar(20) DEFAULT NULL,
  `cdn_domain` varchar(100) DEFAULT NULL COMMENT 'CDN域名',
  `service_phone_number` varchar(20) DEFAULT NULL COMMENT '客服电话',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站设置 : 服务器域名邮件';

-- ----------------------------
-- Table structure for ht_sms_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_sms_config`;
CREATE TABLE `ht_sms_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `max_ip_count` int(11) DEFAULT NULL COMMENT '每天最大发送量（同一IP）',
  `max_machine_count` int(11) DEFAULT NULL COMMENT '每天最大发送量（同一设备）',
  `max_browser_count` int(11) DEFAULT NULL COMMENT '每天最大发送量（同一浏览器）',
  `max_phone_count` int(11) DEFAULT NULL COMMENT '每天最大发送量（同一手机号）',
  `max_interval_time` int(11) DEFAULT NULL COMMENT '发送短信的间隔时间(单位：秒)',
  `max_valid_time` int(11) DEFAULT NULL COMMENT '验证码有效时间(单位：分钟）',
  `notice_to_phone` varchar(20) DEFAULT NULL COMMENT '接受超限通知的手机号',
  `notice_to_email` varchar(30) DEFAULT NULL COMMENT '接受超限通知的邮箱',
  `notice_to_time` int(11) NOT NULL COMMENT '发送超限通知的间隔时间',
  `repay_mobiles` varchar(11) DEFAULT NULL COMMENT '还款提醒手机号',
  `full_mobiles` varchar(11) DEFAULT NULL COMMENT '满标提醒手机号',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='通知发送配置';

-- ----------------------------
-- Table structure for ht_sms_errcode
-- ----------------------------
DROP TABLE IF EXISTS `ht_sms_errcode`;
CREATE TABLE `ht_sms_errcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rescode` varchar(20) DEFAULT NULL,
  `errfrom` varchar(255) DEFAULT NULL,
  `errmsg` varchar(255) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='预警码字典表';

-- ----------------------------
-- Table structure for ht_sms_mail_template
-- ----------------------------
DROP TABLE IF EXISTS `ht_sms_mail_template`;
CREATE TABLE `ht_sms_mail_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail_name` varchar(20) NOT NULL COMMENT '模板名称',
  `mail_value` varchar(30) NOT NULL DEFAULT '',
  `mail_status` int(1) NOT NULL DEFAULT '1' COMMENT '是否开启 0关闭 1开启',
  `mail_content` varchar(20000) NOT NULL COMMENT '模板内容',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='邮件模板表';

-- ----------------------------
-- Table structure for ht_sms_notice_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_sms_notice_config`;
CREATE TABLE `ht_sms_notice_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '标识名称',
  `title` varchar(50) DEFAULT NULL COMMENT '配置说明',
  `config_value` varchar(2000) DEFAULT NULL COMMENT '配置值',
  `pvalue` varchar(2000) DEFAULT NULL COMMENT '纯号码',
  `content` varchar(10000) DEFAULT NULL COMMENT '配置内容',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(2000) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1' COMMENT '状态，0是关闭，1是开启',
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=927 DEFAULT CHARSET=utf8 COMMENT='短信通知配置表';

-- ----------------------------
-- Table structure for ht_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `ht_sms_template`;
CREATE TABLE `ht_sms_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tpl_code` varchar(30) NOT NULL DEFAULT '',
  `tpl_name` varchar(20) NOT NULL COMMENT '模板名称',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否开启 0关闭 1开启',
  `tpl_content` varchar(2000) NOT NULL COMMENT '模板内容',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_tpl_code` (`tpl_code`)
) ENGINE=InnoDB AUTO_INCREMENT=947 DEFAULT CHARSET=utf8 COMMENT='短信模板表';

-- ----------------------------
-- Table structure for ht_submissions
-- ----------------------------
DROP TABLE IF EXISTS `ht_submissions`;
CREATE TABLE `ht_submissions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `problem` varchar(11) DEFAULT '' COMMENT '反馈类型',
  `content` varchar(10000) DEFAULT NULL,
  `img` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '会员id',
  `sys_type` int(1) DEFAULT '0' COMMENT '系统类别 0：PC，1：微官网，2：Android，3：IOS，4：其他',
  `sys_version` varchar(20) DEFAULT NULL COMMENT '操作系统版本号',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态 0未审 1已审核',
  `reply` varchar(500) DEFAULT NULL COMMENT '客服回复',
  `platform_version` varchar(20) DEFAULT NULL COMMENT '平台版本号',
  `phone_type` varchar(64) DEFAULT NULL COMMENT '手机型号',
  `replytime` int(11) DEFAULT '0' COMMENT '回复时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=263 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_team
-- ----------------------------
DROP TABLE IF EXISTS `ht_team`;
CREATE TABLE `ht_team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `position` varchar(100) NOT NULL,
  `info` varchar(2000) NOT NULL,
  `imgurl` varchar(255) NOT NULL,
  `imgappurl` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `order_id` int(11) NOT NULL DEFAULT '0',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_user_corner
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_corner`;
CREATE TABLE `ht_user_corner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `corner` int(11) NOT NULL DEFAULT '0' COMMENT '角标数',
  `sign` varchar(200) NOT NULL DEFAULT '' COMMENT '设备唯一标识',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户app角标记录表';

-- ----------------------------
-- Table structure for ht_version
-- ----------------------------
DROP TABLE IF EXISTS `ht_version`;
CREATE TABLE `ht_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) unsigned NOT NULL COMMENT '0 PC ,1 Android , 2 IOS , 3 wechat',
  `version` varchar(12) NOT NULL COMMENT '版本号',
  `is_update` tinyint(1) unsigned DEFAULT '0' COMMENT '是否需要更新（0必须强制更新、1可更新可不更新、2不需要更新）主要针对app',
  `url` varchar(255) DEFAULT NULL COMMENT 'url',
  `content` varchar(500) DEFAULT NULL COMMENT '版本描述',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_withdraw_rule_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_withdraw_rule_config`;
CREATE TABLE `ht_withdraw_rule_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_type` tinyint(1) NOT NULL COMMENT '用户类型 0个人 1企业',
  `min_money` decimal(12,2) NOT NULL COMMENT '最小金额',
  `max_money` decimal(12,2) NOT NULL COMMENT '最大金额',
  `start_time` varchar(8) NOT NULL COMMENT '工作日开始时间',
  `end_time` varchar(8) NOT NULL COMMENT '工作日结束时间',
  `is_holiday` tinyint(1) NOT NULL COMMENT '是否节假日 1节假日 0工作日',
  `could_withdraw` tinyint(1) NOT NULL COMMENT '可否提现 1可以 0不可以 ',
  `route_code` char(1) NOT NULL COMMENT '通道 1默认空，2大额通道，3小额，0不传',
  `pay_alliance_code` tinyint(1) NOT NULL COMMENT '联行号 1有 0无',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0可用 1删除 ',
  `create_by` varchar(30) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(30) NOT NULL COMMENT '修改人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='提现规则配置表';

-- ----------------------------
-- Table structure for ht_withdraw_time_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_withdraw_time_config`;
CREATE TABLE `ht_withdraw_time_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `year` char(4) NOT NULL COMMENT '年份',
  `holiday_name` varchar(30) NOT NULL COMMENT '假日名称',
  `start_date` date NOT NULL DEFAULT '0000-00-00' COMMENT '假日开始时间',
  `end_date` date NOT NULL DEFAULT '0000-00-00' COMMENT '假日结束时间',
  `holiday_type` tinyint(1) NOT NULL COMMENT '假日类型 1补休 2假期',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0可用 1删除  ',
  `create_by` varchar(30) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(30) NOT NULL COMMENT '修改人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='提现时间配置表';

-- ----------------------------
-- Table structure for ht_workflow
-- ----------------------------
DROP TABLE IF EXISTS `ht_workflow`;
CREATE TABLE `ht_workflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `workname_id` int(11) DEFAULT NULL COMMENT '业务名称表id',
  `flow_status` tinyint(4) NOT NULL COMMENT '流程状态 1正常，2异常',
  `check_status` tinyint(4) NOT NULL COMMENT '是否需要审核 1是，2否',
  `mail_notifier` varchar(200) DEFAULT NULL COMMENT '邮件预警通知人',
  `create_user` varchar(10) DEFAULT NULL COMMENT '创建操作人',
  `update_user` varchar(10) DEFAULT NULL COMMENT '更新操作人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8 COMMENT='业务流程表';

-- ----------------------------
-- Table structure for ht_workflow_node
-- ----------------------------
DROP TABLE IF EXISTS `ht_workflow_node`;
CREATE TABLE `ht_workflow_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role` int(11) DEFAULT NULL COMMENT '流程节点角色，1=角色，2=个人',
  `workflow_id` int(11) NOT NULL COMMENT '业务流程表ID',
  `flow_order` int(11) NOT NULL COMMENT '流程顺序',
  `admin_id` int(11) NOT NULL COMMENT 'admin后台用户id或者角色ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1285 DEFAULT CHARSET=utf8 COMMENT='业务流程节点表';

-- ----------------------------
-- Table structure for ht_workname
-- ----------------------------
DROP TABLE IF EXISTS `ht_workname`;
CREATE TABLE `ht_workname` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `work_name` varchar(30) NOT NULL COMMENT '业务名称',
  `charge_name` varchar(20) NOT NULL COMMENT '业务主管姓名',
  `charge_mail` varchar(30) NOT NULL COMMENT '业务主管邮件',
  `status` tinyint(2) NOT NULL COMMENT '1可用，2禁用',
  `create_user` varchar(10) DEFAULT NULL COMMENT '创建操作人',
  `update_user` varchar(10) DEFAULT NULL COMMENT '更新操作人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8 COMMENT='业务名称表';
