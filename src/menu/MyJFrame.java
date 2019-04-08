package menu;

import constant.Constant;
import event.MyKeyListener;

import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by xfh on 2018/5/19.
 */
public class MyJFrame extends JFrame {

    public  void launchFrame(){
        setTitle("My 俄罗斯方块");
        setBounds(Constant.frame_x, Constant.frame_y, Constant.frame_width, Constant.frame_height);


        //添加三个面板
        setLayout(null); //自定义布局
        add(LeftJPanel.getInstance().getPanel());  //添加左边面板
        add(CenterPanel.getInstance()); //添加中间面板
        add(RightPanel.getInstance().getPanel()); //添加右边面板


        //鼠标点击关闭窗口
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
                //后面要添加，弹出确认退出窗口
            }
        });


        setResizable(false); //禁止缩放窗口
        setVisible(true);
    }


    public static void main(String[] args){
       new MyJFrame().launchFrame();
    }
}

