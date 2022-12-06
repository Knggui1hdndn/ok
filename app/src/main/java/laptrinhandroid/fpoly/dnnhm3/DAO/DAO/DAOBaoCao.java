package laptrinhandroid.fpoly.dnnhm3.DAO.DAO;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.BaoCao;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonBan;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.HoaDonNhapKho;
import laptrinhandroid.fpoly.dnnhm3.Fragment.Entity.SanPham;
import laptrinhandroid.fpoly.dnnhm3.JDBC.DbSqlServer;

public class DAOBaoCao {
    SimpleDateFormat sfmNgayTrongNam = new SimpleDateFormat("yyyy-MM-dd");
    Connection connection;

    SimpleDateFormat sfmNgay = new SimpleDateFormat("dd");

    public DAOBaoCao(){
        DbSqlServer db = new DbSqlServer(); // hàm khởi tạo để mở kết nối
        connection = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<HoaDonBan> getListHoaDonBanByDay(int position, Date date) throws SQLException {

        List<HoaDonBan> list = new ArrayList<>();
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM HoaDonBan where " + getStringDate(1,position, date) ;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new HoaDonBan(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getFloat(5)));// Đọc dữ liệu từ ResultSet
        }
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<HoaDonNhapKho> getListHoaDonNhapByDay(int position, Date date) throws SQLException {

        List<HoaDonNhapKho> list = new ArrayList<>();
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM HoaDonNhapKho where " + getStringDate(2, position, date) ;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            list.add(new HoaDonNhapKho(rs.getInt(1),rs.getInt(2),
                    rs.getInt(3),rs.getDate(4),rs.getFloat(5))); //ddoc du lieu tu resultset
        }
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<BaoCao> getListLaiLo(int position, Date date) throws SQLException {
        List<BaoCao> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "select ChiTietHoaDon.tenSP, ChiTietHoaDon.soLuong, ChiTietHoaDon.donGia, ChiTietHoaDon.thanhTien, SanPham.giaNhap, SanPham.giaBan, \n" +
                "HoaDonBan.ngayBan from ChiTietHoaDon  INNER JOIN SanPham ON ChiTietHoaDon.maSp = SanPham.maSP INNER JOIN HoaDonBan ON ChiTietHoaDon.maHD = HoaDonBan.maHDBan\n" +
                "WHERE " + getStringDate(3,position, date);
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new BaoCao(rs.getString(1), rs.getInt(2),
                    rs.getFloat(3), rs.getFloat(4),
                    rs.getFloat(5), rs.getFloat(6),
                    rs.getDate(7)));// Đọc dữ liệu từ ResultSet
        }
        return list;
    }

    public List<SanPham> getListSanPham() throws SQLException {
        List<SanPham> list = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = " SELECT * FROM  SanPham";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new SanPham(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getFloat(4), rs.getFloat(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9)));// Đọc dữ liệu từ ResultSet
        }
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<BaoCao> getListTopSanPham(int position, Date date) throws SQLException {
        List<BaoCao> listTop = getListSumSanPham(position, date);
        List<BaoCao> listgetAll = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = null;
        for (int i = 0; i < listTop.size(); i++){
            String sql = "SELECT * FROM SanPham where maSP = " + listTop.get(i).getCthdMaSp();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                listgetAll.add(new BaoCao(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getFloat(4),
                        rs.getFloat(5),rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9),
                        listTop.get(i).getCthdTongSoLuong(), listTop.get(i).getCthdTongTien()));
            }
            rs.close();
        }
        return listgetAll;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<BaoCao> getListSumSanPham(int position, Date date) throws SQLException {
        List<BaoCao> listSum = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "select ChiTietHoaDon.maSp, SUM(ChiTietHoaDon.soLuong), SUM(ChiTietHoaDon.thanhTien) from ChiTietHoaDon " +
                "INNER JOIN SanPham ON ChiTietHoaDon.maSp = SanPham.maSP INNER JOIN HoaDonBan ON ChiTietHoaDon.maHD = HoaDonBan.maHDBan " +
                "WHERE " + getStringDate(3, position, date) + " " +
                "GROUP BY ChiTietHoaDon.maSp, SanPham.maSP, SanPham.loaiSP " +
                "ORDER BY SUM(ChiTietHoaDon.soLuong) DESC ";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            listSum.add(new BaoCao(rs.getInt(1), rs.getInt(2), rs.getDouble(3)));// Đọc dữ liệu từ ResultSet
        }
        rs.close();
        return listSum;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getStringDate(int isTable ,int position, Date date){
        String dateSQL = "";
        String ngay = "";

        switch (isTable){
            case 1: ngay = " ngayBan"; break;
            case 2: ngay = " ngayNhap"; break;
            case 3: ngay = " HoaDonBan.ngayBan"; break;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        LocalDate localDate = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));

        switch (position){
            case 0:{
                Log.e("ngay", localDate.toString());
                dateSQL =  ngay +  " = '" + localDate.toString() + "'";
                break;
            }
            case 1:{
                String firstDayThisWeek = getFirstDayThisWeek(localDate, cal);
                dateSQL = ngay + " >= DATEADD(day, 0 , '" + firstDayThisWeek +
                        "') and  " + ngay + " <= DATEADD(day, 7 ,  '" + firstDayThisWeek + "')";
                Log.e("ngay",  ": Ngay đầu tuần này " + firstDayThisWeek + " | " + dateSQL );
                break;
            }


            case 2:{
                String firstDayThisMonth = getFirstDayThisMonth(localDate);
                dateSQL =  ngay + " >= DATEADD(day, 0 , '" + firstDayThisMonth +
                        "') and  " + ngay + "  <= DATEADD(day, 30 ,  '" + firstDayThisMonth + "')";
                Log.e("ngay",  ": Ngay đầu tháng này " + firstDayThisMonth  );
                break;
            }
            case 3:{
                //lấy ngày đầu tuần này xong lùi ngày lại tuần trước bằng câu lệnh sql
                String firstDayThisWeek = getFirstDayThisWeek(localDate, cal);

                dateSQL =  ngay + "  >= DATEADD(day, -7 , '" + firstDayThisWeek +
                        "') and  " + ngay + " <= DATEADD(day, -1 ,  '" + firstDayThisWeek + "')";
                Log.e("ngay",  ": Ngay đầu tuần này xong lùi lại tuần trước" + firstDayThisWeek + " | " + dateSQL );
                break;
            }
            case 4:{
                //lấy ngày đầu tháng này xong lùi ngày lại tháng trước bằng câu lệnh sql
                String firstDayThisMonth = getFirstDayThisMonth(localDate);
                dateSQL =  ngay + " >= DATEADD(day, -30 , '" + firstDayThisMonth +
                        "') and  " + ngay + " <= DATEADD(day, -1 ,  '" + firstDayThisMonth + "')";
                Log.e("ngay",  ": Ngay đầu tháng này rồi lùi lại tháng trước" + firstDayThisMonth  );
                break;
            }
            case 5:

                Log.e("ngay", localDate.toString());

                Log.e("ngay",  "Ngay trong dã trọn " + localDate.with(WeekFields.of(Locale.TAIWAN).getFirstDayOfWeek())
                        .with(WeekFields.of(Locale.TAIWAN).weekOfWeekBasedYear(), cal.get(Calendar.WEEK_OF_YEAR)));
                dateSQL =   ngay + " = '" + localDate.toString() + "'";
                break;

        }
        return dateSQL;
    }

    // lấy ngày đầu tiên của tuần thứ bao nhiêu trong năm
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getFirstDayThisWeek(LocalDate localDate, Calendar calendar ){
        return localDate.with(WeekFields.of(Locale.US).getFirstDayOfWeek())
                .with(WeekFields.of(Locale.US).weekOfWeekBasedYear(), calendar.get(Calendar.WEEK_OF_YEAR)).toString();
    }

    // lấy ngày đầu tiên của tháng trong localDate
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getFirstDayThisMonth(LocalDate localDate){
        return localDate.withDayOfMonth(1).toString();
    }
}
