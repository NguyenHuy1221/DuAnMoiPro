package com.example.duanmaupro.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmaupro.Database.DbHelper;
import com.example.duanmaupro.model.ChiTietHoaDon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ChiTietHoaDonDao {

    DbHelper dbHelper;

    public ChiTietHoaDonDao(Context context){
        this.dbHelper = new DbHelper(context);
    }

    public long themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("masp", chiTietHoaDon.getSanPham().getMasp());
        values.put("idhoadon", chiTietHoaDon.getHoaDon().getIdhoadon());
        values.put("soluong", chiTietHoaDon.getSoluong());
        values.put("dongia", chiTietHoaDon.getDongia());

        long result = sqLiteDatabase.insert("CTHD", null, values);

        sqLiteDatabase.close();
        return result;
    }
    public void updateSanPham(int idhoadon1 , int idhoadon2) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String query = "UPDATE SAN_PHAM SET soluong = soluong - (SELECT soluong FROM CTHD WHERE CTHD.masp = SAN_PHAM.masp AND CTHD.idhoadon = ?) WHERE masp IN (SELECT masp FROM CTHD WHERE idhoadon = ?)";
        sqLiteDatabase.execSQL(query, new String[] {String.valueOf(idhoadon1), String.valueOf(idhoadon2)});
        sqLiteDatabase.close();
    }





//    public ArrayList<ChiTietHoaDon> layDanhSachChiTietHoaDon() {
//        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
//        String query = ("SELECT CTHD.idcthd, SAN_PHAM.masp, SAN_PHAM.tensp, SAN_PHAM.giasp, SAN_PHAM.soluong, SAN_PHAM.imagesp, SAN_PHAM.size, HOA_DON.idhoadon, HOA_DON.idkhachhang, HOA_DON.idnhanvien, HOA_DON.ngay, HOA_DON.tongtien, CTHD.soluong, CTHD.dongia, KHACH_HANG.tenkhachhang, KHACH_HANG.sdt, KHACH_HANG.diachi\n" +
//                "                FROM CTHD \n" +
//                "                INNER JOIN SAN_PHAM ON CTHD.masp = SAN_PHAM.masp \n" +
//                "                INNER JOIN HOA_DON ON CTHD.idhoadon = HOA_DON.idhoadon \n" +
//                "                INNER JOIN KHACH_HANG ON HOA_DON.idkhachhang = KHACH_HANG.idkhachhang");
//
//        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                int idcthd = cursor.getInt(0);
//                int masp = cursor.getInt(1);
//                String tensp = cursor.getString(2);
//                int giasp = cursor.getInt(3);
//                int solg = cursor.getInt(4);
//                String img = cursor.getString(5);
//                String size = cursor.getString(6);
//                int idhd = cursor.getInt(7);
//                int idkhachhang = cursor.getInt(8);
//                int idnhanvien = cursor.getInt(9);
//                String ngay = cursor.getString(10);
//                String tongtien = cursor.getString(11);
//                int soluong = cursor.getInt(12);
//                int dongia = cursor.getInt(13);
//                String tenKhachHang = cursor.getString(14);
//                String sdtKhachHang = cursor.getString(15);
//                String diaChiKhachHang = cursor.getString(16);
//
//                sanPham sanPham = new sanPham(masp, tensp, giasp, solg, img, size);
//                HoaDon hoaDon = new HoaDon(idhd, idkhachhang, idnhanvien, ngay, tongtien);
//                KhachHang khachHang = new KhachHang(tenKhachHang, sdtKhachHang, diaChiKhachHang);
//                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(idcthd, sanPham, hoaDon, khachHang, soluong, dongia);
//
//                list.add(chiTietHoaDon);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        sqLiteDatabase.close();
//        return list;
//    }


