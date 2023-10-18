package com.example.duanmaupro.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanmaupro.DAO.sanPhamDAO;
import com.example.duanmaupro.R;
import com.example.duanmaupro.model.sanPham;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class sanPhamAdapter extends RecyclerView.Adapter<sanPhamAdapter.ViewHolder> {

    private Context context;
    private ArrayList<sanPham> listSP;
    private sanPhamDAO sanPhamDAO;


    public sanPhamAdapter(Context context, ArrayList<sanPham> listSP, sanPhamDAO sanPhamDAO) {
        this.context = context;
        this.listSP = listSP;
        this.sanPhamDAO = sanPhamDAO;
    }

    public void updatelist(ArrayList<sanPham> newlist){
        this.listSP = newlist;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_ql_sp,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tensp.setText("Tên SP: "+listSP.get(position).getTensp());
//        holder.giasp.setText(listSP.get(position).getGiasp()+"");

        NumberFormat numberFormat = new DecimalFormat("#,###");
        double mNumer = listSP.get(position).getGiasp();
        String formattnumber = numberFormat.format(mNumer);
        holder.giasp.setText("Giá: " +formattnumber + " đ");

        holder.size.setText("Size : "+listSP.get(position).getSize());


        holder.soluong.setText("Số lượng: "+listSP.get(position).getSoluong());

        // xử lí hhinhf ảnh
        Glide.with(context).load(listSP.get(position).getImagesp()).into(holder.imgsp);

        SharedPreferences sharedPreferences2 = context.getSharedPreferences("data", MODE_PRIVATE);

        int idchucvu = sharedPreferences2.getInt("chucvu",3);
        boolean check = true;
        if (idchucvu != 1){
            check = false;

        }
        if (check == true){
            // thêm san phẩm


            holder.suaSP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuaSP(listSP.get(holder.getAbsoluteAdapterPosition()));
                }
            });

            holder.xoaSP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xoaSP(listSP.get(holder.getAbsoluteAdapterPosition()).getTensp()
                            ,listSP.get(holder.getAbsoluteAdapterPosition()).getMasp());
                }
            });

        } else {
            Toast.makeText(context, "bạn không có quyền sử dụng chức năng này", Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public int getItemCount() {
        return listSP.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tensp,giasp,soluong;
        ImageView imgsp;
        private TextView suaSP;
        private TextView xoaSP,size;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.ten_qlsp);
            giasp = itemView.findViewById(R.id.gia_qlsp);
            soluong = itemView.findViewById(R.id.soluong_qlsp);
            imgsp = itemView.findViewById(R.id.img_qlsp);
            suaSP = itemView.findViewById(R.id.sua_qlsp);
            xoaSP = itemView.findViewById(R.id.xoa_qlsp);
            size = itemView.findViewById(R.id.size_qlsp);
        }
    }

    private void SuaSP(sanPham MsanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_san_pham,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        // ánh xạ
        EditText edtTen = view.findViewById(R.id.txt_sTenSP);
        EditText edtSoLuong = view.findViewById(R.id.txt_SsoLuongSP);
        EditText edtGia = view.findViewById(R.id.txt_SgiaSP);
        ImageView imageView = view.findViewById(R.id.iv_suahinhsp);
        Spinner spinner = view.findViewById(R.id.spn_sua);

        List<String> size = new ArrayList<>();
        size.add("38");
        size.add("39");
        size.add("40");
        size.add("41");
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, size);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sizeAdapter);



        Button btnThoat = view.findViewById(R.id.btn_SThoat);
        Button btnLuu = view.findViewById(R.id.btn_SLuu);


        edtTen.setText(MsanPham.getTensp());
        edtSoLuong.setText(String.valueOf(MsanPham.getSoluong()));
        edtGia.setText(String.valueOf(MsanPham.getGiasp()));
        String imageUrl = MsanPham.getImagesp();

        Glide.with(context).load(imageUrl).into(imageView);


        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int masp = MsanPham.getMasp();
                String tensp = edtTen.getText().toString().trim();
                String solg = edtSoLuong.getText().toString().trim();
                String giasp = edtGia.getText().toString().trim();
                String selectedSize = spinner.getSelectedItem().toString();



                String regexTen = "[^\\d]{1,}";
                String regexGia = "\\d{1,}";
                String regexSoLuong = "\\d{1,}";

                if(tensp.isEmpty()&&solg.isEmpty()&&giasp.isEmpty()) {
                    Toast.makeText(context, "Vui Lòng Nhập Đủ Dữ Liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                // kiểm tra tên
                if (tensp.equals("")){
                    Toast.makeText(context, "Chưa Nhập Tên Sản Phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }else if (tensp.matches(regexTen)){

                }else {
                    Toast.makeText(context, "Tên Không Hợp Lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                //kiểm  tra số lượng
                if (solg.equals("")){
                    Toast.makeText(context, "Chưa Nhập Số Lượng Sản Phẩm", Toast.LENGTH_SHORT).show();
                    return;
                } else if (solg.matches(regexSoLuong)) {
                    int soluongInt = Integer.parseInt(solg);
                    if (soluongInt <= 0){
                        Toast.makeText(context, "Nhập Số Lượng không Hợp Lệ ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(context, "Nhập Số Lượng không Hợp Lệ ", Toast.LENGTH_SHORT).show();
                    return;
                }
                // kiểm tra giá
                if (giasp.equals("")){
                    Toast.makeText(context, "Chưa Nhập Giá Sản Phẩm", Toast.LENGTH_SHORT).show();
                    return;
                } else if (giasp.matches(regexGia)) {
                    int soluongInt = Integer.parseInt(giasp);
                    if (soluongInt <= 0){
                        Toast.makeText(context, "Giá Không Hợp Lệ ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(context, "Giá Không Hợp Lệ ", Toast.LENGTH_SHORT).show();
                    return;
                }


                sanPham sp = new sanPham(masp,tensp,Integer.parseInt(giasp),Integer.parseInt(solg),"",selectedSize);
                    boolean check = sanPhamDAO.updateSP(sp);
                    if (check){
                        Toast.makeText(context, "Sủa Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
                        ArrayList<sanPham> capnhap = sanPhamDAO.getDS();
                        updatelist(capnhap);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Sủa Sản Phẩm Thất Bại", Toast.LENGTH_SHORT).show();
                    }


            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }


    private void xoaSP(String tensp,int masp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc muốn xóa\""+ tensp + "\" ");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = sanPhamDAO.deleteSP(String.valueOf(masp));
                if (check) {
                    Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    ArrayList<sanPham> listsp = sanPhamDAO.getDS();
                    updatelist(listsp);
                }else {
                    Toast.makeText(context, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog =builder.create();
        dialog.show();

    }

}
