package menu;

import constant.Constant;
import event.MyKeyListener;
import pojo.MyBox;
import util.BoxUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


/**
 * Created by xfh on 2018/5/19.
 */
public class CenterPanel extends JPanel {

    private MyBox myBox = new MyBox();
    RightPanel rightPanel = null;

    private int downCondition = 0;   // 用于控制下降速度
    private int intervalTime = Constant.intervalTime ; //默认是 90 ，没重画18次就下降一次
    private Boolean indexFlag =  true; //是否需要进行随机选择方块
    private int[][] exist = new int[16][10];
    private Boolean ifOver = false;   // 表示一局是否已经结束
    private Boolean pauseFlag = false;
    private Boolean startFlag = false;

    public Boolean getIfOver() {
        return ifOver;
    }

    public Boolean getPauseFlag() {
        return pauseFlag;
    }

    public void setPauseFlag(Boolean pauseFlag) {
        this.pauseFlag = pauseFlag;
    }

    public Boolean getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(Boolean startFlag) {
        this.startFlag = startFlag;
    }

    public int[][] getExist() {
        return exist;
    }
    public void setExist(int[][] exist) {
        this.exist = exist;
    }

    public int getDownCondition() {
        return downCondition;
    }

    public void setDownCondition(int downCondition) {
        this.downCondition = downCondition;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public MyBox getMyBox() {
        return myBox;
    }

    public void setMyBox(MyBox myBox) {
        this.myBox = myBox;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);   //调用父类的paint可以出去画面出现两次的情况
        if(startFlag){
            // 判断是否需要 重新获取 方块
            if(indexFlag&&!ifOver){  // 为true表示要重新获取方块
                // 先消去 可以消去的，然后在统计的得分
                int[] row = new int[4];
                int rowIndex = -1;
                for(int i=15;i>=0;i--){
                    Boolean flag = true;
                    for(int j=0;j<10;j++){
                        if(exist[i][j]!=1){
                            flag = false;
                            break;
                        }
                    }
                    if(flag){ // 加分
                        if(rightPanel==null){
                            rightPanel = RightPanel.getInstance();
                        }
                        rightPanel.updateScore(rightPanel.getScore()+10);
                        rowIndex++;
                        row[rowIndex]=i;  // 记录要消去的地方
                    }
                }

                // 消去
                if(rowIndex!=-1){
                    delete(row,rowIndex+1);
                }

                //  再随机 产生一个
                setMyBox(LeftJPanel.getInstance().getMyBox());
                // 记录上次定的
                Constant.pastIndex = Constant.index;
                Constant.index = new BoxUtil().getRand();
                intervalTime = Constant.intervalTime ; //默认是 90 ，每重画90次就下降一次
                System.out.println("downCondition = " + downCondition);
                System.out.println("intervalTime = "+ intervalTime);

                LeftJPanel.getInstance().myPaint(); // 画一次
                indexFlag = false;
            }

            // 不管如何先把已经存在的 画了
            paintExist(g);

            // 调下降按钮
            if(!pauseFlag){
                downCondition++;
                if(downCondition>=intervalTime){ // 要下落
                    // 先判断是否可以下落
                    if(!canDown(getMyBox())){ // 不能下落 就要开产生新的 方块
                        indexFlag = true;
                        for(int i=0;i<4;i++){ // 存档
                            exist[myBox.getY()[i]/50][myBox.getX()[i]/50] = 1;
                        }
                        Boolean overFlag = gameOver();  // 判断一局是否结束
                        if(!overFlag){ // 一局结束
                            ifOver=true;
                            // 开始计算最高分 // 更新数据库记录 记录
                            if(rightPanel==null){
                                rightPanel = RightPanel.getInstance();
                            }
                            if(!rightPanel.getUpdateScoreFlag()){
                                rightPanel.updateMaxScore();
                                centerPanel.requestFocus();
                                rightPanel.setUpdateScoreFlag(true);
                            }
                        }
                    }else{
                        int[] yy;
                        yy = getMyBox().getY();
                        for(int i=0;i<4;i++){
                            if(downCondition>1){  // 正常下落
                                yy[i] = yy[i]+50;
                            }else{        // 快速下落
                                yy[i] = yy[i]+25;
                            }
                        }
                        getMyBox().setY(yy);
                        downCondition = 0;
                    }
                }
            }

            // 画下降中的方块
            if(ifOver){
                g.setFont(new Font("宋体",Font.BOLD,40));
                g.setColor(Color.red);
                g.drawString("游  戏  结  束",100,350);

            }else{
                g.setColor(Color.blue);
                g.fillRect(getMyBox().getX()[0], getMyBox().getY()[0], 50, 50);
                g.fillRect(getMyBox().getX()[1], getMyBox().getY()[1], 50, 50);
                g.fillRect(getMyBox().getX()[2], getMyBox().getY()[2], 50, 50);
                g.fillRect(getMyBox().getX()[3], getMyBox().getY()[3], 50, 50);
            }
        }

    }

