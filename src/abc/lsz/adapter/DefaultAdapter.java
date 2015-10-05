package abc.lsz.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class DefaultAdapter<T> extends BaseAdapter{

	protected List<T> datas;
	
	public DefaultAdapter(List<T> datas) {
		this.datas = datas;
	}
	
	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas != null ? datas.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return datas != null ? datas.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		@SuppressWarnings("unchecked")
		BaseHolder<T> holder = convertView == null ? this.getHolder(datas.get(position)) : (BaseHolder<T>)convertView.getTag();
		return holder.getContentView();
	}
	
	protected abstract BaseHolder<T> getHolder(T t);
	
}
