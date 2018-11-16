/*
 * jQuery validation 验证类型扩展
 */
// 邮政编码验证    
jQuery.validator.addMethod("isZipCode", function(value, element) {
	var zip = /^[0-9]{6}$/;
	return this.optional(element) || (zip.test(value));
}, "请正确填写您的邮政编码");
// 金钱验证    
jQuery.validator.addMethod("isMoney", function(value, element) {
	var zip = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	return this.optional(element) || (zip.test(value));
}, "请正确填写您的邮政编码");
// 身份证号码验证
jQuery.validator.addMethod("isIdCardNo", function(value, element) {
	var idCard = /^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/;
	return this.optional(element) || (idCard.test(value));
}, "请输入正确的身份证号码");

// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
	var length = value.length;
	return this.optional(element) || (length == 11 && /^[1][3-9][0-9]{9}$/.test(value));
}, "请正确填写您的手机号码");

// 电话号码验证
jQuery.validator.addMethod("isPhone", function(value, element) {
	var length = value.length;
	return this.optional(element) || (length == 11 && /^[1][3-9][0-9]{9}$/.test(value));
}, "请正确填写您的电话号码")

// 密码
jQuery.validator.addMethod("ispwd", function(value, element) {
	var ispwd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/;
	var isnum = /^[0-9]{1}$/;
	return this.optional(element) || (ispwd.test(value) );
}, "6~16 位，必须由数字和字母组成，首位必须是字母，区分大小写");
//密码
jQuery.validator.addMethod("ispassword", function(value, element) {
	var ispwd = /^(?![0-9]+$)(?![a-zA-Z]+$)(?![\`\~\!\@\#\$\%\^\&\*\(\)\_\+\-\=\{\}\|\[\]\\\;\'\:\"\,\.\/\<\>\?]+$)[0-9A-Za-z\`\~\!\@\#\$\%\^\&\*\(\)\_\+\-\=\{\}\|\[\]\\\;\'\:\"\,\.\/\<\>\?]{8,16}$/;
	var isnum = /^[0-9]{1}$/;
	return this.optional(element) || (ispwd.test(value) );
}, "8~16 位，必须由数字、字母和字符组成，且不少于两种，区分大小写");
// 手机验证码
jQuery.validator.addMethod("telcode", function(value, element) {
	var telcode = /\b\d{6}\b/;
	return this.optional(element) || (telcode.test(value));
}, "校验码错误，请重新输入");

// 用户名字符验证
jQuery.validator.addMethod("userName", function(value, element) {
	return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
}, "用户名只能包括中文字、英文字母、数字和下划线");

// 联系电话(手机/电话皆可)验证
jQuery.validator.addMethod("isTel", function(value, element) {
	var length = value.length;
	var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
	var tel = /^\d{3,4}-?\d{7,9}$/;
	return this.optional(element) || (tel.test(value) || mobile.test(value));
}, "请正确填写您的联系电话");
//校验金额
jQuery.validator.addMethod("checkMoney", function(value, element) {
	return this.optional(element) || /^(([1-9]\d{0,12})|0)(\.\d{1,2})?$/.test(value);
}, "金额必须大于0且为整数或小数，小数点后不超过2位");

// IP地址验证
jQuery.validator.addMethod("ip", function(value, element) {
	return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);
}, "请填写正确的IP地址");
//是否是银行卡号
jQuery.validator.addMethod("isBankCard", function(value, element) {
	return this.optional(element) || bankCardVer(value.toString());
}, "请填写正确的银行卡号码");
//身份证严格验证
jQuery.validator.addMethod("isIdCard", function(value, element) {
	return this.optional(element) || isIdCardNo(value.toString());
}, "请填写正确的身份证号");
//提现可提现金额验证
jQuery.validator.addMethod("withdrawmoney", function(value, element) {
	return this.optional(element) || withdrawMoneyRule(value);
}, "当天充值资金当天无法提现，请调整取现金额。");

//银行卡Luhn规则
function bankCardVer(cardnum){
	if(cardnum.length<16 || cardnum.length>19){
		return false;
	}
    var sum = 0;
    var digit = 0;
    var addend = 0;
    var timesTwo = false;
    for (var i = cardnum.length - 1; i >= 0; i--)
    {
        digit = cardnum[i] - '0';
        if (timesTwo)
        {
            addend = digit * 2;
            if (addend > 9) {
                addend -= 9;
            }
        }
        else {
            addend = digit;
        }
        sum += addend;
        timesTwo = !timesTwo;
    }
    var modulus = sum % 10;
    return modulus == 0;
}
//提现可提现金额验证
function withdrawMoneyRule(num){
    if(parseFloat($("#bankBalance").val().replace(/,/g,''))<parseFloat(num) && (parseFloat($("#total").val().replace(/,/g,''))>parseFloat(num) || parseFloat($("#total").val().replace(/,/g,''))==parseFloat(num))){
        return false;
    }
    return true;

}
// 身份证号码的验证规则
function isIdCardNo(num) {
	// if (isNaN(num)) {alert("输入的不是数字！"); return false;}
	var len = num.length, re;
/*	if (len == 15)
		re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/);
	else if (len == 18)
		re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/);*/
	if (len == 15)
	    re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})([Xx0-9])$/);
	  else if (len == 18)
	    re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})([Xx0-9])$/);
	else {
//		alert("输入的数字位数不对");
		return false;
	}
	var a = num.match(re);
	if (a != null) {
		if (len == 15) {
			var D = new Date("19" + a[3] + "/" + a[4] + "/" + a[5]);
			var B = D.getYear() == a[3] && (D.getMonth() + 1) == a[4] && D.getDate() == a[5];
		} else {
			var D = new Date(a[3] + "/" + a[4] + "/" + a[5]);
			var B = D.getFullYear() == a[3] && (D.getMonth() + 1) == a[4] && D.getDate() == a[5];
		}
		if (!B) {
//			alert("输入的身份证号 " + a[0] + " 里出生日期不对");
			return false;
		}
	}
	if (!re.test(num)) {
//		alert("身份证最后一位只能是数字和字母");
		return false;
	}

	return true;
}