    //单实例模式
    private CenterPanel(){
        //定义一个重画窗口的线程类，内部类
        class PaintThread extends Thread{
            public void run(){
                while(true){
                    centerPanel.repaint();
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        new PaintThread().start(); //启动重画
    }
    private static final CenterPanel centerPanel = new CenterPanel();
    public static CenterPanel getInstance(){
        if(centerPanel==null){
          //  centerPanel = new CenterPanel();
        }
            centerPanel.setBounds(300,0,500,800);
            centerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            centerPanel.setVisible(true);
            //centerPanel = new CenterPanel();


            //centerPanel = new CenterPanel();
        return centerPanel;
    }

    //画网格线
    private void drawLine(Graphics g){
        g.setColor(Color.black);
        for (int x = 50; x <= 450; x += 50) {
            g.drawLine(x,0,x,800);
        }
        for (int y = 50; y <= 750; y += 50) {
            g.drawLine(0,y,500,y);
        }
    }

    // 判断是否可以下降
    private synchronized Boolean canDown(MyBox myBox){
        if(myBox.getY()[0]%50!=0){ // 移动了25 下一个25 一定可以移动
            return true;
        }
        // 判断是否超出范围
        int max = Arrays.stream(myBox.getY()).max().getAsInt();
        if(max>=750){
            return false;
        }
        // 先判断是否已经存在
        for(int i=0;i<4;i++){
            if(myBox.getY()[i]/50+1>15||myBox.getX()[i]/50>10){
                return false;
            }
            if(exist[myBox.getY()[i]/50+1][myBox.getX()[i]/50]==1){
                return false;
            }
        }
        return true;
    }

    // 先画已经存在的
    private void paintExist(Graphics g){
        g.setColor(Color.blue);
        for(int i=0;i<16;i++){
            for(int j=0;j<10;j++){
                if(exist[i][j]==1){  // 要画
                    g.fillRect(j*50, i*50, 50, 50);
                }
            }
        }
    }

    // 判断一局是否结束
    private Boolean gameOver(){
        int min = Arrays.stream(myBox.getY()).min().getAsInt();
        if(min<=0){
            return false;
        }
        return true;
    }

    // 消去 方块
    private synchronized void delete(int[] row,int len) {

        if (len==1){
            for(int i=row[0];i>0;i--){
                for(int j = 0;j<10;j++){
                    exist[i][j] = exist[i-1][j];
                }
            }
            // 归零 最上面的
            for(int j=0;j<10;j++){
                exist[0][j] =0;
            }
        }else if(len == 2){
            for(int i=row[0];i>row[1]+1;i--){
                for(int j = 0;j<10;j++){
                    exist[i][j] = exist[i-1][j];
                }
            }
            for(int i=row[1]+1;i>1;i--){
                for(int j = 0;j<10;j++){
                    exist[i][j] = exist[i-2][j];
                }
            }
            // 归零 最上面的
            for(int i=0;i<2;i++){
                for(int j = 0;j<10;j++){
                    exist[i][j] = 0;
                }
            }
        }else if(len==3){
            if(row[2]+1 == row[1]){
                if(row[1]+1==row[0]){
                    int k = row[0];
                    for(int j=0;j<10;j++){
                        exist[k][j] = exist[k-3][j];
                    }
                }else{
                    int k = row[0];
                    for(int j=0;j<10;j++){
                        exist[k][j] = exist[k-1][j];
                    }
                }
            }else {
                int k = row[0];
                for (int j = 0; j < 10; j++) {
                    exist[k][j] = exist[k - 2][j];
                }
            }
            for(int i = row[0]-1;i>2;i--) {
                for (int j = 0; j < 10; j++) {
                    exist[i][j] = exist[i - 3][j];
                }
            }


            // 归零 最上面的
            for(int i=0;i<3;i++){
                for(int j = 0;j<10;j++){
                    exist[i][j] = 0;
                }
            }
        }else{
            for(int i=row[0];i>3;i--){
                for(int j=0;j<10;j++){
                    exist[i][j] = exist[i - 4][j];
                }
            }
            // 归零 最上面的
            for(int i=0;i<4;i++){
                for(int j = 0;j<10;j++){
                    exist[i][j] = 0;
                }
            }
        }
    }

    // 初始化所有数据
    public void initialize(){
         downCondition = 0;   // 用于控制下降速度
         intervalTime = Constant.intervalTime ; //默认是 90 ，没重画18次就下降一次
         indexFlag =  true; //是否需要进行随机选择方块
         exist = new int[16][10];
         ifOver = false;
         pauseFlag = false;
    }

}




