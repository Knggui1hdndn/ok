package laptrinhandroid.fpoly.dnnhm3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.ThongBaoAdmin;

public class ThongBaoAdminDAO {
    Connection objConn;

    public ThongBaoAdminDAO() {
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        objConn = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }

    public boolean addThongBaoAdmin(ThongBaoAdmin thongBaoFromAdmin) throws SQLException {

        String s1 = "Insert into ThongBaoAdmin(maNV, ngay,doc) values ( ?,?,?)";
        if(objConn!=null){
            PreparedStatement preparedStatement=objConn.prepareStatement(s1);

            preparedStatement.setInt(1,thongBaoFromAdmin.getMaNV());
            preparedStatement.setString(2,thongBaoFromAdmin.getNgay());
            preparedStatement.setBoolean(3,thongBaoFromAdmin.getDoc());
            if (preparedStatement.executeUpdate() > 0) {
                preparedStatement.close();
                return true;
            }
        }
        return false;
    }
    public boolean updateThongBaoAdmin(boolean doc,int id) throws SQLException {

        String s1 = "Update ThongBaoAdmin set doc=? where id=?";
        if(objConn!=null){
            PreparedStatement preparedStatement=objConn.prepareStatement(s1);

            preparedStatement.setBoolean(1,doc);
            preparedStatement.setInt(2,id);



            if (preparedStatement.executeUpdate() > 0) {
                preparedStatement.close();
                return true;
            }
        }
        return false;
    }
    public boolean deleteThongBaoAdmin(int id) throws SQLException {
        String s1 = "Delete * from ThongBaoAdmin where id = ?";
        if(objConn!=null){
            PreparedStatement preparedStatement=objConn.prepareStatement(s1);
           preparedStatement.setInt(1,id);
            if (preparedStatement.executeUpdate() > 0) {
                preparedStatement.close();
                return true;
            }
        }
        return false;
    }
    public int getCountThongBaoAdmin() throws SQLException {
        if (objConn!=null){
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT count(*) FROM  ThongBaoAdmin where doc=0";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                return rs.getInt(1);
            }
            statement.close();// Đóng kết nối
        }
        return 0;
    }
    public List<ThongBaoAdmin> getListThongBaoAdmin() throws SQLException {
        List<ThongBaoAdmin> list = new ArrayList<>();

        if (objConn!=null){
            Statement statement = objConn.createStatement();// Tạo đối tượng Statement.
            String sql = " SELECT * FROM  ThongBaoAdmin";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(new ThongBaoAdmin(rs.getInt(1), rs.getInt(2), rs.getString(3),rs.getBoolean(4)));// Đọc dữ liệu từ ResultSet
            }
            statement.close();// Đóng kết nối
            Collections.reverse(list);
            return list;
        }


        return null;
    }
}
