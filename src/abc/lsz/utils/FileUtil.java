package abc.lsz.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;
import android.os.StatFs;

/**
 * IO流工具类
 * @author BD
 * 	获取存储信息可能需要这些权限
 	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>  
	<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>  
	<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>  
 */
public class FileUtil {
    
    /**
     * 判断外部存储是否存在和挂载
     * @return
     */
    public static boolean isExternalMounted(){
    	return Environment.MEDIA_REMOVED.equals(Environment.getExternalStorageState()) || 
    		   Environment.MEDIA_UNMOUNTED.equals(Environment.getExternalStorageState()) ? false : true;
    }
    
	/**
	 * 外部存储SDCard是否可读写
	 * @return
	 */
    public static boolean isExternalStorageRWable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
 
    /**
     * 获取指定路径下可用空间
     * @param path 文件路径
     * @return 可用空间
     */
    @SuppressWarnings("deprecation")
	public static long getUsableSpace(File path)
    {
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }
    
    /**
     * 递归创建目录
     * @param file
     * @return 是否创建成功
     */
    public static boolean createDir(File file) {
    	return file.exists() ? true: file.mkdirs();
    }
    
    
    /**
     * 读取文件到内存
     * @param file : 文件路径
     * @return 
     */
    public static byte[] readFile(File file){
    	
    	FileInputStream in = null;
    	ByteArrayOutputStream baos = null;
    	try {
			in   = new FileInputStream(file);
			baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024<<3];
			while((len = in.read(buffer)) != -1){
				baos.write(buffer, 0, len);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(in != null) in.close();
				if(baos != null) baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
    }
    
    /**
     * 将输入流转换成字符串(utf-8)
     * @param is      : 输入流
     * @param charset : 数组原有编码，不指定则默认为UTF-8解码
     * @return utf-8 编码的字符串
     */
    public static String inputToString(InputStream is, String charset){
    	if(is == null){
    		return null;
    	}
    	
    	ByteArrayOutputStream baos = null;
    	try {
			baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			return charset == null ? baos.toString() : new String(baos.toString().getBytes(charset), "UTF-8"); 
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
				if(baos != null) baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	return null;
    }
    
    /**
     * 将内存中的字节数组输出到文件
     * @param buf  : 输出的字节数组
     * @param file : 输出的位置
     */
    public static void byteOutFile(byte[] buf, File file){
    	FileOutputStream out  = null;
    	try {
			out  = new FileOutputStream(file);
			out.write(buf, 0, buf.length);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    
    /**
     * 将输入流输出到指定路径
     * @param is   : 输入流
     * @param file : 输出路径
     */
    public static void outputFile(InputStream is, File file) {
    	if(is == null) {
    		throw new RuntimeException("is输入流为空");
    	}
    	FileOutputStream out = null;
    	try {
			out = new FileOutputStream(file);
			int len = 0;
			byte[] buffer = new byte[1024 << 3];
			while((len = is.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(is != null) is.close();
				if(out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    public static void deleteDir(File dirFile){
    	if(dirFile == null || !dirFile.isDirectory()){
    		return;
    	}
    }
}
