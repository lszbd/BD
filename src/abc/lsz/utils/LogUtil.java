package abc.lsz.utils;

import android.util.Log;


/**
 * 日志工具类
 * @author BD
 *
 */
public class LogUtil {
	
	/**
	 * 当前模式 : 是否是调试模式
	 */
	public static boolean debugMode = true;
	
	private static final String TAG =  "BD";
	
	
	public static void i(String msg){
		if(debugMode){
			Log.i(TAG, msg);
		}
	}
	
	public static void d(String msg){
		if(debugMode){
			Log.d(TAG, msg);
		}
	}
	
	public static void i(String tag, String msg){
		if(debugMode){
			Log.i(tag, msg);
		}
	}
	
	public static void d(String tag, String msg){
		if(debugMode){
			Log.d(tag, msg);
		}
	}
	
	
	public static void e(String tag, String msg) {
		if(debugMode){
			Log.e(tag, msg);
		}
	}
	
	
	/**
	 * 是否开启Debug模式
	 * @param debugMode
	 */
	public static void setDebugMode(boolean debugMode) {
		LogUtil.debugMode = debugMode;
	}
}