//    public ArrayList<ChiTietHoaDon> layDanhSachChiTietHoaDon() {
//        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
//        String query = "SELECT CTHD.idcthd, CTHD.masp, CTHD.idhoadon, CTHD.soluong, CTHD.dongia, " +
//                "SAN_PHAM.tensp, SAN_PHAM.giasp, SAN_PHAM.soluong AS sanpham_soluong, SAN_PHAM.imagesp, SAN_PHAM.size, " +
//                "HOA_DON.idkhachhang, HOA_DON.idnhanvien, HOA_DON.ngay, HOA_DON.tongtien, " +
//                "KHACH_HANG.tenkhachhang, KHACH_HANG.sdt, KHACH_HANG.diachi, HOA_DON.idmkm " +
//                "FROM CTHD " +
//                "INNER JOIN SAN_PHAM ON CTHD.masp = SAN_PHAM.masp " +
//                "INNER JOIN HOA_DON ON CTHD.idhoadon = HOA_DON.idhoadon " +
//                "INNER JOIN KHACH_HANG ON HOA_DON.idkhachhang = KHACH_HANG.idkhachhang";
//
//        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                int idcthd = cursor.getInt(0);
//                int masp = cursor.getInt(1);
//                int idhd = cursor.getInt(2);
//                int soluong = cursor.getInt(3);
//                int dongia = cursor.getInt(4);
//                String tensp = cursor.getString(5);
//                int giasp = cursor.getInt(6);
//                int sanpham_soluong = cursor.getInt(7);
//                String img = cursor.getString(8);
//                String size = cursor.getString(9);
//                int idkhachhang = cursor.getInt(10);
//                int idnhanvien = cursor.getInt(11);
//                String ngay = cursor.getString(12);
//                String tongtien = cursor.getString(13);
//                String tenKhachHang = cursor.getString(14);
//                String sdtKhachHang = cursor.getString(15);
//                String diaChiKhachHang = cursor.getString(16);
//                int idmakhuyenmai = cursor.getInt(17);
//
//                sanPham sanPham = new sanPham(masp, tensp, giasp, sanpham_soluong, img, size);
//                HoaDon hoaDon = new HoaDon(idhd, idkhachhang, idnhanvien, ngay, tongtien,idmakhuyenmai);
//                KhachHang khachHang = new KhachHang(tenKhachHang, sdtKhachHang, diaChiKhachHang);
//                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(idcthd, sanPham, hoaDon, khachHang, soluong, dongia);
//
//                list.add(chiTietHoaDon);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        sqLiteDatabase.close();
//        return list;
//    }





