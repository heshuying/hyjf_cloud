/*
 *  utils.js
 *  工具类方法
 scrollTo : 滚屏,
 fixNum : 小数不足两位补零,
 setCookie : 设置cookie,
 getCookie : 根据cookie名称获取值,
 checkCookie : 根据cookie名称检测是否存在该cookie,
 clearForm : 清空表单,
 */

try {
    $.ready();
} catch (e) {
    console.error("未引入jQuery类库文件。" + e.name + ": " + e.message);
}
var utils = utils || {};

$.extend(utils, {
    /*
     * 初始化页面
     */
    init: function() {
        this.progress(); //初始化页面进度条
        this.subNav(); //初始化二级菜单
        this.saoShare();//二维码弹窗
    },
    /*
     * 初始化二级菜单
     */
    subNav: function(){
        $(".nav-main,.nav-sub").find("li.has-nav").mouseenter(function(){
            var _item = $(this),
                subnav = _item.children("ul"),
                arrow = _item.children("a").children(".iconfont");
            subnav.stop().slideDown(250);
        })
            .mouseleave(function() {
                var _item = $(this),
                    subnav = _item.children("ul"),
                    arrow = _item.children("a").children(".iconfont");
                subnav.stop().slideUp(250);
            })
    },
    /*
     * @fun 滚屏
     * @param name [string] 带有“.” 或“#”的id/class选择器名
     * @param add {num} [add = 0] 距离$(name)的顶部偏移量
     * @param speed {num} [speed = 300] 滚动速度
     */
    scrollTo: function(name, add, speed) {
        speed = speed || 300,
            add = add || 0;
        if (!name) {
            $("html,body").animate({
                scrollTop: 0
            }, speed);
        } else if ($(name).length > 0) {
            $("html,body").animate({
                scrollTop: $(name).offset().top + (add || 0)
            }, speed);
        }
    },
    /*
     * @fun 小数不足两位补零
     * @ param num {number} 待格式化的数字
     * @ param bool {boolean} 是否保留2位小数 true 保留，false不保留
     */
    toDecimal2: function(num, bool) {
        var f = parseFloat(num);
        if (isNaN(f)) {
            return false;
        }
        if (bool === true) {
            if(Math.ceil(num)>num){
                var fs=f.toString()
                var reg=/^(\d+\.)\d{1,2}/
                f=reg.exec(fs)[0]
            }

        }
        var s = f.toString();

        var rs = s.indexOf('.');
        if (rs < 0) {
            rs = s.length;
            s += '.';
        }
        while (s.length <= rs + 2) {
            s += '0';
        }
        return s;
    },
    /*
     * @fun 1位数补零
     * @ param num {number} 待格式化的数字
     */
    fmtSingleDigit: function(num) {
        var res = num;
        if (num > 9) {
            res = num;
        } else {
            res = "0" + num;
        }
        return res;
    },
    /*
     *  @fun 设置cookie
     *  @param c_name {string} cookie名字
     *  @param value {string} cookie值
     *  @param expiredays {num} cookie期限,天数
     */
    setCookie: function(c_name, value, expiredays) {
        var exdate = new Date();
        exdate.setDate(exdate.getDate() + expiredays);
        document.cookie = c_name + "=" + escape(value) +
            ((expiredays === null) ? "" : ";expires=" + exdate.toGMTString());
    },
    /*
     *  @fun 根据cookie名称获取值
     *  @param c_name {string} cookie名字
     */
    getCookie: function(c_name) {
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
    },
    /*
     *  @fun 根据cookie名称检测是否存在该cookie
     *  @param c_name {string} cookie名字
     */
    checkCookie: function(c_name) {
        c_name = this.getCookie(c_name);
        if (typeof c_name !== 'undefined' && c_name !== "") {
            return true;
        } else {
            return false;
        }
    },
    /**
     * @fun 清空form表单
     * @param formid {string} id选择器名称不带“#”
     */
    clearForm: function(formid) {
        $(':input', '#' + formid).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
    },

    /*
     * 加载页面进度条
     */
    progress: function() {
        if (typeof NProgress === "undefined") {
            return false;
        }
        NProgress.start();
        $(window).load(function() {
            NProgress.done();
        });
    },
    /*
     * 全局弹窗
     * @param id [string] 弹窗id
     * @param title [string] 弹窗标题文字 若没有隐藏标题
     * @param content [html] 弹窗内容
     * @param btntxt [string] 确认按钮文字
     */
    alert: function(options) {
        var _this = this;
        var id = options.id || "dialogAlert", //弹窗id
            type = options.type || "alert", //弹窗类型 alert || confirm
            content = options.content || "", //弹窗内容
            confirmtxt = options.btntxt || "确 定", //确认按钮文字
            canceltxt = options.canceltxt || "取 消", //取消按钮文字
            alertImg='tip';
        fnconfirm = options.fnconfirm || function() {
                _this.alertClose(id);
            }; //确认按钮事件
        if((options.alertImg !='undefined') && (options.alertImg !="") &&(typeof options.alertImg!='undefined')){
            alertImg=options.alertImg;
        }
        if (!$("#" + id).length) {
            var str = '<div class="alert" id="' + id + '">';
            str += '<div onclick="utils.alertClose(\'' + id + '\')" class="close"> <span class="iconfont icon-cha"></span></div>';
            str += '<div class="icon '+alertImg+'"></div>';
            str += '<div class="content">' + content + '</div>';
            str += '<div class="btn-group">';
            if (type == "alert" || type == "tip") {
                str += '<div class="btn btn-primary single" id="' + id + 'Confirm">' + confirmtxt + '</div>';
            } else {
                str += '<div class="btn" id="' + id + 'Cancel">' + canceltxt + '</div>';
                str += '<div class="btn btn-primary" id="' + id + 'Confirm">' + confirmtxt + '</div>';
            }
            str += '</div>';
            str += '</div>';
            $("body").append(str);
        } else if ($("#" + id).length && content != "") {
            var that = $("#" + id);
            $(that).find('.content').html(content);
        }
        if (!$(".overlayer").length) {
            $("body").append('<div class="overlayer"></div>');
        }
        var height = $("#" + id).height();
        var margintop = -height / 2 - 30;
        $(".overlayer").fadeIn();
        $("body").addClass("popOpened");
        $("#" + id).css({ "margin-top": margintop }).fadeIn();
        //确认按钮点击事件
        $("#" + id + 'Confirm').unbind('click').on("click", fnconfirm);
        //取消按钮点击事件
        $("#" + id + 'Cancel').unbind('click').on("click", function() {
            _this.alertClose(id);
        });
    },
    /*
     * 关闭弹窗
     * @param id [string] 可选 要关闭的弹窗id  default 所有弹窗
     */
    alertClose: function(id) {
        id = id ? $("#" + id) : $(".dialog");
        id.fadeOut();
        $(".overlayer").fadeOut();
        $("body").removeClass("popOpened");
    },
    /*
     * 倒计时
     * @param  ele 存放倒计时的容器
     * @param s 倒计时剩余时间
     */
    timer: function(ele, s,callback) {
        var callback=callback||function (){}
        var self = this;
        self._timmer(ele, s);
        s--;
        var interval = window.setInterval(function() {
            if (s < 0) {
                clearInterval(interval);
                callback();
            }
            self._timmer(ele, s);
            s--;
        }, 1000);
    },
    _timmer: function(ele, s) {
        var day = 0,
            hour = 0,
            minute = 0,
            second = 0; //时间默认值
        if (s > 0) {
            day = Math.floor(s / (60 * 60 * 24));
            hour = Math.floor(s / (60 * 60)) - (day * 24);
            minute = Math.floor(s / 60) - (day * 24 * 60) - (hour * 60);
            second = Math.floor(s) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
        }
        if (minute <= 9) minute = '0' + minute;
        if (second <= 9) second = '0' + second;
        ele.html(hour + ' : ' + minute + ' : ' + second);
    },
    /*
     * 刷新页面
     */
    refresh: function() {
        window.location.reload(true);
    },
    /*
     *检测是否是数字
     */
    checkNumber: function(val) {
        var reg = /^[0-9]*$/;
        if (reg.test(val)) {
            return true;
        }
        return false;
    },
    /*
     * 判断是否是IE8以下
     *
     */
    isIe8: function() {
        var version = parseInt($.browser.version);
        return $.browser.msie && version < 9;
    },
    alt:function(el){
        $(el).hover(function(){
            var word=$(this).data('alt');
            var alt=$(this).find('.alt-tit');
            if(alt.length){
                $(this).find('.alt-tit').stop().fadeIn(150);
            }else{
                $(this).append('<span class="alt-tit">'+word+'</span>')
                $(this).find('.alt-tit').stop().fadeIn(150);
            }
        },function(){
            $(this).find('.alt-tit').stop().fadeOut(150);
        })
    },
    //app下载hover弹层
    saoShare:function(){
        $("#saomaItem").hover(function() {
            $("#saoma").stop().fadeIn(300);
        }, function() {
            $("#saoma").fadeOut(300);
        });
    },
    loadingHTML :'<div class="loading"><div class="icon"><div class="text">Loading...</div></div></div>'
});

$(function() {
    utils.init();
});
