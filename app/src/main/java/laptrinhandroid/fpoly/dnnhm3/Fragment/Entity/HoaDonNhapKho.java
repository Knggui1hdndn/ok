package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

import java.io.Serializable;
import java.util.Date;

public class HoaDonNhapKho implements Serializable {
    private int maHDNhap;
    private int maNV;
    private int maNCC;
    private Date ngayNhap;
    private float tongTien;


    public HoaDonNhapKho() {
    }

    public HoaDonNhapKho(int maHDNhap, Date ngayNhap) {
        this.maHDNhap = maHDNhap;
        this.ngayNhap = ngayNhap;
    }

    public HoaDonNhapKho(int maNV, int maNCC, Date ngayNhap, float tongTien) {
        this.maNV = maNV;
        this.maNCC = maNCC;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
    }

    public HoaDonNhapKho(int maHDNhap, int maNV, int maNCC, Date ngayNhap, float tongTien) {
        this.maHDNhap = maHDNhap;
        this.maNV = maNV;
        this.maNCC = maNCC;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getMaHDNhap() {
        return maHDNhap;
    }

    public void setMaHDNhap(int maHDNhap) {
        this.maHDNhap = maHDNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }


    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
}
