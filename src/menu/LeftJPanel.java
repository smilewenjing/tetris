package menu;

import constant.Constant;
import event.MyKeyListener;
import pojo.MyBox;
import util.BoxUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xfh on 2018/5/19.
 */
public class LeftJPanel {

    private BoxUtil boxUtil = new BoxUtil();  // 工具类
    private final java.util.List<MyBox> boxList = boxUtil.getList();
    private Boolean flag = true;  // 判断第一次需要初始化

    private MyBox myBox = new MyBox();

    public MyBox getMyBox() {
        MyBox myBox1 = new MyBox();
        if(flag){
            setMyBox(boxList.get(Constant.index));
            flag = false;
        }
        for(int i=0;i<4;i++){
            myBox1.getY()[i] = myBox.getY()[i];
            myBox1.getX()[i] = myBox.getX()[i];
        }
        return myBox1;
    }

    public void setMyBox(MyBox myBox) {
        this.myBox = myBox;
    }

    public void myPaint(){
        // 获取随机 方块
        MyBox myBox = boxList.get(Constant.index);
        setMyBox(myBox);
        //解决下一个显示的问题
        System.out.println("index = " + Constant.index);
        System.out.println(myBox);
        panel2.repaint();
    }

    private  JPanel panel = new JPanel(); //主面板
    private  JLabel lab1=new JLabel();
    private  JPanel panel2 = new JPanel() { //显示下一个方块
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.blue);
            g.fillRect(getMyBox().getX()[0]-125, getMyBox().getY()[0]+50, 50, 50);
            g.fillRect(getMyBox().getX()[1]-125, getMyBox().getY()[1]+50, 50, 50);
            g.fillRect(getMyBox().getX()[2]-125, getMyBox().getY()[2]+50, 50, 50);
            g.fillRect(getMyBox().getX()[3]-125, getMyBox().getY()[3]+50, 50, 50);

        }
    };
    private  JLabel lab2 = new JLabel();
    private  JLabel lab3 = new JLabel();
    private  JLabel lab4 = new JLabel();
    private  JLabel lab5 = new JLabel();

    public JPanel getPanel(){
        panel.setBounds(0,0,300,800);
        panel.setBorder(BorderFactory.createLineBorder(Color.blue));
        panel.setLayout(null); //自定义布局
        lab1.setFont(Constant.myFont);
        lab1.setText("下 一 个");
        lab1.setBounds(100,50,150,50);

        panel2.setBounds(20,110,250,250);
        panel2.setBorder(BorderFactory.createLineBorder(Color.blue));

        lab2.setBounds(60,410,150,40);
        lab2.setFont(Constant.myFont);
        lab2.setText("↑     旋 转");


        lab3.setBounds(60,460,150,40);
        lab3.setFont(Constant.myFont);
        lab3.setText("→     右 移");


        lab4.setBounds(60,510,150,40);
        lab4.setFont(Constant.myFont);
        lab4.setText("↓     加 速");


        lab5.setBounds(60,570,150,40);
        lab5.setFont(Constant.myFont);
        lab5.setText("←     左 移");


        panel.add(lab1);
        panel.add(panel2);
        panel.add(lab2);
        panel.add(lab3);
        panel.add(lab4);
        panel.add(lab5);
        return panel;
    }


    //单实例模式
    private LeftJPanel(){} //私有构造方法，不允许其他类构造该对象
    private static final LeftJPanel leftpanel = new LeftJPanel();
    public static LeftJPanel getInstance(){
        return leftpanel;
    }

}
