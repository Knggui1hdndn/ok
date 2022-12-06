package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

import java.sql.Date;

public class BaoCao {
    //chi tiết hóa đơn
    private int cthdId, cthdMaHD, cthdMaSp;
    private String cthdAnh, cthdTenSP;
    private int cthdSoLuong;
    private float cthdDonGia;
    private String cthdKhuyenMai;
    private float cthdThanhTien;

    private int cthdTongSoLuong;
    private double cthdTongTien;

    //sản phẩm
    private int spMaSP, spLoaiSP;
    private String spTenSP;
    private float spGiaNhap, spGiaBan;
    private String spAnh;
    private int spSoLuongDaBan, spSoLuong;
    private String spGhiChu;

    //hóa đơn bán
    private int hdbMaHDBan;
    private int hdbMaNV, hdbMaKH;
    private Date hdbNgayBan;
    private float hdbTongTien;




    //____contrustor
    public BaoCao() {
    }

    public BaoCao(String cthdTenSP, int cthdSoLuong, float cthdDonGia, float cthdThanhTien, float spGiaNhap, float spGiaBan, Date hdbNgayBan) {
        this.cthdTenSP = cthdTenSP;
        this.cthdSoLuong = cthdSoLuong;
        this.cthdDonGia = cthdDonGia;
        this.cthdThanhTien = cthdThanhTien;
        this.spGiaNhap = spGiaNhap;
        this.spGiaBan = spGiaBan;
        this.hdbNgayBan = hdbNgayBan;
    }

    //tổng sản phẩm đã bán
    public BaoCao(int cthdMaSp, int cthdTongSoLuong, double cthdTongTien) {
        this.cthdMaSp = cthdMaSp;
        this.cthdTongSoLuong = cthdTongSoLuong;
        this.cthdTongTien = cthdTongTien;
    }

    //sản phẩm
    public BaoCao(int spMaSP, int spLoaiSP, String spTenSP, float spGiaNhap, float spGiaBan,
                  int spSoLuongDaBan, int spSoLuong, String spGhiChu) {
        this.spMaSP = spMaSP;
        this.spLoaiSP = spLoaiSP;
        this.spTenSP = spTenSP;
        this.spGiaNhap = spGiaNhap;
        this.spGiaBan = spGiaBan;

        this.spSoLuongDaBan = spSoLuongDaBan;
        this.spSoLuong = spSoLuong;
        this.spGhiChu = spGhiChu;
    }

    //cả sản phẩm và tổng đã bán
    public BaoCao(int spMaSP, int spLoaiSP, String spTenSP, float spGiaNhap, float spGiaBan, String spAnh,
                  int spSoLuongDaBan, int spSoLuong, String spGhiChu,int cthdTongSoLuong, double cthdTongTien) {

        this.spMaSP = spMaSP;
        this.spLoaiSP = spLoaiSP;
        this.spTenSP = spTenSP;
        this.spGiaNhap = spGiaNhap;
        this.spGiaBan = spGiaBan;
        this.spAnh = spAnh;
        this.spSoLuongDaBan = spSoLuongDaBan;
        this.spSoLuong = spSoLuong;
        this.spGhiChu = spGhiChu;
        this.cthdTongSoLuong = cthdTongSoLuong;
        this.cthdTongTien = cthdTongTien;
    }



    //------------------geter-setter
    public int getCthdId() {
        return cthdId;
    }

    public void setCthdId(int cthdId) {
        this.cthdId = cthdId;
    }

    public int getCthdMaHD() {
        return cthdMaHD;
    }

    public void setCthdMaHD(int cthdMaHD) {
        this.cthdMaHD = cthdMaHD;
    }

    public int getCthdMaSp() {
        return cthdMaSp;
    }

    public void setCthdMaSp(int cthdMaSp) {
        this.cthdMaSp = cthdMaSp;
    }

    public String getCthdAnh() {
        return cthdAnh;
    }

    public void setCthdAnh(String cthdAnh) {
        this.cthdAnh = cthdAnh;
    }

    public String getCthdTenSP() {
        return cthdTenSP;
    }

    public void setCthdTenSP(String cthdTenSP) {
        this.cthdTenSP = cthdTenSP;
    }

    public int getCthdSoLuong() {
        return cthdSoLuong;
    }

    public void setCthdSoLuong(int cthdSoLuong) {
        this.cthdSoLuong = cthdSoLuong;
    }

