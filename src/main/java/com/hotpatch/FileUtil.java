package com.hotpatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 读取指定目录所有文件
 * 
 * @author ksfzhaohui
 * 
 */
public class FileUtil {
	private final static Logger logger = Logger.getLogger(FileUtil.class);

	/**
	 * 读取指定目录中的所有文件
	 * 
	 * @param filepath
	 * @return
	 */
	public static List<File> readfile(String filepath) {
		List<File> list = new ArrayList<File>();
		File file = new File(filepath);
		if (!file.isDirectory()) {
			list.add(file);
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + File.separator
						+ filelist[i]);
				if (!readfile.isDirectory()) {
					list.add(readfile);
				} else if (readfile.isDirectory()) {
					list.addAll(readfile(filepath + File.separator
							+ filelist[i]));
				}
			}
		}
		return list;
	}

	/**
	 * 读取文件的二进制数据
	 * 
	 * @param fileName
	 * @return
	 */
	public static byte[] getBytesFromFile(String fileName) {
		InputStream is = null;
		try {
			File file = new File(fileName);
			is = new FileInputStream(file);
			long length = file.length();
			byte[] bytes = new byte[(int) length];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}
			return bytes;
		} catch (Exception e) {
			logger.error("read file error", e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 删除指定目录的所有文件
	 * 
	 * @param path
	 */
	public static void deleteAllFilesOfDir(File path) {
		if (!path.exists())
			return;
		if (path.isFile()) {
			path.delete();
			return;
		}
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteAllFilesOfDir(files[i]);
		}
		path.delete();
	}
}
