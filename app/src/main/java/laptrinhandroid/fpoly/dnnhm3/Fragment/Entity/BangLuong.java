package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

public class BangLuong {
    private int id;
    private int maNV;
    private float luongCB;

    private float ungLuong;
    private float thuong;
    private String ngayThang;

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    public float getThuong() {
        return thuong;
    }

    public void setThuong(float thuong) {
        this.thuong = thuong;
    }

    public BangLuong(int id, int maNV, float luongCB, float ungLuong, float thuong, String ngayThang) {
        this.id = id;
        this.maNV = maNV;
        this.luongCB = luongCB;
        this.ungLuong = ungLuong;
        this.thuong = thuong;
        this.ngayThang = ngayThang;
    }

    public BangLuong(int maNV, float luongCB, float ungLuong, float thuong, String ngayThang) {
        this.maNV = maNV;
        this.luongCB = luongCB;
        this.ungLuong = ungLuong;
        this.thuong = thuong;
        this.ngayThang = ngayThang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public float getLuongCB() {
        return luongCB;
    }

    public void setLuongCB(float luongCB) {
        this.luongCB = luongCB;
    }

    public float getUngLuong() {
        return ungLuong;
    }

    public void setUngLuong(float ungLuong) {
        this.ungLuong = ungLuong;
    }


    public BangLuong() {
    }
}
