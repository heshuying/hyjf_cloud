package com.hyjf.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class DownloadPictureUtil {
	/**
	 * 根据图片URL下载图片
	 * 
	 * @param urlString
	 *            图片URL
	 * @param filename
	 *            下载后图片名称
	 * @param savePath
	 * @throws Exception
	 */
	public static void download(String urlString, String filename, String savePath) throws Exception {
		if(StringUtils.isBlank(urlString)){
			return;
		}
		if(urlString.length() < 4 || !urlString.substring(0, 4).equals("http")){
			return;
		}
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(5 * 1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + File.separator + filename + ".png");
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}

	public static String convert(String key) {
		switch (key) {
		case "idCardA":
            return "身份证正面";
		case "idCardB":
            return "身份证反面";
		case "carLicense":
            return "行驶证";
		case "driveLicense":
            return "驾照";
		case "tci":
            return "交强险";
		case "tlrc":
            return "车辆登记证末页";
		case "carKey":
            return "车钥匙";
		case "residenceCardA":
            return "居住证正面";
		case "residenceCardB":
            return "居住证反面";
		case "vci":
            return "商业险";
		case "households":
            return "户口簿";
		case "marriage":
            return "婚姻证明";
		case "propertyRight":
            return "产调";
		case "businessLicense":
            return "营业执照";
		case "creditReports":
            return "征信报告";
		case "bankStatements":
            return "银行流水";
		case "fieldSurveies":
            return "实调照";
		case "feeList":
            return "费用清单";
		case "contracts":
            return "合同";
		case "registerCards":
            return "车辆登记证";
		case "others":
            return "其他";
		case "insurances":
            return "保险";
		case "carInvoice":
            return "购车发票";
		case "vehiclePurchaseTax":
            return "购置税发票";
		case "vehicleMortgageRegister":
            return "车辆抵/质押登记";
		case "backCashPledges":
            return "退押金";
		case "otherLeaseFinal":
            return "其他终审附件";
		case "otherLeaseStandard":
            return "其他合规附件";
		case "reconsiders":
            return "其他合规附件";
		case "nameplate":
			return "车身-铭牌";
		case "surfaceYQ":
			return "人车合照";
		case "surfaceZC":
			return "车身正面";
		case "surfaceZQ":
			return "车身正前";
		case "surfaceZH":
			return "车身正后";
		case "surfaceCD":
			return "车身车灯";
		case "surfaceLT":
			return "车身轮胎";
		case "innerDP":
			return "内饰-顶棚";
		case "innerCM":
			return "内饰-车门";
		case "innerQP":
			return "内饰-前排";
		case "innerFXP":
			return "内饰-方向盘";
		case "innerZK":
			return "内饰-中控";
		case "ybp":
			return "内饰-仪表盘";
		case "bsx":
			return "变速箱";
		case "fdjc":
			return "发动机舱";
		case "bt":
			return "备胎";
		default:
			break;
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		String json = "{"
				+ "'IdCardA': 'http://www.sznews.com/ent/images/attachement/jpg/site3/20141011/4437e629783815a2bce254.jpg',"
				+ "'IdCardB': 'http://www.mayilc.com/Uploads/55bad96fce2b1.jpg',"
				+ "'CarLicense': 'http://www.9cvc.com/kindeditor/attached/image/20150701/20150701085512171217.jpg',"
				+ "'DriveLicense': 'http://s10.sinaimg.cn/bmiddle/004iWrFWty6WfLd6LEdf9&690',"
				+ "'TCI': 'http://dealer2.autoimg.cn/dealerdfs/g9/M0B/FA/F5/620x0_1_q87_autohomedealer__wKjBzlZdU1SAEO9uAABV4jFkaDY510.jpg',"
				+ "'TLRC': ''," + "'CarKey': ''," + "'ResidenceCardA': ''," + "'ResidenceCardB': '',"
				+ "'VCI': 'https://cdn.goodsure.cn/upfiles/litpics/pic1417246907226.jpg',"
				+ "'Household': 'http://www.shilehui.com/UserImage/64136/20110522032459723788.JPG'," + "'Marriage': '',"
				+ "'ChanDiao': ''," + "'BusinessLicense': ''," + "'CreditReport': ''," + "'BankStatements': '',"
				+ "'FieldSurvey': ''," + "'FeeList': ''," + "'Contract': ["
				+ "'http://img1.epanshi.com/216/product/w-4c241c4a3a392.jpg',"
				+ "'http://img1.epanshi.com/216/product/w-4c241c4a1f5e1.jpg',"
				+ "'http://p13.qhimg.com/t018318e002694eeb05.jpg']," + "'RegisterCard': [" + "''," + "''],"
				+ "'Other': [" + "''," + "'']," + "'Insurance': [" + "''," + "'']," + "'CarInvoice': '',"
				+ "'VehiclePurchaseTax': ''," + "'VehicleMortgageRegister': ''," + "'BackCashPledge': [" + "'',"
				+ "'']," + "'OtherLeaseFinal': [" + "''," + "'']," + "'OtherLeaseCompliance': [" + "''," + "'']}";
		Map<String, Object> map = JSON.parseObject(json, Map.class);
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if (val != null) {
				if (val instanceof String && StringUtils.isNotBlank(val.toString())) {
					download(val.toString(), convert(key.toString()), "D://16//");
				} else if (val instanceof List) {
					List list = (List) val;
					for (int i = 0; i < list.size(); i++) {
						if (StringUtils.isNotBlank(list.get(i).toString())) {
							download(list.get(i).toString(), convert(key.toString()) + (i + 1), "D://16//");
						}
					}
				}
			}

		}
	}
}
