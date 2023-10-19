package com.example.duanmaupro.model;

public class ChiTietHoaDon {
    private int idcthd;
    private sanPham sanPham;
    private HoaDon hoaDon;
    private KhachHang khachHang;
    private int soluong;
    private int dongia;
    private int idmkm;


    public ChiTietHoaDon(){

    }

    public ChiTietHoaDon(int idcthd, sanPham sanPham, HoaDon hoaDon, KhachHang khachHang, int soluong, int dongia, int idmkm) {
        this.idcthd = idcthd;
        this.sanPham = sanPham;
        this.hoaDon = hoaDon;
        this.khachHang = khachHang;
        this.soluong = soluong;
        this.dongia = dongia;
        this.idmkm = idmkm;
    }

    public int getIdcthd() {
        return idcthd;
    }

    public void setIdcthd(int idcthd) {
        this.idcthd = idcthd;
    }

    public com.example.duanmaupro.model.sanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(com.example.duanmaupro.model.sanPham sanPham) {
        this.sanPham = sanPham;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public int getIdmkm() {
        return idmkm;
    }

    public void setIdmkm(int idmkm) {
        this.idmkm = idmkm;
    }
}