//    public List<HoaDon> layDanhSachHoaDonVaSanPham() {
//        List<HoaDon> list = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
//        String query = "SELECT HOA_DON.idhoadon, HOA_DON.idkhachhang, HOA_DON.idnhanvien, HOA_DON.ngay, HOA_DON.tongtien, " +
//                "GROUP_CONCAT(SAN_PHAM.tensp) AS danhSachSanPham " +
//                "FROM HOA_DON " +
//                "INNER JOIN CTHD ON HOA_DON.idhoadon = CTHD.idhoadon " +
//                "INNER JOIN SAN_PHAM ON CTHD.masp = SAN_PHAM.masp " +
//                "GROUP BY HOA_DON.idhoadon";
//
//        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                int idhoadon = cursor.getInt(0);
//                int idkhachhang = cursor.getInt(1);
//                int idnhanvien = cursor.getInt(2);
//                String ngay = cursor.getString(3);
//                String tongtien = cursor.getString(4);
//                String danhSachSanPhamString = cursor.getString(5);
//
//                // Chuyển chuỗi danh sách sản phẩm thành danh sách Java
//                List<String> danhSachSanPham = new ArrayList<>(Arrays.asList(danhSachSanPhamString.split(",")));
//
//                HoaDon hoaDon = new HoaDon(idhoadon, idkhachhang, idnhanvien, ngay, tongtien);
//                hoaDon.setDanhSachSanPham(danhSachSanPham);
//                list.add(hoaDon);
//
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        sqLiteDatabase.close();
//        return list;
//    }


    public List<String> layDanhSachHoaDonChiTiet() {
        List<String> thongTinHoaDonList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String query = "SELECT KHACH_HANG.tenkhachhang, KHACH_HANG.sdt, KHACH_HANG.diachi, HOA_DON.ngay, " +
                "GROUP_CONCAT(SAN_PHAM.tensp || ' SL ' || CTHD.soluong) AS danhSachSanPham, " +
                "SUM(CTHD.soluong * SAN_PHAM.giasp) AS tongTien " +
                "FROM HOA_DON " +
                "INNER JOIN KHACH_HANG ON HOA_DON.idkhachhang = KHACH_HANG.idkhachhang " +
                "INNER JOIN CTHD ON HOA_DON.idhoadon = CTHD.idhoadon " +
                "INNER JOIN SAN_PHAM ON CTHD.masp = SAN_PHAM.masp " +
                "GROUP BY HOA_DON.idhoadon";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String tenKhachHang = cursor.getString(0);
                String sdtKhachHang = cursor.getString(1);
                String diaChiKhachHang = cursor.getString(2);
                String ngay = cursor.getString(3);
                String danhSachSanPhamString = cursor.getString(4);
                double tongTien = cursor.getDouble(5);

                // Xử lý chuỗi danh sách sản phẩm và tạo danh sách sản phẩm
                List<String> danhSachSanPham = new ArrayList<>(Arrays.asList(danhSachSanPhamString.split(", ")));

                // Tạo thông tin hóa đơn
                String thongTinHoaDon = "Tên Khách Hàng: " + tenKhachHang +
                        "\nSố Điện Thoại: " + sdtKhachHang +
                        "\nĐịa Chỉ: " + diaChiKhachHang +
                        "\nNgày: " + ngay +
                        "\nDanh Sách Sản Phẩm: " + danhSachSanPham +
                        "\nTổng Tiền: " + tongTien;

                thongTinHoaDonList.add(thongTinHoaDon);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return thongTinHoaDonList;
    }



    public double getDoanhThuTheoNgay() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        double doanhThu = 0;
        String sSQL = "SELECT SUM(total_price) FROM ( " +
                "    SELECT SPDC.masp, SUM(CTHD.soluong * SPDC.giasp) as total_price " +
                "    FROM CTHD " +
                "    INNER JOIN SPDC ON CTHD.masp = SPDC.masp " +
                "    INNER JOIN HOA_DON ON CTHD.idhoadon = HOA_DON.idhoadon " +
                "    WHERE date(HOA_DON.ngay) = date('now') " +
                "    GROUP BY SPDC.masp " +
                ") AS tmp";

        Cursor c = sqLiteDatabase.rawQuery(sSQL, null);

        if (c.moveToFirst()) {


            int columnIndex = c.getColumnIndex("SUM(total_price)");

            if (columnIndex != -1) {
                doanhThu = c.getDouble(columnIndex);
            } else {
                // Handle the case where the column doesn't exist
                Log.e("Error", "Column 'SUM(total_price)' not found");
            }
        }

        c.close();
        return doanhThu;
    }


    public double thongKeTheoNgay(String ngay) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) AS total FROM HOA_DON WHERE ngay = ?";
        String[] selectionArgs = { ngay };
        Cursor c = sqLiteDatabase.rawQuery(sSQL, selectionArgs);
        if (c.moveToFirst()) {
            int columnIndex = c.getColumnIndex("total");
            if (columnIndex != -1) {
                doanhThu = c.getDouble(columnIndex);
            } else {
                // Handle the case where the column doesn't exist
                Log.e("Error", "Column 'total' not found");
            }
        }
        c.close();
        return doanhThu;
    }






    public List<String> layThongTinHoaDon() {
        List<String> thongTinHoaDonList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String query = "SELECT KHACH_HANG.tenkhachhang, KHACH_HANG.sdt, KHACH_HANG.diachi, HOA_DON.ngay, " +
                "GROUP_CONCAT(SAN_PHAM.tensp) AS danhSachSanPham, CTHD.soluong, SAN_PHAM.giasp, HOA_DON.tongtien " +
                "FROM HOA_DON " +
                "INNER JOIN KHACH_HANG ON HOA_DON.idkhachhang = KHACH_HANG.idkhachhang " +
                "INNER JOIN CTHD ON HOA_DON.idhoadon = CTHD.idhoadon " +
                "INNER JOIN SAN_PHAM ON CTHD.masp = SAN_PHAM.masp " +
                "GROUP BY HOA_DON.idhoadon";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String tenKhachHang = cursor.getString(0);
                String sdtKhachHang = cursor.getString(1);
                String diaChiKhachHang = cursor.getString(2);
                String ngay = cursor.getString(3);
                String danhSachSanPhamString = cursor.getString(4);
                int soLuong = cursor.getInt(5);
                int giaSanPham = cursor.getInt(6);
                String tongTien = cursor.getString(7);

                // Xử lý chuỗi danh sách sản phẩm và tạo danh sách sản phẩm
                List<String> danhSachSanPham = new ArrayList<>(Arrays.asList(danhSachSanPhamString.split(",")));

                // Tạo thông tin hóa đơn
                String thongTinHoaDon = "Tên Khách Hàng: " + tenKhachHang +
                        "\nSố Điện Thoại: " + sdtKhachHang +
                        "\nĐịa Chỉ: " + diaChiKhachHang +
                        "\nNgày: " + ngay +
                        "\nDanh Sách Sản Phẩm: " + danhSachSanPham +
                        "\nSố Lượng: " + soLuong +
                        "\nGiá Sản Phẩm: " + giaSanPham +
                        "\nTổng Tiền: " + tongTien;

                thongTinHoaDonList.add(thongTinHoaDon);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return thongTinHoaDonList;
    }


    public double tinhDoanhThuHomNay() {
        double doanhThu = 0;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        // 1. Lấy ngày hôm nay
        Date homNay = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngayHomNay = dateFormat.format(homNay);

        // 2. Truy vấn cơ sở dữ liệu
        String query = "SELECT SUM(tongtien) AS total FROM HOA_DON WHERE ngay = ?";
        String[] selectionArgs = { ngayHomNay };
        Cursor cursor = sqLiteDatabase.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("total");
            if (columnIndex != -1) {
                doanhThu = cursor.getDouble(columnIndex);
            } else {
                // Xử lý trường hợp cột không tồn tại
                Log.e("Lỗi", "Không tìm thấy cột 'total'");
            }
        }

        cursor.close();
        return doanhThu;
    }

    public double thongKeTheoThangNam(String thang, String nam) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) AS total FROM HOA_DON WHERE strftime('%m', ngay) = ? AND strftime('%Y', ngay) = ?";
        String[] selectionArgs = { thang, nam };
        Cursor c = sqLiteDatabase.rawQuery(sSQL, selectionArgs);
        if (c.moveToFirst()) {
            int columnIndex = c.getColumnIndex("total");
            if (columnIndex != -1) {
                doanhThu = c.getDouble(columnIndex);
            } else {
                // Xử lý trường hợp cột không tồn tại
                Log.e("Error", "Column 'total' not found");
            }
        }
        c.close();
        return doanhThu;
    }


    public double thongKeDoanhThuNamHienTai() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double doanhThu = 0;

        // Lấy năm hiện tại
        int namHienTai = getCurrentYear();

        // Truy vấn cơ sở dữ liệu để tính tổng tiền từ hóa đơn trong năm hiện tại
        String query = "SELECT SUM(tongtien) FROM HOA_DON WHERE strftime('%Y', ngay) = ?";
        String[] selectionArgs = {String.valueOf(namHienTai)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            doanhThu = cursor.getDouble(0);
        }

        cursor.close();
        db.close();

        return doanhThu;
    }

    // Phương thức để lấy năm hiện tại
    private int getCurrentYear() {
        return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    }

}
