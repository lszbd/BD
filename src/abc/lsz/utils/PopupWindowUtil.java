package abc.lsz.utils;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 弹出窗口工具类
 * @author BD
 *
 */
public class PopupWindowUtil {

	/**
	 * 显示对话框
	 * @param rootView  : 显示的内容
	 * @param width     : 窗口宽
	 * @param height    : 窗口高
	 * @param childView : 指定的控件
	 * @param x         : X 轴的偏移量
	 * @param y         : Y 轴的偏移量
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static PopupWindow showPopupWindow(View rootView, View childView, int x, int y) {
		rootView.measure(0, 0);          // 手动测量后, getMeasuredWidth()和getMeasuredHeight()才会起效
		PopupWindow popupWindow = new PopupWindow(rootView, rootView.getMeasuredWidth(), rootView.getMeasuredHeight());
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);             // 失去焦点隐藏S
		popupWindow.setOutsideTouchable(true);      // 支持返回键删除
		popupWindow.showAsDropDown(childView, x, y);
		return popupWindow;
	}
}
