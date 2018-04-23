package com.hyjf.common.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {

	/**
	 * serialVersionUID:TODO 这个变量是干什么的
	 */
		
	private static final long serialVersionUID = 1045021088884528457L;

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 禁止图像缓存,使得单击验证码可以刷新验证码图片
		resp.setHeader("Pragma", "nocache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");

		BufferedImage bim = new BufferedImage(70, 20, BufferedImage.TYPE_INT_RGB);
		Graphics2D gc = bim.createGraphics();
		// 设置图片填充颜色
		gc.setColor(Color.yellow);
		gc.fillRect(0, 0, 70, 20);
		// 设置边框颜色
		gc.setColor(Color.pink);
		gc.drawRect(0, 0, 69, 19);
		// 产生4位随机数
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		// 设置干扰线颜色
		gc.setColor(Color.cyan);
		for (int j = 0; j < 30; j++) {
			int x = rand.nextInt(70);
			int y = rand.nextInt(20);
			int x1 = rand.nextInt(6);
			int y1 = rand.nextInt(6);
			// 往图片里面画干扰线
			gc.drawLine(x, y, x + x1, y + y1);
		}

		for (int i = 0; i < 4; i++) {
			// 将生成的数字写入到图片中去,int转成string
			String str = GetCode.getRandomCode(1);
			// 设置字体颜色
			gc.setColor(Color.RED);
			gc.drawString(str, i * 10 + 20, 15);
			sb.append(str);
		}
		// 将stringbuffer转成string
		String code = String.valueOf(sb);
		// 将生成的验证码的值放到session中去
		req.getSession().setAttribute("code", code);
		// 将图片以流的形式输出
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(bim, "jpg", sos);
		sos.close();
	}
}
