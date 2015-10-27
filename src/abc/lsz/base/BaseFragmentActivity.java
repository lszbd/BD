package abc.lsz.base;

import abc.lsz.interfaces.FragmentCallback;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

public abstract class BaseFragmentActivity extends FragmentActivity implements Handler.Callback, FragmentCallback{
	
	/**
	 * 当前Activity是否已经退出
	 */
	public static boolean isExit;
	
	/**
	 * 消息处理器
	 */
	public static Handler handler;
	
	/**
	 * 当前Activity显示的视图
	 */
	public View rootView;
	
	/**
	 * 当前类TAG(类名)
	 */
	protected String TAG;

	
	
	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);      // 去除标题
		init();                                              // 初始化数据
		rootView = initView(savedInstanceState);             // 初始化视图
		setContentView(rootView);                            // 设置视图
		initData(savedInstanceState);                        // 初始化数据
		initListener();    									 // 初始化监听
		this.getSupportFragmentManager();
	}

	
	/**
	 * 初始化方法
	 */
	protected final void init() {
		handler = new Handler(this);                         // 创建消息处理器
		TAG     = this.getClass().getSimpleName();           // 获取当前类名当前类打印日志Tag
		ActivityManager.addActivity(this);                   // 添加当前类到Activity管理器中
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		isExit = true;                         // 标识当前 Activity 已经退出
		ActivityManager.removeActivity(this);  // 从 Activity 管理器中移除
	}
	
	/**
	 * 初始化Activity视图
	 * @return 显示的视图
	 */
	public abstract View initView(Bundle savedInstanceState);
	
	
	/**
	 * 初始化Activity数据
	 * @param savedInstanceState 上一个该实例的状态数据
	 */
	public void initData(Bundle savedInstanceState, View ...view) {
		
	}
	
	/**
	 * 初始化监听
	 * @param v  需要设置监听的控件
	 */
	public void initListener(View...view){
		
	}
	
	/**
	 * 启动该Activity的方法
	 * @param context : 上下文
	 * @param data    : 启动该Activity所需参数
	 */
	public void actionStart(Context context, Object...data) {
		
	};
	
	/**
	 * 主线程执行方法(用于更新UI)
	 */
	public void execMainThreadTask(Runnable runnable) {
		if(Thread.currentThread() == Looper.getMainLooper().getThread()) {
			throw new RuntimeException(TAG + " execMainThreadTask 方法只能在子线程中执行");
		}
		this.runOnUiThread(runnable);
	}
	
	/**
	 * 处理消息队列消息
	 */
	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends View> T findView(int id){
		return (T) rootView.findViewById(id);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends View> T findView(String tag){
		return (T) this.rootView.findViewWithTag(tag);
	}
}
