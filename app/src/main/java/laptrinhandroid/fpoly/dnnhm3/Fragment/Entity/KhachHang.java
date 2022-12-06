package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

public class KhachHang {
    private int maKhach;
    private String  anh,hoTen,soDT,diaChi;



    public KhachHang() {
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKhach=" + maKhach +
                ", anh='" +  '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", soDT='" + soDT + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }

    public KhachHang(int maKhach, String anh, String hoTen, String soDT, String diaChi) {
        this.maKhach = maKhach;
        this.anh = anh;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.diaChi = diaChi;
    }

    public KhachHang(String anh, String hoTen, String soDT, String diaChi) {
        this.anh = anh;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.diaChi = diaChi;
    }

    public int getMaKhach() {
        return maKhach;
    }

    public void setMaKhach(int maKhach) {
        this.maKhach = maKhach;
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
