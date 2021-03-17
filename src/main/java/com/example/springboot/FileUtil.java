package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;


public class FileUtil {


	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	


	/**
	 * 写出文本文件
	 * 
	 * @param content
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static void writeTxtFile(String content, String fileName) throws Exception {
		FileOutputStream o = null;
		o = new FileOutputStream(new File(fileName));
		o.write(content.getBytes("UTF-8"));
		o.close();
	}

	/**
	 * 文件重命名
	 * 
	 * @param path
	 *            文件目录
	 * @param oldname
	 *            原来的文件名
	 * @param newname
	 *            新文件名
	 */
	public static void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (!oldfile.exists()) {
				logger.info(oldfile + "不存在");
				return;// 重命名文件不存在
			}
			if (newfile.exists()) {// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				newfile.delete();
				logger.info(path + newname + "已经存在, 已删除该文件!");
			}
			oldfile.renameTo(newfile);
		} else {
			logger.info("新文件名和旧文件名相同!");
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {// 判断文件是否存在
			if (file.isFile()) {// 判断是否是文件
				file.delete();// 删除文件
				logger.info("删除文件成功:" + file.getPath());
			} else if (file.isDirectory()) {// 否则如果它是一个目录
				File[] files = file.listFiles();// 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
					deleteFile(files[i].getPath());// 把每个文件用这个方法进行迭代
				}
				file.delete();// 删除文件夹
				logger.info("删除文件成功:" + file.getPath());
			}
		} else {
			logger.info("所删除的文件不存在:" + path);
		}
	}

	public static void deleteFiles(String[] files) {
		if (files != null && files.length > 0) {
			for (String str : files) {
				if (StringUtils.isEmpty(str)) {
					File file = new File(str);
					if (file.exists()) {
						file.delete();
						logger.info("Excel删除成功:" + str);
					} else {
						logger.info(str + "不存在");
					}
				}
			}
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean exists(String path) {
		if (StringUtils.isEmpty(path)) {
			File file = new File(path);
			if (StringUtils.isEmpty(file)) {
				if (file.exists()) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * 如果File不存在创建,
	 * 	如果file是目录，递归创建
	 *  如果file是文件，先创建目录结构，再创建文件
	 * @param file
	 * @throws IOException
	 */
	public static File createFile(File file) throws IOException {
		if(!file.exists()) {
			if(file.isDirectory()) {
				file.mkdirs();
			} else {
				File parent = file.getParentFile();
				parent.mkdirs();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
				file.createNewFile();
			}
		}
		return file;
	}
	
	public static File createFile(String fileStr) throws IOException {
		return createFile(new File(fileStr));
	}

	public static BufferedReader getFileBufferReader(String file) throws FileNotFoundException {
		return new BufferedReader(
				new InputStreamReader(new FileInputStream(file)));
	}
	
	public static BufferedReader getFileBufferReader(File file) throws FileNotFoundException {
		return new BufferedReader(
				new InputStreamReader(new FileInputStream(file)));
	}
	
	public static PrintWriter getPrintWriter(File file) throws FileNotFoundException, IOException {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
		return pw;
	}
	
	public static PrintWriter getPrintWriter(String file) throws FileNotFoundException, IOException {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(file)),"utf-8"));
		return pw;
	}
	
	public static PrintWriter getPrintWriter(String file, String encoding) 
		throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(file)), encoding));
		return pw;
	}
	
	public static File saveFile(InputStream is, String targetFile, int bufferSize) 
		throws Exception {
		File f = createFile(targetFile);
		OutputStream os = new FileOutputStream(f);
		byte[] b = new byte[bufferSize];
		
		while(is.read(b) != -1) {
			os.write(b);
		}
		os.flush();
		os.close();
		return f;
	}
	public static File saveFile(File sourceFile, String targetFile, int bufferSize) 
		throws Exception {
		File f = new File(targetFile);
		OutputStream os = new FileOutputStream(f);
		byte[] b = new byte[bufferSize];
		FileInputStream is = new FileInputStream(sourceFile);
		while(is.read(b) != -1) {
			os.write(b);
		}
		os.flush();
		os.close();
		return f;
}
	
	
	
	public static String readFileToString(String file) {
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader reader = getFileBufferReader(file);
			String temp = reader.readLine();
			while(temp != null) {
				buffer.append(temp).append("\n");
				temp = reader.readLine();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	public static void writeStringToFile(String text, File file) throws IOException {
		PrintWriter pw = getPrintWriter(file);
		pw.write(text);
		pw.flush();
		pw.close();
	}
	
	public static void main(String[] args) throws Exception {
		//testGetFileBufferReader();
		//testReadFileToString();
//		List<String> a = null;
//		for(int i=0; i<a.size(); i++) {
//			System.out.println(a.get(i));
//		}
		deleteDirect(new File("C:/Documents and Settings/Ivan0513/桌面/Log/2010-04/复件 7个表"));
		
	}
	
	public static void testReadFileToString() {
		System.out.println(readFileToString("./sourceFolder/baoshengSalary/salesReward.sql"));
	}
	
	public static void testGetFileBufferReader() {
		try {
			//File file = new File("");
			BufferedReader br = getFileBufferReader("./sourceFolder/baoshengSalary/salesReward.sql");
			String line = br.readLine();
			while(line != null) {
				System.out.println(line);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	//目录的文件、子目录的文件路径
	//可能栈溢出
	public static List getFileList(String directoryPath, List container)
		throws Exception {
		if(container == null) {
			container = new LinkedList();
		}
		File directory = new File(directoryPath);
		if(directory.exists()) {
			if(directory.isFile()) {
				container.add(directory.getAbsolutePath());
			} else {
				File[] files = directory.listFiles();
				for(int i=0; i<files.length; i++) {
					if(files[i].isFile()) {
						container.add(files[i].getAbsolutePath());
					} else {
						container.addAll(getFileList(files[i].getAbsolutePath(), container));
					}
				}
			}
		}
		return container;
	}
	
	public static List getFileList(File directory, List container)
		throws Exception {
		if(container == null) {
			container = new LinkedList();
		}
		if(directory.exists()) {
			if(directory.isFile()) {
				container.add(directory.getAbsolutePath());
			} else {
				File[] files = directory.listFiles();
				for(int i=0; i<files.length; i++) {
					if(files[i].isFile()) {
						container.add(files[i].getAbsolutePath());
					} else {
						container.addAll(getFileList(files[i], container));
					}
				}
			}
		}
		return container;
	}
	
	
	//目录的子目录，子目录的子目录
	public static List getSubDirtList(File directory, List container)
		throws Exception {
		if(container == null) {
			container = new LinkedList();
		}
		if(directory.exists()) {
			File[] files = directory.listFiles();
			for(int i=0; i<files.length; i++) {
				//System.out.println(files[i].getAbsolutePath());
				if(files[i].isDirectory()) {
					container.add(files[i].getAbsolutePath());
					container.addAll(getSubDirtList(files[i], container));
				}
			}
		}
		return container;
	}
	
	/**
	 * 删除目录下的所有文件、子目录、子目录文件
	 * @param directory
	 * @throws Exception 
	 */
	public static void deleteDirectoryContent(File directory) throws Exception {
		List deleteList = getFileList(directory, null);
		for(int i=0; i<deleteList.size(); i++) {
			File file = new File((String)deleteList.get(i));
			file.delete();
		}
		List deleteDirtList = getSubDirtList(directory, null);
		for(int i=0; i<deleteDirtList.size(); i++) {
			File file = new File((String)deleteDirtList.get(i));
			file.delete();
		}
	}
	
	/***
	 * 删除目录、目录下的所有文件、子目录、子目录文件
	 */
	public static void deleteDirect(File direct) throws Exception {
		deleteDirectoryContent(direct);
		direct.delete();
	}
	
	/**
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 */
	public static Properties getProperties(String propertiesPath) 
		throws FileNotFoundException, IOException {
		Properties p = new Properties();
		FileInputStream is = new FileInputStream(propertiesPath);
		p.load(is);
		is.close();
		return p;
	}
	/**
	 * 获得项目的根目录到      ias-web
	 * @return
	 */
	public static String getWebPath(){
		String filePath =  new File(FileUtil.class.getClassLoader().getResource("").getPath()).getParentFile().getParentFile() + "/";
		return filePath;
	}

	/**
	 * @throws Exception 
	 * @描述:
	 * @方法名: generateHtml
	 * @param htmlPlatform
	 * @return
	 * @返回类型 String
	 * @创建人 weiqh	@创建时间 2018年1月30日 下午4:19:12
	 * @修改人 weiqh	@修改时间 2018年1月30日 下午4:19:12
	 * @修改备注
	 * @since
	 * @throws
	 */
	public static String generateHtml(String rootPath, String htmlPlatform, String content) throws Exception {
		Date date = new Date();// 时间用于创建文件夹
		String path = htmlPlatform + DateUtail.getDateStr(date, "yyyy") + "/" + DateUtail.getDateStr(date, "MM") + "/" + DateUtail.getDateStr(date, "dd") + "/";// 用于存库
		// 文件名字
		String fiername = UUID.randomUUID().toString().replaceAll("-", "") + ".html";
		String dirs = path + fiername;

		File f = new File(rootPath + path);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(rootPath + dirs);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		osw.write(htmlhead(content));
		osw.close();
		String htmlpath = dirs;
		logger.info("保存路径-->" + htmlpath);
		return htmlpath;
	}
	
	/**
	 * 生成html头与尾
	 * 
	 * @throws Exception
	 */
	private static String htmlhead(String content) throws Exception {
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE html>");
		html.append("<html><head>");
		html
				.append("<meta http-equiv=\"content-type\" content=\"text/html\"; charset=\"UTF-8\" />");
		html.append("</head><body>");
		html.append(content);
		html.append("</body></html>");
		return html.toString();
	}
	
	/**
	 * 获取html文件内容
	 * 
	 * @param htmlpath
	 */
	public static String readHtml(String htmlpath, String urlaa, String htmlPlatform)
			throws Exception {
		String dirs = urlaa;
		int index = htmlpath.indexOf(htmlPlatform.substring(1, htmlPlatform.length()-1));
		if (index > 0) {
			String filePath = htmlpath.substring(htmlpath.indexOf(htmlPlatform), htmlpath.length());
			StringBuffer htmlBuffer = new StringBuffer();
			File file = new File(dirs + filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), "UTF-8");// 考虑到汉子编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					htmlBuffer.append(lineTxt);
				}
				read.close();
			}
			return htmlBuffer.toString();
		}
		return "";
	}

}
