package abc.lsz.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

/**
 * 网络访问工具类
 * @author BD
 * <p>
 * 注意所需权限  :
 * 	  <uses-permission android:name="android.permission.INTERNET" />
 * </p> 
 */
public class InternetUtil {
	
	/**
     * 检测网络是否可用
     * @return
     */
    public static boolean isNetworkConnected(Context context, String msg) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        boolean isNetworkConn = ni != null && ni.isConnectedOrConnecting();
        if(msg != null && !isNetworkConn) {
        	ToastUtil.shortToast(context, msg);
        }
        return isNetworkConn;
    }
    
    /**
     * 判断网络类型
     * @param context
     * @return : 0 没有网络, 1 WIFI网络, 2 WAP网络, 3 NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }        
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if(!TextUtils.isEmpty(extraInfo)){
                if (extraInfo.toLowerCase(Locale.getDefault()).equals("cmnet")) {
                    netType = 0x03;
                } else {
                    netType = 0x02;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 0x01;
        }
        return netType;
    }
    
    
    /** 
     * 使用 post 方式提交请求
     * @param url    : url
     * @param data   : 数据
     * @param params : 请求头字段
     * @return
     * @throws RuntimeException 
     */
    public static String post(String url, String data, Map<String, String> params) throws RuntimeException{
    	HttpURLConnection conn = null;
    	int responseCode = -1;
    	try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if(params != null && params.size() > 0){
				for(Map.Entry<String, String> entry : params.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			conn.connect();
			
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			
			responseCode = conn.getResponseCode();
			if(responseCode == 200) {
				return FileUtil.inputToString(conn.getInputStream(), null);
			}else{
				LogUtil.e("Network", "网络访问失败 responseCode = " + responseCode);
//				throw new RuntimeException("网络访问失败 responseCode = " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("网络访问失败 responseCode = " + responseCode + " msg = " + e.getMessage());
		}finally{
			if(conn != null) conn.disconnect(); 
		}
    	return null;
    }
    
    
    /**
     * 使用 get 方式提交请求
     * @param url   : url
     * @param data  : 数据, Values&Values&Values的格式
     * @return
     */
    public static String get(String url, Map<String, String> params) {
    	HttpURLConnection conn = null;
    	int responseCode = -1;
    	try {
    		if(params != null && params.size() > 0) {
    			StringBuilder sb = new StringBuilder().append(url);
    			if(!url.contains("?")){
    				sb.append("?");
    			}else if(!url.endsWith("&")){
    				sb.append("&");
    			}
    			for(Map.Entry<String, String> entry : params.entrySet()) {
    				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}
    			sb.deleteCharAt(sb.length() -1);   // 删除最后一个字符 &
    			url = sb.toString();
    			LogUtil.e("Get", "url = " + url);
    		}
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			
			responseCode = conn.getResponseCode();
			if(responseCode == 200){
				return FileUtil.inputToString(conn.getInputStream(), null);
			}else{
				LogUtil.e("Network", "网络访问失败 responseCode = " + responseCode);
//				throw new RuntimeException("网络访问失败 responseCode = " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
//			throw new RuntimeException("网络访问失败 responseCode = " + responseCode + " msg = " + e.getMessage());
		}finally{
			if(conn != null) conn.disconnect();
		}
    	return null;
    }
}
