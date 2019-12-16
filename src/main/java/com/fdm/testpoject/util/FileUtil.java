package com.fdm.testpoject.util;

import java.io.File;

/**
 * @author fdm
 * @date 2019/9/4 14:45
 * @Description:
 */
public class FileUtil {

    /**
     * （本地操作）删除本地文件
     *
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        if (!file.isFile()) {
            return false;
        }
        return file.delete();
    }

    /**
     * (本地操作)创建本地目录
     *
     * @param path
     */
    public void mkdirs(String path) {
        File f = new File(path);
        String fs = f.getParent();
        f = new File(fs);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

}
