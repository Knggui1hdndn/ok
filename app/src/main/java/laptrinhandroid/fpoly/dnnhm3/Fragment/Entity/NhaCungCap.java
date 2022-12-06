package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

public class NhaCungCap {
    private int maNCC;
    private String  anh,hoTen,soDT,diaChi;

    public NhaCungCap(int maNCC, String anh, String hoTen, String soDT, String diaChi) {
        this.maNCC = maNCC;
        this.anh = anh;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.diaChi = diaChi;
    }

    public NhaCungCap(String anh, String hoTen, String soDT, String diaChi) {
        this.anh = anh;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.diaChi = diaChi;
    }

    public NhaCungCap() {

    }

    @Override
    public String toString() {
        return "NhaCungCap{" +
                "maNCC=" + maNCC +
                ", anh='" + anh + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", soDT='" + soDT + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
