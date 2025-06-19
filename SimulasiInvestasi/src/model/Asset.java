package model;

public abstract class Asset {
    protected String nama;
    protected double harga;

    public Asset(String nama, double harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public abstract String getJenis();
}
