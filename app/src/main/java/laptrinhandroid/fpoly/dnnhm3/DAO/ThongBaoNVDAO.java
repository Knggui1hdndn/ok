package laptrinhandroid.fpoly.dnnhm3.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ThongBaoNV;

public class ThongBaoNVDAO {
    Connection objConn;

    public ThongBaoNVDAO() {
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        objConn = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }

    public int getCountThongBaoNhanVien(int maNV) throws SQLException {
        if (objConn != null) {
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT count(*) FROM  ThongBaoNV where maNV='" + maNV + "' and doc=0";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
            statement.close();// Đóng kết nối
        }
        return 0;
    }

    public boolean addThongBaoNhanVien(ThongBaoNV nhanVien) throws SQLException {

        String s1 = "Insert into ThongBaoNV(maNV, ngay,message,trangThai,doc) values ( ?,?,?,?,?)";
        if (objConn != null) {
            PreparedStatement preparedStatement = objConn.prepareStatement(s1);

            preparedStatement.setInt(1, nhanVien.getMaNV());
            preparedStatement.setString(2, nhanVien.getNgay());
            preparedStatement.setString(3, nhanVien.getMessage());
            preparedStatement.setString(4, nhanVien.getTrangThai());
            preparedStatement.setBoolean(5, nhanVien.getDoc());


            if (preparedStatement.executeUpdate() > 0) {
                preparedStatement.close();
                return true;
            }
        }
        return false;
    }

    public boolean deleteThongBaoNhanVien(int id) throws SQLException {
        String s1 = "Delete * from ThongBaoNV where id = ?";
        if (objConn != null) {
            PreparedStatement preparedStatement = objConn.prepareStatement(s1);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                preparedStatement.close();
                return true;
            }
        }
        return false;
    }

    public boolean updateThongBaoNhanVien(boolean doc, int id) throws SQLException {
        String s1 = "Update ThongBaoNV set doc=? where id=?";
        if (objConn != null) {
            PreparedStatement preparedStatement = objConn.prepareStatement(s1);
            preparedStatement.setBoolean(1, doc);
            preparedStatement.setInt(2, id);
            if (preparedStatement.executeUpdate() > 0) {
                preparedStatement.close();
                return true;
            }
        }
        return false;
    }

    public List<ThongBaoNV> getListNotiNhanVien(int maNV) throws SQLException {
        List<ThongBaoNV> list = new ArrayList<>();

        if (objConn != null) {
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT * FROM  ThongBaoNV where maNV='" + maNV + "'";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                ThongBaoNV thongBaoNV = new ThongBaoNV(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6));
                list.add(thongBaoNV);// Đọc dữ liệu từ ResultSet
                Log.d("ssssssssss", "getListNotiNhanVien: " + thongBaoNV);
            }
            statement.close();// Đóng kết nối
            Collections.reverse(list);
            return list;
        }
        return null;
    }
}
