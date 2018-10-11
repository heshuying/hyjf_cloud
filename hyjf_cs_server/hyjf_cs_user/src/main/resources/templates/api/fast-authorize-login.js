$(function() {
	// 真实传值隐藏域
	var mobileinput = $("#loginBean_loginUserName");
	// 展示数据文本框
    var mobilelabel = $("#loginBean_loginUserName_Text");

    // 有mobile值格式化展示
	if( mobileinput.val() != '' ){
		var val = mobileinput.val();
        mobilelabel.val(val.substr(0,3)+'****'+val.substr(7));
        mobilelabel.prop('readonly',true);
	}
	// 无mobile值赋值为输入值
	else{
        mobilelabel.change(function(){
            mobileinput.val($(this).val());
		})
	}
	$('.check label').click(function(){
		if($('#loginBean_readAgreement').is(':checked')){
			$('.btn-sq').removeClass('disabled');
			
		}else{
			$('.btn-sq').addClass('disabled');
		}
	})

	$('.btn-sq').click(function(){
		//点击授权
		
		if(!$(this).hasClass('disabled')){
			$("#loginForm").submit();
		}
	})

	$("#loginForm").validate(
			{
				errorPlacement:function(error,element) {
					error.appendTo(".fast-login");
				},
				ignore: '.ignore',
				// errorLabelContainer:".error-box",
				onkeyup : false,
				groups : {
					form : "loginBean_loginUserName loginBean_loginPassword"
				},
				submitHandler : function(form) {
					var successCallback = function(data) {
						if (data.status) {
							data.status = data.statusCode;
							data.statusCode = "";
						}
						// modify by zhangjp 支持登录完成后跳转回原页面 20161014 start
						if (data.retUrl) {
							var datajson = encodeURIComponent(JSON.stringify(data)); 
							//var uns = decodeURIComponent('%7B%22hyjfUserName%22%3A%22hyjf776569%22%2C%22statusCode%22%3A%22%22%2C%22status%22%3A0%2C%22retUrl%22%3A%22http%3A%2F%2Fnewweb2.hyjf.com%2Fhyjf-api-web%2Fjsp%2Ftest%2Fthreepart2.jsp%22%2C%22bindUniqueIdScy%22%3A%22OPCx9ctZprVnPFFEA1rlKrGkX7NJVDyxSVpuXZ0uLxqs3tvjsBtl40BBnB7NYCL6QO%2B%2F%2FLR%2BKn%2BePI%2FtsgADfMRVpM2WUazGM9fPuQ1dQTPR1T7qj%2FtCbkUunGDxZNG0HJ%2FfcEs7h23cKALpxpvb7IZw65jQfRn%2FmbZE9Be%2BPyE%3D%22%2C%22statusDesc%22%3A%22%E6%8E%88%E6%9D%83%E6%88%90%E5%8A%9F%22%7D');
							location.href = data.retUrl+"?datajson="+datajson;
						} else {
							location.href = webPath
									+ "/user/pandect/pandect.do";
						}
						// modify by zhangjp 支持登录完成后跳转回原页面 20161014 start

					};
					var errorCallback = function(data) {
						utils.alert({
							id : "tempidDialog",
							type : "tip",
							content : data.error
						});
					}
					var params = {};
					$($("#loginForm").serializeArray()).each(function () {
						params[this.name] = this.value;
					});
					
					//params.loginUserName = $("#loginBean\\.loginUserName").val();
					
					params['loginBean_loginPassword'] = $.md5($("#loginBean_loginPassword").val());
					
					doRequest(successCallback, errorCallback, webPath
							+ "/api/user/bind.do", params);
				},
				rules : {
                    loginBean_loginUserName_Text : {
						required : true
						
					// maxlength : 16
					},
					loginBean_loginUserName : {
						required : true,
						isMobile : true
						// maxlength : 16
					},
					loginBean_loginPassword : {
						required : true,
					// ispwd:true,
					// minlength : 6,
					// maxlength : 16
					}
				},
				messages : {
					loginBean_loginUserName_Text : {
						required : '请输入手机号/用户名'
					},
					loginBean_loginUserName : {
						required : '请输入手机号/用户名',
						isMobile : "请输入正确的手机号码"
					},
					loginBean_loginPassword : {
						required : '请输入密码',
					// ispwd:'请输入正确的密码',
					// minlength : "请输入正确的密码",
					// maxlength : "请输入正确的密码"
					}
				},

			});
})
