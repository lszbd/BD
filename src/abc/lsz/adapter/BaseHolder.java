package abc.lsz.adapter;

import android.view.View;

public abstract class BaseHolder<T> {
	
	private View contentView;

	protected T data;
	
	public BaseHolder(T data) {
		this.data = data;
		contentView = initView();
		contentView.setTag(this);
	}

	/**
	 * 初始化当前列表项视图
	 * @return
	 */
	public abstract View initView();	
	
	/**
	 * 获取当前列表项视图
	 * @return
	 */
	public View getContentView() {
		return contentView;
	}
}
