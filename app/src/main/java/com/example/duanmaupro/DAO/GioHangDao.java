package com.example.duanmaupro.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmaupro.Database.DbHelper;
import com.example.duanmaupro.model.GioHang;
import com.example.duanmaupro.model.taiKhoan;

import java.util.ArrayList;

public class GioHangDao {

    DbHelper dbHelper;

    public GioHangDao(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    // lấy danh sách sản phẩm
    public ArrayList<GioHang> getDS(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<GioHang> listsp = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SPDC",null);

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                listsp.add(new GioHang(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        return listsp;
    }



    public boolean ThemSP(GioHang gioHang){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("masp",gioHang.getMasp());
        contentValues.put("tensp",gioHang.getTensp());
        contentValues.put("giasp",gioHang.getGiasp());
        contentValues.put("soluong",gioHang.getSoluong());
        contentValues.put("imagesp",gioHang.getImagesp());
        contentValues.put("size",gioHang.getSize());


        long check = sqLiteDatabase.insert("SPDC",null,contentValues);
        if (check == -1){
            Log.e("HUY", "Lỗi khi thêm sản phẩm vào cơ sở dữ liệu");
            return false;
        }
        return true;
    }

    public boolean updateGH(GioHang GioHang) {

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("soluong",GioHang.getSoluong());


        long check = sqLiteDatabase.update("SPDC",contentValues,"masp=?",new String[]{String.valueOf(GioHang.getMasp())});
        if (check <=0 ){
            return false;
        }
        return true;
    }






    public boolean xoaSP(String masp){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("SPDC","masp=?",new String[]{String.valueOf(masp)});
        if (check != -1){
            return true;
        }
        return false;

    }

    public boolean xoaTatCaSanPham() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM SPDC");
        db.close();
        return true;
    }

    public boolean CapNhatSoLuong(int idsp, String size, int soLuongMoi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("soluong", soLuongMoi);

        // Xác định điều kiện cập nhật bằng WHERE
        String whereClause = "masp = ? AND size = ?";
        String[] whereArgs = {String.valueOf(idsp), size};

        int rowsUpdated = db.update("SPDC", values, whereClause, whereArgs);

        db.close();

        return rowsUpdated > 0;
    }


}
