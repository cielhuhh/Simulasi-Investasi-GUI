package model;

public class Transaksi {
    private Asset asset;
    private int jumlah;

    public Transaksi(Asset asset, int jumlah) {
        this.asset = asset;
        this.jumlah = jumlah;
    }

    public Asset getAsset() {
        return asset;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getTotal() {
        return jumlah * asset.getHarga();
    }
}
