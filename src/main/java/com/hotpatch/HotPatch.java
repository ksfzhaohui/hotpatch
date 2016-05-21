package com.hotpatch;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 热替换
 * 
 * @author ksfzhaohui
 * 
 */
public class HotPatch {

	private final static Logger logger = Logger.getLogger(HotPatch.class);

	public static void premain(String agentArgs, Instrumentation inst) {
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
				new HotPatchThread(inst), 5, 5, TimeUnit.SECONDS);
		logger.info("hotPatch starting...");
	}
}
