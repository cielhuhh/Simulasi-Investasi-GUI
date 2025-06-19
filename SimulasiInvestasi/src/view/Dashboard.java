package view;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.User;

public class Dashboard extends JFrame {

    private final User user;
    private Portofolio portofolioWindow;
    private final Color BACKGROUND_COLOR = new Color(18, 18, 18);
    private final Color CARD_COLOR = new Color(30, 30, 30);
    private final Color PRIMARY_COLOR = new Color(0, 150, 255);
    private final Color SECONDARY_COLOR = new Color(100, 255, 200);

    public Dashboard(User user) {
        if (user == null) throw new IllegalArgumentException("User tidak boleh null");
        this.user = user;

        initUI();
    }

    private void initUI() {
        setTitle("Dashboard - Simulasi Investasi");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 640));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
            }
        });

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainContentPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND_COLOR);
        header.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Left side with welcome message
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel welcome = new JLabel("Selamat Datang, " + user.getNama());
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcome.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Dashboard Simulasi Investasi");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(180, 180, 180));
        
        leftPanel.add(welcome, BorderLayout.NORTH);
        leftPanel.add(subtitle, BorderLayout.SOUTH);
        
        // Right side with user info
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setBackground(BACKGROUND_COLOR);
        
        // User name label
        JLabel userNameLabel = new JLabel(user.getNama());
        userNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userNameLabel.setForeground(Color.WHITE);
        userNameLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
        
        // Profile button
        JButton profileBtn = new JButton(user.getNama().charAt(0) + "");
        profileBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        profileBtn.setBackground(PRIMARY_COLOR);
        profileBtn.setForeground(Color.WHITE);
        profileBtn.setFocusPainted(false);
        profileBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        profileBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileBtn.setBorderPainted(false);
        profileBtn.setOpaque(true);
        
        rightPanel.add(userNameLabel);
        rightPanel.add(profileBtn);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);
        return header;
    }

    private JPanel createMainContentPanel() {
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(BACKGROUND_COLOR);
        content.setBorder(new EmptyBorder(10, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 15, 0);

        // Summary Card
        content.add(createSummaryCard(), gbc);

        // Investment Cards
        JPanel cards = new JPanel(new GridLayout(1, 3, 20, 0));
        cards.setBackground(BACKGROUND_COLOR);
        cards.setBorder(new EmptyBorder(0, 0, 10, 0));
        cards.add(createInvestmentCard("Saham", "Rp 15.000.000", new Color(0, 200, 150), 60));
        cards.add(createInvestmentCard("Obligasi", "Rp 8.500.000", new Color(200, 100, 255), 30));
        cards.add(createInvestmentCard("Reksadana", "Rp 12.300.000", new Color(255, 180, 0), 45));

        gbc.insets = new Insets(5, 0, 15, 0);
        content.add(cards, gbc);

        // Action Buttons
        content.add(createActionButtonsPanel(), gbc);

        return content;
    }

    private JPanel createSummaryCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 50)),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)));

        JLabel title = new JLabel("Ringkasan Portofolio");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        card.add(title, BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(2, 2, 20, 20));
        stats.setBackground(CARD_COLOR);
        stats.add(createStatItem("Total Investasi", "Rp 35.800.000", PRIMARY_COLOR));
        stats.add(createStatItem("Keuntungan", "Rp 4.200.000 (+13%)", SECONDARY_COLOR));
        stats.add(createStatItem("Aset Terbesar", "Saham (42%)", Color.WHITE));
        stats.add(createStatItem("Performa", "↑ 8.5% (30 hari)", SECONDARY_COLOR));
        card.add(stats, BorderLayout.CENTER);

        return card;
    }

    private JPanel createStatItem(String label, String value, Color valueColor) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(CARD_COLOR);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(new Color(180, 180, 180));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 18));
        val.setForeground(valueColor);

        p.add(lbl, BorderLayout.NORTH);
        p.add(val, BorderLayout.CENTER);

        return p;
    }

    private JPanel createInvestmentCard(String type, String value, Color accentColor, int progress) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 50)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(CARD_COLOR);
        
        JLabel typeLbl = new JLabel(type);
        typeLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        typeLbl.setForeground(accentColor);
        
        JLabel valLbl = new JLabel(value);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valLbl.setForeground(Color.WHITE);
        
        topPanel.add(typeLbl, BorderLayout.NORTH);
        topPanel.add(valLbl, BorderLayout.CENTER);
        
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(progress);
        bar.setForeground(accentColor);
        bar.setBackground(new Color(50, 50, 50));
        bar.setBorderPainted(false);
        bar.setStringPainted(true);
        bar.setString(progress + "%");
        bar.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        bar.setPreferredSize(new Dimension(100, 10));

        card.add(topPanel, BorderLayout.CENTER);
        card.add(bar, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createActionButtonsPanel() {
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        actions.setBackground(BACKGROUND_COLOR);

        JButton transaksiBtn = createModernButton("Buat Transaksi Baru", PRIMARY_COLOR);
        transaksiBtn.addActionListener(e -> openTransaksiForm());

        JButton portfolioBtn = createModernButton("Lihat Portofolio", new Color(100, 255, 200));
        portfolioBtn.addActionListener(e -> openPortofolio());

        JButton reportBtn = createModernButton("Generate Laporan", new Color(255, 120, 100));
        reportBtn.addActionListener(e -> new GenerateLaporan().setVisible(true));

        actions.add(transaksiBtn);
        actions.add(portfolioBtn);
        actions.add(reportBtn);

        return actions;
    }

    private JButton createModernButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                // No border
            }
        };
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);

        return btn;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel();
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(new EmptyBorder(15, 0, 15, 0));

        JLabel footerLbl = new JLabel("© 2025 Simulasi Investasi - Kelompok PBO | Versi 1.0.0");
        footerLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLbl.setForeground(new Color(120, 120, 120));
        footer.add(footerLbl);

        return footer;
    }

    private void openPortofolio() {
        if (portofolioWindow == null || !portofolioWindow.isDisplayable()) {
            portofolioWindow = new Portofolio(user.getId());
        }
        portofolioWindow.setVisible(true);
        portofolioWindow.toFront();
    }

    private void openTransaksiForm() {
        if (portofolioWindow == null || !portofolioWindow.isDisplayable()) {
            portofolioWindow = new Portofolio(user.getId());
        }
        new TransaksiForm(portofolioWindow, user.getId()).setVisible(true);
    }
}