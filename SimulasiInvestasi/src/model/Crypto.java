package model;

public class Crypto extends Asset {
    public Crypto(String nama, double harga) {
        super(nama, harga);
    }

    @Override
    public String getJenis() {
        return "Crypto";
    }
}
