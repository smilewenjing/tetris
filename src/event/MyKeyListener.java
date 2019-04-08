package event;

import constant.Constant;
import menu.CenterPanel;
import pojo.MyBox;
import util.BoxUtil;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * Created by xfh on 2018/6/20.
 */
public class MyKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        CenterPanel centerPanel = CenterPanel.getInstance();
        int[] x;
        int[] y;

        if ( e.getKeyCode()==KeyEvent.VK_DOWN ){
            System.out.println("按下了-下键");
            centerPanel.setIntervalTime(Constant.quickIntervalTime);

        } else if (e.getKeyCode()==KeyEvent.VK_UP){
            System.out.println("按下了上键");
            // 处于加速下降时不允许 旋转 , 只有在正常情况下可以旋转
            if(centerPanel.getIntervalTime()>10&&Constant.pastIndex!=-1){
               MyBox myBox =  new BoxUtil().turnMyBox(centerPanel.getMyBox(), Constant.pastIndex);
               // 判断是否符合 范围
                if(ifOK(myBox,centerPanel.getExist())){
                    centerPanel.setMyBox(myBox);
                }
            }

        } else if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            System.out.println("按下了右键-右移");
            MyBox myBox = centerPanel.getMyBox();
            // 先判断是否可以右移
            int max = Arrays.stream(myBox.getX()).max().getAsInt();
            if(max<450){ // 可以进行右移
                if(!ifExist(myBox,centerPanel.getExist(),1)){
                    int[] xx = myBox.getX();
                    for(int i=0;i<4;i++){
                        xx[i] += 50;
                    }
                    myBox.setX(xx);
                    centerPanel.setMyBox(myBox);
                }
            }
        } else if (e.getKeyCode()==KeyEvent.VK_LEFT){
            System.out.println("按下了左键");
            MyBox myBox = centerPanel.getMyBox();
            // 先判断是否可以左移
            int min = Arrays.stream(myBox.getX()).min().getAsInt();
            if(min>0){ // 可以进行左移
                if(!ifExist(myBox,centerPanel.getExist(),0)){
                    int[] xx = myBox.getX();
                    for(int i=0;i<4;i++){
                        xx[i] -= 50;
                    }
                    myBox.setX(xx);
                    centerPanel.setMyBox(myBox);
                }
            }
        } else {

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // 判断是否已经存在
    private Boolean  ifExist(MyBox myBox,int[][] exist,int type){
        if(type==1){ // 右移
            for(int i=0;i<4;i++){
                if(exist[myBox.getY()[i]/50][myBox.getX()[i]/50+1]==1){
                    return true;
                }
            }

        }else{
            for(int i=0;i<4;i++){
                if(myBox.getX()[i]/50-1<0){
                    continue;
                }
                if(exist[myBox.getY()[i]/50][myBox.getX()[i]/50-1]==1){
                    return true;
                }
            }
        }
        return false;
    }

    // 旋转后判断是否在范围内
    private Boolean ifOK(MyBox myBox, int[][] exist) {
        int xMin = Arrays.stream(myBox.getX()).min().getAsInt();
        int xMax = Arrays.stream(myBox.getX()).max().getAsInt();
        int yMin = Arrays.stream(myBox.getY()).min().getAsInt();
        int yMax = Arrays.stream(myBox.getY()).max().getAsInt();
        if(xMin<0||xMax>450||yMin<0||yMax>750){
            return false;
        }
        for(int i=0;i<4;i++){
            if(exist[myBox.getY()[i]/50][myBox.getX()[i]/50]==1){
                return false;
            }
        }
        return true;
    }

}
