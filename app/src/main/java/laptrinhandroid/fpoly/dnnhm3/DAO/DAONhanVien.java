package laptrinhandroid.fpoly.dnnhm3.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhanVien;
import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;

public class DAONhanVien {
    Connection objConn;

    public DAONhanVien() {
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        objConn = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }

    public boolean addNhanVien(NhanVien nhanVien) throws SQLException {
        Statement statement = objConn.createStatement();
         String s1 = "Insert into NhanVien(anh, hoTen,gioiTinh,soDT,diaChi,anhPhoToCC,anhXNKcoTATS,email,passwords,token,ngayBD,ngaySinh) values (" +
                "'" + nhanVien.getAnh() + "'," +
                "'" + nhanVien.getHoTen() + "'," +
                "'" + nhanVien.getGioiTinh() + "'," +
                "'" + nhanVien.getSoDT() + "'," +
                "'" + nhanVien.getDiaChi() + "'," +
                "'" + nhanVien.getAnhPhoToCC() + "'," +
                "'" + nhanVien.getAnhXNKcoTATS() + "'," +
                "'" + nhanVien.getEmail() + "'," +
                "'" + nhanVien.getPasswords() + "'," +
                "'" + nhanVien.getToken() + "'," +
                "'" + nhanVien.getNgayBD() + "'," +
                "'" + nhanVien.getNgaySinh() + "')";
        if (statement.executeUpdate(s1) > 0) {
            statement.close();
            return true;
        }
        statement.close();
        return false;
    }

    public boolean updateNhanVien(NhanVien nhanVien) throws SQLException {
        String sql = "UPDATE NhanVien  SET " + "anh = ?," + " hoTen =?," + "gioiTinh=?" + ",soDT=?" + ",diaChi=?" + ",anhPhoToCC=?" + ",anhXNKcoTATS=?" + ",email=?" + ",passwords=?" + ",token=?" + ",ngayBD=?" + ",ngaySinh=?" + "  WHERE maNv=?;";
        PreparedStatement preparedStatement = objConn.prepareStatement(sql);
        preparedStatement.setString(1, nhanVien.getAnh());
        preparedStatement.setString(2, nhanVien.getHoTen());
        preparedStatement.setString(3, nhanVien.getGioiTinh());
        preparedStatement.setString(4, nhanVien.getSoDT());
        preparedStatement.setString(5, nhanVien.getDiaChi());
        preparedStatement.setString(6, nhanVien.getAnhPhoToCC());
        preparedStatement.setString(7, nhanVien.getAnhXNKcoTATS());
        preparedStatement.setString(8, nhanVien.getEmail());
        preparedStatement.setString(9, nhanVien.getPasswords());
        preparedStatement.setString(10, nhanVien.getToken());
        preparedStatement.setDate(11,  nhanVien.getNgayBD());
        preparedStatement.setDate(12,  nhanVien.getNgaySinh());
        preparedStatement.setInt(13, nhanVien.getMaNv());
        if (preparedStatement.executeUpdate() > 0) {
            preparedStatement.close();
            return true;
        }
        return false;
    }

    public boolean deleteNhanVien(int maNv) throws SQLException {
        Statement statement = objConn.createStatement();
        String sql = "Delete from NhanVien where maNv='" + maNv + "'";
        if (statement.executeUpdate(sql) > 0) {
            statement.close();
            return true;
        }
        return false;
    }

    public List<NhanVien> getListNhanVien() throws SQLException {
        List<NhanVien> list = new ArrayList<>();
 
        if (objConn!=null){
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT * FROM  NhanVien where maNv>28 or maNv<28";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(new NhanVien(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getDate(12), rs.getDate(13)));// Đọc dữ liệu từ ResultSet
            }
            statement.close();// Đóng kết nối
            Log.d("ssssssssw", "getListNhanVien: "+list.size());
            return list;
        }

 
        return null;
    }
    public  NhanVien  getListNhanVien(int maNV) throws SQLException {
        NhanVien list = new NhanVien();

        if (objConn!=null){
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT * FROM  NhanVien where maNV='" + maNV + "'";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list=new NhanVien(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getDate(12), rs.getDate(13));// Đọc dữ liệu từ ResultSet
                statement.close();// Đóng kết nối
                return list;
            }
            return null;
        }


        return null;
    }
    public NhanVien checkLogin(String email,String password) throws SQLException {
        NhanVien nhanVien=null;
       if(objConn!=null){
           Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
           String sql = " SELECT * FROM  NhanVien where CONVERT(NVARCHAR(MAX), email)=N'"+email+"' and CONVERT(NVARCHAR(MAX), passwords)=N'"+password+"'";
           // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
           ResultSet rs = statement.executeQuery(sql);
           while (rs.next()) {
               nhanVien=new NhanVien(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getDate(12), rs.getDate(13));// Đọc dữ liệu từ ResultSet
           }
           statement.close();// Đóng kết nối
       return nhanVien;
       }
        return null;
    }

    public boolean checkLoginAdmin(String email, String password) throws SQLException {
        if (objConn != null) {
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT * FROM  admin where CONVERT(NVARCHAR(MAX), email)=N'" + email + "' and CONVERT(NVARCHAR(MAX), password)=N'" + password + "'";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
            ResultSet rs = statement.executeQuery(sql);
            Boolean aBoolean=false;
            while (rs.next()) {
                aBoolean=true;
             }
            statement.close();// Đóng kết nối
            return aBoolean;
        }
        return false;
    }

    public boolean updateAdmin(String email, String token) throws SQLException {
        String sql = "UPDATE admin  SET " + "token = ? " + " WHERE CONVERT(NVARCHAR(MAX), email)=N'"+email+"';";
        PreparedStatement preparedStatement = objConn.prepareStatement(sql);
         preparedStatement.setString(1, token);

        if (preparedStatement.executeUpdate() > 0) {
            preparedStatement.close();
            return true;
        }
        return false;
    }
}
