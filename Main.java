// Anggota :
// Agil Mughni
// Glenn Hakim

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
    private double hargaOngkir;
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

    public void hargaOngkir(double jarakPengiriman){
        hargaOngkir = (jarakPengiriman <= 10) ? 10000 : 20000;
    }

    public double getHargaOngkir() {
        return hargaOngkir;
    }
    
    public String getAlamatPengiriman() {
        return alamatPengiriman;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    @Override
    public void pernyataanDiskon() {
        System.out.println(String.format("Anda mendapatkan diskon pembelian online sebesar %.0f%%", (diskon * 100)));
    }

    public double hitungDiskon() {
        hargaSetelahDiskon = totalHarga * diskon;
        return hargaSetelahDiskon;
    }

    @Override
    public double hitungTotal() {
        return (getTotalHarga() - hitungDiskon()) + hargaOngkir;
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

class PesananMakanan extends PesananOnline {
    private String namaMakanan;
    
    public PesananMakanan(int nomorPesanan, String namaPelanggan, String alamatPengiriman, String namaMakanan) {
        super(nomorPesanan, namaPelanggan, alamatPengiriman);
        this.namaMakanan = namaMakanan;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    @Override
    public void print() {
        System.out.println("Mencetak kwitansi pesanan " + namaMakanan + " online...");
    }
}

class PesananDiToko extends Pesanan implements PrintKwitansi, Pengiriman {
    private String lokasiToko;
    private double totalHarga;

    public PesananDiToko(int nomorPesanan, String namaPelanggan, String lokasiToko) {
        super(nomorPesanan, namaPelanggan);
        this.lokasiToko = lokasiToko;
    }

    public String getLokasiToko() {
        return lokasiToko;
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
        System.out.println("Mengirim pesanan dari toko di " + lokasiToko);
    }
}

class PesananBarangElektronik extends PesananDiToko {
    private String jenisElektronik;
    
    public PesananBarangElektronik(int nomorPesanan, String namaPelanggan, String lokasiToko, String jenisElektronik) {
        super(nomorPesanan, namaPelanggan, lokasiToko);
        this.jenisElektronik = jenisElektronik;
    }
    
    public String getJenisElektronik() {
        return jenisElektronik;
    }
    
    @Override
    public double hitungTotal() {
        return super.hitungTotal();
    }

    @Override
    public void print() {
        System.out.println("Mencetak kwitansi pesanan " + jenisElektronik + " di toko ...");
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
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

        System.out.println("\n======================================================\n");

        PesananOnline pesananOnline = new PesananOnline(1, "Glenn", "Lamdingin");
        System.out.println("Pesanan " + pesananOnline.getNomorPesanan() + ": Pembelian Online");
        pesananOnline.tambahProduk(27000, 2);  // Membeli 2 barang online masing-masing seharga Rp27000 per item
        pesananOnline.pernyataanDiskon();
        pesananOnline.hargaOngkir(10);
        System.out.println("Harga ongkir ke " + pesananOnline.getAlamatPengiriman() + " = Rp " + decimalFormat.format(pesananOnline.getHargaOngkir()));
        System.out.println("Total harga sebelum diskon dan ongkir = Rp " + decimalFormat.format(pesananOnline.getTotalHarga()));
        System.out.println("Total harga setelah diskon dan ongkir = Rp " + decimalFormat.format(pesananOnline.hitungTotal()));
        pesananOnline.print();
        pesananOnline.ship();
        
        System.out.println("\n======================================================\n");

        PesananDiToko pesananDiToko = new PesananDiToko(2, "Agil", "Blang Bintang");
        System.out.println("Pesanan " + pesananDiToko.getNomorPesanan() + ": Belanja di toko");
        pesananDiToko.tambahProduk(10000, 3);  // Membeli 3 barang di toko seharga Rp10000 per item
        System.out.println("Total harga belanja di toko = Rp " + decimalFormat.format(pesananDiToko.hitungTotal()));
        pesananDiToko.print();
        pesananDiToko.ship();
        
        System.out.println("\n======================================================\n");

        PesananPengembalian pesananPengembalian = new PesananPengembalian(3, "Glenn", "Produk cacat");
        System.out.println("Pesanan" + pesananPengembalian.getNomorPesanan() + ": Permintaan Pengembalian");
        pesananPengembalian.tambahProduk(25000, 1);  // Mengembalikan produk cacat
        System.out.println("Jumlah pengembalian = Rp " + decimalFormat.format(pesananPengembalian.hitungTotal()));
        pesananPengembalian.print();
        pesananPengembalian.returnOrder();

        System.out.println("\n======================================================\n");
        
        PesananBarangElektronik pesananElektronik = new PesananBarangElektronik(4, "Agil", "Ulee Kareng", "Smartphone");
        System.out.println("Pesanan " + pesananElektronik.getNomorPesanan() + ": Pembelian " + pesananElektronik.getJenisElektronik() + " di toko");
        System.out.println("Nama Pelanggan: " + pesananElektronik.getNamaPelanggan());
        System.out.println("Lokasi toko: " + pesananElektronik.getLokasiToko());
        pesananElektronik.tambahProduk(3500000, 1);  // Membeli 1 barang elektronik di toko seharga Rp3500000 per item
        System.out.println("Total harga " + pesananElektronik.getJenisElektronik() + "= Rp " + decimalFormat.format(pesananElektronik.hitungTotal()));
        pesananElektronik.print();
        pesananElektronik.ship();
        
        System.out.println("\n======================================================\n");

        PesananMakanan pesananMakanan = new PesananMakanan(5, "Glenn", "Darussalam", "Pizza");
        System.out.println("Pesanan " + pesananMakanan.getNomorPesanan() + ": Pembelian " + pesananMakanan.getNamaMakanan() + " online");
        pesananMakanan.pernyataanDiskon();
        pesananMakanan.tambahProduk(56000, 3);  // Membeli 3 makanan online seharga Rp56000 per item
        System.out.println("Nama Pelanggan: " + pesananMakanan.getNamaPelanggan());
        System.out.println("Alamat Pelanggan: " + pesananMakanan.getAlamatPengiriman());
        pesananMakanan.hargaOngkir(14);
        System.out.println("Harga ongkir ke " + pesananMakanan.getAlamatPengiriman() + " = Rp " + decimalFormat.format(pesananMakanan.getHargaOngkir()));
        System.out.println("Total harga sebelum diskon dan ongkir = Rp " + decimalFormat.format(pesananMakanan.getTotalHarga()));
        System.out.println("Total harga setelah diskon dan ongkir = Rp " + decimalFormat.format(pesananMakanan.hitungTotal()));
        pesananMakanan.print();
        pesananMakanan.ship();

        System.out.println("\n======================================================\n");
    }
}
