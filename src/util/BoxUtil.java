package util;

import pojo.MyBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 还是要改MyBox对象 传入  一个点的坐标，哪一个box 是否旋转
 * Created by xfh on 2018/5/25.
 */
public class BoxUtil {

    private static  List<MyBox> list = null;

    // 获取 7 不同的个方形集合
    public static List<MyBox> getList(){
        if(list!=null){
            return list;
        }else{
            List<MyBox> resultList = new ArrayList<>();
            MyBox myBox1 = new MyBox(); //一字长 方形
            int[] x1 = {150,200,250,300};
            int[] y1 = {0,0,0,0};
            myBox1.setX(x1);
            myBox1.setY(y1);
            resultList.add(myBox1);

            MyBox myBox2 = new MyBox(); //田字 方形
            int[] x2 = {200,250,200,250};
            int[] y2 = {0,0,50,50};
            myBox2.setX(x2);
            myBox2.setY(y2);
            resultList.add(myBox2);

            MyBox myBox3 = new MyBox(); //左7 方形
            int[] x3 = {200,250,250,250};
            int[] y3 = {0,0,50,100};
            myBox3.setX(x3);
            myBox3.setY(y3);
            resultList.add(myBox3);

            MyBox myBox4 = new MyBox(); //右7 方形
            int[] x4 = {250,200,200,200};
            int[] y4 = {0,0,50,100};
            myBox4.setX(x4);
            myBox4.setY(y4);
            resultList.add(myBox4);

            MyBox myBox5 = new MyBox(); //左Z 方形
            int[] x5 = {200,250,250,300};
            int[] y5 = {0,0,50,50};
            myBox5.setX(x5);
            myBox5.setY(y5);
            resultList.add(myBox5);

            MyBox myBox6 = new MyBox(); //右Z 方形
            int[] x6 = {200,250,250,300};
            int[] y6 = {50,50,0,0};
            myBox6.setX(x6);
            myBox6.setY(y6);
            resultList.add(myBox6);

            MyBox myBox7 = new MyBox(); //土字 方形
            int[] x7 = {200,150,200,250};  // 以中间一个为第一个
            int[] y7 = {50,50,0,50};
            myBox7.setX(x7);
            myBox7.setY(y7);
            resultList.add(myBox7);
            list = resultList;
        }
        return list;
    }

