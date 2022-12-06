package laptrinhandroid.fpoly.dnnhm3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.KhachHang;
import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;

public class Daokhachhang {
    Connection objConn;

    public Daokhachhang() {
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        objConn = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }

    public boolean addKhachhang(KhachHang khachHang) throws SQLException  {
        Statement statement = null;
        try {
            statement = objConn.createStatement();
//            String s1="INSERT INTO KhachHang VALUES('unknow', 'Nguyen Van a', '038655787', '22 Mỹ Đình')";
            String s1 = "Insert into KhachHang( anh,hoTen,soDT,diaChi ) values (" +
                    "'" + khachHang.getAnh() + "'," +
                    "'" + khachHang.getHoTen() + "'," +
                    "'" + khachHang.getSoDT() + "'," +
                    "'" + khachHang.getDiaChi() + "')" ;
            if (statement.executeUpdate(s1) > 0) {
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateSanPham(KhachHang khachHang) {
        String sql = "UPDATE KhachHang  SET "  + " anh =?," + "hoTen=?" + ",soDT=?" + ",diaChi=?" + " WHERE maKhach='"+khachHang.getMaKhach()+"';";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = objConn.prepareStatement(sql);
            preparedStatement.setInt(1, khachHang.getMaKhach());
            preparedStatement.setString(2, khachHang.getHoTen());
            preparedStatement.setString(3, khachHang.getAnh());
            preparedStatement.setString(4, khachHang.getDiaChi());
            preparedStatement.setString(5, khachHang.getSoDT());

            if (preparedStatement.executeUpdate(sql) > 0) {
                preparedStatement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSanPham(int maKh) {
        Statement statement = null;
        try {
            statement = objConn.createStatement();
            String sql = "Delete from KhachHang where maKhach='" + maKh + "'";
            if (statement.executeUpdate(sql) > 0) {
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhachHang> getlistkhachang(String sql, String...selectionArgs) throws SQLException {
        List<KhachHang> list = new ArrayList<>();
        Statement statement = objConn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new KhachHang(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));// Đọc dữ liệu từ ResultSet
        }

        return list;
    }
    public KhachHang getIdKhachhang(String id) {
        String sql = "SELECT * FROM KhachHang WHERE maKhach='"+id+"'";
        List<KhachHang> listTV = new ArrayList<>();
        try {
            listTV = getlistkhachang(sql,id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTV.get(0);

    }
    public List<KhachHang> getAllkhachang() throws SQLException{
        String sql = "SELECT * FROM KhachHang"  ;
        try {
            return getlistkhachang(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
