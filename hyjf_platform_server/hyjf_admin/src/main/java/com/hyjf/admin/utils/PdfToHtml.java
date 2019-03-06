package com.hyjf.admin.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将pdf转换为图片
 *
 * @author：yinhui
 * @Date: 2018/8/9  15:49
 */
public class PdfToHtml extends PDFToImage {
	private static final Logger logger = LoggerFactory.getLogger(PdfToHtml.class);

    /**
     * 将pdf转换为图片 ，返回图片名字
     *
     * @param pdfPath  pdf文件的路径
     * @param savePath 图片保存的地址
     * @param imgType  图片保存方式
     */
	 public static List<String> pdftoImg(String pdfPath, String savePath, String imgType) {
	        List<String> listImg = new ArrayList<>();

	        File file = new File(pdfPath);
	        PDDocument pdDocument = null;
	        
	        
	        File p=new File(savePath);
	        if(!p.exists()){
	            p.mkdir();
	        }
	        try {
	 
	            pdDocument =  PDDocument.load(file);
	            PDPageTree pages = pdDocument.getPages();
	            PDFRenderer renderer = new PDFRenderer(pdDocument);
	            for (int i = 0; i < pages.getCount(); i++) {
	                String saveFileName = savePath+"/"+i+"."+imgType;
	                File dstFile = new File(saveFileName);
	                BufferedImage image = renderer.renderImageWithDPI(i, 122);
	                BufferedImage srcImage = resize(image, 1191, 1684);//产生缩略图
	                ImageIO.write(srcImage,imgType, dstFile);
	                listImg.add(String.valueOf(i));
	            }
	        } catch (Exception e) {
	            logger.error(e.getMessage());
	        }finally{
	            if(pdDocument != null){
	                try {
	                    pdDocument.close();
	                } catch (IOException e) {
	                    logger.error(e.getMessage());
	                }
	            }
	        }

	        return listImg;
	    }

    private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

}
