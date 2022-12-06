package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

import java.io.Serializable;

public class ChiTietHoaDonNhap implements Serializable {
    private int id,maHD, maSp;
    private String anh,tenSP;
    private int soLuong;
    private float donGia;
     private float thanhTien;

    public ChiTietHoaDonNhap(int id, int maHD, int maSp, String anh, String tenSP, int soLuong, float donGia, float thanhTien) {
        this.id = id;
        this.maHD = maHD;
        this.maSp = maSp;
        this.anh = anh;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
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

    public ChiTietHoaDonNhap() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ChiTietHoaDonNhap(int maHD, int maSp, String anh, String tenSP, int soLuong, float donGia, float thanhTien) {
        this.maHD = maHD;
        this.maSp = maSp;
        this.anh = anh;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }


}
