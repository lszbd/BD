package abc.lsz.utils;

import abc.lsz.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

/**
 * 对话框工具类
 * @author BD
 *
 */
public class DialogUtil {
	
	/**
	 * 自定义对话框
	 * @param context
	 * @param layout
	 * @return
	 */
	public static Dialog showDiyDialog(Context context, View view) {
		// 使用自定义的主题
		Dialog dialog = new Dialog(context, R.style.MyDialogTheme);
		dialog.setCancelable(false);     // 设置抢占焦点
		dialog.setContentView(view);     
		return dialog;
	}
}
