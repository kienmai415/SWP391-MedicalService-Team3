/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author maiki
 */
public class MyDao extends DBContext {
    public Connection con = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    public String xSql = null;
    public MyDao(){
        con = connection;
    }
    public void finalize(){
        try {
            if(con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}