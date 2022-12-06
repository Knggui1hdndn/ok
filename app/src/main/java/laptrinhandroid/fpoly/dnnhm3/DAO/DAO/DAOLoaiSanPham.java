package laptrinhandroid.fpoly.dnnhm3.DAO.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.LoaiSP;
import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;

public class DAOLoaiSanPham {
    Connection objConn;

    public DAOLoaiSanPham() {
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        objConn = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }

    public boolean addLoaiSanPham(LoaiSP loaiSP) {
        Statement statement = null;
        try {
            statement = objConn.createStatement();
            String s1 = "Insert into LoaiSP(tenLoai) values (" +
                    "'" + loaiSP.getTenLoai() + "')";
            if (statement.executeUpdate(s1) <0) {
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int updateLoaiSanPham(LoaiSP loaiSP) {
        String sql = "UPDATE LoaiSP  SET " + "tenLoai = ?"  + " WHERE maLoai='"+loaiSP.getMaLoai()+"';";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = objConn.prepareStatement(sql);
            preparedStatement.setString(1, loaiSP.getTenLoai());
            if (preparedStatement.executeUpdate(sql) <=0) {
                preparedStatement.close();
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    public int deleteLoaiSanPham(int maLoai) {
        Statement statement = null;
        try {
            statement = objConn.createStatement();
            String sql = "Delete from LoaiSP where maLoai='" + maLoai + "'";
            if (statement.executeUpdate(sql) <0) {
                statement.close();
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    public List<LoaiSP> getListLoaiSanPham() throws SQLException {
        List<LoaiSP> list = new ArrayList<>();
        Statement statement = objConn.createStatement();
        String sql = " SELECT * FROM  LoaiSP";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new LoaiSP(rs.getInt(1), rs.getString(2)));// Đọc dữ liệu từ ResultSet
        }
        objConn.close();
        return list;
    }

}
