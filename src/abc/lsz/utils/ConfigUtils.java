package abc.lsz.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 偏好设置工具类
 * @author BD
 * 缓存类, 所有的数据都是采用SharedPreferences方式存储和获取.
 */
public class ConfigUtils {
	
	private ConfigUtils() { }
	
	private static SharedPreferences mSharedPreferences;
	
	/**
	 * 缓存文件名
	 */
	private static final String CONFIGFILENAME = "Config";
	
	/**
	 * @param context
	 * @param key 要取的数据的键
	 * @param defValue 缺省值
	 * @return
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(CONFIGFILENAME, Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getBoolean(key, defValue);
	}
	
	/**
	 * 存储一个boolean类型数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putBoolean(Context context, String key, boolean value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(CONFIGFILENAME, Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}
	
	/**
	 * 存储一个String类型的数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putString(Context context, String key, String value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(CONFIGFILENAME, Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putString(key, value).commit();
	}
	
	/**
	 * 根据key取出一个String类型的值
	 * @param context
	 * @param key 
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(CONFIGFILENAME, Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getString(key, defValue);
	}
}
