package abc.lsz.base;

import abc.lsz.interfaces.FragmentCallback;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements Handler.Callback{

	/**
	 * 该Fragment绑定的Activity
	 */
	public Activity activity;
	
	/**
	 * Fragment显示视图
	 */
	protected View rootView;
	
	/**
	 * 消息处理器
	 */
	public static Handler handler;
	
	/**
	 * Fragment回调接口
	 */
	protected FragmentCallback callback;
	
	
	/**
	 * 当前Fragment是否已经退出标记
	 */
	public static boolean isFragmentExit = false;
	
	
	/**
	 * 当前类TAG(类名)
	 */
	public static String TAG;

	@Override
	public final void onAttach(Activity activity) {
		if (activity instanceof FragmentCallback) {
			this.activity = activity;
		} else {
			new Exception("该Activity必须实现Fragment的回调接口 : FragmentCallback");
		}
		super.onAttach(activity);
		handler = new Handler(this);
		TAG     = this.getClass().getSimpleName();
		
	}

	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		rootView = initView(inflater, savedInstanceState);
		initData(savedInstanceState);
		initListener();
		return rootView;
	}

	@Override
	public void onDetach() {
		isFragmentExit = true;
		super.onDetach();
	}
	
	/**
	 * 初始化视图
	 * @param inflater
	 * @param savedInstanceState
	 * @return
	 */
	public abstract View initView(LayoutInflater inflater, Bundle savedInstanceState);

	
	/**
	 * 初始化数据
	 * @param savedInstanceState
	 * @param view
	 */
	public void initData(Bundle savedInstanceState, View... view) {

	}

	/**
	 * 初始化监听
	 * @param view
	 */
	public void initListener(View... view) {

	}
	
	/**
	 * 主线程执行方法(用于更新UI)
	 */
	public void execMainThreadTask(Runnable runnable) {
		if(Thread.currentThread() == Looper.getMainLooper().getThread()) {
			throw new RuntimeException(TAG + " execMainThreadTask 方法只能在子线程中执行");
		}
		activity.runOnUiThread(runnable);
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends View> T findView(int id){
		return this.rootView != null ? (T)this.rootView.findViewById(id) : null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends View> T findView(String tag){
		return this.rootView != null ? (T)this.rootView.findViewWithTag(tag) : null;
	}
}
