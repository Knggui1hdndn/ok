package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

public class ThongBaoNV {
    private int id, maNV;
    private String ngay, message, trangThai;
    Boolean doc;

    public Boolean getDoc() {
        return doc;
    }

    public void setDoc(Boolean doc) {
        this.doc = doc;
    }

    public ThongBaoNV(int maNV, String ngay, String message, String trangThai, Boolean doc) {
        this.maNV = maNV;
        this.ngay = ngay;
        this.message = message;
        this.trangThai = trangThai;
        this.doc = doc;
    }

    public ThongBaoNV() {
    }

    public ThongBaoNV(int id, int maNV, String ngay, String message, String trangThai, Boolean doc) {
        this.id = id;
        this.maNV = maNV;
        this.ngay = ngay;
        this.message = message;
        this.trangThai = trangThai;
        this.doc = doc;
    }

    @Override
    public String toString() {
        return "ThongBaoNV{" +
                "id=" + id +
                ", maNV=" + maNV +
                ", ngay='" + ngay + '\'' +
                ", message='" + message + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", doc=" + doc +
                '}';
    }

    //recyclerView.findViewHolderForAdapterPosition(0).itemView.performClick()
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
