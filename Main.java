import java.text.DecimalFormat;

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
    public void pernyataanDiskon() {
        System.out.println(String.format("Anda mendapatkan diskon pembelian online sebesar %.0f%%", (diskon * 100)));
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

class PesananBarangElektronik extends Pesanan implements PrintKwitansi, Pengiriman {
    private String alamatPengiriman;
    private String jenisBarang;
    private double hargaBarang;
    private int jumlahBarang;
    private double totalHarga;

    public PesananBarangElektronik(int nomorPesanan, String namaPelanggan, String jenisBarang, double hargaBarang, int jumlahBarang, String alamatPengiriman) {
        super(nomorPesanan, namaPelanggan);
        this.jenisBarang = jenisBarang;
        this.hargaBarang = hargaBarang;
        this.jumlahBarang = jumlahBarang;
        this.totalHarga = hargaBarang * jumlahBarang;
        this.alamatPengiriman = alamatPengiriman;
    }

    public String getAlamatPengiriman() {
        return this.alamatPengiriman;
    }

    @Override
    public double hitungTotal() {
        return totalHarga;
    }

    @Override
    public void print() {
        System.out.println("Mencetak kwitansi pesanan barang elektronik...");
    }

    @Override
    public void ship() {
        System.out.println("Mengirim pesanan barang elektronik ke alamat pelanggan");
    }
}

class PesananMakanan extends Pesanan implements PrintKwitansi, Pengiriman {
    private String alamatPengiriman;
    private String jenisMakanan;
    private double hargaMakanan;
    private int jumlahMakanan;
    private double totalHarga;

    public PesananMakanan(int nomorPesanan, String namaPelanggan, String jenisMakanan, double hargaMakanan, int jumlahMakanan, String alamatPengiriman) {
        super(nomorPesanan, namaPelanggan);
        this.jenisMakanan = jenisMakanan;
        this.hargaMakanan = hargaMakanan;
        this.jumlahMakanan = jumlahMakanan;
        this.totalHarga = hargaMakanan * jumlahMakanan;
        this.alamatPengiriman = alamatPengiriman;
    }

    public String getAlamatPengiriman() {
        return this.alamatPengiriman;
    }

    @Override
    public double hitungTotal() {
        return totalHarga;
    }

    @Override
    public void print() {
        System.out.println("Mencetak kwitansi pesanan makanan...");
    }

    @Override
    public void ship() {
        System.out.println("Mengirim pesanan makanan ke alamat pelanggan");
    }
}

public class Main {
    public static void main(String[] args) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

        PesananOnline pesananOnline = new PesananOnline(1, "Glenn", "Lamdingin");
        System.out.println("Pesanan " + pesananOnline.getNomorPesanan() + ": Pembelian Elektronik Online");
        pesananOnline.tambahProduk(24000, 2);  // Membeli 2 barang elektronik masing-masing seharga $12000
        pesananOnline.pernyataanDiskon();
        System.out.println("Total harga sebelum diskon = Rp " + decimalFormat.format(pesananOnline.hitungTotal()));
        System.out.println("Total harga setelah diskon = Rp " + decimalFormat.format(pesananOnline.hitungDiskon()));
        pesananOnline.print();
        pesananOnline.ship();

        PesananDiToko pesananDiToko = new PesananDiToko(2, "Agil", "Blang Bintang");
        System.out.println("\nPesanan " + pesananDiToko.getNomorPesanan() + ": Belanja Bahan Makanan");
        pesananDiToko.tambahProduk(10000, 3);  // Membeli 3 barang seharga $10000 per item
        System.out.println("Total harga belanja di toko = Rp " + decimalFormat.format(pesananDiToko.hitungTotal()));
        pesananDiToko.print();
        pesananDiToko.ship();

        PesananPengembalian pesananPengembalian = new PesananPengembalian(3, "Glenn", "Produk cacat");
        System.out.println("\nPesanan" + pesananPengembalian.getNomorPesanan() + ": Permintaan Pengembalian");
        pesananPengembalian.tambahProduk(25000, 1);  // Mengembalikan produk cacat
        System.out.println("Jumlah pengembalian = Rp " + decimalFormat.format(pesananPengembalian.hitungTotal()));
        pesananPengembalian.print();
        pesananPengembalian.returnOrder();

        PesananBarangElektronik pesananElektronik = new PesananBarangElektronik(4, "Agil", "Smartphone", 5000000, 1, "Blang Bintang");
        System.out.println("\nPesanan " + pesananElektronik.getNomorPesanan() + ": Pembelian Smartphone");
        System.out.println("Nama Pelanggan: " + pesananElektronik.getNamaPelanggan());
        System.out.println("Alamat Pelanggan: " + pesananElektronik.getAlamatPengiriman());
        System.out.println("Total harga smartphone = Rp " + decimalFormat.format(pesananElektronik.hitungTotal()));
        pesananElektronik.print();
        pesananElektronik.ship();

        PesananMakanan pesananMakanan = new PesananMakanan(5, "Glenn", "Pizza", 153000, 2, "Lamdingin");
        System.out.println("\nPesanan " + pesananMakanan.getNomorPesanan() + ": Pembelian Pizza");
        System.out.println("Nama Pelanggan: " + pesananMakanan.getNamaPelanggan());
        System.out.println("Alamat Pelanggan: " + pesananMakanan.getAlamatPengiriman());
        System.out.println("Total harga pesanan pizza = Rp " + decimalFormat.format(pesananMakanan.hitungTotal()));
        pesananMakanan.print();
        pesananMakanan.ship();
    }
}
