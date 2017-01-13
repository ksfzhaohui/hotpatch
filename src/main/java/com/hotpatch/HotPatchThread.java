package com.hotpatch;

import java.io.File;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 热替换线程
 * 
 * @author ksfzhaohui
 * 
 */
public class HotPatchThread implements Runnable {

	private final static Logger logger = Logger.getLogger(HotPatchThread.class);
	private Instrumentation inst;

	public HotPatchThread(Instrumentation inst) {
		this.inst = inst;
	}

	public void run() {
		try {
			List<File> list = FileUtil.readfile(HotPatch.ROOT_PATH);
			if (list != null && list.size() > 0) {
				for (File file : list) {
					Class<?> clazz = Class.forName(getPackageName(file));
					byte[] array = FileUtil.getBytesFromFile(file.getPath());
					ClassDefinition def = new ClassDefinition(clazz, array);
					inst.redefineClasses(def);

					file.delete();
					logger.info("hotpatch " + file.getPath() + " success");
				}
			}
		} catch (Exception e) {
			logger.error("hotpatching error", e);
		}
	}

	/**
	 * 获取类的包名+类名
	 * 
	 * @param file
	 * @return
	 */
	private String getPackageName(File file) {
		String path = file.getPath();
		int index = path.indexOf(HotPatch.ROOT_PATH);
		path = path.substring(index + HotPatch.ROOT_PATH.length() + 1);
		path = path.split("\\.")[0];
		path = path.replaceAll("\\" + File.separator, ".");
		return path;
	}
}
