package com.hl.retrofitrxjavademo.rxhttp;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 缓存工具
 * Created by HL on 2017/9/17/0017.
 */

public class CacheUtil {

    private static long WIFI_CACHE_TIME = 5 * 60 * 1000;
    private static long OTHER_CACHE_TIME = 60 * 60 * 1000;

    /**
     * 保存缓存数据
     *
     * @param context
     * @param ser
     * @param file
     * @return
     */
    public static boolean saveObject(Context context, Serializable ser, String file) {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取缓存数据
     *
     * @param context
     * @param file
     * @param expireTime
     * @return
     */
    public static Serializable readObject(Context context, String file, long expireTime) {

        if (!isExistDataCache(context, file)) {
            return null;
        }
        if (isDataTimeOut(context, file, expireTime) && expireTime != 0) {
            return null;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvalidClassException) {
                //反序列化失败，删除缓存文件
                File data = context.getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 判断缓存是否过期
     *
     * @param context
     * @param file
     * @param exprieTime
     * @return
     */
    public static boolean isDataTimeOut(Context context, String file, long exprieTime) {
        File data = context.getFileStreamPath(file);
        long time = data.lastModified();
        if (System.currentTimeMillis() - time > exprieTime) {
            return true;
        }
        return false;
    }

    /**
     * 判断缓存是否存在
     *
     * @param context
     * @param file
     * @return
     */
    public static boolean isExistDataCache(Context context, String file) {
        if (context == null) {
            return false;
        }
        boolean exist = false;
        File data = context.getFileStreamPath(file);
        if (data.exists()) {
            exist = true;
        }
        return exist;
    }

    /**
     * 删除缓存
     *
     * @param context
     * @param file
     * @return
     */
    public static boolean deleteObject(Context context, String file) {
        File data = context.getFileStreamPath(file);
        if (data.exists()) {
            data.delete();
            return true;
        } else {
            return false;
        }
    }
}
