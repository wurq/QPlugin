package com.arch.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.arch.qplugin.R;
import com.arch.base.util.ProcessUtil;
import com.arch.base.util.StorageUtil;
import com.arch.base.util.SystemUtil;
import com.arch.base.util.ToastUtil;
import com.arch.base.util.config.ServEnv;
import com.arch.base.util.storage.StorageType;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;


/**
 * Created by wurongqiu on 2017/7/17.
 */

public class CrashHandle implements UncaughtExceptionHandler{
    public final static String TAG = "CrashHandle";
    private static UncaughtExceptionHandler smCrashHandler = null;

    private Context mContext;

    private static CrashHandle sCrashHandleInstance = null;

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private CrashHandle() {
    }

    public static CrashHandle getInstance(/*Context context*/) {
        synchronized (CrashHandle.class) {
            if (sCrashHandleInstance == null) {
                synchronized (CrashHandle.class) {
                    sCrashHandleInstance = new CrashHandle();
//                    sCrashHandleInstance.init(context);
                }
            }
        }
        return sCrashHandleInstance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        if(ServEnv.isDebug() == true) {
            mContext = context;

            //获取系统默认的UncaughtException处理器
            smCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
            //设置该CrashHandler为程序的默认处理器
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<>();

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean hasHandle = false;
        if (null != ex) {

            if (ex instanceof TimeoutException) {
                // 处理TimeoutException问题
//                        if (sTimeoutCounter < sMaxTimeoutRetry) {
//                            Log.i("ExceptionHandler",
//                                    "Wrapper handle,retrycount="
//                                            + sTimeoutCounter);
//                            sTimeoutCounter++;
//                            // 作为自定义异常
//                            MstApplication.handleCatchException(ex,
//                                    "retryCount=" + sTimeoutCounter, null);
//                            hasHandle = true;
//                        }
            }
        }
        if (!handleException(ex) && null != smCrashHandler) {
            smCrashHandler.uncaughtException(thread, ex);
        }

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtil.makeShortToast(R.string.restart_system);
                Looper.loop();
            }
        }.start();
        //收集设备参数信息
        collectDeviceInfo(mContext);
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public  void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = SystemUtil.getCurrentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-"+ ProcessUtil.getCurrentProcessName() + time + "-" + timestamp + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = StorageUtil.getWritePath(fileName, StorageType.TYPE_CRASH);

                FileOutputStream fos = new FileOutputStream(path);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }


//    private void init( )  {
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setWrapperHandler();
//            }
//        }, 2000);
//    }
    /**
     * crash处理，增加外层的处理逻辑
     */
//    private static void setWrapperHandler() {
//        sCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
//        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
//
////            private Context mContext;
//
//        });
//    }


}
