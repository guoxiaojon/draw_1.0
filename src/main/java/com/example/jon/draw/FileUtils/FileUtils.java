package com.example.jon.draw.FileUtils;

import android.os.Environment;
import android.util.Log;

import com.example.jon.draw.DrawView.DrawView;
import com.example.jon.draw.graphics.Arc;
import com.example.jon.draw.graphics.Circle;
import com.example.jon.draw.graphics.Oval;
import com.example.jon.draw.graphics.Rect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jon on 2016/2/29.
 */
public class FileUtils {
    public static String ROOT = Environment.getExternalStorageDirectory()+ File.separator;

    //将List中的实现Serializable的对象写入手机的存储空间中
    public static void savePicture(List...lists){
        Log.d("data",ROOT);
        File dir = new File(ROOT+"pic_cache");
        dir.mkdirs();
        for(int i =0;i<lists.length;i++){
            if(lists[i].size()== 0)
                continue;
            //是正方形

            if(lists[i].get(0) instanceof Rect){
                ObjectOutputStream oos = null;
                for(int x = 0;x<lists[i].size();x++){
                    try {
                        oos = new ObjectOutputStream(new FileOutputStream(ROOT+"pic_cache"+File.separator+"rect_"+x));
                        oos.writeObject(lists[i].get(x));
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                continue;

            }



            //圆
            if(lists[i].get(0) instanceof Circle){
                ObjectOutputStream oos = null;
                for(int x = 0;x<lists[i].size();x++){
                    try {
                        oos = new ObjectOutputStream(new FileOutputStream(ROOT+"pic_cache"+File.separator+"circle_"+x));
                        oos.writeObject(lists[i].get(x));
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                continue;

            }


            //椭圆
            if(lists[i].get(0) instanceof Oval){
                ObjectOutputStream oos = null;
                for(int x = 0;x<lists[i].size();x++){
                    try {
                        oos = new ObjectOutputStream(new FileOutputStream(ROOT+"pic_cache"+File.separator+"oval_"+x));
                        oos.writeObject(lists[i].get(x));
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                continue;

            }

            //弧线
            if(lists[i].get(0) instanceof Arc){
                ObjectOutputStream oos = null;
                for(int x = 0;x<lists[i].size();x++){
                    try {
                        oos = new ObjectOutputStream(new FileOutputStream(ROOT+"pic_cache"+File.separator+"arc_"+x));
                        oos.writeObject(lists[i].get(x));
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                continue;

            }


        }

    }


    //从手机中将之前存储的各种对象读出来，存放到DrawView的响应List中
    public static void openPicture(){

        File file = null;
        List<Rect> list_rect = new ArrayList<Rect>();
        List<Oval> list_oval = new ArrayList<Oval>();
        List<Circle> list_circle = new ArrayList<Circle>();
        List<Arc> list_arc = new ArrayList<Arc>();

        //将文件中的长方形找到变成对象
        for (int i =0;i<100;i++){
            file = new File(ROOT+"pic_cache"+File.separator+"rect_"+ i);
            if(!file.exists())
                break;

            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ROOT+"pic_cache"+File.separator+"rect_"+ i));
                Rect rect = (Rect)ois.readObject();
                list_rect.add(rect);
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //椭圆
        for (int i =0;i<100;i++){
            file = new File(ROOT+"pic_cache"+File.separator+"oval_"+ i);
            if(!file.exists())
                break;

            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ROOT+"pic_cache"+File.separator+"oval_"+ i));
                Oval oval = (Oval)ois.readObject();
                list_oval.add(oval);
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //圆

        for (int i =0;i<100;i++){
            file = new File(ROOT+"pic_cache"+File.separator+"circle_"+ i);
            if(!file.exists())
                break;

            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ROOT+"pic_cache"+File.separator+"circle_"+ i));
                Circle circle = (Circle)ois.readObject();
                list_circle.add(circle);
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //弧
        for (int i =0;i<100;i++){
            file = new File(ROOT+"pic_cache"+File.separator+"arc_"+ i);
            if(!file.exists())
                break;

            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ROOT+"pic_cache"+File.separator+"arc_"+ i));
                Arc arc = (Arc)ois.readObject();
                list_arc.add(arc);
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        DrawView.finished_list_Rect = list_rect;
        DrawView.finished_list_Oval = list_oval;
        DrawView.finished_list_Circle = list_circle;
        DrawView.finished_list_Arc = list_arc;
    }
}
