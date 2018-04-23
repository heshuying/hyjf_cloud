package com.hyjf.common.pdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.InputSource;

import com.hyjf.common.file.FileUtil;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.PropUtils;
import com.lowagie.text.pdf.BaseFont;

public class PdfGenerator {

	/**
	 * 生成pdf合同
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 * @param fileType
	 * @param variables
	 * @throws Exception
	 */
	public static void generatePdf(HttpServletRequest request, HttpServletResponse response, String fileName, String fileType, Map<String, Object> variables) throws Exception {
		String ftlPath = PropUtils.getSystem(CustomConstants.CONTRACT_FTL_PATH);
		String ftlName = "";
		if (fileType.equals(CustomConstants.CREDIT_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.CREDIT_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.HJH_CREDIT_CONTRACT)) {
				ftlName = PropUtils.getSystem(CustomConstants.HJH_CREDIT_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.PLAN_CREDIT_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.PLAN_CREDIT_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.TENDER_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.TENDER_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.BORROWER_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.BORROWER_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.BORROWER_CONTRACT_HXF)) {
			ftlName = PropUtils.getSystem(CustomConstants.BORROWER_HXF_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.HTJ_TENDER_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.HTJ_TENDER_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.HTJ_INVEST_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.HTJ_INVEST_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.RTB_TENDER_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.RTB_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.RTB_TENDER_CONTRACT_ZSC)) {
			ftlName = PropUtils.getSystem(CustomConstants.RTB_CONTRACT_ZSC_FTL_NAME);
		} else if (fileType.equals(CustomConstants.NEW_HJH_INVEST_CONTRACT)) {
            ftlName = PropUtils.getSystem(CustomConstants.NEW_HJH_INVEST_CONTRACT_FTL_NAME);
        } else if (fileType.equals(CustomConstants.NEW_TENDER_CONTRACT)) {
            ftlName = PropUtils.getSystem(CustomConstants.NEW_TENDER_CONTRACT_FTL_NAME);
        } else if (fileType.equals(CustomConstants.NEW_DIARY_CONTRACT)) {
            ftlName = PropUtils.getSystem(CustomConstants.NEW_HJH_DIARY_FTL_NAME);
        } 
		
		
		
		String fontPath = PropUtils.getSystem(CustomConstants.CONTRACT_FONT_PATH);
		OutputStream outputStream = null;
		try {
			File fileDir = new File(PropUtils.getSystem(CustomConstants.HYJF_MAKEPDF_TEMPPATH) + "/sealpdf/");
			File file = new File(PropUtils.getSystem(CustomConstants.HYJF_MAKEPDF_TEMPPATH) + "/sealpdf/" + fileName);
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
			String htmlStr = HtmlGenerator.generate(ftlPath, ftlName, variables);// 根绝ftl文件路径拼接模板数据
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));
			ITextRenderer renderer = new ITextRenderer();
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if (StringUtils.isNotBlank(fontPath)) {
				fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
			renderer.setDocument(doc, null);
			/*
			 * if (baseURL != null && !baseURL.equals("")) {
			 * renderer.getSharedContext().setBaseURL("file:/" + baseURL); }
			 */
			renderer.layout();
			renderer.createPDF(outputStream);
			outputStream.flush();
			outputStream.close();
			// PDF文件进行加章处理
			try {
				seal(request, response, fileName, PropUtils.getSystem(CustomConstants.HYJF_WEB_PDF_URL) + PropUtils.getSystem(CustomConstants.CONTRACT_TEMPPDF_PATH) + fileName);
				// 加章完删除文件
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
				if (file.exists()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * PDF文件进行加章处理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param fileName
	 *            文件名称
	 * @param fileUrl
	 *            文件URL路径
	 */
	public static String seal(HttpServletRequest request, HttpServletResponse response, String fileName, String fileUrl) {
		// PDF文件添加公章请求
		StringBuffer strBuf = new StringBuffer();
		try {
			strBuf.append(" <?xml version=\"1.0\" encoding=\"utf-8\" ?> ");
			strBuf.append(" <SealDocRequest> ");
			strBuf.append(" <BASE_DATA> ");
			strBuf.append("         <!--应用系统id--> ");
			strBuf.append("     <SYS_ID>" + PropUtils.getSystem(CustomConstants.HYJF_SEAL_SYSID) + "</SYS_ID> ");
			strBuf.append("         <!--用户id--> ");
			strBuf.append("     <USER_ID>" + PropUtils.getSystem(CustomConstants.HYJF_SEAL_USERID) + "</USER_ID> ");
			strBuf.append("         <!--用户密码--> ");
			strBuf.append("     <USER_PSD>" + PropUtils.getSystem(CustomConstants.HYJF_SEAL_PASSWORD) + "</USER_PSD> ");
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
			String url = PropUtils.getSystem(CustomConstants.HYJF_SEAL_URL);
			params.put("address", PropUtils.getSystem(CustomConstants.HYJF_SEAL_ADDRESS));
			params.put("port", PropUtils.getSystem(CustomConstants.HYJF_SEAL_PORT));
			params.put("operate", PropUtils.getSystem(CustomConstants.HYJF_SEAL_OPERATE));
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
			System.out.println(fileUrl);
			System.out.println(strBuf.toString());
			System.out.println(str);
			System.out.println(pdfUrl + "...................................................");
			FileUtil.getServletFile(request, response, pdfUrl.substring(0, pdfUrl.length() - 1), fileName);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 生成batchPdf文件
	 * 
	 * @param filePath
	 * @param fileName
	 * @param fileType
	 * @param variables
	 * @throws Exception
	 */
	public static String generateLocal(String fileName, String fileType, Map<String, Object> variables) throws Exception {
		String ftlPath = PropUtils.getSystem(CustomConstants.CONTRACT_FTL_PATH);
		String ftlName = "";
		if (fileType.equals(CustomConstants.CREDIT_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.CREDIT_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.TENDER_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.TENDER_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.HTJ_TENDER_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.HTJ_TENDER_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.RTB_TENDER_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.RTB_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.RTB_TENDER_CONTRACT_ZSC)) {
			ftlName = PropUtils.getSystem(CustomConstants.RTB_CONTRACT_ZSC_FTL_NAME);	
		} else if (fileType.equals(CustomConstants.NEW_HJH_INVEST_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.NEW_HJH_INVEST_CONTRACT_FTL_NAME);
		} 
		//散标居间服务协议
		else if (fileType.equals(CustomConstants.NEW_TENDER_CONTRACT_FTL_NAME)) {
			ftlName = PropUtils.getSystem(CustomConstants.NEW_TENDER_CONTRACT_FTL_NAME);
		}
		String fontPath = PropUtils.getSystem(CustomConstants.CONTRACT_FONT_PATH);
		OutputStream outputStream = null;
		try {
			File fileDir = new File(PropUtils.getSystem(CustomConstants.HYJF_MAKEPDF_TEMPPATH) + "/sealpdf/");
			File file = new File(PropUtils.getSystem(CustomConstants.HYJF_MAKEPDF_TEMPPATH) + "/sealpdf/" + fileName);
			System.out.println("-------------加签前生成的pdf地址：" + PropUtils.getSystem(CustomConstants.HYJF_MAKEPDF_TEMPPATH) + "/sealpdf/" + fileName);
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
			String htmlStr = HtmlGenerator.generate(ftlPath, ftlName, variables);// 根绝ftl文件路径拼接模板数据
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));
			ITextRenderer renderer = new ITextRenderer();
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if (StringUtils.isNotBlank(fontPath)) {
				fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
			renderer.setDocument(doc, null);
			/*
			 * if (baseURL != null && !baseURL.equals("")) {
			 * renderer.getSharedContext().setBaseURL("file:/" + baseURL); }
			 */
			renderer.layout();
			renderer.createPDF(outputStream);
			outputStream.flush();
			outputStream.close();
			// PDF文件进行加章处理
			try {
				String path = sealLocal(fileName, PropUtils.getSystem(CustomConstants.HYJF_WEB_PDF_URL) + PropUtils.getSystem(CustomConstants.CONTRACT_TEMPPDF_PATH) + fileName);
				// 加章完成删除文件
				file.delete();
				return path;
			} catch (Exception e) {
				e.printStackTrace();
				if (file.exists()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * PDF文件进行加章处理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param fileName
	 *            文件名称
	 * @param fileUrl
	 *            文件URL路径
	 */
	public static String sealLocal(String fileName, String fileUrl) {
		System.out.println(fileName);
		System.out.println(fileUrl);
		// PDF文件添加公章请求
		StringBuffer strBuf = new StringBuffer();
		try {
			strBuf.append(" <?xml version=\"1.0\" encoding=\"utf-8\" ?> ");
			strBuf.append(" <SealDocRequest> ");
			strBuf.append(" <BASE_DATA> ");
			strBuf.append("         <!--应用系统id--> ");
			strBuf.append("     <SYS_ID>" + PropUtils.getSystem(CustomConstants.HYJF_SEAL_SYSID) + "</SYS_ID> ");
			strBuf.append("         <!--用户id--> ");
			strBuf.append("     <USER_ID>" + PropUtils.getSystem(CustomConstants.HYJF_SEAL_USERID) + "</USER_ID> ");
			strBuf.append("         <!--用户密码--> ");
			strBuf.append("     <USER_PSD>" + PropUtils.getSystem(CustomConstants.HYJF_SEAL_PASSWORD) + "</USER_PSD> ");
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
			String url = PropUtils.getSystem(CustomConstants.HYJF_SEAL_URL);
			params.put("address", PropUtils.getSystem(CustomConstants.HYJF_SEAL_ADDRESS));
			params.put("port", PropUtils.getSystem(CustomConstants.HYJF_SEAL_PORT));
			params.put("operate", PropUtils.getSystem(CustomConstants.HYJF_SEAL_OPERATE));
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
			System.out.println("-----------加签后生成的pdf地址：" + pdfUrl);
			return pdfUrl;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 生成pdf合同
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 * @param fileType
	 * @param variables
	 * @throws Exception
	 */
	public static File generatePdfFile(HttpServletRequest request, HttpServletResponse response, String fileName, String fileType, Map<String, Object> variables) throws Exception {
		String ftlPath = PropUtils.getSystem(CustomConstants.CONTRACT_FTL_PATH);
		String ftlName = "";
		if (fileType.equals(CustomConstants.CREDIT_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.CREDIT_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.TENDER_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.TENDER_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.BORROWER_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.BORROWER_CONTRACT_FTL_NAME);
		} else if (fileType.equals(CustomConstants.BORROWER_CONTRACT_HXF)) {
			ftlName = PropUtils.getSystem(CustomConstants.BORROWER_HXF_CONTRACT_FTL_NAME);
		} else if(fileType.equals(CustomConstants.CREDIT_CONTRACT)){
			ftlName = PropUtils.getSystem(CustomConstants.CREDIT_CONTRACT_FTL_NAME);
		}//散标居间服务协议
        else if (fileType.equals(CustomConstants.NEW_TENDER_CONTRACT_FTL_NAME)) {
            ftlName = PropUtils.getSystem(CustomConstants.NEW_TENDER_CONTRACT_FTL_NAME);
        }
        //汇计划债转协议
        else if (fileType.equals(CustomConstants.HJH_CREDIT_CONTRACT)) {
			ftlName = PropUtils.getSystem(CustomConstants.HJH_CREDIT_CONTRACT_FTL_NAME);
        }
		String fontPath = PropUtils.getSystem(CustomConstants.CONTRACT_FONT_PATH);
		OutputStream outputStream = null;
		File nfile = null;
		try {
			File fileDir = new File(PropUtils.getSystem(CustomConstants.HYJF_MAKEPDF_TEMPPATH) + "/sealpdf/");
			File file = new File(PropUtils.getSystem(CustomConstants.HYJF_MAKEPDF_TEMPPATH) + "/sealpdf/" + fileName);
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
			String htmlStr = HtmlGenerator.generate(ftlPath, ftlName, variables);// 根绝ftl文件路径拼接模板数据
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));
			ITextRenderer renderer = new ITextRenderer();
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if (StringUtils.isNotBlank(fontPath)) {
				fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
			renderer.setDocument(doc, null);
			/*
			 * if (baseURL != null && !baseURL.equals("")) {
			 * renderer.getSharedContext().setBaseURL("file:/" + baseURL); }
			 */
			renderer.layout();
			renderer.createPDF(outputStream);
			outputStream.flush();
			outputStream.close();
			// PDF文件进行加章处理
			System.out.println(PropUtils.getSystem(CustomConstants.HYJF_WEB_PDF_URL) + PropUtils.getSystem(CustomConstants.CONTRACT_TEMPPDF_PATH) + fileName);
			try {
				nfile = sealFile(request, response, fileName, PropUtils.getSystem(CustomConstants.HYJF_WEB_PDF_URL) + PropUtils.getSystem(CustomConstants.CONTRACT_TEMPPDF_PATH) + fileName);
				// 加章完成删除文件
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
				if (file.exists()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return nfile;
	}

	/**
	 * PDF文件进行加章处理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param fileName
	 *            文件名称
	 * @param fileUrl
	 *            文件URL路径
	 */
	public static File sealFile(HttpServletRequest request, HttpServletResponse response, String fileName, String fileUrl) {
		// PDF文件添加公章请求
		StringBuffer strBuf = new StringBuffer();
		File file = null;
		try {
			strBuf.append(" <?xml version=\"1.0\" encoding=\"utf-8\" ?> ");
			strBuf.append(" <SealDocRequest> ");
			strBuf.append(" <BASE_DATA> ");
			strBuf.append("         <!--应用系统id--> ");
			strBuf.append("     <SYS_ID>" + PropUtils.getSystem(CustomConstants.HYJF_SEAL_SYSID) + "</SYS_ID> ");
			strBuf.append("         <!--用户id--> ");
			strBuf.append("     <USER_ID>" + PropUtils.getSystem(CustomConstants.HYJF_SEAL_USERID) + "</USER_ID> ");
			strBuf.append("         <!--用户密码--> ");
			strBuf.append("     <USER_PSD>" + PropUtils.getSystem(CustomConstants.HYJF_SEAL_PASSWORD) + "</USER_PSD> ");
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
			String url = PropUtils.getSystem(CustomConstants.HYJF_SEAL_URL);
			params.put("address", PropUtils.getSystem(CustomConstants.HYJF_SEAL_ADDRESS));
			params.put("port", PropUtils.getSystem(CustomConstants.HYJF_SEAL_PORT));
			params.put("operate", PropUtils.getSystem(CustomConstants.HYJF_SEAL_OPERATE));
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
			file = FileUtil.getFile(request, response, pdfUrl.substring(0, pdfUrl.length() - 1), fileName);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return file;
	}

}
