package com.hyjf.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.plexus.util.StringUtils;

import com.hyjf.common.cache.RedisUtils;

public class RandomValidateCode {
	private Random random = new Random();
	private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";// 随机产生的字符串
	
	public static final String CLASSNAME=RandomValidateCode.class.getName();

	private int width = 118;// 图片宽
	private int height = 42;// 图片高
	private int lineSize = 50;// 干扰线数量
	private int stringNum = 4;// 随机产生字符数量
	/*
	 * 获得字体
	 */

	private Font getFont() {
		return new Font("Fixedsys", Font.CENTER_BASELINE, 30);
	}

	/*
	 * 获得颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * 生成随机图片
	 */
	public void getRandcode(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);

		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 30));
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drowLine(g);
		}
		// 绘制随机字符
		String randomString = "";
		for (int i = 1; i <= stringNum; i++) {
			randomString = drowString(g, randomString, i);
		}
		// Cookie phpSessionIdCookie = CookieUtils.getCookieByName(request,
		// "JSESSIONID");
		// if (phpSessionIdCookie != null) {
		// RedisUtils.set("JSESSIONID_RandomValidateCode_" +
		// phpSessionIdCookie.getValue(), randomString, 10 * 60);//10分钟有效
		// }

		HttpSession session = request.getSession();
		//TODO 获取sessionid方法有变化
		RedisUtils.set(CLASSNAME+"RandomValidateCode_" + session.getId(), randomString, 10 * 60);// 10分钟有效
		// SessionUtils.setSession(RANDOMCODEKEY, randomString);
		g.dispose();
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());// 将内存中的图片通过流动形式输出到客户端
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 绘制字符串
	 */
	private String drowString(Graphics g, String randomString, int i) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
		randomString += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 20 * i, 30);
		return randomString;
	}

	/*
	 * 绘制干扰线
	 */
	private void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/*
	 * 获取随机的字符
	 */
	public String getRandomString(int num) {
		return String.valueOf(randString.charAt(num));
	}

	/**
	 * 检查图片验证码
	 *
	 * @param request
	 * @param randomCode
	 * @return
	 */
	public boolean checkRandomCode(HttpServletRequest request, String randomCode) {
		// Cookie phpSessionIdCookie = CookieUtils.getCookieByName(request,
		// "JSESSIONID");
		// if (phpSessionIdCookie != null) {
		// String oldCode = RedisUtils.get("JSESSIONID_RandomValidateCode_" +
		// phpSessionIdCookie.getValue());
		// if (oldCode != null && StringUtils.equalsIgnoreCase(oldCode,
		// randomCode)) {
		// return true;
		// }
		//
		// }
		// return false;

		//TODO 获取sessionid方法有变化
		HttpSession session = request.getSession();
		String oldCode = RedisUtils.get(CLASSNAME+"RandomValidateCode_" + session.getId());
		if (oldCode != null && StringUtils.equalsIgnoreCase(oldCode, randomCode)) {
			return true;
		}
		return false;
	}
}
