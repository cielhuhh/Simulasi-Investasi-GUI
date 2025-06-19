package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Asset;
import model.Transaksi;
import utils.DBConnection;

public class TransaksiController {
    public static boolean simpanTransaksi(Transaksi transaksi) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO transaksi (jenis_asset, nama_asset, jumlah, harga_per_unit, total) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            Asset asset = transaksi.getAsset();

            stmt.setString(1, asset.getJenis());
            stmt.setString(2, asset.getNama());
            stmt.setInt(3, transaksi.getJumlah());
            stmt.setDouble(4, asset.getHarga());
            stmt.setDouble(5, transaksi.getTotal());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
