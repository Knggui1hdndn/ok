package laptrinhandroid.fpoly.dnnhm3.Fragment.Entity;

import java.io.Serializable;
import java.sql.Date;
public class NhanVien implements Serializable {
    private   int maNv;
    private  String anh, hoTen, gioiTinh, soDT, diaChi,  email,anhPhoToCC, anhXNKcoTATS, passwords, token;
    private Date ngayBD, ngaySinh;

    public NhanVien() {
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNv=" + maNv +
                ", anh='" + anh + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", soDT='" + soDT + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", anhPhoToCC='" + anhPhoToCC + '\'' +
                ", anhXNKcoTATS='" + anhXNKcoTATS + '\'' +
                ", email='" + email + '\'' +
                ", passwords='" + passwords + '\'' +
                ", token='" + token + '\'' +
                ", ngayBD=" + ngayBD +
                ", ngaySinh=" + ngaySinh +
                '}';
    }

    public int getMaNv() {
        return maNv;
    }

    public void setMaNv(int maNv) {
        this.maNv = maNv;
    }

    public NhanVien(int maNv, String anh, String hoTen, String gioiTinh, String soDT, String diaChi, String anhPhoToCC, String anhXNKcoTATS, String email, String passwords, String token, Date ngayBD, Date ngaySinh) {
        this.maNv = maNv;
        this.anh = anh;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.anhPhoToCC = anhPhoToCC;
        this.anhXNKcoTATS = anhXNKcoTATS;
        this.email = email;
        this.passwords = passwords;
        this.token = token;
        this.ngayBD = ngayBD;
        this.ngaySinh = ngaySinh;
    }

    public NhanVien(String anh, String hoTen, String gioiTinh, String soDT, String diaChi, String anhPhoToCC, String anhXNKcoTATS, String email, String passwords, String token, Date ngayBD, Date ngaySinh) {
        this.anh = anh;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.anhPhoToCC = anhPhoToCC;
        this.anhXNKcoTATS = anhXNKcoTATS;
        this.email = email;
        this.passwords = passwords;
        this.token = token;
        this.ngayBD = ngayBD;
        this.ngaySinh = ngaySinh;
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

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
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

    public String getAnhPhoToCC() {
        return anhPhoToCC;
    }

    public void setAnhPhoToCC(String anhPhoToCC) {
        this.anhPhoToCC = anhPhoToCC;
    }

    public String getAnhXNKcoTATS() {
        return anhXNKcoTATS;
    }

    public void setAnhXNKcoTATS(String anhXNKcoTATS) {
        this.anhXNKcoTATS = anhXNKcoTATS;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(Date ngayBD) {
        this.ngayBD = ngayBD;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}


