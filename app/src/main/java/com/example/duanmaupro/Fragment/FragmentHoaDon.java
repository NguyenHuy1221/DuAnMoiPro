package com.example.duanmaupro.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaupro.Adapter.ChiTietHoaDonAdapter;
import com.example.duanmaupro.Adapter.showhoadonpass2;
import com.example.duanmaupro.DAO.ChiTietHoaDonDao;
import com.example.duanmaupro.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentHoaDon extends Fragment {

    private RecyclerView recyclerView;
    private ChiTietHoaDonAdapter chiTietHoaDonAdapter;
    private ChiTietHoaDonDao chiTietHoaDonDao;
    private showhoadonpass2  show2;
    private List<String> thongTinHoaDonList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chiTietHoaDonDao = new ChiTietHoaDonDao(getContext());

//        List<HoaDon> hoaDonList = chiTietHoaDonDao.layDanhSachHoaDonVaSanPham();

        thongTinHoaDonList = new ArrayList<>();
        thongTinHoaDonList = chiTietHoaDonDao.layDanhSachHoaDonChiTiet();

//        ArrayList<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonDao.layDanhSachChiTietHoaDon();

//        chiTietHoaDonAdapter = new ChiTietHoaDonAdapter(chiTietHoaDonList);
        show2 = new showhoadonpass2(getContext(),thongTinHoaDonList);
        recyclerView.setAdapter(show2);
//        recyclerView.setAdapter(chiTietHoaDonAdapter);

        return view;
    }
}