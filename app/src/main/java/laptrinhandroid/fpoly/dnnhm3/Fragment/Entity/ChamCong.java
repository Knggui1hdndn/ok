package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;



import com.google.type.DateTime;

import java.sql.Date;
import java.sql.Time;

public class ChamCong {
    private int maCong;
    private int  maNV;
    private Time gioBatDau,gioKetThuc;
    private Date  ngay;
    private int xacNhanChamCong;

    @Override
    public String toString() {
        return "ChamCong{" +
                "maCong=" + maCong +
                ", maNV=" + maNV +
                ", gioBatDau=" + gioBatDau +
                ", gioKetThuc=" + gioKetThuc +
                ", ngay=" + ngay +
                ", xacNhanChamCong=" + xacNhanChamCong +
                '}';
    }

    public ChamCong() {

    }

    public int getMaCong() {
        return maCong;
    }

    public void setMaCong(int maCong) {
        this.maCong = maCong;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public Time getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(Time gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public Time getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(Time gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public int getXacNhanChamCong() {
        return xacNhanChamCong;
    }

    public void setXacNhanChamCong(int xacNhanChamCong) {
        this.xacNhanChamCong = xacNhanChamCong;
    }

    public ChamCong(int maNV, Time gioBatDau, Time gioKetThuc, Date ngay, int xacNhanChamCong) {
        this.maNV = maNV;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.ngay = ngay;
        this.xacNhanChamCong = xacNhanChamCong;
    }

    public ChamCong(int maCong, int maNV, Time gioBatDau, Time gioKetThuc, Date ngay, int xacNhanChamCong) {
        this.maCong = maCong;
        this.maNV = maNV;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.ngay = ngay;
        this.xacNhanChamCong = xacNhanChamCong;
    }
}
