package pojo;

import java.util.Arrays;

/**
 * 四个点的坐标
 * Created by xfh on 2018/5/25.
 */
public class MyBox {
    private int[] x =new int[4];
    private int[] y =new int[4];

    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "MyBox{" +
                "x=" + Arrays.toString(x) +
                ", y=" + Arrays.toString(y) +
                '}';
    }
}
