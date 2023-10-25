interface PrintKwitansi {
    void print();
}

interface Pengiriman {
    void ship();
}

interface Pengembalian {
    void returnOrder();
}

interface KuponDiskon {
    void pernyataanDiskon();
    double hitungDiskon();
}

abstract class Pesanan {
    private int nomorPesanan;
    private String namaPelanggan;

    public Pesanan(int nomorPesanan, String namaPelanggan) {
        this.nomorPesanan = nomorPesanan;
        this.namaPelanggan = namaPelanggan;
    }

    public int getNomorPesanan() {
        return nomorPesanan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public abstract double hitungTotal();
}

class PesananOnline extends Pesanan implements PrintKwitansi, Pengiriman, KuponDiskon {
    private String alamatPengiriman;
    private double totalHarga;
    private double diskon = 0.2;
    private double hargaSetelahDiskon;

    public PesananOnline(int nomorPesanan, String namaPelanggan, String alamatPengiriman) {
        super(nomorPesanan, namaPelanggan);
        this.alamatPengiriman = alamatPengiriman;
    }

    public void tambahProduk(double harga, int jumlah) {
        totalHarga += harga * jumlah;
    }

    @Override
    public void pernyataanDiskon(){
        System.out.println(String.format("Anda mendapatkan diskon pembelian online sebesar %.0f%%", (diskon*100)));
    }
    @Override
    public double hitungDiskon() {
        hargaSetelahDiskon = totalHarga * diskon;
        return hargaSetelahDiskon;
    }

    @Override
    public double hitungTotal() {
        return totalHarga - hargaSetelahDiskon;
    }

    @Override
    public void print() {
        System.out.println("Mencetak kwitansi pesanan online...");
    }

    @Override
    public void ship() {
        System.out.println("Mengirim pesanan online ke " + alamatPengiriman);
    }
}

class PesananDiToko extends Pesanan implements PrintKwitansi, Pengiriman {
    private String lokasiToko;
    private double totalHarga;

    public PesananDiToko(int nomorPesanan, String namaPelanggan, String lokasiToko) {
        super(nomorPesanan, namaPelanggan);
        this.lokasiToko = lokasiToko;
    }

    public void tambahProduk(double harga, int jumlah) {
        totalHarga += harga * jumlah;
    }

    @Override
    public double hitungTotal() {
        return totalHarga;
    }

    @Override
    public void print() {
        System.out.println("Mencetak kwitansi pesanan di toko...");
    }

    @Override
    public void ship() {
        System.out.println("Mengirim pesanan di toko ke " + lokasiToko);
    }
}

class PesananPengembalian extends Pesanan implements PrintKwitansi, Pengembalian {
    private String alasanPengembalian;
    private double totalHarga;

    public PesananPengembalian(int nomorPesanan, String namaPelanggan, String alasanPengembalian) {
        super(nomorPesanan, namaPelanggan);
        this.alasanPengembalian = alasanPengembalian;
    }

    public void tambahProduk(double harga, int jumlah) {
        totalHarga += harga * jumlah;
    }

    @Override
    public double hitungTotal() {
        return totalHarga;
    }

    @Override
    public void print() {
        System.out.println("Mencetak kwitansi pesanan pengembalian...");
    }

    @Override
    public void returnOrder() {
        System.out.println("Memproses pesanan pengembalian karena: " + alasanPengembalian);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Pesanan Online");
        PesananOnline pesananOnline = new PesananOnline(1, "Glenn", "Lamdingin");
        pesananOnline.tambahProduk(24000, 2);
        pesananOnline.pernyataanDiskon();
        System.out.println("Harga sebelum diskon = Rp " + (int) pesananOnline.hitungDiskon());
        System.out.println("Harga setelah diskon = Rp " + (int) pesananOnline.hitungTotal());
        pesananOnline.print();
        pesananOnline.ship();
        
        System.out.println("Pesanan di Toko");
        PesananDiToko pesananDiToko = new PesananDiToko(2, "Agil", "Darussalam");
        pesananDiToko.tambahProduk(30000, 3);
        System.out.println("\n\nJumlah harga = Rp " + (int) pesananDiToko.hitungTotal());
        pesananDiToko.print();
        pesananDiToko.ship();
        
        System.err.println("Pengembalian pesanan");
        PesananPengembalian pesananPengembalian = new PesananPengembalian(3, "Alice Johnson", "Produk cacat");
        pesananPengembalian.tambahProduk(25000, 1);
        System.out.println("\n\njumlah harga pengembalian = Rp " + (int) pesananPengembalian.hitungTotal());
        pesananPengembalian.print();
        pesananPengembalian.returnOrder();
    }
}
