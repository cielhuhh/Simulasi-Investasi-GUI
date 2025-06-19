// package view;
package view;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class GenerateLaporan extends JFrame {

    public GenerateLaporan() {
        initUI();
    }

    private void initUI() {
        setTitle("Laporan Investasi - Modern UI");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(250, 250, 250));
        setLayout(new BorderLayout(0, 0));

        // Header Panel
        createHeaderPanel();
        
        // Main Content Panel
        createContentPanel();
        
        // Footer Panel
        createFooterPanel();
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 255, 255));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                new EmptyBorder(20, 40, 20, 40)
        ));

        // Title with subtitle
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setBackground(new Color(255, 255, 255));
        
        JLabel titleLabel = new JLabel("Laporan Transaksi Investasi");
        titleLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 24));
        titleLabel.setForeground(new Color(40, 40, 40));
        
        JLabel dateLabel = new JLabel("Periode: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy | HH:mm")));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateLabel.setForeground(new Color(120, 120, 120));
        
        titlePanel.add(titleLabel);
        titlePanel.add(dateLabel);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Add logo/icon placeholder
        JLabel iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(40, 40));
        iconLabel.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 10));
        iconLabel.setBackground(new Color(245, 245, 245));
        iconLabel.setOpaque(true);
        headerPanel.add(iconLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(250, 250, 250));
        contentPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Summary Cards
        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        summaryPanel.setBackground(new Color(250, 250, 250));
        
        String[] summaryTitles = {"Total Transaksi", "Total Investasi", "Rata-rata/Transaksi", "Transaksi Sukses"};
        String[] summaryValues = {"24", "Rp 48.750.000", "Rp 2.031.250", "100%"};
        Color[] summaryColors = {
            new Color(70, 130, 180), 
            new Color(76, 175, 80), 
            new Color(255, 152, 0), 
            new Color(156, 39, 176)
        };

        for (int i = 0; i < 4; i++) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 230, 230)),
                    new EmptyBorder(15, 20, 15, 20)
            ));
            
            JLabel titleLabel = new JLabel(summaryTitles[i]);
            titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            titleLabel.setForeground(new Color(120, 120, 120));
            
            JLabel valueLabel = new JLabel(summaryValues[i]);
            valueLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
            valueLabel.setForeground(summaryColors[i]);
            
            card.add(titleLabel, BorderLayout.NORTH);
            card.add(valueLabel, BorderLayout.CENTER);
            
            summaryPanel.add(card);
        }

        contentPanel.add(summaryPanel, BorderLayout.NORTH);

        // Table Section
        String[] columnNames = {"ID Transaksi", "Jenis Investasi", "Jumlah", "Tanggal", "Status", "Detail"};
        Object[][] data = {
                {"TR001", "Saham Blue Chip", "Rp 5.000.000", "10 Mei 2025", "Sukses", "Lihat"},
                {"TR002", "Obligasi Negara", "Rp 3.000.000", "12 Mei 2025", "Sukses", "Lihat"},
                {"TR003", "Reksadana Pasar Uang", "Rp 2.500.000", "1 Juni 2025", "Sukses", "Lihat"},
                {"TR004", "Saham Growth", "Rp 4.000.000", "15 Juni 2025", "Sukses", "Lihat"},
                {"TR005", "ETF Global", "Rp 6.200.000", "20 Juni 2025", "Proses", "Lihat"},
                {"TR006", "Reksadana Campuran", "Rp 3.750.000", "22 Juni 2025", "Sukses", "Lihat"},
                {"TR007", "Obligasi Korporasi", "Rp 4.500.000", "25 Juni 2025", "Sukses", "Lihat"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the "Detail" column is clickable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 5 ? JButton.class : Object.class;
            }
        };

        JTable laporanTable = new JTable(model);
        
        // Custom table styling
        laporanTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        laporanTable.setRowHeight(45);
        laporanTable.setShowGrid(false);
        laporanTable.setIntercellSpacing(new Dimension(0, 0));
        laporanTable.setSelectionBackground(new Color(230, 245, 255));
        laporanTable.setSelectionForeground(Color.BLACK);
        
        // Custom header
        laporanTable.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        laporanTable.getTableHeader().setBackground(new Color(245, 245, 245));
        laporanTable.getTableHeader().setForeground(new Color(80, 80, 80));
        laporanTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 230)));
        laporanTable.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        // Custom cell rendering
        laporanTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        setBackground(Color.WHITE);
                    } else {
                        setBackground(new Color(248, 248, 248));
                    }
                }
                
                // Style specific columns
                if (column == 2) { // Amount column
                    setForeground(new Color(0, 150, 136));
                    setFont(getFont().deriveFont(Font.BOLD));
                } else if (column == 4) { // Status column
                    if ("Sukses".equals(value)) {
                        setForeground(new Color(46, 125, 50));
                    } else {
                        setForeground(new Color(211, 47, 47));
                    }
                }
                
                return this;
            }
        });
        
        // Style button column
        laporanTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        laporanTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(laporanTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(255, 255, 255));
        footerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
                new EmptyBorder(15, 40, 15, 40)
        ));

        // Left footer - Info
        JLabel infoLabel = new JLabel("Data diperbarui secara real-time | © 2025 InvestasiKu");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(150, 150, 150));
        footerPanel.add(infoLabel, BorderLayout.WEST);

        // Right footer - Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(255, 255, 255));
        
        JButton refreshButton = createModernButton("Refresh Data", new Color(100, 181, 246));
        JButton closeButton = createModernButton("Tutup Laporan", new Color(239, 83, 80));
        
        refreshButton.addActionListener(e -> refreshData());
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        footerPanel.add(buttonPanel, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void refreshData() {
        // Refresh action would go here
        JOptionPane.showMessageDialog(this, "Data telah diperbarui", "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                new EmptyBorder(8, 20, 8, 20)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    // Button renderer for table
    static class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setBackground(new Color(245, 245, 245));
            setForeground(new Color(66, 133, 244));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(218, 220, 224)),
                    new EmptyBorder(5, 10, 5, 10)
            ));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Button editor for table
    static class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Action when button is clicked
                JOptionPane.showMessageDialog(button, "Detail transaksi akan ditampilkan");
            }
            isPushed = false;
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            GenerateLaporan frame = new GenerateLaporan();
            frame.setVisible(true);
        });
    }
}