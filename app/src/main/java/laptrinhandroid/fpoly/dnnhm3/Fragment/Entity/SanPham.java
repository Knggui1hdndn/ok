package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int maSP, loaiSP;
    private String tenSP;
    private float giaNhap, giaBan;
    private String anh;
    private int soLuongDaBan, soLuong;
    private String ghiChu;

    public SanPham() {

    }

    @Override
    public String toString() {
        return "SanPham{" +
                "maSP=" + maSP +
                ", loaiSP=" + loaiSP +
                ", tenSP='" + tenSP + '\'' +
                ", giaNhap=" + giaNhap +
                ", giaBan=" + giaBan +
                ", anh='" + anh + '\'' +
                ", soLuongDaBan=" + soLuongDaBan +
                ", soLuong=" + soLuong +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }

    public SanPham(int maSP, int loaiSP, String tenSP, float giaNhap, float giaBan, String anh, int soLuongDaBan, int soLuong, String ghiChu) {
        this.maSP = maSP;
        this.loaiSP = loaiSP;
        this.tenSP = tenSP;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.anh = anh;
        this.soLuongDaBan = soLuongDaBan;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }


    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(int loaiSP) {
        this.loaiSP = loaiSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public float getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(float giaNhap) {
        this.giaNhap = giaNhap;
    }

    public float getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(float giaBan) {
        this.giaBan = giaBan;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getSoLuongDaBan() {
        return soLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan) {
        this.soLuongDaBan = soLuongDaBan;
    }


    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
