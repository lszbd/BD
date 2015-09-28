package abc.lsz.utils;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Json工具类
 * @author BD
 *
 */
public class JsonUtils {
	
	private JsonUtils() {  }
	
	private static final Map<String, SoftReference<String>> cacheMap = new HashMap<String, SoftReference<String>>();
	
	/**
	 * 清空内存中的缓存
	 */
	public static void clearMemoryCache() {
		cacheMap.clear();
	}
	
	/**
	 * 获取缓存中的 JSON 数据
	 * @param dirFile      : 缓存目录
	 * @param url          : JSON 数据的 url
	 * @param isRequestNet : 是否请求网络数据
	 * @return 获取到的JSON数据或null
	 */
	public static String getJSON(File dirFile, String url, boolean isRequestNet) {
		System.out.println("JsonUtils.getJSON()");
		SoftReference<String> softReference = cacheMap.get(MD5.getMessageDigest(url));
		return softReference == null ? getLocalJSON(dirFile, url, isRequestNet) : softReference.get();
	}

	/**
	 * 加载本地的JOSN数据
	 * @param dirFile      : 缓存目录
	 * @param url          : JSON 数据的 url
	 * @param isRequestNet : 是否请求网络数据
	 * @return 获取到的JSON数据或null
	 */
	public static String getLocalJSON(File dirFile, String url, boolean isRequestNet) {
		System.out.println("JsonUtils.getLocalJSON()");
		File file = new File(dirFile, MD5.getMessageDigest(url));
		if(file.exists()){
			String json = new String(FileUtil.readFile(file));
			cacheMap.put(MD5.getMessageDigest(url), new SoftReference<String>(json));
			return json;
		}else{
			return isRequestNet ?  getNetworkJSON(dirFile, url) : null;
		}
	}

	/**
	 * 获取网络上的 JSON 数据
	 * @param dirFile : 缓存目录
	 * @param url     : JSON 数据的 url
	 * @return 获取到的JSON数据或null
	 */
	public static String getNetworkJSON(File dirFile, String url) {
		System.out.println("JsonUtils.getNetworkJSON()");
		final String json = InternetUtil.get(url, null);
		if(dirFile != null && json != null) {
			if(!dirFile.exists()) dirFile.mkdirs();
			FileUtil.byteOutFile(json.getBytes(), new File(dirFile, MD5.getMessageDigest(url)));
		}
		return json;
	}
}
