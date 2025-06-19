package model;

public class Saham extends Asset {
    public Saham(String nama, double harga) {
        super(nama, harga);
    }

    @Override
    public String getJenis() {
        return "Saham";
    }
}
