package menu;

import constant.Constant;
import event.MyKeyListener;
import util.JDBCUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xfh on 2018/5/19.
 */
public class RightPanel {
    private JPanel panel = new JPanel();
    private JLabel lab1 = new JLabel();
    private JButton but1 = new JButton();
    private JButton but2 = new JButton();
    private JButton but3 = new JButton();
    private JButton but4 = new JButton();
    private JLabel scoreLab = new JLabel();
    private int score = 0;
    private Boolean updateScoreFlag = false;

    public Boolean getUpdateScoreFlag() {
        return updateScoreFlag;
    }

    public void setUpdateScoreFlag(Boolean updateScoreFlag) {
        this.updateScoreFlag = updateScoreFlag;
    }

    private JDBCUtil jdbcUtil = new JDBCUtil();
    private int maxScore = jdbcUtil.getMaxScore() ;

    private CenterPanel centerPanel = CenterPanel.getInstance();

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public JPanel getPanel(){
        panel.setBounds(800,0,300,800);
        panel.setBorder(BorderFactory.createLineBorder(Color.blue));
        panel.setLayout(null);
        panel.addKeyListener(new MyKeyListener());
        lab1.setFont(Constant.myFont);
        lab1.setText("历史最高分： "+maxScore);
        lab1.setBounds(50,150,200,50);
      //  lab1.setBorder(BorderFactory.createLineBorder(Color.blue));



        but1.setFont(Constant.myFont);
        but1.setText("分数清零");
        but1.setBounds(90,210,110,50);
        but1.setBorder(BorderFactory.createLineBorder(Color.blue));
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(" 按下了分数请零键 ");
                jdbcUtil.updateMaxScore(0);
                lab1.setText("历史最高分： "+0);
                panel.requestFocus();
            }

        });

        scoreLab.setFont(Constant.myFont);
        scoreLab.setText("当前分数： "+score);
        scoreLab.setBounds(60,330,200,50);
        //  lab1.setBorder(BorderFactory.createLineBorder(Color.blue));

        but2.setFont(Constant.myFont);
        but2.setText("开始游戏");  //后面点击后要改成 继续
        but2.setBounds(100,460,110,50);
        but2.setBorder(BorderFactory.createLineBorder(Color.blue));
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(" 按下了暂停 ");
                if(!centerPanel.getIfOver()){
                    if(!centerPanel.getStartFlag()){
                        centerPanel.setStartFlag(true);
                        centerPanel.setPauseFlag(true);
                    }
                    if(centerPanel.getPauseFlag()){
                        centerPanel.setPauseFlag(false);
                        but2.setText("暂停游戏");
                        panel.requestFocus();
                    }else{
                        centerPanel.setPauseFlag(true);
                        but2.setText("继续游戏");
                    }
                }
            }
        });
        but2.requestFocus();


        but3.setFont(Constant.myFont);
        but3.setText("重新开始");
        but3.setBounds(100,540,110,50);
        but3.setBorder(BorderFactory.createLineBorder(Color.blue));
        but3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(" 按下了开始建 ");
                if(centerPanel.getIfOver()){   // 要在已经结束的时候重新开始才有意义
                    // 初始化一切数据
                    score = 0;
                    updateScoreFlag = false;
                    centerPanel.initialize();

                }else{
                    // JOptionPane.showMessageDialog(null, "还有开始游戏或者游戏正在进行中");
                }
                panel.requestFocus();
            }
        });


        but4.setFont(Constant.myFont);
        but4.setText("退出游戏");
        but4.setBounds(100,620,110,50);
        but4.setBorder(BorderFactory.createLineBorder(Color.blue));
        but4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 暂停游戏
                centerPanel.setPauseFlag(true);
                // 弹出一个 退出的 提示框
                myDialog();
            }
        });

        panel.add(lab1);
        panel.add(but1);
        panel.add(but2);
        panel.add(but3);
        panel.add(but4);
        panel.add(scoreLab);
        return panel;
    }




    //单实例模式
    private RightPanel(){
        super();
    }
    private static  RightPanel rightPanel ;
    public static RightPanel getInstance(){
        if(rightPanel==null){
            rightPanel = new RightPanel();
        }
        return rightPanel;
    }

    // 弹出退出框
    private void myDialog(){
        JDialog  dialog =  new JDialog();
        dialog.setTitle("退出游戏");
        JLabel jLabel = new JLabel();
        JButton OkButton = new JButton("是（Y）");
        JButton NoButton = new JButton("否（N）");
        dialog.setBounds(500,300,800,200);
        dialog.setLayout(null);


        jLabel.setFont(Constant.myFont);
        jLabel.setText(" 是 否 退 出 游 戏 ");
        jLabel.setBounds(50,20,200,50);

        OkButton.setFont(Constant.myFont);
        OkButton.setBounds(260,100,110,50);
        OkButton.setBorder(BorderFactory.createLineBorder(Color.blue));
        OkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        NoButton.setFont(Constant.myFont);
        NoButton.setBounds(420,100,110,50);
        NoButton.setBorder(BorderFactory.createLineBorder(Color.blue));
        NoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.setPauseFlag(false);
                dialog.setVisible(false);
            }
        });

        dialog.add(jLabel);
        dialog.add(OkButton);
        dialog.add(NoButton);
        dialog.setModal(true); // 设置为模态窗口  不能操做其他窗口
        dialog.setVisible(true);
    }

    // b更新数据库 内容
    public void updateMaxScore(){
        if(score>maxScore){
            // 更新数据库
           jdbcUtil.updateMaxScore(score);
            lab1.setText("历史最高分： "+score);
        }
    }

    // 更新当前分数
    public void updateScore(int newScore){
        setScore(newScore);
        scoreLab.setText("当前分数： "+newScore);
    }

}
