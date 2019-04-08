package util;

import java.sql.*;

/**
 * Created by xfh on 2018/6/22.
 */
public class JDBCUtil {

    private String dbUrl ="jdbc:mysql://120.78.51.133:3306/tetris?characterEncoding=utf-8&useSSL=false";
    private String user = "root";
    private String password = "XieFeihong@107107";
    private String jdbcName = "com.mysql.jdbc.Driver";

    // 获取数据库连接
    private  Connection getCon() throws Exception{
        Class.forName(jdbcName);
        Connection con = DriverManager.getConnection(dbUrl,user,password);
        return con;
    }
    // 关闭连接
    private void close(Statement stmt,Connection con) throws Exception{
        if(stmt != null){
            stmt.close();
        }
        if(con!=null){
            con.close();
        }
    }

    // 获取最高记录
    public int getMaxScore(){
        try{
            Connection con = getCon();
            String sql = "select *  from recond where id = ? ";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1,1);
            ResultSet rs = pStmt.executeQuery(); //返回结果集
            int max_score = 0;
            if(rs.next()){
                max_score  = rs.getInt("max_score");
            }
            close(pStmt,con);
            return max_score;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    //  更细数据库
    public void updateMaxScore(int score) {

        try{
            Connection con = getCon();
            String sql = "UPDATE recond set max_score = ?  where id = ? ";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1,score);
            pStmt.setInt(2,1);
            System.out.println(" 更新数据库 ");
            pStmt.executeUpdate();//执行sql语句

            close(pStmt,con);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
