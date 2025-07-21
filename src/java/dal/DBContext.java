/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maiki
 */
public class DBContext {

    protected Connection connection;
    
    private static final DBContext instance = new DBContext();

    public static DBContext getInstance() {
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public DBContext() {
        try {
            String user = "sa"; //sửa theo cấu hình cảu mình
            String pass = "123"; // sửa theo cấu hình cảu mình
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=Medicare_Booking"; //đổi tên DATABASE
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        DBContext db = new DBContext();
        if (db.connection != null) {
            System.out.println("Kết nối database thành công: " + db.connection);
        } else {
            System.out.println("Kết nối database thất bại!");
        }
    }
}
