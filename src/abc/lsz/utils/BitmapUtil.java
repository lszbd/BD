package abc.lsz.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.WindowManager;

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
	public static Bitmap getBitmap(Activity activity, File dirFile, String url, boolean isRequestNet) {
		System.out.println("BitmapUtil.getBitmap()");
		SoftReference<Bitmap> softReference = bitmapCaches.get(MD5.getMessageDigest(url));
		return softReference != null ? softReference.get() : getLocalBitmap(activity, dirFile, url, isRequestNet);
	}
	
	/**
	 * 获取本地缓存(二级缓存)
	 * @param dirFile  : 缓存目录
	 * @param url      : 路径
	 */
	public static Bitmap getLocalBitmap(Activity activity, File dirFile, String url, boolean isRequestNet) {
		
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
			}finally {
				if(is != null)
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		return isRequestNet ? getNetBitmap(activity, dirFile, url) : null;
	}
	
	/**
	 * 从网络获取图片(三级缓存)
	 * @param dirFile  : 缓存目录
	 * @param url      : 路径
	 */
	public static Bitmap getNetBitmap(Activity activity, final File dirFile, final String url){
//		System.out.println("BitmapUtil.getNetBitmap()");
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10000); 
			conn.setReadTimeout(5000);
			
			int responseCode = conn.getResponseCode();
			if(responseCode == 200) {
				Bitmap bitmap = activity == null ? BitmapFactory.decodeStream(conn.getInputStream())
						                         : zoomBitmap(activity, conn.getInputStream());
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
    
    /**
     * 回收Bitmap
     * @param bitmap
     */
    public static void recycledBitmap(Bitmap bitmap){
    	if(!bitmap.isRecycled()){
    		bitmap.recycle();     // 回收图片所占的内存
            System.gc();          // 提醒系统及时回收
    	}
    }
    
    /**
     * 按照屏幕比例缩放Bitmap
     * @param activity : Activity
     * @param is       : 位图字节流
     * @return         : 返回缩放后的位图
     */
    @SuppressWarnings("deprecation")
	public static Bitmap zoomBitmap(Activity activity, InputStream is){
    //  1. 获取屏幕的宽高信息
        WindowManager windowManager = activity.getWindowManager();
			int width  = windowManager.getDefaultDisplay().getWidth();
            int height = windowManager.getDefaultDisplay().getHeight();
//      	System.out.println("屏幕的宽 = " + width + "   高 = " + height);

    //  2.获取图片的宽高
        Options options = new BitmapFactory.Options();    // 解析位图的附加条件
            options.inJustDecodeBounds = true;            // 只解析位图的头文件信息(图片的附加信息)
        BitmapFactory.decodeStream(is, null, options);
        int bitmapWidth  = options.outWidth;    
        int bitmapHeight = options.outHeight;
//      	System.out.println("图片的宽 = " + bitmapWidth + "   高 = " + bitmapHeight);

    //  3.计算图片的缩放比例
        int dx = bitmapWidth  / width;
        int dy = bitmapHeight / height;

        int scale = 1;
        if(dx > dy && dy > 1){
            scale = dx;
//          System.out.println("按照水平方向绽放，缩放比例 = " + dx);
        }
        if(dy > dx && dx > 1){
            scale = dy;
//          System.out.println("按照垂直方法缩放，缩放比例 = " + dy);
        }

    //  4.返回缩放后的位图给调用者
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;   // 解析全部图片
        return BitmapFactory.decodeStream(is, null, options);

    }
    
    
    
    /**
     * 处理大图片工具方法，避免 OOM
     * @param imgPath : 图片路径
     * @return : 返回缩放后的图片
     */
    @SuppressWarnings("deprecation")
	public static Bitmap getCompressionBitmap(Activity activity, String imgPath){
    //  1. 获取屏幕的宽高信息
        WindowManager windowManager = activity.getWindowManager();
			int width  = windowManager.getDefaultDisplay().getWidth();
            int height = windowManager.getDefaultDisplay().getHeight();
//      	System.out.println("屏幕的宽 = " + width + "   高 = " + height);

    //  2.获取图片的宽高
        Options options = new BitmapFactory.Options();    // 解析位图的附加条件
            options.inJustDecodeBounds = true;            // 只解析位图的头文件信息(图片的附加信息)
        BitmapFactory.decodeFile(imgPath, options);
        int bitmapWidth  = options.outWidth;    
        int bitmapHeight = options.outHeight;
//      	System.out.println("图片的宽 = " + bitmapWidth + "   高 = " + bitmapHeight);

    //  3.计算图片的缩放比例
        int dx = bitmapWidth  / width;
        int dy = bitmapHeight / height;

        int scale = 1;
        if(dx > dy && dy > 1){
            scale = dx;
//          System.out.println("按照水平方向绽放，缩放比例 = " + dx);
        }
        if(dy > dx && dx > 1){
            scale = dy;
//          System.out.println("按照垂直方法缩放，缩放比例 = " + dy);
        }

    //  4.返回缩放后的位图给调用者
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;   // 解析全部图片
        return BitmapFactory.decodeFile(imgPath, options);
    }
}
