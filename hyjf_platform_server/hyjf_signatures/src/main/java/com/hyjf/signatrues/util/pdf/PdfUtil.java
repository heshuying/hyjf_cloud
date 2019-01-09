package com.hyjf.signatrues.util.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.util.List;

/**
 * @author xiasq
 * @version PdfUtil, v0.1 2019/1/8 15:52
 */
public class PdfUtil {

    /**
     * 将多张图片转换成PDF
     * @param imagePath
     * 图片路径列表
     * @param pdfPath
     * 保存的PDF路径
     */
    public static void imageTOpdf(List imagePath, String pdfPath){
        String imgPath = null;
        PDDocument document = null;
        try{

            // 创建PDF文档
            document = new PDDocument();
            for (int i = 0; i < imagePath.size(); i++) {

                imgPath = (String) imagePath.get(i);
                PDImageXObject pdImage = PDImageXObject.createFromFile(imgPath, document);
                PDRectangle pRectangle=new PDRectangle(pdImage.getWidth(), pdImage.getHeight());
                PDPage pp=new PDPage(pRectangle);
                PDPageContentStream contentStream = new PDPageContentStream(document, pp);
                contentStream.drawImage(pdImage,1, -20,pdImage.getWidth(),pdImage.getHeight());
                document.addPage(pp);
                contentStream.close();

            }

            // 保存PDF文档
            document.save(pdfPath);
            //关闭文档
            document.close();
        }catch(Exception e){
            e.getStackTrace();
            throw new RuntimeException();
        }
    }
}
