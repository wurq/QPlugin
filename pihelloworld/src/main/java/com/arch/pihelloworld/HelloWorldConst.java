package com.arch.pihelloworld;

import com.arch.commonconst.CommonCst;
import com.arch.commonconst.PluginIdConst;

/**
 * Created by wurongqiu on 2018/5/16.
 */

public class HelloWorldConst {
    private static final int BASE_FUNCTION_ID = PluginIdConst.PiHelloWorld  << CommonCst.CONST_ID_OFFSET;

    /**
     * 插件页面索引id
     */
    public final class ViewId {
        // 插件框架测试
        public static final int HELLO_WORLD_MAIN_VIEW = BASE_FUNCTION_ID + 0x0001; // 插件主页面
        public static final int HELLO_WORLD_RESOURCE_VIEW = BASE_FUNCTION_ID + 0x0002; // 资源获取示例页面
        public static final int HELLO_WORLD_ACTIVITY_VIEW = BASE_FUNCTION_ID + 0x0003; // activity示例页面
        public static final int HELLO_WORLD_SERVICE_VIEW = BASE_FUNCTION_ID + 0x0004; // service示例页面
        public static final int HELLO_WORLD_REQUEST_VIEW = BASE_FUNCTION_ID + 0x0005; // 插件通信示例页面
        public static final int HELLO_WORLD_THEAD_POOL_VIEW = BASE_FUNCTION_ID + 0x0006; // 线程池示例页面
        public static final int HELLO_WORLD_DB_VIEW = BASE_FUNCTION_ID + 0x0007; // 数据库示例页面
        public static final int HELLO_WORLD_NT_VIEW = BASE_FUNCTION_ID + 0x0008; // 数据库示例页面
        public static final int HELLO_WORLD_INSTALL_VIEW = BASE_FUNCTION_ID + 0x0009; // 插件安装页面
    }
}