    // 选转 ， 顺时针选转
    public MyBox turnMyBox(MyBox myBox,int index) {
        if(index<0||index>6){
            return myBox;
        }
        MyBox resultBox = new MyBox();
        int[] x = new int[4];
        int[] y = new int[4];
        switch(index){
            case 0 : // 一字长型
                x[2] = myBox.getX()[2];
                y[2] = myBox.getY()[2];
                if(myBox.getY()[0]==myBox.getY()[1]){  // 横躺
                    //第三个为准
                    x[0] = x[2]; x[1] = x[2]; x[3] = x[2];
                    y[0] = y[2]+100; y[1] = y[2]+50;y[3] = y[2]-50;
                }else{
                    y[0] = y[2]; y[1] = y[2]; y[3] = y[2];
                    x[0] = x[2]-100; x[1] = x[2]-50;x[3] = x[2]+50;
                }
                resultBox.setX(x);
                resultBox.setY(y);
                return resultBox;
            case 1 :         // 田字
                return myBox;
            case 2 :         // 左 7
                x[0] = myBox.getX()[1];
                y[0] = myBox.getY()[1];
                if(myBox.getY()[0]==myBox.getY()[1]){
                    x[1] = x[0];
                    if (myBox.getY()[3]>myBox.getY()[0]) {
                        y[1] = y[0]+50;
                        y[2] = y[1];
                        y[3] = y[1];
                        x[2] = x[0]-50;
                        x[3] = x[0]-100;
                    }else{
                        y[1] = y[0]-50;
                        y[2] = y[1];
                        y[3] = y[1];
                        x[2] = x[0]+50;
                        x[3] = x[0]+100;
                    }
                }else{
                    y[1] = y[0];
                    if(myBox.getY()[1]>myBox.getY()[0]){
                        x[1] = x[0]-50;
                        x[2] = x[1];
                        x[3] = x[1];
                        y[2] = y[0]-50;
                        y[3] = y[0]-100;
                    }else{
                        x[1] = x[0] + 50;
                        x[2] = x[1];
                        x[3] = x[1];
                        y[2] = y[0]+50;
                        y[3] = y[0]+100;
                    }
                }
                resultBox.setX(x);
                resultBox.setY(y);
                return resultBox;
            case 3 :        // 右7
                x[1] = myBox.getX()[0];
                y[1] = myBox.getY()[0];
                if(myBox.getY()[0]==myBox.getY()[1]){
                    x[0] = x[1];
                    if (myBox.getY()[3]>myBox.getY()[0]) {
                        y[0] = y[1] + 50;
                        x[2] = x[1] - 50;
                        y[2] = y[1];
                        x[3] = x[1] -100;
                        y[3] = y[1];
                    }else{
                        y[0] = y[1] - 50;
                        x[2] = x[1] + 50;
                        y[2] = y[1];
                        x[3] = x[1] + 100;
                        y[3] = y[1];
                    }
                }else{
                    y[0] = y[1];
                    if(myBox.getY()[0]<myBox.getY()[1]){
                        x[0] = x[1] + 50;
                        x[2] = x[1];
                        y[2] = y[1] + 50;
                        x[3] = x[1];
                        y[3] = y[1] +100;
                    }else{
                        x[0] = x[1] - 50;
                        x[2] = x[1];
                        y[2] = y[1] - 50;
                        x[3] = x[1];
                        y[3] = y[1] - 100;
                    }
                }
                resultBox.setX(x);
                resultBox.setY(y);
                return resultBox;
            case 4 :      // 左 Z
                if(myBox.getY()[0] == myBox.getY()[1]){
                    x[1] = myBox.getX()[1] + 50;
                    y[1] = myBox.getY()[1];
                    x[0] = x[1];
                    y[0] = y[1] - 50;
                    x[2] = x[1] -50;
                    y[2] = y[1];
                    x[3] = x[2];
                    y[3] = y[2]+50;
                }
                else{
                    x[1] = myBox.getX()[1] - 50;
                    y[1] = myBox.getY()[1];
                    x[0] = x[1] - 50;
                    y[0] = y[1];
                    x[2] = x[1];
                    y[2] = y[1] + 50;
                    x[3] = x[2] + 50;
                    y[3] = y[2];
                }
                resultBox.setX(x);
                resultBox.setY(y);
                return resultBox;
            case 5 :    // 右 Z
                if(myBox.getY()[0] == myBox.getY()[1]){
                    x[1] = myBox.getX()[2];
                    y[1] = myBox.getY()[2];
                    x[0] = x[1];
                    y[0] = y[1] - 50;

                    x[2] = x[1] + 50;
                    y[2] = y[1];

                    x[3] = x[2];
                    y[3] = y[2]+50;
                }
                else{
                    x[1] = myBox.getX()[1];
                    y[1] = myBox.getY()[1]+50;
                    x[0] = x[1] - 50;
                    y[0] = y[1];
                    x[2] = x[1];
                    y[2] = y[1] - 50;
                    x[3] = x[2] + 50;
                    y[3] = y[2];
                }
                resultBox.setX(x);
                resultBox.setY(y);
                return resultBox;
            case 6 :    // 土字
                x[0] = myBox.getX()[0];  // 以中间一个为第一个
                y[0] = myBox.getY()[0];
                if(myBox.getY()[0] == myBox.getY()[1]){
                    if(myBox.getY()[0] > myBox.getY()[2]){
                        x[1] = x[0];
                        y[1] = y[0]-50;
                        x[2] = x[0]+ 50;
                        y[2] = y[0];
                        x[3] = x[0];
                        y[3] = y[0] + 50;
                    }else{
                        x[1] = x[0];
                        y[1] = y[0] + 50;
                        x[2] = x[0] -  50;
                        y[2] = y[0];
                        x[3] = x[0];
                        y[3] = y[0] - 50;
                    }
                }else{
                    if(myBox.getY()[0] > myBox.getY()[1]){
                        x[1] = x[0] + 50;
                        y[1] = y[0];
                        x[2] = x[0];
                        y[2] = y[0]+50;
                        x[3] = x[0] - 50;
                        y[3] = y[0];
                    }else{
                        x[1] = x[0] - 50;
                        y[1] = y[0];
                        x[2] = x[0];
                        y[2] = y[0] - 50;
                        x[3] = x[0] + 50;
                        y[3] = y[0];
                    }
                }
                resultBox.setX(x);
                resultBox.setY(y);
                return resultBox;
        }
        return myBox;
    }

    // 获取随机数 0 - 6
    public int getRand(){
        return  (int)(Math.random()*7);
    }

}
