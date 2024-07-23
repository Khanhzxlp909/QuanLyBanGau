/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Administrator
 */
public class ConnectionHelper {

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=QuanLyBanGau");
        dataSource.setUsername("sa");
        dataSource.setPassword("123456789");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(20);
        dataSource.setMaxTotal(25);
    }
    public static void connectionCount(){
        System.out.println("Active: "+dataSource.getNumActive());
                System.out.println("Idle: "+dataSource.getNumIdle());
    }
    public static void close(Connection connectionToClose) {
        try {
            connectionToClose.close();
        } catch (SQLException ex) {
             DialogHelper.alert(null,"Gặp lỗi khi đóng connection: ");
            ex.printStackTrace(System.out);
        }
    }

    public static void close(PreparedStatement stmt) {
        try {
            stmt.close();
        } catch (SQLException ex) {
             DialogHelper.alert(null, "Gặp lỗi khi đóng connection: ");
            ex.printStackTrace(System.out);
        }
    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException ex) {
             DialogHelper.alert(null, "Gặp lỗi khi đóng connection: ");
            ex.printStackTrace(System.out);
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            System.out.println("Kiểm tra kết nối tới SQL Server....");
            conn = dataSource.getConnection();
            System.out.println("Kết nối thành công!");
        } catch (SQLException ex) {
            System.out.println("Kết nối thất bại: " + ex.getMessage()); // In thông tin chi tiết về lỗi
            ex.printStackTrace(); // In stack trace của ngoại lệ để xác định nguyên nhân lỗi
        }
        return conn;
    }

}
