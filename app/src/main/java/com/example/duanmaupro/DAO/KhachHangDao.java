package com.example.duanmaupro.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmaupro.Database.DbHelper;
import com.example.duanmaupro.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDao {
    private DbHelper dbHelper;

    public KhachHangDao(Context context) {
        dbHelper = new DbHelper(context);
    }

//    public boolean insertKhachHang(KhachHang khachHang) {
//        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("tenkhachhang", khachHang.getTenkhachhang());
//        values.put("sdt", khachHang.getSdt());
//        values.put("diachi", khachHang.getDiachi());
//
//        long check = sqLiteDatabase.insert("KHACH_HANG",null,values);
//        if (check == -1){
//            return false;
//        }
//        return true;
//    }


    public long insertKhachHang(KhachHang khachHang) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenkhachhang", khachHang.getTenkhachhang());
        values.put("sdt", khachHang.getSdt());
        values.put("diachi", khachHang.getDiachi());

        long khachHangId = sqLiteDatabase.insert("KHACH_HANG", null, values);

        return khachHangId;
    }

    public List<KhachHang> getAllKhachHang() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<KhachHang> listsp = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM KHACH_HANG", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                listsp.add(new KhachHang(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));

            } while (cursor.moveToNext());
        }
        return listsp;

    }
}
