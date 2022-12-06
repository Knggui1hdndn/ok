package laptrinhandroid.fpoly.dnnhm3.DAO.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;


public class DAOHoaDonNhap {
    Connection objConn;

    public DAOHoaDonNhap() {
        DbSqlServer db=new DbSqlServer();//hàm khởi tạo để mở kết nỗi
        objConn = db.openConnect();//tạo mới DAO thì mở kết nỗi csdl
    }
    public boolean addHoaDon(HoaDonNhapKho hoaDonNhapKho){
        Statement statement=null;
        try{
            statement=objConn.createStatement();
            String h1="insert into HoaDonNhapKho(";
            if (statement.executeUpdate(h1)>0){
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return false;
    }

    public List<HoaDonNhapKho> getListHoaDonNhap() throws SQLException{
        List<HoaDonNhapKho> list =new ArrayList<>();
        Statement statement=objConn.createStatement();
        String sql="select * from HoaDonNhapKho";
        ResultSet rs=statement.executeQuery(sql);
        while (rs.next()){
            list.add(new HoaDonNhapKho(rs.getInt(1),rs.getInt(2),
                    rs.getInt(3),rs.getDate(4),rs.getFloat(5))); //ddoc du lieu tu resultset
        }
        statement.close();
        return list;
    }
}
