package com.example.duanmaupro.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaupro.Adapter.GioHangAdapter;
import com.example.duanmaupro.DAO.ChiTietHoaDonDao;
import com.example.duanmaupro.DAO.GioHangDao;
import com.example.duanmaupro.DAO.HoaDonDao;
import com.example.duanmaupro.DAO.KhachHangDao;
import com.example.duanmaupro.R;
import com.example.duanmaupro.ThanhToanThanhCong;
import com.example.duanmaupro.TotalPriceUpdateListener;
import com.example.duanmaupro.model.ChiTietHoaDon;
import com.example.duanmaupro.model.GioHang;
import com.example.duanmaupro.model.HoaDon;
import com.example.duanmaupro.model.KhachHang;
import com.example.duanmaupro.model.sanPham;
import com.example.duanmaupro.service.myAplication;
import com.example.duanmaupro.service.myService;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentThanhToan extends Fragment implements TotalPriceUpdateListener {

    TextView tongtien;
    Button btnXacNhan;
    Button btnTiepTuc;
    private RecyclerView recyclerViewMua;
    private GioHangAdapter gioHangAdapter;
    private List<GioHang> gioHangList;
    private GioHangDao gioHangDao;

    private EditText nameKH;
    private EditText phoneKH;
    private EditText address;
    private KhachHangDao khachHangDao;
    private EditText makhuyenmai;
    private HoaDonDao hoaDonDao;
    private ChiTietHoaDonDao chiTietHoaDonDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanh_toan, container, false);
        btnXacNhan = view.findViewById(R.id.btnXacNhanThanhToan);
        recyclerViewMua = view.findViewById(R.id.rcv_thanhtoan);
        btnTiepTuc = view.findViewById(R.id.tieptuc);
        tongtien = view.findViewById(R.id.tv_tongTien);
        nameKH = view.findViewById(R.id.edt_nameKH);
        phoneKH = view.findViewById(R.id.edt_phoneKH);
        address = view.findViewById(R.id.edt_address);
        makhuyenmai = view.findViewById(R.id.edt_khuyenmai);

        khachHangDao = new KhachHangDao(getContext());
        hoaDonDao = new HoaDonDao(getContext());
        gioHangDao = new GioHangDao(getContext());
        chiTietHoaDonDao = new ChiTietHoaDonDao(getContext());

        gioHangList = gioHangDao.getDS();
        ArrayList<GioHang> listGH = gioHangDao.getDS();
        updateTotalPrice();

        gioHangAdapter = new GioHangAdapter(getContext(), listGH, gioHangDao);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerViewMua.setLayoutManager(gridLayoutManager);
        recyclerViewMua.setAdapter(gioHangAdapter);

        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_Trang_Chu fragmentTrangChu = new Fragment_Trang_Chu();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_navigation, fragmentTrangChu);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendNotification();

                String name = nameKH.getText().toString();
                String phone = phoneKH.getText().toString();
                String addres = address.getText().toString();
                int mkm = Integer.parseInt(makhuyenmai.getText().toString());


                String regexTen = "[^\\d]{1,}";
                String sodienthoai = "\\d{1,10}";
                String dc = "\\w{1,}";

                if (name.isEmpty() && phone.isEmpty() && addres.isEmpty()) {
                    Toast.makeText(getContext(), "Vui Lòng Nhập Đủ Dữ Liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                // kiểm tra tên
                if (name.equals("")) {
                    Toast.makeText(getContext(), "Chưa nhập tên khách hàng", Toast.LENGTH_SHORT).show();
                    return;
                } else if (name.matches(regexTen)) {

                } else {
                    Toast.makeText(getContext(), "Tên Không Hợp Lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                //kiểm  tra sdt
                if (phone.equals("")) {
                    Toast.makeText(getContext(), "Chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                } else if (phone.matches(sodienthoai)) {

                } else {
                    Toast.makeText(getContext(), "Nhập số điện thoại không hợp lệ ", Toast.LENGTH_SHORT).show();
                    return;
                }
                // kiểm tra dc
                if (addres.equals("")) {
                    Toast.makeText(getContext(), "Chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (addres.matches(dc)) {

                } else {
                    Toast.makeText(getContext(), "địa chỉ không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                KhachHang khachHang = new KhachHang(name, phone, addres);
                long khachHangId = khachHangDao.insertKhachHang(khachHang);


                if (khachHangId != -1) {
                    Toast.makeText(getContext(), "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setIdkhachhang((int) khachHangId);
                    hoaDon.setIdnhanvien(1);
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String ngay = dateFormat.format(currentDate);
                    hoaDon.setNgay(ngay);

                    hoaDon.setTongtien(updateTotalPrice());
                    int fixloimkm=5;
                    if (mkm == 0 || mkm == 1 || mkm == 2 || mkm == 3 || mkm == 4){
                        fixloimkm = mkm;
                    } else if (makhuyenmai.getText().toString() == null){
                        fixloimkm = 5;
                    } else {
                        Toast.makeText(getContext(), "Mã khuyến mãi không tồn tại", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    hoaDon.setIdmkm(fixloimkm);

                    long ktHD = hoaDonDao.themHoaDon(hoaDon);
                    int totalPrice = 0;
                    if (ktHD != -1) {
                        Toast.makeText(getContext(), "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                        List<GioHang> gioHangList = gioHangDao.getDS();
                        hoaDon.setIdhoadon((int) ktHD);
                        for (GioHang gioHang : gioHangList) {
                            int idsp = gioHang.getMasp();
                            int giaSanPham = gioHang.getGiasp();
                            int soluong = gioHang.getSoluong();
                            String size = gioHang.getSize();
                            int thanhTien = giaSanPham * soluong;
                            totalPrice += thanhTien;

                            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                            chiTietHoaDon.setSanPham(new sanPham(idsp, gioHang.getTensp(), giaSanPham, soluong, gioHang.getImagesp(), size));
                            chiTietHoaDon.setHoaDon(hoaDon);
                            chiTietHoaDon.setSoluong(soluong);
                            chiTietHoaDon.setDongia(thanhTien);

                            long themCTHD = chiTietHoaDonDao.themChiTietHoaDon(chiTietHoaDon);
                            if (themCTHD != -1) {
                                Toast.makeText(getContext(), " mua sản phầm thành công", Toast.LENGTH_SHORT).show();
                                chiTietHoaDonDao.updateSanPham(hoaDon.getIdhoadon(), hoaDon.getIdhoadon());
                            } else {
                                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {
                        Toast.makeText(getContext(), "Thêm hóa đơn thất bại", Toast.LENGTH_SHORT).show();
                    }
                    gioHangDao.xoaTatCaSanPham();
                    gioHangAdapter.clearData();
                    gioHangAdapter.notifyDataSetChanged();

//                    Fragment_Trang_Chu fragmentTrangChu = new Fragment_Trang_Chu();
//                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.layout_navigation, fragmentTrangChu);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                    sendNotification();
                    Intent intent = new Intent(getActivity(), ThanhToanThanhCong.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getContext(), "Thêm khách hàng thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }


    private String updateTotalPrice() {
        int totalPrice = 0;

        // Lặp qua danh sách sản phẩm trong giỏ hàng
        for (GioHang gioHang : gioHangList) {
            int giaSanPham = gioHang.getGiasp();
            int soLuong = gioHang.getSoluong();
            totalPrice += giaSanPham * soLuong;
        }

        // Định dạng số tiền và hiển thị trong TextView
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String formattedTotalPrice = formatter.format(totalPrice);
        tongtien.setText("Tổng tiền : " + formattedTotalPrice + " đ");
        return formattedTotalPrice;
    }


    @Override
    public void onUpdateTotalPrice(int newTotalPrice) {
        updateTotalPriceTextView(newTotalPrice);
    }

    private void updateTotalPriceTextView(int newTotalPrice) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String formattedTotalPrice = formatter.format(newTotalPrice);
        tongtien.setText("Tổng tiền : " + formattedTotalPrice + " đ");
    }


    private void sendNotification() {
        // Được giả định là bạn đã có một tài nguyên hình ảnh với tên là "example_image"
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sanpham3);

        if (bitmap != null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), myAplication.CHANNEL_ID)
                    .setSmallIcon(R.drawable.sanpham3)
                    .setContentTitle("CẢM ƠN BẠN ĐÃ QUAN TÂM VÀ ỦNG HỘ")
                    .setContentText("THANK KIU FOR LISTENING")
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .bigLargeIcon(null)
                    )
                    .setLargeIcon(bitmap)
                    .setColor(Color.RED)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());

            // Kiểm tra quyền gửi thông báo
            if (notificationManagerCompat.areNotificationsEnabled()) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // Yêu cầu quyền gửi thông báo từ người dùng
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.POST_NOTIFICATIONS}, 7979);
                } else {
                    notificationManagerCompat.notify(getNotificationID(), builder.build());
                }
            } else {
                // Yêu cầu quyền thông báo
                // Hãy thực hiện yêu cầu quyền thông qua hộp thoại hoặc cài đặt ứng dụng
            }
        } else {
            // Xử lý khi bitmap là null
        }
    }

    private int getNotificationID() {
        return (int) new Date().getTime();
    }



}