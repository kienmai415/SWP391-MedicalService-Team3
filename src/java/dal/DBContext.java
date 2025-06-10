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

    public DBContext() {
        try {
            String user = "sa"; // Sửa theo cấu hình của bạn
            String pass = "sa"; // Sửa theo cấu hình của bạn
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=Medicare_Booking"; // Đổi tên DATABASE
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Connection is null or closed, recreating...");
                String user = "sa"; // Sửa theo cấu hình của bạn
                String pass = "sa"; // Sửa theo cấu hình của bạn
                String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=Medicare_Booking"; // Đổi tên DATABASE
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("Connection recreated successfully");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error recreating connection: " + ex.getMessage());
            ex.printStackTrace();
        }
        return connection;
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
