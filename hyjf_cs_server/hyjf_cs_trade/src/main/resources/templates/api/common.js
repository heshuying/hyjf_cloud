// 全局分页大小数据
var pageSize = 10;
/**
 * 清空form表单
 */
function clearForm(formid) {
	$(':input', '#' + formid).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
}

/**
 * ajax请求封装
 * 
 * @param successCallback
 *            请求成功的回调函数
 * @param errorCallback
 *            请求失败的回调函数
 * @param url
 *            请求的url地址
 * @param data
 *            请求的数据
 * @param paging
 *            是否进行分页 true/false
 * @param flipClass
 *            分页按钮虚拟class的名称 可以为null
 * @param paginationId
 *            分页挂载元素的id 不可以为null
 */
function doRequest(successCallback, errorCallback, url, data, paging, flipClass, paginationId) {
	try {
		var paramsInner = {
			url : url,
			type : 'post',
			dataType : 'json',
			timeout : 60000,
			success : function(data) {
				if (data.status == true) {
					if (paging == true) {
						handlePages(data.paginator, url, flipClass, paginationId);
					}
					successCallback(data);
				} else {
					if (data.errorCode == "707") {
						window.location.href = webPath + "/user/login/init.do";
					} else if (data.errorCode == "708") {
						window.location.href = webPath + "/bank/web/user/bankopen/init.do";
					} else {
						errorCallback(data);
					}
				}
			},
			error : function(xhr, textStatus, errorThrown) {
				var err = {
					'result_code' : 1,
					'error_code' : 1,
					'error' : errorThrown
				};
				errorCallback(err);
			},
			data : ''
		};
		paramsInner.data = data;
		$.ajax(paramsInner);
	} catch (e) {
		var err = {
			'result_code' : 1,
			'error_code' : 1,
			'error' : e.message
		};
		errorCallback(err);
	}
}
/*
 * title 显示的标题，如果没有可写为空，使用默认值 ;content 弹出框显示的内容，如果没有可写为空，使用默认值; cssType 弹出框类型值可为
 * "success" "error" "warning" "tip" "confirm";id绑定事件用 目前暂时就两种情况：tip confirm
 * <div id="dialogContainerId"></div>这是dialog的容器
 * @param Object 确定和取消按钮的文字
 */
function showTip(title, content, cssType, id,txt) {
	var dialogHTML = null;
	var _title = "提示信息";
	var dialogHTML = null;
	var confirmTxt = "确定",
		cancelTxt = "取消";
	var txt = txt || {};
	if(txt.confirm != undefined){
		confirmTxt = txt.confirm;
	}
	if(txt.cancel != undefined){
		cancelTxt = txt.cancel;
	}
	if (title) {
		_title = title;
	}
	if (!$("#dialogContainerId").text()) {
		$("body").append("<div id='dialogContainerId'></div>");
	}

	if ("confirm" == cssType) {
		dialogHTML = '<div class="pop-overlayer" style="display:block;"></div><div class="pop-box pop-tips" id="' + id + '" style="display:block;">' + '<a class="pop-close" href="javascript:void(0);" onclick="closeTip()"></a>' + '<div class="pop-main">' + '<div class="pop-txt">' + content
				+ '</div>' + '<div class="btns">' + '<a href="javascript:;" class="confirm" onclick="closeTip();dealAction(\'' + id + '\');">'+confirmTxt+'</a>' + '<a href="javascript:;" class="cancel" onclick="closeTip();cancelAction(\'' + id + '\');">'+cancelTxt+'</a>' + '</div>' + '<div class="clearfix"></div>'
				+ '</div>' + '</div>';

	} else if ("tip" == cssType) {
		dialogHTML = '<div class="pop-overlayer" style="display:block;"></div><div class="pop-box pop-tips-hastitle" id="' + id + '" style="display:block;">' + '<a class="pop-close" href="javascript:void(0);" onclick="closeTip()"></a>' + '<h4 class="pop-title">' + _title + '</h4>'
				+ '<div class="pop-main">' + '<div class="pop-txt">' + content + '</div>' + '<div class="btns">' + '<a href="javascript:;" class="confirm" onclick="closeTip();dealAction(\'' + id + '\');">'+confirmTxt+'</a>' + '</div>' + '<div class="clearfix"></div>' + '</div>' + '</div>';
	}
	$("#dialogContainerId").empty();
	$("#dialogContainerId").append(dialogHTML);
}

/*
 * 关闭弹窗
 */
function closeTip() {
	$(".pop-overlayer").fadeOut();
	$(".pop-box").fadeOut();
}
function dealAction() {

}
function cancelAction() {

}

