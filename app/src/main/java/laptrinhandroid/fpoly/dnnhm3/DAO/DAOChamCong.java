package laptrinhandroid.fpoly.dnnhm3.DAO;

import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChamCong;
import laptrinhandroid.fpoly.dnnhm3.XuLiNgay.FormatDay;
import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;

public class DAOChamCong implements Serializable {
    Connection objConn;

    public DAOChamCong() {
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        objConn = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }

    public boolean addChamCong(ChamCong chamCong) throws SQLException {

         if (objConn != null) {

             String s1 = "Insert into ChamCong(maNV, gioBatDau,gioKetThuc,ngay,xacNhanChamCong) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = objConn.prepareStatement(s1);
            preparedStatement.setInt(1, chamCong.getMaNV());
            preparedStatement.setObject(2, chamCong.getGioBatDau());
            preparedStatement.setObject(3, chamCong.getGioKetThuc());
            preparedStatement.setDate(4, chamCong.getNgay());
            preparedStatement.setInt(5, chamCong.getXacNhanChamCong());
            if (preparedStatement.executeUpdate() > 0) {
                preparedStatement.close();
                return true;
            }
        }


        return false;
    }

    public boolean updateChamCong(ChamCong chamCong) throws SQLException {
        String sql = "UPDATE ChamCong  SET " +  " gioBatDau =?," + "gioKetThuc=?" + ",ngay=?" + ",xacNhanChamCong=?" +
                " WHERE ngay='" + chamCong.getNgay() + "' and maNV='"+chamCong.getMaNV()+"' ";
        if (objConn != null) {
            PreparedStatement preparedStatement = null;
            preparedStatement = objConn.prepareStatement(sql);
            preparedStatement.setObject(1, chamCong.getGioBatDau());
            preparedStatement.setObject(2, chamCong.getGioKetThuc());
            preparedStatement.setDate(3, chamCong.getNgay());
            preparedStatement.setInt(4, chamCong.getXacNhanChamCong());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }

         return false;
    }


    public List<CalendarDay> getListChamCong(int maNV, int trangThai) throws SQLException {
        List<CalendarDay> list = new ArrayList<>();
        if (objConn != null) {
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT  ngay FROM  ChamCong where maNV='" + maNV + "' AND xacNhanChamCong='" + trangThai + "'";
            if(trangThai==2){{
                sql = " SELECT  ngay FROM  ChamCong where maNV='" + maNV + "' AND (xacNhanChamCong='" + trangThai + "' or gioKetThuc IS  NULL)";
            }}
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được gioKetThuclưu trong ResultSet
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(FormatDay.calendarDay(rs.getDate(1)));// Đọc dữ liệu từ ResultSet
            }
            statement.close();// Đóng kết nối
        } else {
            return null;
        }

        return list;
    }
    public ChamCong getChamCong(int maNV ) throws SQLException {
         ChamCong chamCong=null;
        if (objConn != null) {
            String sql = "SELECT  *  FROM  ChamCong where maNV=? AND ngay=?";
            PreparedStatement statement = objConn.prepareStatement(sql);// Tạo đối tượng Statement.
            statement.setInt(1,maNV);
            statement.setDate(2,new Date(System.currentTimeMillis()) );
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                chamCong=new ChamCong(rs.getInt(1), rs.getInt(2), rs.getTime(3), rs.getTime(4), rs.getDate(5), rs.getInt(6));// Đọc dữ liệu từ ResultSet
            }
            statement.close();// Đóng kết nối
            return chamCong;
        }

        return null;
    }

    public List<ChamCong> getListChamCong(int maNV,String ngay) throws SQLException {
        List<ChamCong> getList;
        if (objConn != null) {
            getList = new ArrayList<>();
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT  * FROM  ChamCong where maNV='" + maNV + "' AND  ngay like '" + ngay + "%'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                getList.add(new ChamCong(rs.getInt(1), rs.getInt(2), rs.getTime(3), rs.getTime(4), rs.getDate(5), rs.getInt(6)));
            }
            statement.close();
            return getList;
        }


        return null;
    }
    public List<ChamCong> getListChamCong(int maNV) throws SQLException {
        List<ChamCong> getList;
        if (objConn != null) {
            getList = new ArrayList<>();
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT  * FROM  ChamCong where maNV='" + maNV + "' and gioKetThuc IS NOT NULL";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                getList.add(new ChamCong(rs.getInt(1), rs.getInt(2), rs.getTime(3), rs.getTime(4), rs.getDate(5), rs.getInt(6)));
            }
            statement.close();
            return getList;
        }


        return null;
    }
}
