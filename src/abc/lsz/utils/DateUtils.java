package abc.lsz.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @Author BD
 */
public class DateUtils {
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getData() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
}