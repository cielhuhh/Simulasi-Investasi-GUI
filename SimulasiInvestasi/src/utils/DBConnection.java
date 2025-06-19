package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/simulasi_investasi";
    private static final String DB_USER = "root"; // default XAMPP
    private static final String DB_PASSWORD = ""; // kosong kalau belum diset di phpMyAdmin

    public static Connection getConnection() {
        try {
            // Penting! Wajib load driver secara eksplisit untuk memastikan koneksi
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Coba koneksi
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Koneksi ke database berhasil.");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC MySQL tidak ditemukan.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Gagal koneksi ke database.");
            e.printStackTrace();
        }

        return null;
    }
}
