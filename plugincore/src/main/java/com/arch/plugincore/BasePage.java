package com.arch.plugincore;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * Created by wurongqiu on 2018/5/10.
 */

public abstract class BasePage {
    private final static String TASK_NAME = "asyncTask";

    /** 当前页面上下文背景 **/
    protected Context mContext;

    /** 当前页面布局资源Id **/
    protected int mLayoutId;

    /** 页面的真正的内容视图(除开了标题栏视图的)**/
    protected View mContentView;

//    /** 当前页面模板 **/
//    protected BaseTemplate mTemplate;

    /** 当前界面是否已经显示 (调用onResume） 2013-04-27 应maxhe 需求添加 by williamlin**/
    private boolean mViewResume = false;

    /** 当前界面是否已经销毁 **/
    private boolean mViewDestroy = false;

    /** 页面异步任务流程处理，如数据加载，UI刷新等 **/
    private Handler mHandler;

    /**
     * 构造
     * @param context
     * 		  上下文
     */
    public BasePage(Context context){
        mContext = context;

    }

    /**
     * 构造
     * @param context
     *        上下文
     * @param layoutId
     *        定制xml布局形式的view，与非xml布局形式getCustomView()对应，优先选择layoutID形式
     */
    public BasePage(Context context, int layoutId){
        mContext = context;
        mLayoutId = layoutId;
    }


    /**
     * 定制非xml布局形式的view，与构造函数 resLayoutID xml布局相对应，优先选择resLayoutID形式
     * 用于代码实现布局view的情况
     * @return
     */
    protected View createContentView(){
        return null;
    }


    /**
     * 初始化完成视图模版创建工作
     * 由BaseActivity调用
     */
    public final void frameInit(){
//        mTemplate = createTemplate();
//        mTemplate.setmDrawCallBackListener(this);
        if (mLayoutId > 0){	// 外部传入自定义布局ID，解析获取页面视图
            mContentView = UIConfig.inflate(mContext, mLayoutId, null);

        }else{				// 子类负责自定义实现页面视图
            mContentView = createContentView();
        }

//        if (mContentView != null) {
//            mTemplate.addContentView(mContentView);
//        }
    }


//    /**初始化模板，并返回**/
//    public abstract BaseTemplate createTemplate();

    /**
     * 对应activity 生命周期 onCreate
     * 		子类建议调用
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState){
//        mOnCreateTimeMillis = System.currentTimeMillis();
//        if(mExpandWork != null){
//            mExpandWork.onCreate(savedInstanceState,this);
//        }
    }
}
