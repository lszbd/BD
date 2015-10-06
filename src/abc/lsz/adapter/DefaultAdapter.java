package abc.lsz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class DefaultAdapter<T> extends BaseAdapter {
	
	/**
	 * 适配器显示的数据
	 */
	protected List<T> adapterDatas;
	
	/**
	 * 上下文
	 */
	protected Context context;
	
	/**
	 * Item 布局
	 */
	public int itemLayoutId;
	
	@SuppressWarnings("unused")
	private DefaultAdapter() {
		
	};
	
	/**
	 * 构造函数
	 * @param <pre>context      : 上下文</pre>
	 * @param <pre>datas        : 适配器的数据</pre>
	 * @param <pre>itemLayoutId : 列表项布局文件ID</pre>
	 */
	public DefaultAdapter(Context context, List<T> datas, int itemLayoutId) {
		this.context      = context;
		this.itemLayoutId = itemLayoutId;
		this.adapterDatas = datas;
	}
	
	public List<T> getDatas() {
		return adapterDatas;
	}

	public void setDatas(List<T> datas) {
		this.adapterDatas = datas;
	}

	@Override
	public int getCount() {
		return adapterDatas != null ? adapterDatas.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return adapterDatas != null ? adapterDatas.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder = ViewHolder.getHolder(context, convertView, parent, itemLayoutId);
		this.setItemData(holder, adapterDatas, position);
		return holder.getContentView();
	}
	
	/**
	 * 设置 Item 数据
	 * @param <pre>holder        : ViewHelper</pre>
	 * @param <pre>adapterDatas  : Adapter显示的数据</pre>
	 * @param <pre>position      : 当前位置</pre>
	 */
	public abstract void setItemData(ViewHolder holder, List<T> adapterDatas, int position);
}
