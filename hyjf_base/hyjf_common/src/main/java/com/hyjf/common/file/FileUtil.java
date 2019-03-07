/*
 * @(#)FileUtil.java　2008/9/16
 *
 * Copyright (c) 2010-2011 Benben, http://www.51jsr.com
 */
package com.hyjf.common.file;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <code><strong> FileUtil  文件目录操作类 </strong><code>
 *
 * @author <strong> benben </strong>
 * @version 2008/9/16 11:02:34
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class FileUtil {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileUtil.class);

	// 构造函数 - 防止本类实列化
	protected FileUtil() {
	}

	/**
	 * 读取指定的文件
	 *
	 * @param fileName
	 *            String 目标文件名
	 * @return byte[]
	 */
	public static byte[] readFile(String fileName) {
		return FileUtil.readFile(new File(fileName), false);
	}

	public static byte[] readFile(String fileName, boolean isBom) {
		return FileUtil.readFile(new File(fileName), isBom);
	}

	/**
	 * 读取指定的文件
	 *
	 * @param file
	 *            File 目标文件
	 * @return byte[]
	 */
	public static byte[] readFile(File file, boolean isBom) {
		RandomAccessFile raf = null;
		try {
			int offset = 0;
			byte[] tmp = new byte[4];
			raf = new RandomAccessFile(file, "r");
			if(raf.read(tmp) < 1) {
				tmp = new byte[0];
			} else {
				// BOM头处理
				raf.seek(offset = isBom ? getPosAfterBom(tmp) : 0);
				raf.read(tmp = new byte[(int) file.length() - offset]);
			}//Endif
			return tmp;
		} catch (Exception e) {
			logger.error("Read the files failure! File: " + file.getPath());
		} finally {
			try {
				if(raf != null){
					raf.close();
				}
			} catch (Exception e) {
			}// Endtry
		}// Endtry
		return null;
	}

	// 取得UTF系列编码文件的BOM头之后的位置
	private static int getPosAfterBom(byte[] b) {
		int offset = 0;
		if (b.length > 3
				&& ((b[0] == 0 && b[1] == 0 && b[2] == -2 && b[3] == -1)
						|| (b[0] == -1 && b[1] == -2 && b[2] == 0 && b[3] == 0))) {
			offset = 4;
		} else if (b.length > 2 && b[0] == -17 && b[1] == -69 && b[2] == -65) {
			offset = 3;
		} else if (b.length > 1
				&& ((b[0] == -2 && b[1] == -1) || (b[0] == -1 && b[1] == -2))) {
			offset = 2;
		}// Endif
		return offset;
	}

	/**
	 * 将data保存到指定的新文件中
	 *
	 * @param fileName
	 *            String 目标文件名
	 * @param data
	 *            byte[] 要保存的数据
	 */
	public static void writeFile(String fileName, byte[] data) {
		FileUtil.writeFile(fileName, data, false);
	}

	public static void writeFile(File file, byte[] data) {
		FileUtil.writeFile(file, data, false);
	}

	public static void writeFile(String fileName, byte[] data,
			boolean appendFlag) {
		FileUtil.writeFile(new File(fileName), data, appendFlag);
	}

	/**
	 * 将data保存到指定的新文件中
	 *
	 * @param file
	 *            File 目标文件
	 * @param data
	 *            byte[] 要保存的数据
	 * @param appendFlag
	 *            boolean 是否向文件追加内容
	 */
	public static void writeFile(File file, byte[] data, boolean appendFlag) {
		RandomAccessFile raf = null;
		try {
			if (!appendFlag && file.exists()) {
				file.delete();
			}// Endif
			raf = new RandomAccessFile(file, "rw");
			if (appendFlag) {
				raf.seek(file.length());
			}// Endif
			raf.write(data);
		} catch (Exception e) {
			logger.error("Save the files failure! File: " + file.getPath());
		} finally {
			try {
				if(raf!= null){
					raf.close();
				}
			} catch (Exception e) {
			}// Endtry
		}// Endtry
	}

	/**
	 * 复制文件
	 *
	 * @param sourceFile
	 *            String 源文件名
	 * @param destinationFile
	 *            String 目标文件名
	 */
	public static void copy(String sourceFile, String destinationFile) {
		FileUtil.copy(new File(sourceFile), new File(destinationFile));
	}

	/**
	 * 复制文件
	 *
	 * @param sourceFile
	 *            String 源文件
	 * @param destinationFile
	 *            String 目标文件
	 */
	public static void copy(File sourceFile, File destinationFile) {
		FileUtil.writeFile(destinationFile, FileUtil.readFile(sourceFile, false));
	}

	/**
	 * 复制文件夹及文件夹下的所有子文件与子文件夹
	 *
	 * @param sourcePath
	 *            String 源文件夹名
	 * @param destinationPath
	 *            String 目标文件夹名
	 */
	public static void xcopy(String sourcePath, String destinationPath) {
		FileUtil.xcopy(new File(sourcePath), new File(destinationPath));
	}

	/**
	 * 复制文件夹及文件夹下的所有子文件与子文件夹
	 *
	 * @param sourcePath
	 *            String 源文件夹
	 * @param destinationPath
	 *            String 目标文件夹
	 */
	public static void xcopy(File sourcePath, File destinationPath) {
		// 检测源文件夹
		if (!sourcePath.exists()) {
			logger.warn("Source file folder does not exist! File: "
					+ sourcePath.getPath());
			return;
		}// Endif

		// 创建目标文件夹
		destinationPath.mkdirs();

		File[] files = sourcePath.listFiles();
		String destPath = destinationPath.getPath() + File.separator;
		for (File file : files) {
			if (file.isFile()) {
				FileUtil.copy(file, new File(destPath + file.getName()));
			} else if (file.isDirectory()) {
				FileUtil.xcopy(file, new File(destPath + file.getName()));
			}// Endif
		}// Endfor
	}

	/**
	 * 删除指定目录下的所有子文件及子目录
	 *
	 * @param directory
	 *            String 要删除的目录名
	 */
	public static void deltree(String directory) {
		FileUtil.deltree(new File(directory));
	}

	/**
	 * 删除指定目录下的所有子文件及子目录
	 *
	 * @param directory
	 *            String 要删除的目录
	 */
	public static void deltree(File directory) {
		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null && files.length > 0) {
				for (File file : files) {
					FileUtil.deltree(file);
				}// Endfor
			}// Endif
		}// Endif
		directory.delete();
	}

	/**
	 * 返回指定文件夹下所有子文件中文件名附合正则规则的文件集合 (包括子文件中的所有文件)
	 *
	 * @param list
	 *            List 附合正则规则的文件集合
	 * @param directory
	 *            File 要查找的文件夹
	 * @param fileNameRegex
	 *            String 文件名正则表现
	 */
	public static void getfiles(List<File> list, File directory,
			String fileNameRegex) {
		String fnRegex = fileNameRegex.replaceAll("\\.", "\\\\.").replaceAll(
				"\\*", ".*").intern();
		File[] childs = directory.listFiles();
		for (File file : childs) {
			if (file.isFile()) {
				if (file.getName().matches(fnRegex)) {
					list.add(file);
				}// Endif
			} else if (file.isDirectory()) {
				FileUtil.getfiles(list, file, fileNameRegex);
			}// Endif
		}// Endfor
	}

	/**
	 * 获取指定文件的扩展名
	 *
	 * @param file
	 *            File 目标文件
	 * @return String
	 */
	public static String getExtension(File file) {
		return file == null || !file
				.isFile() ? "" : FileUtil.getExtension(file.getName());
	}

	/**
	 * 获取指定文件的扩展名
	 *
	 * @param fileName
	 *            File 目标文件名
	 * @return String
	 */
	public static String getExtension(String fileName) {
		int offset = fileName.lastIndexOf(".") + 1;
		return StringUtils.isEmpty(fileName)
				|| offset < 1 ? "" : fileName.substring(offset).toLowerCase();
	}
	/**
	 * 创建目录
	 * @param destDirName
	 * @return
	 */
	public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {
			logger.info("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {
			logger.info("创建目录" + destDirName + "成功！");
            return true;  
        } else {
			logger.info("创建目录" + destDirName + "失败！");
            return false;  
        }  
    }  

    /**
     * 通过HTTP方式获取文件
     *
     * @param strUrl
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean getRemoteFile(String strUrl, String fileName) throws IOException {
    	logger.info("-------------getRemoteFile地址："+strUrl + "-----" +fileName);
		URL url = new URL(strUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
		// http正文内，因此需要设为true, 默认情况下是false;
		conn.setDoOutput(true);
		// 设置是否从httpUrlConnection读入，默认情况下是true;
		conn.setDoInput(true);
		// Post 请求不能使用缓存
		conn.setUseCaches(false);
		conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
		conn.setRequestProperty("Accept-Charset", "UTF-8");
		// 设定请求的方法为"POST"，默认是GET
		conn.setRequestMethod("POST");
		DataInputStream input = null;
		byte[] buffer = new byte[1024];
		int count = 0;
		try (DataOutputStream output = new DataOutputStream(new FileOutputStream(fileName))) {
			input = new DataInputStream(conn.getInputStream());
			while ((count = input.read(buffer)) != -1) {
				output.write(buffer, 0, count);
			}
		} catch (Exception e) {
			logger.error("文件获取失败:", e);
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return true;
	}

    /**
     * 通过HTTP方式下载文件
     *
     * @param strUrl
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean getServletFile(HttpServletRequest request, HttpServletResponse response, String strUrl, String fileName) throws IOException {

        URL url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;
        conn.setDoOutput(true);
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        conn.setDoInput(true);
        // Post 请求不能使用缓存
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        // 设定请求的方法为"POST"，默认是GET
        conn.setRequestMethod("POST");
        DataInputStream input =null;
        ServletOutputStream output = null;
        byte[] buffer = new byte[1024];
        int count = 0;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setContentType("application/pdf;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String((fileName).getBytes("UTF-8"), "ISO8859-1"));
            input=new DataInputStream(conn.getInputStream());
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
        	if(output!=null){
                output.flush();
                output.close();
        	}
			if(input != null){
				input.close();
			}
        }
        return true;
    }

    /**
     * 通过HTTP方式获取文件
     *
     * @param strUrl
     * @param fileName
     * @return file
     * @throws IOException
     */
    public static File getFile(String strUrl, String fileName) throws IOException {

        File file = new File(fileName);
        URL url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;
        conn.setDoOutput(true);
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        conn.setDoInput(true);
        // Post 请求不能使用缓存
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        // 设定请求的方法为"POST"，默认是GET
        conn.setRequestMethod("POST");
		InputStream inputStream = null;
		try (FileOutputStream fos = new FileOutputStream(file)) {
			//得到输入流
			inputStream = conn.getInputStream();
			//获取自己数组
			byte[] getData = readInputStream(inputStream);
			//数据写入文件
			fos.write(getData);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return file;
	}

	public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//设置超时间为3秒
		conn.setConnectTimeout(5*1000);
		//防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		logger.info("============开始下载合同！=======================");
		//得到输入流
		InputStream inputStream = conn.getInputStream();
		//获取自己数组
		byte[] getData = readInputStream(inputStream);

		//文件保存位置
		File saveDir = new File(savePath);
		if(!saveDir.exists()){
			logger.info("============"+savePath+"目录不存在！开始创建=======================");
			saveDir.mkdirs();
		}else{
			logger.info("============"+savePath+"目录已存在！=======================");
		}
		File file = new File(saveDir+File.separator+fileName);
		if (file.exists()) {
			logger.info("=============" + file.getPath() + ",文件存在！------------------------");
		} else {
			logger.info("=============" + saveDir + File.separator + fileName + ",文件不存在！------------------------");
		}
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(getData);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		logger.info("info:" + url + " download success");

	}

    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    private static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }

	/**
	 * 判断文件是否存在
	 * @param filePath
	 * @return
	 */
	public static boolean judeFileExists(String filePath) {

		File file = new File(filePath);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 图片拼接
	 * @param files
	 * @param type
	 * @param targetFile
	 */
    public static void mergeImage(String[] files, int type, String targetFile) {
        int len = files.length;
        if (len < 1) {
            throw new RuntimeException("图片数量小于1");
        }
        File[] src = new File[len];
        BufferedImage[] images = new BufferedImage[len];
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            try {
                src[i] = new File(files[i]);
                images[i] = ImageIO.read(src[i]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }
        int newHeight = 0;
        int newWidth = 0;
        for (int i = 0; i < images.length; i++) {
            // 横向
            if (type == 1) {
                newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
                newWidth += images[i].getWidth();
            } else if (type == 2) {// 纵向
                newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
                newHeight += images[i].getHeight();
            }
        }
        if (type == 1 && newWidth < 1) {
            return;
        }
        if (type == 2 && newHeight < 1) {
            return;
        }

        // 生成新图片
        try {
            BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            int height_i = 0;
            int width_i = 0;
            for (int i = 0; i < images.length; i++) {
                if (type == 1) {
                    ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
                            images[i].getWidth());
                    width_i += images[i].getWidth();
                } else if (type == 2) {
                    ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
                    height_i += images[i].getHeight();
                }
            }
            //输出想要的图片
            ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




	/**
	 * 获取文件夹下的所有文件名称
	 * add by cwyang 20181030
	 * @param path
	 * @return
	 */
	public static List getFileName(String path) {
		ArrayList list = new ArrayList();
		File f = new File(path);
		if (!f.exists()) {
			list.add(path + " not exists");
			return list;
		}
		File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			if (fs.isDirectory()) {
				list.add(fs.getName() + " [目录]");
			} else {
				list.add(fs.getName());
			}
		}
		return list;
	}

}