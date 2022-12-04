package laptrinhandroid.fpoly.dnnhm3.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;

public class DAOhoadon {
    Connection connection;
    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
    public DAOhoadon() {
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        connection = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }
    public boolean Insert(HoaDonBan objUser) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            String s1="INSERT INTO KhachHang VALUES('unknow', 'Nguyen Van a', '038655787', '22 Mỹ Đình')";
        String s1 = "Insert into HoaDonBan(maNV,maKH,ngayBan,tongTien ) values (" +
                "'" + objUser.getMaNV() + "'," +
                "'" + objUser.getMaKH() + "'," +
                "'" + format.format(objUser.getNgayBan()) + "'," +
                "'" + objUser.getTongTien() + "')" ;
            if (statement.executeUpdate(s1) > 0) {
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(HoaDonBan hdb) throws SQLException{
        Statement statement=connection.createStatement();
        String sql="Update HoaDonBan set MaHDBan="+hdb.getMaHDBan()+ hdb.getMaNV()+hdb.getMaKH()+hdb.getNgayBan()+hdb.getTongTien()+"";
         if(statement.executeUpdate(sql)>0){
             connection.close();
             return true;
         }else {
             connection.close();
             return false;
         }
    }
    public boolean Delete(HoaDonBan objUser) throws SQLException {
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "delete from HoaDonBan where MaHDBan = " + objUser.getMaHDBan();
        if (statement.executeUpdate(sql) > 0){
            connection.close();
            return true;
        }
        else
            connection.close();
        return false;
    }



    public List<HoaDonBan> getListHoadonban(String sql, String...selectionArgs) throws SQLException {
        List<HoaDonBan> list = new ArrayList<>();
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
//         sql = " SELECT * FROM  HoaDonBan";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new HoaDonBan(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getFloat(5)));// Đọc dữ liệu từ ResultSet
        }
    statement.close();// Đóng kết nối
        return list;
    }
    public int getIdHodon(int hoaDonBan) {
        int maHD = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM HoaDonBan  where MaHDBan =5";
            ResultSet rs = statement.executeQuery(sql);
            maHD = rs.getInt(1);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maHD;
    }

//    public HoaDonBan getIdHodon(String id) {
//        String sql = "SELECT * FROM HoaDonBan WHERE MaHDBan=?";
//        List<HoaDonBan> listTV = null;
//        try {
//            listTV = getListHoadonban(sql,id);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return listTV.get(0);
//    }
    public HoaDonBan getIdhoadonban(String id) {
        String sql = "SELECT * FROM HoaDonBan WHERE MaHDBan=?";
        List<HoaDonBan> listTV = null;
        try {
            listTV = getListHoadonban(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTV.get(0);
    }
    public List<HoaDonBan> getAllhoadon() throws SQLException{
        String sql = "SELECT * FROM HoaDonBan"  ;
        try {
            return getListHoadonban(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
