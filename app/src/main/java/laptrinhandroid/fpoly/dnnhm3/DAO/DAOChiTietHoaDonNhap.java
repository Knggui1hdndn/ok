package laptrinhandroid.fpoly.dnnhm3.DAO;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ChiTietHoaDonNhap;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;

public class DAOChiTietHoaDonNhap {
    Connection connection;
    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
    public DAOChiTietHoaDonNhap() {
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        connection = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }
    public boolean Insertchitiethoadonnhap(ChiTietHoaDonNhap objUser) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            String s1="INSERT INTO KhachHang VALUES('unknow', 'Nguyen Van a', '038655787', '22 Mỹ Đình')";
            String s1 = "Insert into ChiTietHoaDonNhap(maHDNhap,maSp,anh,tenSP,soLuong,donGia,thanhTien ) values (" +
                    "'" + objUser.getMaHD() + "'," +
                    "'" + objUser.getMaSp() + "'," +
                    "'" + objUser.getAnh() + "'," +
                    "'" + objUser.getTenSP() + "'," +
                    "'" + objUser.getSoLuong() + "'," +
                    "'" + objUser.getDonGia() + "'," +
                    "'" + objUser.getThanhTien() + "')" ;
            if (statement.executeUpdate(s1) > 0) {
                // statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("ssssssssssssssss", "Insertchitiethoadonnhap: "+e.getMessage());
        }
        return false;
    }

    public boolean update(ChiTietHoaDonNhap hdn) throws SQLException{
        Statement statement=connection.createStatement();
        String sql="Update ChiTietHoaDonNhap set id="+hdn.getMaHD()+ hdn.getMaSp()+hdn.getAnh()+hdn.getTenSP()+hdn.getSoLuong()+hdn.getDonGia()+hdn.getThanhTien()+"";
        if(statement.executeUpdate(sql)>0){
            //  connection.close();
            return true;
        }else {
            //  connection.close();
            return false;
        }
    }
    @SuppressLint("SuspiciousIndentation")
    public boolean Delete(ChiTietHoaDonNhap objUser) throws SQLException {
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "delete from ChiTietHoaDonNhap where id = " + objUser.getId();
        if (statement.executeUpdate(sql) > 0){
            connection.close();
            return true;
        }
        else
            //  connection.close();
            return false;
    }



    public List<ChiTietHoaDonNhap> getListchitietHoadonnhap(String sql) throws SQLException {
        List<ChiTietHoaDonNhap> list = new ArrayList<>();
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
//        sql = " SELECT * FROM  ChiTietHoaDonNhap";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new ChiTietHoaDonNhap(rs.getInt(1), rs.getInt(2),rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6),rs.getFloat(7),rs.getFloat(8)));// Đọc dữ liệu từ ResultSet
        }
//        connection.close();// Đóng kết nối
        return list;
    }

    public List<ChiTietHoaDonNhap> getIdchitietHoadonnhap(@NonNull HoaDonNhapKho hoaDonNhapKho) throws SQLException {
       // Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "SELECT * FROM ChiTietHoaDonNhap WHERE maHDNhap = "+hoaDonNhapKho.getMaHDNhap()+"";
//        ResultSet rs = statement.executeQuery(sql);
//        while (rs.next()) {
//            list.add(new ChiTietHoaDonNhap(rs.getInt(1), rs.getInt(2),rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6),rs.getFloat(7),rs.getFloat(8)));// Đọc dữ liệu từ ResultSet
//        }
        try {
            return getListchitietHoadonnhap(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public List<ChiTietHoaDonNhap> getAllchitiethoadonnhap() throws SQLException{
        String sql = "SELECT * FROM ChiTietHoaDonNhap"  ;
        try {
            return getListchitietHoadonnhap(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

