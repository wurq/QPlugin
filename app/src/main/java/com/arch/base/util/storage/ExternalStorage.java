package com.arch.base.util.storage;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import com.arch.base.application.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wurongqiu on 2017/7/17.
 */

public class ExternalStorage {
    protected static final String NO_MEDIA_FILE_NAME = ".nomedia";
    private static final String TAG = "ExternalStorage";
    /**
     * app文件存放根目录名
     */
    private static final String APP_DIRECTORY_NAME = BaseApplication.getApplication().getPackageName() + "/";
    private static ExternalStorage sInstance;
    /**
     * 外部存储是否MOUNTED
     */
    private boolean mMounted = false;
//    /**
//     * 程序所需的文件夹是否都已经创建成功
//     */
////    private boolean mFoldersReady = false;
    /**
     * 外部存储根目录
     */
    private String mExternalStorageDir = null;

    private static Set<StorageType> mStorageTypes = new HashSet<>();

    private ExternalStorage() {
    }

    synchronized public static ExternalStorage getInstance() {
        if (sInstance == null) {
            sInstance = new ExternalStorage();
        }
        sInstance.loadStorageState();
        return sInstance;
    }

    private static String getMD5Prefix(String md5) {
        if (md5.length() < 4) {
            return md5;
        } else {
            return md5.substring(0, 2) + "/" + md5.substring(2, 4);
        }
    }

    private void loadStorageState() {
        mMounted = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (mMounted) {
            String externalPath = Environment.getExternalStorageDirectory().getPath();
            if (/*mFoldersReady &&*/ externalPath.equals(mExternalStorageDir)) {
                return;
            }
            mExternalStorageDir = externalPath;
            /*mFoldersReady =*/
            createSubFolders();
        } else {
            mExternalStorageDir = null;
//            mFoldersReady = false;
        }
    }

    private boolean createSubFolders() {
        if (!isExternalStorageExist()) {
            return false;
        }
        boolean result = true;
        String appDirectory = mExternalStorageDir + "/" + APP_DIRECTORY_NAME;
        File root = new File(appDirectory);
        if (!root.exists() || !root.isDirectory()) {
            root.mkdirs();
        }

        for (StorageType mStorageType : mStorageTypes) {
            result &= makeDirectory(appDirectory + mStorageType.getStoragePath());
        }
        if (result) {
            createNoMediaFile(appDirectory);
        }
//        mFoldersReady = result;
        return result;
    }

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    private boolean makeDirectory(String path) {
        File file = new File(path);
        boolean exist = file.exists();
        if (!exist) {
            exist = file.mkdirs();
            Log.i(TAG,"-------\n "+ " path: " + path);
        }
        return exist;
    }

    private void createNoMediaFile(String path) {
        File noMediaFile = new File(path + "/" + NO_MEDIA_FILE_NAME);
        try {
            if (!noMediaFile.exists()) {
                noMediaFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 外置存储卡是否存在
     *
     * @return
     */
    public boolean isExternalStorageExist() {
        return mMounted;
    }

    /**
     * 获取外置存储卡剩余空间
     *
     * @return
     */
    public long getAvailableExternalSize() {
        return getResidualSpace(mExternalStorageDir);
    }

    /**
     * 获取目录剩余空间
     *
     * @param directoryPath
     * @return
     */
    private long getResidualSpace(String directoryPath) {
        try {
            StatFs sf = new StatFs(directoryPath);
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            long availCountByte = availCount * blockSize;
            return availCountByte;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 文件全名转绝对路径（写）
     *
     * @param fileName 文件全名（文件名.扩展名）
     * @return 返回绝对路径信息
     */
    public String getWritePath(String fileName, StorageType fileType) {
        mStorageTypes.add(fileType);
        if (TextUtils.isEmpty(fileName) || !checkSubFolders()) {
            return "";
        }
        return pathForName(fileName, fileType, false, false);
    }

    /**
     * 返回指定类型的文件夹路径
     *
     * @param fileType
     * @return
     */
    public String getDirectoryByDirType(StorageType fileType) {
        if (isExternalStorageExist()) {
            mStorageTypes.add(fileType);
            String dirName = String.format("/%s%s", APP_DIRECTORY_NAME, fileType.getStoragePath());
            return mExternalStorageDir + dirName;
        }

        return null;
    }

    private boolean checkSubFolders() {
//        if (!mFoldersReady) {
        return createSubFolders();
//        }
//        return true;
    }

    private String pathForName(String fileName, StorageType type, boolean dir, boolean check) {
        String directory = getDirectoryByDirType(type);
        StringBuilder path = new StringBuilder(directory);
        if (type.isStorageByMD5()) {
            path.append(getMD5Prefix(fileName));
        }

        if (!dir) {
            path.append("/").append(fileName);
        }

        String pathString = path.toString();
        File file = new File(pathString);

        if (check) {
            if (file.exists()) {
                if ((dir && file.isDirectory())
                        || (!dir && !file.isDirectory())) {
                    return pathString;
                }
            }

            return "";
        } else {
            return pathString;
        }
    }
}
