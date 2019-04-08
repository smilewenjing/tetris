package constant;

import util.BoxUtil;

import java.awt.Font;

/**
 * Created by xfh on 2018/5/19.
 */
public class Constant {
    public  static final int  frame_x =300;
    public static final int frame_y=100;
    public static final int frame_width = 1106;  //1106
    public static final int frame_height = 835;  //经过调试的， 固定窗口后会变成835
    public static final Font myFont = new Font("微软雅黑",Font.ITALIC,22);
    public static final int intervalTime = 40;
    public static final  int quickIntervalTime = 1;

    public static int index = new BoxUtil().getRand();     // 随机选择是哪一个方块
    public static int pastIndex = -1;     // 随机选择是哪一个方块

    private Constant(){}
    private static  final Constant constant = new Constant();
    public static Constant getInstance(){
        return constant;
    }
}
