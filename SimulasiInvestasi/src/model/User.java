package model;

/**
 * Model User yang menyimpan id (primary‑key), nama lengkap,
 * username (bisa dipakai sebagai email / login name), password, dan role.
 */
public class User {

    private final int    id;
    private final String nama;      // untuk tampilan di Dashboard
    private final String username;  // untuk login
    private final String password;
    private final String role;      // admin/user

    /* ========= KONSTRUKTOR ========= */
    public User(int id, String nama, String username, String role) {
        this.id       = id;
        this.nama     = nama;
        this.username = username;
        this.password = ""; // kosongkan karena tidak dikembalikan dari database untuk keamanan
        this.role     = role;
    }

    public User(int id, String nama, String username, String password, String role) {
        this.id       = id;
        this.nama     = nama;
        this.username = username;
        this.password = password;
        this.role     = role;
    }

    /* ========= GETTER ========= */
    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    /* ========= LOGIKA PASSWORD ========= */
    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }
}
