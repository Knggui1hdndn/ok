package laptrinhandroid.fpoly.dnnhm3.DAO;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.BangLuong;

import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;

public class DAOBangLuong {
    Connection objConn;

    public DAOBangLuong() {
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        objConn = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }

    public boolean addBangLuong(BangLuong bangLuong) throws SQLException {
        BangLuong chamCong = null;
        if (objConn != null) {
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = "select * from BangLuong where ngayThang like '" + bangLuong.getNgayThang() + "%'";
            ResultSet rs = statement.executeQuery(sql);
            boolean b = true;
            while (rs.next()) {
                b = false;
            }
            if (b) {
                String s1 = "Insert into BangLuong(maNV, luongCB,ungLuong,thuong,ngayThang) values ( ?,?,?,?,?)";
                PreparedStatement preparedStatement = objConn.prepareStatement(s1);
                preparedStatement.setInt(1, bangLuong.getMaNV());
                preparedStatement.setFloat(2, bangLuong.getLuongCB());
                preparedStatement.setFloat(3, bangLuong.getUngLuong());
                preparedStatement.setString(5, bangLuong.getNgayThang());
                preparedStatement.setFloat(4, bangLuong.getThuong());

                if (preparedStatement.executeUpdate() > 0) {
                    preparedStatement.close();
                    return true;
                }
                preparedStatement.close();
            }
        }
        return false;
    }

    public boolean updateBangLuong(BangLuong bangLuong  ) throws SQLException {
        String sql = "UPDATE BangLuong  SET " + " luongCB =?," + "ungLuong=?" + ",thuong=?" + ",ngayThang=?" + " WHERE maNv='" + bangLuong.getMaNV() + "' and id='" + bangLuong.getId() + "';";
        PreparedStatement preparedStatement = objConn.prepareStatement(sql);
        preparedStatement.setFloat(1, bangLuong.getLuongCB());
        preparedStatement.setFloat(2, bangLuong.getUngLuong());
        preparedStatement.setFloat(3, bangLuong.getThuong());
        preparedStatement.setString(4, bangLuong.getNgayThang());
        if (preparedStatement.executeUpdate() > 0) {
            preparedStatement.close();
            return true;
        }
        return false;
    }

    public boolean deleteBangLuong(int maNv) throws SQLException {
        Statement statement = objConn.createStatement();
        String sql = "Delete from BangLuong where maNv='" + maNv + "'";
        if (statement.executeUpdate(sql) > 0) {
            statement.close();
            return true;
        }
        return false;
    }

    public BangLuong getBangLuong(int maNV) throws SQLException {
        BangLuong chamCong = null;
        if (objConn != null) {
            CalendarDay calendarDay=CalendarDay.today();
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT  * FROM  BangLuong where maNV='" + maNV + "' and CONVERT(NVARCHAR(MAX), ngayThang)=N'" + calendarDay.getYear()+"-"+calendarDay.getMonth() + "'  ";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {

                chamCong = new BangLuong(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4), rs.getFloat(5), rs.getString(6));
            }
            statement.close();
            if (chamCong != null) {
                return chamCong;
            }
        }


        return null;
    }

    public BangLuong getBangLuong(int maNV, String ngayThang) throws SQLException {
        BangLuong chamCong = null;
        if (objConn != null) {
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT  * FROM  BangLuong where maNV='" + maNV + "' and ngayThang='" + ngayThang + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                chamCong = new BangLuong(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4), rs.getFloat(5), rs.getString(6));
            }
            statement.close();
            if (chamCong != null) {
                return chamCong;
            }
        }


        return null;
    }

    public HashMap<Integer, Integer> getTongSoGioLamNhanVien() throws SQLException {
        CalendarDay calendarDay=CalendarDay.today();
        String s = " select maNV,sum(DATEDIFF(hour, gioBatDau,gioKetThuc))  from ChamCong where xacNhanChamCong=1  and MONTH(ngay)='"+calendarDay.getMonth()+"' group by maNV order by sum(DATEDIFF(hour, gioBatDau,gioKetThuc)) DESC";
        HashMap<Integer, Integer> list;
        if (objConn != null) {
            list = new HashMap<>();
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
            ResultSet rs = statement.executeQuery(s);
            while (rs.next()) {
                list.put(rs.getInt(1), rs.getInt(2));
            }
            statement.close();// Đóng kết nối
            return list;
        }
        return null;
    }

    public List<BangLuong> getListBangLuong(int maNV, String ngay) throws SQLException {
        List<BangLuong> list = new ArrayList<>();
        if (objConn != null) {
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = "SELECT * FROM  BangLuong where maNV='" + maNV + "' and   ngayThang like '" + ngay + "%'";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(new BangLuong(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4), rs.getFloat(5), rs.getString(6)));
                // Đọc dữ liệu từ ResultSet
            }
            statement.close();// Đóng kết nối
            return list;
        }
        return null;
    }

    public List<BangLuong> getListBangLuong(int maNV) throws SQLException {
        List<BangLuong> list = new ArrayList<>();
        if (objConn != null) {
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = "SELECT * FROM  BangLuong where maNV='" + maNV + "'  ";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(new BangLuong(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4), rs.getFloat(5), rs.getString(6)));
                // Đọc dữ liệu từ ResultSet
            }
            statement.close();// Đóng kết nối
            return list;
        }
        return null;
    }
}
