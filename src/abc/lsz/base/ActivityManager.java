package abc.lsz.base;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * Activity管理器
 * @author BD
 */
public class ActivityManager {

	public static List<SoftReference<Activity>> activitys = new ArrayList<SoftReference<Activity>>();
	
	/**
	 * 添加Activity
	 * @param activity
	 * @return
	 */
	public static boolean addActivity(Activity activity){
		return activitys.add(new SoftReference<Activity>(activity));
	}
	
	/**
	 * 删除Activity
	 * @param activity
	 * @return
	 */
	public static boolean removeActivity(Activity activity){
		return activitys.remove(activity);
	}
	
	/**
	 * 删除所有Activity, 退出程序
	 */
	public static void finishAllActivity(){
		for(SoftReference<Activity> softReference : activitys){
			softReference.get().finish();
		}
	}
}
