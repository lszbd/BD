package abc.lsz.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片工具类
 * @author BD
 *
 */
public class BitmapUtil {
	/**
	 * 图片内存缓存集合
	 */
	private static Map<String, SoftReference<Bitmap>> bitmapCaches = new HashMap<String, SoftReference<Bitmap>>();
	
	/**
	 * 清空图片内存缓存
	 */
	public static void resetBitmapCache(){
		bitmapCaches.clear();
	}
	
	
	
	/**
	 * 获取图片(一级缓存)
	 * @param dirFile  : 缓存目录
	 * @param url      : 图片路径
	 */
	public static Bitmap getBitmap(File dirFile, String url) {
		System.out.println("BitmapUtil.getBitmap()");
		SoftReference<Bitmap> softReference = bitmapCaches.get(MD5.getMessageDigest(url));
		return softReference != null ? softReference.get() : getLocalBitmap(dirFile, url);
	}
	
	/**
	 * 获取本地缓存(二级缓存)
	 * @param dirFile  : 缓存目录
	 * @param url      : 路径
	 */
	public static Bitmap getLocalBitmap(File dirFile, String url) {
		
		System.out.println("BitmapUtil.getlocalBitmap()");	
		
		File file = new File(dirFile, MD5.getMessageDigest(url));
		if(file.exists()) {
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
			    bitmapCaches.put(MD5.getMessageDigest(url), new SoftReference<Bitmap>(bitmap));
			    return bitmap;
			} catch (Exception e) {
				e.printStackTrace();
				return getNetBitmap(dirFile, url);
			}finally {
				if(is != null)
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}else{
			return getNetBitmap(dirFile, url);
		}
	}
	
	/**
	 * 从网络获取图片(三级缓存)
	 * @param dirFile  : 缓存目录
	 * @param url      : 路径
	 */
	public static Bitmap getNetBitmap(final File dirFile, final String url){
		
		System.out.println("BitmapUtil.getNetBitmap()");
		
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10000); 
			conn.setReadTimeout(5000);
			
			int responseCode = conn.getResponseCode();
			if(responseCode == 200) {
				Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
			    // 添加到缓存中
				bitmapCaches.remove(MD5.getMessageDigest(url));     // 删除缓存中原有位图
			    bitmapCaches.put(MD5.getMessageDigest(url), new SoftReference<Bitmap>(bitmap));
			    // 保存到本地
			    if(dirFile != null) {
			    	if(!dirFile.exists()) dirFile.mkdirs();
			    	bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(new File(dirFile, MD5.getMessageDigest(url))));
			    }
			    return bitmap;
			}
		} catch (IOException e) {
			 e.printStackTrace();
		}finally{
			if(conn != null) conn.disconnect();     // 断开链接
		}
		 return null;
	}

    /**
     * 获得bitmap的字节大小.
     * @return : bitmap字节大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
//    
//    public static Bitmap zoomBitmap(){
//    	
//    }
    
}
