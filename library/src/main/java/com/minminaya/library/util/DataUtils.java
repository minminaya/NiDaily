package com.minminaya.library.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Niwa 2017年6月2日
 *         这个是关于数据相关的工具类，例如list对象保存成本地文件
 *         file文件的操作类
 */
public class DataUtils {


    /*
     *
     *  将对象object写入路径filePath下的fileName文件中
     *
     * 1.先建立目录
     * 2.接着建立文件，文件名建议用对象名加上seria后缀，比如要保存list对象，文件名是list.seria，代表list序列化后保存的文件
     *
     *  @param object 要保存为文件的目标对象
     *  @param filePath 保存文件的路径，假如没有就创建一个
     *  @param fileName 保存文件的文件名
     *
     *
     *
     * */
    public static boolean writeObject(Object object, File filePath, String fileName) {

        File file = new File(filePath, fileName);
        if (!file.exists()) {

            try {
                file.createNewFile();//如果没有该文件，则创建新文件
            } catch (IOException e) {
                Logger.d("DataUtils", "文件写入错误");
                e.printStackTrace();
            }
        } else {
            file.delete();//如果该文件存在则删除文件
            try {
                System.out.println(fileName + "文件" + "已存在" + "-------->" + "准备重新建立文件");
                file.createNewFile();//重新建立文件
            } catch (IOException e) {
                Logger.d("DataUtils", "文件写入错误");
                e.printStackTrace();
                return false;
            }

        }


        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Logger.d("DataUtils", "文件写入错误");
            e.printStackTrace();
            return false;
        }
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException e) {
            Logger.d("DataUtils", "文件写入错误");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读取保存的对象文件
     *
     * @param filePath 要读取的目标文件的位置
     * @param fileName 目标文件名
     */
    public static Object readObject(String filePath, String fileName) {
        String path = filePath + "/" + fileName;

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        Object object = null;

        try {
            fileInputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            Logger.d("DataUtils", "文件读取错误");
            e.printStackTrace();
            return object;
        }
        try {
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            Logger.d("DataUtils", "文件读取错误");
            e.printStackTrace();
            return object;
        }

        try {
            if (objectInputStream != null) {
                object = objectInputStream.readObject();
            }
        } catch (ClassNotFoundException | IOException e) {
            Logger.d("DataUtils", "文件读取错误");
            e.printStackTrace();
            return object;
        }
        try {
            fileInputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return object;

    }

}