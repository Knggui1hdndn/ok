package laptrinhandroid.fpoly.dnnhm3.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;


public class DAOHoaDonNhap {
    Connection objConn;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");

    public DAOHoaDonNhap() {
        DbSqlServer db=new DbSqlServer();//hàm khởi tạo để mở kết nỗi
        objConn = db.openConnect();//tạo mới DAO thì mở kết nỗi csdl
    }
    public boolean addHoaDon(HoaDonNhapKho hoaDonNhapKho)throws SQLException{
        Statement statement=null;
        try{
            statement=objConn.createStatement();
            String h1="Insert into HoaDonNhapKho(maNV,maNcc,ngayNhap,tongTien) values ("
                    +hoaDonNhapKho.getMaNV()+","
                    +hoaDonNhapKho.getMaNCC()+",'"
                    +hoaDonNhapKho.getNgayNhap()+"',"
                    +hoaDonNhapKho.getTongTien()+")";
            if (statement.executeUpdate(h1)>0){
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("ssssssssw", "addHoaDon: "+e.getMessage());
        }
    return false;
    }

    public List<HoaDonNhapKho> getlistHD(String sql, String...selectionArgs) throws SQLException {
        List<HoaDonNhapKho> list = new ArrayList<>();
        Statement statement = objConn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new HoaDonNhapKho(rs.getInt(1),rs.getInt(2),rs.getInt(3)
                    ,rs.getDate(4),rs.getFloat(5)));// Đọc dữ liệu từ ResultSet
        }

        return list;
    }
    public HoaDonNhapKho getIdHD(String id) {
        String sql = "SELECT * FROM HoaDonNhapKho WHERE maHDNhap="+id+"";
        List<HoaDonNhapKho> listHD = null;
        try {
            listHD = getlistHD(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listHD.get(0);
    }

    public List<HoaDonNhapKho> getListHoaDonNhap() throws SQLException{
        List<HoaDonNhapKho> list =new ArrayList<>();
        Statement statement=objConn.createStatement();
        String sql=" select * from HoaDonNhapKho";
        ResultSet rs=statement.executeQuery(sql);
        while (rs.next()){
            list.add(new HoaDonNhapKho(rs.getInt(1),rs.getInt(2),rs.getInt(3)
                    ,rs.getDate(4),rs.getFloat(5))); //ddoc du lieu tu resultset
        }
        statement.close();
        return list;
    }

}
