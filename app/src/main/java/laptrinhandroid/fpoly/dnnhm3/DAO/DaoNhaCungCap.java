package laptrinhandroid.fpoly.dnnhm3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.NhaCungCap;
import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;

public class DaoNhaCungCap {
    Connection objConn;

    public DaoNhaCungCap() {
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        objConn = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }

    public boolean addNhaCungCap(NhaCungCap nhaCungCap) throws SQLException {
        Statement statement = null;
        try {
            statement = objConn.createStatement();
//            String s1="INSERT INTO KhachHang VALUES('unknow', 'Nguyen Van a', '038655787', '22 Mỹ Đình')";
            String s1 = "Insert into NhaCungCap( anh,hoTen,soDT,diaChi ) values (" +
                    "'" + nhaCungCap.getAnh() + "'," +
                    "'" + nhaCungCap.getHoTen() + "'," +
                    "'" + nhaCungCap.getSoDT() + "'," +
                    "'" + nhaCungCap.getDiaChi() + "')" ;
            if (statement.executeUpdate(s1) > 0) {
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateNhaCung(NhaCungCap nhaCungCap) {
        String sql = "UPDATE NhaCungCap  SET "  + " anh =?," + "hoTen=?" + ",soDT=?" + ",diaChi=?" + " WHERE maNCC='"+nhaCungCap.getMaNCC()+"';";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = objConn.prepareStatement(sql);
            preparedStatement.setInt(1, nhaCungCap.getMaNCC());
            preparedStatement.setString(2, nhaCungCap.getHoTen());
            preparedStatement.setString(3, nhaCungCap.getAnh());
            preparedStatement.setString(4, nhaCungCap.getDiaChi());
            preparedStatement.setString(5, nhaCungCap.getSoDT());

            if (preparedStatement.executeUpdate(sql) > 0) {
                preparedStatement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNhaCung(int maNCC) {
        Statement statement = null;
        try {
            statement = objConn.createStatement();
            String sql = "Delete from NhaCungCap where maNCC='" + maNCC + "'";
            if (statement.executeUpdate(sql) > 0) {
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<NhaCungCap> getlistNCC(String sql, String...selectionArgs) throws SQLException {
        List<NhaCungCap> list = new ArrayList<>();
        Statement statement = objConn.createStatement();
//        sql = " SELECT * FROM  NhaCungCap";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new NhaCungCap(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));// Đọc dữ liệu từ ResultSet
        }

        return list;
    }
    public NhaCungCap getIdTV(String id) {
        String sql = "SELECT * FROM NhaCungCap WHERE maKhach=?";
        List<NhaCungCap> listTV = null;
        try {
            listTV = getlistNCC(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTV.get(0);
    }
    public List<NhaCungCap> getAllNhaCung() throws SQLException{
        String sql = "SELECT * FROM NhaCungCap"  ;
        try {
            return getlistNCC(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
