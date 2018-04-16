package com.arch.base.util.storage;


import com.arch.base.util.StorageUtil;

/**
 * Created by wurongqiu on 2017/7/17.
 */

public enum StorageType {
    TYPE_DATA(DirectoryName.DATA_DIRECTORY_NAME, false),
    TYPE_CRASH(DirectoryName.CRASH_DIRECTORY_NAME, false),
    TYPE_TXT(DirectoryName.TXT_DIRECTORY_NAME, false),
    TYPE_LOG(DirectoryName.LOG_DIRECTORY_NAME, false),
    TYPE_APK(DirectoryName.APK_DIRECTORY_NAME, false),
    TYPE_TEMP(DirectoryName.TEMP_DIRECTORY_NAME, false),
    TYPE_MAIL(DirectoryName.MAIL_DIRECTORY_NAME, false),
    TYPE_FILE(DirectoryName.FILE_DIRECTORY_NAME, false),

    TYPE_HEAD(DirectoryName.HEAD_DIRECTORY_NAME, false),
    TYPE_BG(DirectoryName.BG_DIRECTORY_NAME, false),
    TYPE_QRCODE(DirectoryName.QRCODE_DIRECTORY_NAME, false),
    TYPE_STICKER(DirectoryName.STICKER_DIRECTORY_NAME, false),
    TYPE_PA_ICON(DirectoryName.PA_ICON_DIRECTORY_NAME, false),
    TYPE_ASK_ICON(DirectoryName.ASK_ICON_DIRECTORY_NAME, false),
    TYPE_BMP_CACHE(DirectoryName.BMP_CACHE_DIRECTORY_NAME, false),
    TYPE_MEET_ICON(DirectoryName.MEET_ICON_DIRECTORY_NAME, false),

    TYPE_IMAGE(DirectoryName.IMAGE_DIRECTORY_NAME, true),
    TYPE_AUDIO(DirectoryName.AUDIO_DIRECTORY_NAME, true),
    TYPE_VIDEO(DirectoryName.VIDEO_DIRECTORY_NAME, true),

    TYPE_THUMB_IMAGE(DirectoryName.THUMB_DIRECTORY_NAME, true),
    TYPE_THUMB_VIDEO(DirectoryName.THUMB_DIRECTORY_NAME, true),
    TYPE_THUMB_MUSIC(DirectoryName.THUMB_DIRECTORY_NAME, true),
    TYPE_THUMB_SHARE(DirectoryName.THUMB_DIRECTORY_NAME, true),

    TYPE_STATISTICS(DirectoryName.STATISTICS_DIRECTORY_NAME, false);

    private DirectoryName storageDirectoryName;
    private boolean storageByMD5;
    private long storageMinSize;

    StorageType(DirectoryName dirName, boolean storageByMD5) {
        this(dirName, storageByMD5, StorageUtil.THRESHOLD_MIN_SPCAE);
    }

    StorageType(DirectoryName dirName, boolean storageByMD5, long storageMinSize) {
        this.storageDirectoryName = dirName;
        this.storageByMD5 = storageByMD5;
        this.storageMinSize = storageMinSize;
    }

    public String getStoragePath() {
        return storageDirectoryName.getPath();
    }

    public boolean isStorageByMD5() {
        return storageByMD5;
    }

    public long getStorageMinSize() {
        return storageMinSize;
    }

    enum DirectoryName {
        DATA_DIRECTORY_NAME("data/", CacheClearStrategy.CLEAR_ALL),
        TXT_DIRECTORY_NAME("txt/", CacheClearStrategy.CLEAR_ALL),
        APK_DIRECTORY_NAME("apk/", CacheClearStrategy.CLEAR_ALL),
        FILE_DIRECTORY_NAME("file/", CacheClearStrategy.CLEAR_ALL),
        LOG_DIRECTORY_NAME("log/", CacheClearStrategy.CLEAR_KEEP_RECENTLY),
        MAIL_DIRECTORY_NAME("mail/", CacheClearStrategy.CLEAR_KEEP_RECENTLY),
        TEMP_DIRECTORY_NAME("temp/", CacheClearStrategy.CLEAR_ALL),
        CRASH_DIRECTORY_NAME("crash/", CacheClearStrategy.CLEAR_KEEP_RECENTLY),

        AUDIO_DIRECTORY_NAME("audio/", CacheClearStrategy.CLEAR_ALL),
        VIDEO_DIRECTORY_NAME("video/", CacheClearStrategy.CLEAR_ALL),
        IMAGE_DIRECTORY_NAME("image/", CacheClearStrategy.CLEAR_ALL),
        BMP_CACHE_DIRECTORY_NAME("img_cache/", CacheClearStrategy.CLEAR_ALL),
        THUMB_DIRECTORY_NAME("thumb/", CacheClearStrategy.CLEAR_KEEP_RECENTLY),
        HEAD_DIRECTORY_NAME("avatar/", CacheClearStrategy.CLEAR_NEVER),
        BG_DIRECTORY_NAME("background/", CacheClearStrategy.CLEAR_NEVER),
        QRCODE_DIRECTORY_NAME("qrcode/", CacheClearStrategy.CLEAR_NEVER),
        STICKER_DIRECTORY_NAME("sticker/", CacheClearStrategy.CLEAR_NEVER),
        PA_ICON_DIRECTORY_NAME("pa_icon/", CacheClearStrategy.CLEAR_NEVER),

        ASK_ICON_DIRECTORY_NAME("ask_icon/", CacheClearStrategy.CLEAR_KEEP_RECENTLY),
        MEET_ICON_DIRECTORY_NAME("meet_icon/", CacheClearStrategy.CLEAR_KEEP_RECENTLY),
        /**
         * 统计数据
         */
        STATISTICS_DIRECTORY_NAME("statistics/", CacheClearStrategy.CLEAR_NEVER),;

        private String path;
        private CacheClearStrategy cacheClearStrategy;

        private DirectoryName(String path, CacheClearStrategy cacheClearStrategy) {
            this.path = path;
            this.cacheClearStrategy = cacheClearStrategy;
        }

        public String getPath() {
            return path;
        }

        public boolean needClearCache() {
            return cacheClearStrategy != CacheClearStrategy.CLEAR_NEVER;
        }

        public int getKeepCacheDays() {
            return cacheClearStrategy.keepCacheDays;
        }
    }

    enum CacheClearStrategy {
        CLEAR_NEVER(-1),
        CLEAR_ALL(0),
        CLEAR_KEEP_RECENTLY(7),;

        // >= 0  表示清理缓存时需要删除，具体数字代表删除时需要保留的天数
        // < 0  表示清理缓存时不删除
        private int keepCacheDays;

        private CacheClearStrategy(int keepCacheDays) {
            this.keepCacheDays = keepCacheDays;
        }
    }
}
