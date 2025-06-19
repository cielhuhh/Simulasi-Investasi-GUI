package view;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import model.Asset;
import model.Crypto;
import model.Saham;
import model.Transaksi;
import utils.DBConnection;

public class TransaksiForm extends JFrame {
    private final JComboBox<String> jenisCombo;
    private final JComboBox<String> namaAssetCombo;
    private final JTextField jumlahField;
    private final JTextField hargaField;

    // Tambahan untuk integrasi dengan Portofolio dan user ID
    private Portofolio portofolioRef;
    private int userId;

    // Constructor default
    public TransaksiForm() {
        setTitle("Form Transaksi Investasi");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(248, 249, 250));
        setLayout(new BorderLayout());

        // HEADER
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(25, 118, 210), getWidth(), 0, new Color(66, 165, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel titleLabel = new JLabel("FORM TRANSAKSI INVESTASI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 235, 240)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 12, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // FIELD: JENIS ASSET
        jenisCombo = createComboBox(new String[]{"Crypto", "Saham"});
        jenisCombo.addActionListener(e -> updateNamaAssetCombo());
        addFormField(formPanel, gbc, 0, "Jenis Asset:", jenisCombo);

        // FIELD: NAMA ASSET
        namaAssetCombo = createComboBox(loadNamaAssetFromDB("Crypto")); // default awal
        addFormField(formPanel, gbc, 1, "Nama Asset:", namaAssetCombo);

        // FIELD: JUMLAH
        jumlahField = createTextField();
        addFormField(formPanel, gbc, 2, "Jumlah:", jumlahField);

        // FIELD: HARGA PER UNIT
        hargaField = createTextField();
        addFormField(formPanel, gbc, 3, "Harga per Unit (Rp):", hargaField);

        // TOMBOL SIMPAN
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.fill = GridBagConstraints.CENTER;

        JButton simpanBtn = createButton("SIMPAN TRANSAKSI");
        simpanBtn.addActionListener(e -> simpanTransaksi());
        formPanel.add(simpanBtn, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // FOOTER
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JLabel footerLabel = new JLabel("© 2025 Simulasi Investasi | Kelompok 4", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(120, 120, 120));
        footerPanel.add(footerLabel);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    // Constructor tambahan untuk Dashboard.java
    public TransaksiForm(Portofolio portofolioRef, int userId) {
        this(); // Memanggil constructor default
        this.portofolioRef = portofolioRef;
        this.userId = userId;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 215, 220)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return combo;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 215, 220)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return tf;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(new Color(21, 101, 192));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(30, 136, 229));
                } else {
                    g2.setColor(new Color(25, 118, 210));
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 42));
        btn.setOpaque(false);

        return btn;
    }

    private void updateNamaAssetCombo() {
        String selected = jenisCombo.getSelectedItem().toString();
        String[] namaAssets = loadNamaAssetFromDB(selected);
        namaAssetCombo.setModel(new DefaultComboBoxModel<>(namaAssets));
    }

    private String[] loadNamaAssetFromDB(String jenis) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT nama FROM aset WHERE jenis = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, jenis);
            ResultSet rs = stmt.executeQuery();

            java.util.List<String> result = new java.util.ArrayList<>();
            while (rs.next()) {
                result.add(rs.getString("nama"));
            }

            return result.toArray(new String[0]);
        } catch (Exception ex) {
            showErrorDialog("Gagal Memuat Aset", ex.getMessage());
            return new String[]{};
        }
    }

    private void simpanTransaksi() {
        try {
            String jenis = jenisCombo.getSelectedItem().toString();
            String nama = namaAssetCombo.getSelectedItem().toString();
            int jumlah = Integer.parseInt(jumlahField.getText().trim());
            double harga = Double.parseDouble(hargaField.getText().trim());

            if (nama.isEmpty()) throw new IllegalArgumentException("Nama asset tidak boleh kosong");
            if (jumlah <= 0) throw new IllegalArgumentException("Jumlah harus lebih dari 0");
            if (harga <= 0) throw new IllegalArgumentException("Harga harus lebih dari 0");

            Asset asset = jenis.equals("Crypto") ? new Crypto(nama, harga) : new Saham(nama, harga);
            Transaksi transaksi = new Transaksi(asset, jumlah);

            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO transaksi_portofolio (jenis_asset, nama_asset, jumlah, harga_per_unit, user_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, jenis);
            stmt.setString(2, nama);
            stmt.setInt(3, jumlah);
            stmt.setDouble(4, harga);
            stmt.setInt(5, userId); 

            stmt.executeUpdate();

            showSuccessDialog("Transaksi berhasil disimpan", "Total: Rp " + String.format("%,.2f", transaksi.getTotal()));
            resetForm();

            if (portofolioRef != null) {
                portofolioRef.refreshData(); // jika ingin auto-refresh portofolio
            }
        } catch (NumberFormatException ex) {
            showErrorDialog("Format Tidak Valid", "Pastikan jumlah dan harga berupa angka");
        } catch (IllegalArgumentException ex) {
            showErrorDialog("Input Tidak Valid", ex.getMessage());
        } catch (Exception ex) {
            showErrorDialog("Gagal Menyimpan", ex.getMessage());
        }
    }

    private void showSuccessDialog(String message, String detail) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<h3 style='color: #2e7d32; margin: 0 0 5px 0;'>" + message + "</h3>" +
                "<p style='color: #424242; margin: 0;'>" + detail + "</p>" +
                "</div></html>");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(messageLabel, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "Sukses", JOptionPane.PLAIN_MESSAGE);
    }

    private void showErrorDialog(String title, String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<h3 style='color: #c62828; margin: 0 0 5px 0;'>" + title + "</h3>" +
                "<p style='color: #424242; margin: 0;'>" + message + "</p>" +
                "</div></html>");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(messageLabel, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void resetForm() {
        jumlahField.setText("");
        hargaField.setText("");
        jumlahField.requestFocus();
    }
}
