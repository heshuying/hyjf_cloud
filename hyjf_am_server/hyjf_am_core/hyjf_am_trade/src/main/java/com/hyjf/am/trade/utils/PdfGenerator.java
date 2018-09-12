package com.hyjf.am.trade.utils;

import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.pdf.HtmlGenerator;
import com.hyjf.common.spring.SpringUtils;
import com.hyjf.common.util.CustomConstants;
import com.lowagie.text.pdf.BaseFont;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PdfGenerator {
    Logger logger = LoggerFactory.getLogger(PdfGenerator.class);
    private static SystemConfig systemConfig = SpringUtils.getBean(SystemConfig.class);

    /**
     * 生成Pdf文件
     *
     * @param fileName
     * @param fileType
     * @param variables
     * @throws Exception
     */
    public String generateLocal(String fileName, String fileType, Map<String, Object> variables) throws Exception {
        String ftlPath = systemConfig.getContractFtlPath();
        String ftlName = "";
        if (fileType.equals(CustomConstants.CREDIT_CONTRACT)) {
            ftlName = systemConfig.getCreditContractFtlName();
        } else if (fileType.equals(CustomConstants.TENDER_CONTRACT)) {
            ftlName = systemConfig.getTenderContractFtlName();
        } else if (fileType.equals(CustomConstants.HTJ_TENDER_CONTRACT)) {
            ftlName = systemConfig.getHtjTenderContractFtlName();
        } else if (fileType.equals(CustomConstants.RTB_TENDER_CONTRACT)) {
            ftlName = systemConfig.getRtbContractFtlName();
        } else if (fileType.equals(CustomConstants.RTB_TENDER_CONTRACT_ZSC)) {
            ftlName = systemConfig.getRtbzscContractFtlName();
        } else if (fileType.equals(CustomConstants.NEW_HJH_INVEST_CONTRACT)) {
            ftlName = systemConfig.getNewHjhInvestContractFtlName();
        } else if (fileType.equals(CustomConstants.NEW_TENDER_CONTRACT_FTL_NAME)) {
            ftlName = systemConfig.getTenderNewContractFtlName();
        }
        String fontPath = systemConfig.getContractFont();
        OutputStream outputStream = null;
        try {
            File fileDir = new File(systemConfig.getHYJF_MAKEPDF_TEMPPATH() + "/sealpdf/");
            File file = new File(systemConfig.getHYJF_MAKEPDF_TEMPPATH() + "/sealpdf/" + fileName);
            logger.info("-------------加签前生成的pdf地址：" + systemConfig.getHYJF_MAKEPDF_TEMPPATH() + "/sealpdf/" + fileName);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            // 根绝ftl文件路径拼接模板数据
            String htmlStr = HtmlGenerator.generate(ftlPath, ftlName, variables);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            if (StringUtils.isNotBlank(fontPath)) {
                fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }
            renderer.setDocument(doc, null);

            renderer.layout();
            renderer.createPDF(outputStream);
            outputStream.flush();
            outputStream.close();
            // PDF文件进行加章处理
            try {
                String path = sealLocal(fileName, systemConfig.getWebPdfHost() + systemConfig.getTempPdfPath() + fileName);
                // 加章完成删除文件
                file.delete();
                return path;
            } catch (Exception e) {
                logger.error("PDF文件加章处理：" + e);
                if (file.exists()) {
                    file.delete();
                }
                throw e;
            }
        } catch (Exception e) {
            logger.error("生成PDF文件：" + e);
            throw e;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    logger.error("关闭输出流：" + e);
                }
            }
        }
    }

    /**
     * PDF文件进行加章处理
     *
     * @param fileName 文件名称
     * @param fileUrl  文件URL路径
     */
    public String sealLocal(String fileName, String fileUrl) {
        logger.info("sealLocal-fileName:" + fileName);
        logger.info("sealLocal-fileUrl:" + fileUrl);
        // PDF文件添加公章请求
        StringBuffer strBuf = new StringBuffer();
        try {
            strBuf.append(" <?xml version=\"1.0\" encoding=\"utf-8\" ?> ");
            strBuf.append(" <SealDocRequest> ");
            strBuf.append(" <BASE_DATA> ");
            strBuf.append("         <!--应用系统id--> ");
            strBuf.append("     <SYS_ID>" + systemConfig.getSealSysId() + "</SYS_ID> ");
            strBuf.append("         <!--用户id--> ");
            strBuf.append("     <USER_ID>" + systemConfig.getSealUserId() + "</USER_ID> ");
            strBuf.append("         <!--用户密码--> ");
            strBuf.append("     <USER_PSD>" + systemConfig.getSealPassword() + "</USER_PSD> ");
            strBuf.append(" </BASE_DATA> ");
            strBuf.append(" <META_DATA> ");
            strBuf.append("      <!--是否模板合并--> ");
            strBuf.append("     <IS_MERGER>false</IS_MERGER> ");
            strBuf.append(" </META_DATA> ");
            strBuf.append(" <FILE_LIST> ");
            strBuf.append("    <TREE_NODE> ");
            strBuf.append("         <!--文档名称--> ");
            strBuf.append("         <FILE_NO>" + fileName + "</FILE_NO> ");
            strBuf.append("         <!--是否加二维码--> ");
            strBuf.append("         <IS_CODEBAR>false</IS_CODEBAR> ");
            strBuf.append("         <!--规则类型0：按照规则号进行盖章，1按照规则信息进行盖章--> ");
            strBuf.append("         <RULE_TYPE>0</RULE_TYPE> ");
            strBuf.append("         <!--规则号，多个规则用逗号隔开--> ");
            strBuf.append("         <RULE_NO>2</RULE_NO> ");
            strBuf.append("         <!--应用场景data是模板数据合成，file是读取FILEPATH文件--> ");
            strBuf.append("         <CJ_TYPE>file</CJ_TYPE> ");
            strBuf.append("         <!--请求类型，1：ftp,0:http-->  ");
            strBuf.append("         <REQUEST_TYPE>0</REQUEST_TYPE> ");
            strBuf.append("         <!--读取文件路径--> ");
            strBuf.append("         <FILE_PATH>" + fileUrl + "</FILE_PATH> ");
            strBuf.append("         <ftp_savepath></ftp_savepath> ");
            strBuf.append("         <!--是否添加标记印章1：是，0：否--> ");
            strBuf.append("         <AREA_SEAL>0</AREA_SEAL> ");
            strBuf.append("     </TREE_NODE> ");
            strBuf.append(" </FILE_LIST> ");
            strBuf.append(" </SealDocRequest> ");

            HashMap<String, String> params = new HashMap<String, String>();
            String url = systemConfig.getSealUrl();
            params.put("address", systemConfig.getSealAddress());
            params.put("port", systemConfig.getSealPort());
            params.put("operate", systemConfig.getSealOperate());
            params.put("hc", strBuf.toString());
            params.put("yewudata", strBuf.toString());
            String str = HttpDeal.post(url, params);

            StringReader sr = new StringReader(str);
            InputSource is = new InputSource(sr);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            NodeList r1 = doc.getElementsByTagName("FILE_MSG");
            String pdfUrl = r1.item(0).getFirstChild().getNodeValue();
            logger.info("-----------加签后生成的pdf地址：" + pdfUrl);
            return pdfUrl;
        } catch (Exception e) {
            logger.error("PDF文件请求加章处理：" + e);
            return null;
        }
    }

}
