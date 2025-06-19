package view;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import utils.DBConnection;

public class Portofolio extends JFrame {

    private final int userId;
    private final DefaultTableModel model;
    private final JLabel totalLabel;

    public Portofolio(int userId) {
        this.userId = userId;

        /* ===== FRAME ===== */
        setTitle("Portofolio Investasi");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(34, 40, 49));

        /* ===== HEADER ===== */
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(getBackground());
        header.setBorder(new EmptyBorder(20, 20, 10, 20));

        JLabel title = new JLabel("📈  Daftar Portofolio Anda");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(30, 136, 229));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        /* ===== TABLE ===== */
        String[] cols = {"Jenis Aset", "Nama Aset", "Jumlah", "Harga / Unit", "Total Nilai"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(0, 150, 136));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(30, 136, 229));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(sp, BorderLayout.CENTER);

        /* ===== FOOTER ===== */
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(getBackground());
        footer.setBorder(new EmptyBorder(10, 20, 20, 20));

        totalLabel = new JLabel();               // di‑update di refresh()
        totalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        totalLabel.setForeground(Color.LIGHT_GRAY);

        JButton close = new JButton("Tutup");
        close.setFont(new Font("Segoe UI", Font.BOLD, 14));
        close.setBackground(new Color(220, 53, 69));
        close.setForeground(Color.WHITE);
        close.setFocusPainted(false);
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.addActionListener(e -> dispose());

        footer.add(totalLabel, BorderLayout.WEST);
        footer.add(close, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);

        /* ===== FIRST LOAD ===== */
        refresh();
    }

    /** Method untuk memperbarui isi tabel dari database */
    public void refresh() {
        model.setRowCount(0);   // bersihkan tabel

        final String SQL = """
            SELECT jenis_asset, nama_asset, jumlah, harga_per_unit,
                   (jumlah * harga_per_unit) AS total_nilai
            FROM transaksi_portofolio
            WHERE user_id = ?""";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("jenis_asset"),
                            rs.getString("nama_asset"),
                            rs.getInt("jumlah"),
                            rupiah(rs.getDouble("harga_per_unit")),
                            rupiah(rs.getDouble("total_nilai"))
                    });
                    count++;
                }
                totalLabel.setText("Total aset ditampilkan: " + count);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Method publik untuk dipanggil dari luar kelas */
    public void refreshData() {
        refresh();
    }

    /* util format Rp */
    private String rupiah(double v) {
        return "Rp " + String.format("%,.0f", v);
    }
}
