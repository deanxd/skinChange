package com.deanxd.skin.lib.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <pre>
 *     author  : NetEase
 *     time    : 2019/1/22
 *     version : v1.1.1
 *     qq      : 8950764
 *     email   : 8950764@qq.com
 *     desc    : 文件工具类
 * </pre>
 */
public class FileUtils {

    /**
     * 复制文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     */
    public static boolean copyFile(File sourceFile, File targetFile) {
        FileInputStream input = null;
        BufferedInputStream inBuff = null;
        FileOutputStream output = null;
        BufferedOutputStream outBuff = null;

        try {
            // 新建文件输入流并对它进行缓冲
            input = new FileInputStream(sourceFile);
            inBuff = new BufferedInputStream(input);

            // 新建文件输出流并对它进行缓冲
            output = new FileOutputStream(targetFile);
            outBuff = new BufferedOutputStream(output);

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();

            return true;
        } catch (Exception e) {
            close(input);
            close(inBuff);
            close(output);
            close(outBuff);
        }
        return false;
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
