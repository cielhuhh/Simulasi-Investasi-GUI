package view;

import controller.AuthController;
import java.awt.*;
import javax.swing.*;
import model.User;

public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMe;

    public LoginForm() {
        setTitle("Login - Simulasi Investasi");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Main container with shadow effect
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(50, 50, 350, 480);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mainPanel.setLayout(null);
        add(mainPanel);

        // Logo/Icon placeholder
        JLabel logo = new JLabel("💹", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        logo.setBounds(125, 20, 100, 80);
        mainPanel.add(logo);

        // Label Judul
        JLabel titleLabel = new JLabel("SIMULASI INVESTASI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(59, 130, 246));
        titleLabel.setBounds(0, 100, 350, 40);
        mainPanel.add(titleLabel);

        // Subjudul
        JLabel subTitle = new JLabel("Masuk ke akun Anda", SwingConstants.CENTER);
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subTitle.setForeground(new Color(107, 114, 128));
        subTitle.setBounds(0, 140, 350, 30);
        mainPanel.add(subTitle);

        // Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(new Color(55, 65, 81));
        userLabel.setBounds(25, 190, 300, 20);
        mainPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(25, 215, 300, 40);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(usernameField);

        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passLabel.setForeground(new Color(55, 65, 81));
        passLabel.setBounds(25, 265, 300, 20);
        mainPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(25, 290, 300, 40);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(passwordField);

        // Ingat Saya dan Lupa Password
        rememberMe = new JCheckBox("Ingat saya");
        rememberMe.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rememberMe.setForeground(new Color(55, 65, 81));
        rememberMe.setBounds(25, 345, 100, 20);
        rememberMe.setFocusPainted(false);
        rememberMe.setBackground(Color.WHITE);
        mainPanel.add(rememberMe);

        JLabel lupaPassword = new JLabel("Lupa password?");
        lupaPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lupaPassword.setForeground(new Color(59, 130, 246));
        lupaPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lupaPassword.setBounds(225, 345, 100, 20);
        mainPanel.add(lupaPassword);

        // Tombol Login
        JButton loginButton = new JButton("MASUK");
        loginButton.setBackground(new Color(59, 130, 246));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBounds(25, 390, 300, 45);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(37, 99, 235));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(59, 130, 246));
            }
        });
        mainPanel.add(loginButton);

        // Divider
        JSeparator divider = new JSeparator();
        divider.setBounds(25, 450, 300, 1);
        divider.setForeground(new Color(229, 231, 235));
        mainPanel.add(divider);

        // Footer
        JLabel footer = new JLabel("© 2025 Simulasi Investasi - Kelompok 4", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(new Color(156, 163, 175));
        footer.setBounds(50, 540, 350, 20);
        add(footer);

        // Aksi Tombol Login
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            AuthController auth = new AuthController();
            User user = auth.getUserIfLoginSuccess(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                Dashboard dashboard = new Dashboard(user);
                dashboard.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}