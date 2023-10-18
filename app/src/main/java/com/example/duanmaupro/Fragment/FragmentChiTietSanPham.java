package com.example.duanmaupro.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duanmaupro.Adapter.GioHangAdapter;
import com.example.duanmaupro.DAO.GioHangDao;
import com.example.duanmaupro.R;
import com.example.duanmaupro.model.GioHang;
import com.example.duanmaupro.model.sanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
//import com.example.assigment_adroid2.R;


public class FragmentChiTietSanPham extends Fragment {


    public FragmentChiTietSanPham() {
        // Required empty public constructor
    }

    private ImageView imgSanPham;
    private TextView tvTen, tvGia,tvSize;
    private ImageButton btnAdd;
    private int so = 1;
    private Button bnt38;
    private Button bnt39;
    private Button bnt40;
    private Button bnt41;
    private Button lastClickedButton; // Biến để lưu trạng thái của nút cuối cùng được nhấn
    private String selectedSize = null;
    private int idgh;

    sanPham mSanPham;
    GioHangDao gioHangDao;
    private GioHangAdapter gioHangAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tiet_san_pham, container, false);

        imgSanPham = view.findViewById(R.id.img_chiTiet);
        tvTen = view.findViewById(R.id.tv_tenChiTiet);
        tvGia = view.findViewById(R.id.tv_giaChiTiet);
        btnAdd = view.findViewById(R.id.btn_mua);

        gioHangDao = new GioHangDao(getContext());



        bnt38 = view.findViewById(R.id.bnt38);
        bnt39 = view.findViewById(R.id.bnt39);
        bnt40 = view.findViewById(R.id.bnt40);
        bnt41 = view.findViewById(R.id.bnt41);

        Bundle bundle = new Bundle();
        tvSize = null;

        bnt38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColor(bnt38);
                selectedSize = String.valueOf(38);
                updateSizeUI();
            }
        });
        bnt39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColor(bnt39);
                selectedSize = String.valueOf(39);
                updateSizeUI();

            }
        });
        bnt40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColor(bnt40);
                selectedSize = String.valueOf(40);
                updateSizeUI();


            }
        });
        bnt41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColor(bnt41);
                selectedSize = String.valueOf(41);
                updateSizeUI();
            }
        });


        if (getArguments() != null) {

            String tvTen1 = getArguments().getString("tensp");
            int giaSanPham1 = getArguments().getInt("giasp");
            String imgUrl = getArguments().getString("image");
            Picasso.get().load(imgUrl).into(imgSanPham);

            // Định dạng số tiền
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            String formattedGia = formatter.format(giaSanPham1);

            String tvGia1 = formattedGia + " đ";
            tvTen.setText(tvTen1);
            tvGia.setText(tvGia1);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSanPham();
                }
            });
        }


        return view;
    }

    private void dialogSanPham() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_chi_tiet_giay_1, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        ImageView imganh = view.findViewById(R.id.anh_chitiet);
        TextView txtname = view.findViewById(R.id.ten_chitiet);
        TextView txtgia = view.findViewById(R.id.gia_chitiet);
        tvSize = view.findViewById(R.id.size_chitiet);
        Button btntru = view.findViewById(R.id.btn_Tru);
        Button btncong = view.findViewById(R.id.btn_Cong);
        TextView txtSo = view.findViewById(R.id.txtSo);
        Button btnT = view.findViewById(R.id.btnThoat);
        Button btnM = view.findViewById(R.id.btnThem);

        Bundle bundle = getArguments();
        if (bundle != null) {

            String imgUrl = bundle.getString("image");
            String tvTen1 = bundle.getString("tensp");
            int giaSanPham1 = bundle.getInt("giasp");


            // Kiểm tra xem đã chọn kích thước (size) chưa
            if (selectedSize == null) {
                Toast.makeText(getContext(), "Vui lòng chọn size", Toast.LENGTH_SHORT).show();
                return;
            }


            // Định dạng số tiền
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            String formattedGia = formatter.format(giaSanPham1);
            String tvGia1 = formattedGia + " đ";
            Picasso.get().load(imgUrl).into(imganh);
            txtname.setText(tvTen1);
            txtgia.setText(tvGia1);
            tvSize.setText(selectedSize);
        }

        btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (so > 1) {
                    so--;
                    txtSo.setText(String.valueOf(so));
                }
            }
        });

        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                so++;
                txtSo.setText(String.valueOf(so));
            }
        });

        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ Bundle
                Bundle bundle = getArguments();
                if (bundle != null) {
                    int idsp = bundle.getInt("masp");
                    String tenSanPham = bundle.getString("tensp");
                    int giaSanPham = bundle.getInt("giasp");
                    int soLuongSanPham = Integer.parseInt(txtSo.getText().toString());
                    String imageUrl = bundle.getString("image");
                    int soluong = bundle.getInt("soluong");
                    String fSize = bundle.getString("size");


                    String size = selectedSize;


                    if (so > soluong) {
                        Toast.makeText(getContext(), "Số lượng trong kho không đủ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Lấy danh sách sản phẩm trong giỏ hàng
                    ArrayList<GioHang> gioHangList = gioHangDao.getDS();

                    // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
                    boolean found = false;
                    for (GioHang gioHang : gioHangList) {
                        if (gioHang.getMasp() == idsp && gioHang.getSize().equals(size)) {
                            gioHang.setSoluong(gioHang.getSoluong() + soLuongSanPham);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        // Sản phẩm chưa tồn tại trong giỏ hàng, thêm sản phẩm mới
                        GioHang gioHang = new GioHang(idsp, tenSanPham, giaSanPham, soLuongSanPham, imageUrl, size);
                        boolean check = gioHangDao.ThemSP(gioHang);
                        if (check) {
                            Toast.makeText(getContext(), "Thêm Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
                        boolean updateResult = gioHangDao.CapNhatSoLuong(idsp, size, soLuongSanPham);
                        if (updateResult) {
                            Toast.makeText(getContext(), "Cập nhật Số Lượng Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Cập nhật Số Lượng Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                // Đến fragment thanh toán
                FragmentThanhToan thanhToanFragment = new FragmentThanhToan();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_navigation, thanhToanFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                dialog.dismiss();
            }
        });



//        btnM.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // Lấy thông tin từ Bundle
//                Bundle bundle = getArguments();
//                if (bundle != null) {
//                    int id = idgh+1;
//                    int idsp = bundle.getInt("masp");
//                    String tenSanPham = bundle.getString("tensp");
//                    int giaSanPham = bundle.getInt("giasp");
//                    int soLuongSanPham = Integer.parseInt(txtSo.getText().toString());
//                    String imageUrl = bundle.getString("image");
//                    int soluong = bundle.getInt("soluong");
//                    String size = selectedSize;
//
//                    if(so>soluong){
//                        Toast.makeText(getContext(), "Số lượng trong kho không đủ", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    GioHang gioHang = new GioHang(idsp,tenSanPham,giaSanPham,soLuongSanPham,imageUrl,size);
//
//                    boolean check = gioHangDao.ThemSP(gioHang);
//                    if (check){
//                        Toast.makeText(getContext(), "Thêm Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }else {
//                        Toast.makeText(getContext(), "Thêm Sản Phẩm Thất Bại", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//
//                FragmentThanhToan thanhToanFragment = new FragmentThanhToan();
//
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.layout_navigation, thanhToanFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//
//                dialog.dismiss();
//            }
//        });

        dialog.show();
    }


    private void changeButtonColor(Button clickedButton) {
        // Đặt màu cho nút được nhấn
        clickedButton.setBackgroundResource(R.drawable.custom_size);

        // Đặt lại màu cho nút cuối cùng được nhấn
        if (lastClickedButton != null && lastClickedButton != clickedButton) {
            lastClickedButton.setBackgroundResource(R.drawable.button_an);
        }

        // Cập nhật nút cuối cùng được nhấn
        lastClickedButton = clickedButton;
    }

    private void updateSizeUI() {
        if (selectedSize != null && tvSize != null) {
            // Hiển thị size đã chọn lên TextView
            tvSize.setText(selectedSize);
        }
    }

}
