package abc.lsz.utils;

/**
 * 字符串工具类
 * @author BD
 * 
 */
public class StringUtils {
	
	/**
	 * 获取文件扩展名
	 * @param name
	 * @return
	 */
	public static String getExtensionName (String name){
		return name.substring(name.lastIndexOf(".") + 1);
	}
}