/**
 * 处理分页数据
 * 
 * @param paginator
 * @param successCallback
 * @param errorCallback
 * @param url
 */
function handlePages(paginator, url, flipClass, paginationId) {

	if (flipClass == null) {
		flipClass = "flip";
	}
	var pageStr = "";
	if (paginator.slider.length > 0) {
		if (paginator.hasPrePage == true) {
			pageStr = pageStr + "<a class='"+flipClass+" prev' href='javascript:void(0)' data-page='" + (paginator.page - 1) + "' itemid='pgprev' >上一页</a>";
		} else {
			pageStr = pageStr + "<div class='prev' itemid='pgprev'>上一页</div>";
		}

		for (var i = 0; i < paginator.slider.length; i++) {
			if (paginator.slider[i] == paginator.page) {
				pageStr = pageStr + "<a class='"+flipClass+" active' data-page='" + paginator.page + "' itemid='pg"+paginator.slider[i]+"'>" + paginator.page + "</a>"
			} else {
				pageStr = pageStr + "<a class='"+flipClass+"' href='javascript:void(0)' data-page='" + paginator.slider[i] + "' itemid='pg"+paginator.slider[i]+"'>" + paginator.slider[i] + "</a>";
			}
		}
		if (paginator.hasNextPage == true) {
			pageStr = pageStr + "<a class='"+flipClass+" next' href='javascript:void(0)' data-page='" + (paginator.page + 1) + "' itemid='pgnext'>下一页</a>";
		} else {
			pageStr = pageStr + "<div class='next' itemid='pgnext'>下一页</div>";
		}
	}
	// 挂载分页
	$("#" + paginationId).html(pageStr);

}

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

function checkToken() {
	var token = $("#tokenGrant").val();
	if ("" == token || undefined == token || null == token) {
		return false;
	} else {
		$("#tokenCheck").val(token);
		$("#tokenGrant").val("");
		return true;
	}
}

function scrollTo(name, add, speed) {
	if (!speed) {
		speed = 300;
	}
	if (!name) {
		$("html,body").animate({
			scrollTop : 0
		}, speed);
	} else if ($(name).length > 0) {
		$("html,body").animate({
			scrollTop : $(name).offset().top + (add || 0)
		}, speed)
	}
};

var hdNav = $("#hdNav"); // 定义二级菜单
hdNav.children("li").mouseover(function() { // 鼠标移入显示菜单
	var _self = $(this);
	var sub = _self.children(".subnav");

	if (sub.length && !sub.is(":animated")) {
		sub.fadeIn(300);
	}
}).mouseleave(function() { // 鼠标移出隐藏菜单
	var _self = $(this);
	var sub = _self.children(".subnav");
	if (sub) {
		sub.fadeOut(300);
	}
})

// 推送消息tab
$(".push-menu-tab").click(function(e) {
	var _self = $(e.target);
	if (_self.is("li")) {
		var idx = _self.attr("panel");
		var panel = _self.parent().next(".push-menu-tab-panel");
		_self.siblings("li.active").removeClass("active");
		_self.addClass("active");
		panel.children("li.active").removeClass("active");
		panel.children("li").eq(idx).addClass("active");
	}
})
// 推送消息开关
$(".hd-bell").click(function() {
	getActivityListPage();
	//getNoticeListPage();
	var _self = $(this);
	if (_self.hasClass("opened")) {
		closePush();
	} else {
		openPush();
	}
})

function openPush() {
	/* 打开推送消息 */
	$(".hd-bell").addClass("opened");
	$(".push-menu").fadeIn(300);
}

function closePush() {
	/* 打开推送消息 */
	$(".hd-bell").removeClass("opened");
	$(".push-menu").fadeOut(200);
}
// 点击其他地方关闭推送消息
$(document).click(function(e) {
	var d = $(e.target);
	if (!d.hasClass('push-menu') && !d.parents('.push-menu').length && !d.hasClass('hd-bell') && !d.parents('.hd-bell').length) {
		closePush();
	}
})
//小数不足两位补零
function fixNum(f_x){
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if(pos_decimal < 0){
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + 2){
		s_x += '0';
	}
	return s_x;
}
function setCookie(c_name, value, expiredays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + expiredays)
	document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}
/** 根据cookie名称获取值 */
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		var c_start = document.cookie.indexOf(c_name + "=");
		var c_end;
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
			if (c_end == -1) {
				c_end = document.cookie.length;
			}
			return unescape(document.cookie.substring(c_start, c_end));
		}
	}
	return "";
}
function checkCookie(cname) {
	var cname = getCookie(cname);
	if (cname != null && cname != "") {
		return true;
	} else {
		return false;
	}
}
