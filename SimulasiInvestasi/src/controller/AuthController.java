package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import utils.DBConnection;

/**
 * AuthController menangani login dan otentikasi pengguna.
 */
public class AuthController {

    /**
     * Method untuk memeriksa login dan mengembalikan objek User jika berhasil.
     *
     * @param username Username yang dimasukkan
     * @param password Password yang dimasukkan
     * @return Objek User jika login berhasil, null jika gagal
     */
    public User getUserIfLoginSuccess(String username, String password) {
        System.out.println("Mencoba login dengan:");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String uname = rs.getString("username");
                String pass = rs.getString("password");

                // Karena di database tidak ada kolom 'nama', kita pakai username sebagai nama
                return new User(id, uname, uname, pass);
            } else {
                System.out.println("Login gagal: tidak ditemukan di database.");
            }

        } catch (SQLException e) {
            System.err.println("Error saat mengambil user: " + e.getMessage());
        }

        return null;
    }
}
