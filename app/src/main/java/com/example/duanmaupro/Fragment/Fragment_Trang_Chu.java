package com.example.duanmaupro.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.duanmaupro.Adapter.logoAdapter;
import com.example.duanmaupro.Adapter.showSanPhamAdapter;
import com.example.duanmaupro.Adapter.sliderAdapter;
import com.example.duanmaupro.ClickItem;
import com.example.duanmaupro.DAO.sanPhamDAO;
import com.example.duanmaupro.R;
import com.example.duanmaupro.model.logo;
import com.example.duanmaupro.model.sanPham;
import com.example.duanmaupro.model.slider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class Fragment_Trang_Chu extends Fragment implements ClickItem {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private sliderAdapter sliderAdapter;
    private RecyclerView recyclerViewLogo;
    private logoAdapter mLogoAdapter;
    private sanPhamDAO mSanPhamDAO;

    private showSanPhamAdapter showSanPhamAdapter;
    private RecyclerView recyclerViewSanPham;
    private FirebaseFirestore database;
    private SearchView searchView;


    public Fragment_Trang_Chu() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__trang__chu, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        searchView = view.findViewById(R.id.search);
        recyclerViewLogo = view.findViewById(R.id.rv_logo);
        recyclerViewSanPham = view.findViewById(R.id.rc_sanPham);

        // khởi tạo database
//        database = FirebaseFirestore.getInstance();

        // hien danh sach slider
        sliderAdapter = new sliderAdapter(getContext(),getListSlider());
        viewPager.setAdapter(sliderAdapter);
        circleIndicator.setViewPager(viewPager);
        sliderAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());


        // searchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng hoàn thành tìm kiếm (nút Enter)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi người dùng thay đổi nội dung tìm kiếm
                if (newText != null && !newText.isEmpty()) {
                    // Gọi phương thức để tìm kiếm sản phẩm
                    searchSanPham(newText);
                } else {
                    // Hiển thị toàn bộ sản phẩm nếu trường tìm kiếm trống
                    ArrayList<sanPham> listsp = mSanPhamDAO.getDS();
                    showSanPhamAdapter.setData(listsp);
                }
                return true;
            }
        });


        // hiện danh sách logo
        mLogoAdapter = new logoAdapter(getContext(),getLogo());
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(),4);
        recyclerViewLogo.setLayoutManager(gridLayoutManager1);
        recyclerViewLogo.setAdapter(mLogoAdapter);


        // hiện sản phẩm
        mSanPhamDAO = new sanPhamDAO(getContext());
        ArrayList<sanPham> listsp = mSanPhamDAO.getDS();

        showSanPhamAdapter = new showSanPhamAdapter(getContext(),listsp,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewSanPham.setLayoutManager(gridLayoutManager);

        recyclerViewSanPham.setAdapter(showSanPhamAdapter);



        return view;
    }

    // danh sách slider
    public List<slider> getListSlider() {
        List<slider> list = new ArrayList<>();
        list.add(new slider(R.drawable.sanpham3));
        list.add(new slider(R.drawable.sanpham3));
        list.add(new slider(R.drawable.sanpham3));
        return list;
    }

    // danh sách logo
    public List<logo> getLogo(){
        List<logo> list = new ArrayList<>();
        list.add(new logo(R.drawable.nike));
        list.add(new logo(R.drawable.adidas));
        list.add(new logo(R.drawable.puma));
        list.add(new logo(R.drawable.reebok));
        return list;
    }

    public void searchSanPham(String tenSanPham) {
        ArrayList<sanPham> listsp = mSanPhamDAO.timKiemSP(tenSanPham);
        showSanPhamAdapter.setData(listsp);
    }


    @Override
    public void onclick(sanPham mSanPham) {
        FragmentChiTietSanPham fragmentChiTietSanPham = new FragmentChiTietSanPham();

        Bundle bundle = new Bundle();
        bundle.putInt("masp",mSanPham.getMasp());
        bundle.putString("image",mSanPham.getImagesp());
        bundle.putString("tensp",mSanPham.getTensp());
        bundle.putInt("giasp", mSanPham.getGiasp());
        bundle.putInt("soluong",mSanPham.getSoluong());
        bundle.putString("size",mSanPham.getSize());
        fragmentChiTietSanPham.setArguments(bundle);


        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_navigation, fragmentChiTietSanPham)
                .addToBackStack(null)
                .commit();

    }

}
