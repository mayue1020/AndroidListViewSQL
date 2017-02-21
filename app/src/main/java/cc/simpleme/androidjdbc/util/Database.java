package cc.simpleme.androidjdbc.util;

/**
 * Created by dark_ on 2017-02-13.
 */

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.sql.*;
import java.util.logging.Logger;

import cc.simpleme.androidjdbc.MainActivity;

public class Database {
    private static String USERNAME = "sa";
    private static String PASSWORD = "Mrma88888888";
    private static String databese;
    private static String DRIVER = "cvom.microsoft.sqlserver.jdbc.SQLServerDriver";
//    private static String URL = "jdbc:sqlserver://simpleme.cc:1433;databaseName=listtest";
    private static String URL = "jdbc:jtds:sqlserver://simpleme.cc:1433;DatabaseName=listtest";//JTDS连接数据库链接


    static {
        try {
            // 加载驱动
//            Class.forName(DRIVER);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");//jdts加载驱动
            System.out.println("加载驱动.......");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static Connection conn = null;

    public Connection getConn() {
        conn = null;
        System.out.println("数据库连接........");
        try{
            conn=java.sql.DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("数据库连接成功！");
        }catch(SQLException e){
            System.out.println("数据库连接失败！");
            e.printStackTrace();
        }

        return conn;
    }

    //查询并返回结果集
    public ResultSet getSQL(String SQL){
        ResultSet rs = null;
        Statement stmt;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rs;
    }

    public String putSQL(String SQL){
        Exception e = null;
        PreparedStatement pstmt = null;
        try{
            getConn();
            pstmt = conn.prepareStatement(SQL);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception ex){
            e = ex;
            ex.printStackTrace();

            System.out.println("sql失败"+SQL);
        }
        return ""+e;
    }
    /*
     * 关闭数据库连接，注意关闭的顺序
     */
    public void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        if(rs!=null){
            try{
                rs.close();
                rs=null;
            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("关闭ResultSet失败");
            }
        }
        if(ps!=null){
            try{
                ps.close();
                ps=null;
            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("关闭PreparedStatement失败");
            }
        }
        if(conn!=null){
            try{
                conn.close();
                conn=null;
            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("关闭Connection失败");
            }
        }
    }
}