    public float getCthdDonGia() {
        return cthdDonGia;
    }

    public void setCthdDonGia(float cthdDonGia) {
        this.cthdDonGia = cthdDonGia;
    }

    public String getCthdKhuyenMai() {
        return cthdKhuyenMai;
    }

    public void setCthdKhuyenMai(String cthdKhuyenMai) {
        this.cthdKhuyenMai = cthdKhuyenMai;
    }

    public float getCthdThanhTien() {
        return cthdThanhTien;
    }

    public void setCthdThanhTien(float cthdThanhTien) {
        this.cthdThanhTien = cthdThanhTien;
    }

    public int getSpMaSP() {
        return spMaSP;
    }

    public void setSpMaSP(int spMaSP) {
        this.spMaSP = spMaSP;
    }

    public int getSpLoaiSP() {
        return spLoaiSP;
    }

    public void setSpLoaiSP(int spLoaiSP) {
        this.spLoaiSP = spLoaiSP;
    }

    public String getSpTenSP() {
        return spTenSP;
    }

    public void setSpTenSP(String spTenSP) {
        this.spTenSP = spTenSP;
    }

    public float getSpGiaNhap() {
        return spGiaNhap;
    }

    public void setSpGiaNhap(float spGiaNhap) {
        this.spGiaNhap = spGiaNhap;
    }

    public float getSpGiaBan() {
        return spGiaBan;
    }

    public void setSpGiaBan(float spGiaBan) {
        this.spGiaBan = spGiaBan;
    }

    public String getSpAnh() {
        return spAnh;
    }

    public void setSpAnh(String spAnh) {
        this.spAnh = spAnh;
    }

    public int getSpSoLuongDaBan() {
        return spSoLuongDaBan;
    }

    public void setSpSoLuongDaBan(int spSoLuongDaBan) {
        this.spSoLuongDaBan = spSoLuongDaBan;
    }

    public int getSpSoLuong() {
        return spSoLuong;
    }

    public void setSpSoLuong(int spSoLuong) {
        this.spSoLuong = spSoLuong;
    }

    public String getSpGhiChu() {
        return spGhiChu;
    }

    public void setSpGhiChu(String spGhiChu) {
        this.spGhiChu = spGhiChu;
    }

    public int getHdbMaHDBan() {
        return hdbMaHDBan;
    }

    public void setHdbMaHDBan(int hdbMaHDBan) {
        this.hdbMaHDBan = hdbMaHDBan;
    }

    public int getHdbMaNV() {
        return hdbMaNV;
    }

    public void setHdbMaNV(int hdbMaNV) {
        this.hdbMaNV = hdbMaNV;
    }

    public int getHdbMaKH() {
        return hdbMaKH;
    }

    public void setHdbMaKH(int hdbMaKH) {
        this.hdbMaKH = hdbMaKH;
    }

    public Date getHdbNgayBan() {
        return hdbNgayBan;
    }

    public void setHdbNgayBan(Date hdbNgayBan) {
        this.hdbNgayBan = hdbNgayBan;
    }

    public float getHdbTongTien() {
        return hdbTongTien;
    }

    public void setHdbTongTien(float hdbTongTien) {
        this.hdbTongTien = hdbTongTien;
    }

    public int getCthdTongSoLuong() {
        return cthdTongSoLuong;
    }

    public void setCthdTongSoLuong(int cthdTongSoLuong) {
        this.cthdTongSoLuong = cthdTongSoLuong;
    }

    public double getCthdTongTien() {
        return cthdTongTien;
    }

    public void setCthdTongTien(double cthdTongTien) {
        this.cthdTongTien = cthdTongTien;
    }

    @Override
    public String toString() {
        return  ", spMaSP=" + spMaSP +
                ", spLoaiSP=" + spLoaiSP +
                ", spTenSP='" + spTenSP + '\'' +
                ", spGiaNhap=" + spGiaNhap +
                ", spGiaBan=" + spGiaBan +
                ", spAnh='" + spAnh + '\'' +
                ", spSoLuongDaBan=" + spSoLuongDaBan +
                ", spSoLuong=" + spSoLuong +
                ", spGhiChu='" + spGhiChu + '\'' +
                ", cthdTongSoLuong=" + cthdTongSoLuong +
                ", cthdTongTien=" + cthdTongTien +
                '}';
    }
}
