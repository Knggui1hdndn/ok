package laptrinhandroid.fpoly.dnnhm3.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
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
            Log.d("ssssssssw", "Insert: "+e.getMessage());

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



    public List<HoaDonBan> getListHoadonban() throws SQLException {
     List<HoaDonBan> hoaDonBans= new ArrayList<>();
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = " SELECT * FROM  HoaDonBan";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            HoaDonBan hoaDonBan=new HoaDonBan(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getFloat(5));
            hoaDonBans.add(hoaDonBan);
        }
    statement.close();// Đóng kết nối
        return hoaDonBans;
    }










}
