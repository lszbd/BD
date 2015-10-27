package abc.lsz.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ViewHolder {

	/** 显示的列表项视图缓冲数组 */
    private final SparseArray<View> views;

    /** 上下文 */
    private final Context context;

    /** 当前列表项显示视图 */
    private View convertView;

    protected ViewHolder(Context context, ViewGroup parent, int layoutId) {
        this.context = context;
        this.views   = new SparseArray<View>();
        convertView  = LayoutInflater.from(context).inflate(layoutId, parent, false);
        convertView.setTag(this);       // 设置 Tag
    }

    /**
     * 获取 ViewHolder 
     * @param <pre>context     : 上下文
     * @param <pre>convertView : 缓存视图
     * @param <pre>parent      : 父控件
     * @param <pre>layoutId    : Item布局文件
     * @return
     */
    public static ViewHolder getHolder(Context context, View convertView, ViewGroup parent, int layoutId) {
         return convertView == null ? new ViewHolder(context, parent, layoutId) 
         							: (ViewHolder) convertView.getTag();
    }
    
    /**
     * 获取 子控件
     * @param viewId : 子控件ID
     * @return : 返回子控件,没有则返回空
     */
    public <T extends View> T getView(int viewId) {
        return retrieveView(viewId);
    }

    /**
     * 获取当前列表项显示的内容视图
     * @return
     */
    public View getContentView() {
        return convertView;
    }

    /**
     * 检索指定ID的View
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
    
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = retrieveView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = retrieveView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }
    
    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = retrieveView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
    
    public ViewHolder setOnItemClickListener(int viewId,AdapterView.OnItemClickListener listener) {
        @SuppressWarnings("rawtypes")
		AdapterView view = retrieveView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }
    
    public ViewHolder setOnItemLongClickListener(int viewId,AdapterView.OnItemLongClickListener listener) {
        @SuppressWarnings("rawtypes")
		AdapterView view = retrieveView(viewId);
        view.setOnItemLongClickListener(listener);
        return this;
    }
    

    public ViewHolder setText(int viewId, String value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }
    
    public ViewHolder setTextSize(int viewId, int testSize) {
        TextView view = retrieveView(viewId);
        view.setTextSize(testSize);
        return this;
    }
    
    public ViewHolder setTextColor(int viewId, int textColor) {
        TextView view = retrieveView(viewId);
        view.setTextColor(textColor);
        return this;
    }
    
    public ViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = retrieveView(viewId);
        view.setTextColor(context.getResources().getColor(textColorRes));
        return this;
    }
    

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = retrieveView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = retrieveView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = retrieveView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = retrieveView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }
    
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = retrieveView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }
    
    

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        return this;
    }

    
    public ViewHolder setTag(int viewId, Object tag) {
        View view = retrieveView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        View view = retrieveView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) retrieveView(viewId);
        view.setChecked(checked);
        return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ViewHolder setAdapter(int viewId, Adapter adapter) {
		AdapterView view = retrieveView(viewId);
        view.setAdapter(adapter);
        return this;
    }
}
