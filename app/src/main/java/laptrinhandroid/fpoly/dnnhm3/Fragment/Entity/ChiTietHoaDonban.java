package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

import java.io.Serializable;

public class ChiTietHoaDonban implements Serializable {
    private int id, maHD, maSp;
    private String anh, tenSP;
    private int soLuong;
    private float donGia;
    private float thanhTien;

    @Override
    public String toString() {
        return "ChiTietHoaDonban{" +
                "id=" + id +
                ", maHD=" + maHD +
                ", maSp=" + maSp +
                ", anh='" + anh + '\'' +
                ", tenSP='" + tenSP + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", thanhTien=" + thanhTien +
                '}';
    }

    public ChiTietHoaDonban() {
    }

    public ChiTietHoaDonban(int maHD, int maSp, String anh, String tenSP, int soLuong, float donGia, float thanhTien) {
        this.maHD = maHD;
        this.maSp = maSp;
        this.anh = anh;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public ChiTietHoaDonban(int id, int maHD, int maSp, String anh, String tenSP, int soLuong, float donGia, float thanhTien) {
        this.id = id;
        this.maHD = maHD;
        this.maSp = maSp;
        this.anh = anh;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }
}
