package com.example.duanmaupro.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.duanmaupro.DAO.ChiTietHoaDonDao;
import com.example.duanmaupro.R;

import java.text.DecimalFormat;
import java.util.Calendar;


public class Fragment_doanh_thu extends Fragment {


    public Fragment_doanh_thu() {
        // Required empty public constructor
    }


    TextView tvNgay, tvThang, tvNam;
    ChiTietHoaDonDao hoaDonChiTietDAO;

    private EditText edtSearch;
    private Button btnSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);

        tvNgay = (TextView) view.findViewById(R.id.tvThongKeNgay);
        tvThang = view.findViewById(R.id.tvThongKeThang);
        tvNam = view.findViewById(R.id.tvThongKeNam);
        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        hoaDonChiTietDAO = new ChiTietHoaDonDao(getContext());
        double doanhThu = hoaDonChiTietDAO.getDoanhThuTheoNgay();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");
        String doanhThuFormatted = decimalFormat.format(doanhThu);
        tvNgay.setText("Hôm nay: " + doanhThuFormatted);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tim = edtSearch.getText().toString().trim();
                hoaDonChiTietDAO = new ChiTietHoaDonDao(getContext());

                // Lấy doanh thu từ DAO
                double doanhThu = hoaDonChiTietDAO.thongKeTheoNgay(tim);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");
                String doanhThuFormatted = decimalFormat.format(doanhThu);
                tvNgay.setText("Hôm nay: " + doanhThuFormatted);
            }
        });

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, nên cộng thêm 1.
        int currentYear = calendar.get(Calendar.YEAR);

        double doanhThu2 = hoaDonChiTietDAO.thongKeTheoThangNam(String.valueOf(currentMonth), String.valueOf(currentYear));
        DecimalFormat decimalFormat2 = new DecimalFormat("###,###,###.##");
        String doanhThuFormatted2 = decimalFormat2.format(doanhThu2);
        tvThang.setText("Tháng " + currentMonth+" : "+ doanhThuFormatted2);

        double doanhThu3 = hoaDonChiTietDAO.thongKeDoanhThuNamHienTai();
        DecimalFormat decimalFormat3 = new DecimalFormat("###,###,###.##");
        String doanhThuFormatted3 = decimalFormat3.format(doanhThu3);

        tvNam.setText("Năm " + getCurrentYear() + ": " + doanhThuFormatted3 + " VNĐ");

        return view;

    }

    private int getCurrentYear() {
        return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    }

}