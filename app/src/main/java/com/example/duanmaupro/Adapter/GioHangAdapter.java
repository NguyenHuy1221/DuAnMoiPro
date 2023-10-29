package com.example.duanmaupro.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duanmaupro.DAO.GioHangDao;
import com.example.duanmaupro.R;
import com.example.duanmaupro.TotalPriceUpdateListener;
import com.example.duanmaupro.model.GioHang;
import com.example.duanmaupro.model.taiKhoan;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {

    private Context context;
    private List<GioHang> listsp;
    private GioHangDao gioHangDao;
    private int tongTien = 0;
    private TotalPriceUpdateListener totalPriceUpdateListener;

    public GioHangAdapter(Context context, List<GioHang> listsp,GioHangDao gioHangDao ) {
        this.context = context;
        this.listsp = listsp;
        this.gioHangDao = gioHangDao;

        if (context instanceof TotalPriceUpdateListener) {
            totalPriceUpdateListener = (TotalPriceUpdateListener) context;
        }
    }

    public void updatelist(ArrayList<GioHang> newlist){
        this.listsp = newlist;
        notifyDataSetChanged();
    }

    public void clearData() {
        listsp.clear();
    }

    public void removeItem(int position) {
        GioHang gioHangRemoved = listsp.get(position);
        gioHangDao.xoaSP(String.valueOf(gioHangRemoved.getMasp()));
        listsp.remove(position);
        notifyItemRemoved(position);

        // Tính tổng tiền mới sau khi xóa sản phẩm
        int newTotalPrice = calculateTotalPrice();

        if (totalPriceUpdateListener != null) {
            totalPriceUpdateListener.onUpdateTotalPrice(newTotalPrice);
        }
    }

    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (GioHang gioHang : listsp) {
            totalPrice += gioHang.getGiasp() * gioHang.getSoluong();
        }
        return totalPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_gio_hang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GioHang gioHang = listsp.get(position);

        // Hiển thị thông tin sản phẩm trong giỏ hàng
        holder.txtTenSanPham.setText(gioHang.getTensp());

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String formattedGia = formatter.format(gioHang.getGiasp());
        holder.txtGiaSanPham.setText(formattedGia + " đ");

        holder.txtSoLuongSanPham.setText(""+gioHang.getSoluong());
        holder.txtSize.setText("Size: " + gioHang.getSize());
        Picasso.get().load(gioHang.getImagesp()).into(holder.imgSanPham);

        int tongTien = gioHang.getGiasp() * gioHang.getSoluong();
        String formattedTongTien = formatter.format(tongTien);
        holder.txtTongTien.setText("Tổng tiền: " + formattedTongTien + " đ");

        holder.apart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy đối tượng TextView từ holder
                TextView txtSoLuongSanPham = holder.txtSoLuongSanPham;
                // Lấy giá trị số nguyên từ TextView
                int soluongsanpham = Integer.parseInt(txtSoLuongSanPham.getText().toString()) -1;
                if (soluongsanpham >=1){


                    holder.txtSoLuongSanPham.setText(String.valueOf(soluongsanpham));

                    GioHang gioHang1 = new GioHang(gioHang.getMasp(), null,null,soluongsanpham,null,null);
                    boolean check = gioHangDao.updateGH(gioHang1);

                    if (check) {

                        ArrayList<GioHang> capnhat = gioHangDao.getDS();
                        updatelist(capnhat);
                    } else {

                    }
                } else {
                    Toast.makeText(context, "số lượng sản phẩm lớn hơn 1 mới được phép xóa", Toast.LENGTH_SHORT).show();
                     soluongsanpham = soluongsanpham + 1;
                }
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtSoLuongSanPham = holder.txtSoLuongSanPham;
                // Lấy giá trị số nguyên từ TextView
                int soluongsanpham = Integer.parseInt(txtSoLuongSanPham.getText().toString()) + 1;
                if (gioHang.getSoluong() <100){


                    holder.txtSoLuongSanPham.setText(String.valueOf(soluongsanpham));
                    GioHang gioHang1 = new GioHang(gioHang.getMasp(), null,null,soluongsanpham,null,null);
                    boolean check = gioHangDao.updateGH(gioHang1);

                    if (check) {

                        ArrayList<GioHang> capnhat = gioHangDao.getDS();
                        updatelist(capnhat);
                    } else {

                    }
                } else {
                    Toast.makeText(context, "số lượng sản phẩm lớn hơn 1 mới được phép xóa", Toast.LENGTH_SHORT).show();
                    soluongsanpham = gioHang.getSoluong() - 1;
                }
            }
        });






        holder.txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog(listsp.get(holder.getAbsoluteAdapterPosition()).getTensp()
                        ,listsp.get(holder.getAbsoluteAdapterPosition()).getMasp());
            }
        });

    }

    @Override
    public int getItemCount() {
         if(listsp != null){
             return listsp.size();
        }
         return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSanPham, txtGiaSanPham, txtSoLuongSanPham, txtTongTien, txtXoa,txtSize,apart,add;
        ImageView imgSanPham ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtGiaSanPham = itemView.findViewById(R.id.txtGiaSanPham);
            txtSoLuongSanPham = itemView.findViewById(R.id.txtSoLuongSanPham);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtXoa = itemView.findViewById(R.id.tx_xoa);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            apart = itemView.findViewById(R.id.apart);
            add = itemView.findViewById(R.id.add);
        }
    }

    public List<String> getTenSanPhamList() {
        List<String> tenSanPhamList = new ArrayList<>();
        for (GioHang gioHang : listsp) {
            tenSanPhamList.add(gioHang.getTensp());
        }
        return tenSanPhamList;
    }

    private void deleteDialog(String tensp, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cảnh Báo ");
        builder.setMessage("Bạn Có Chắc Chắn Muốn Xóa\"" + tensp + "\" ");


        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean chek = gioHangDao.xoaSP(String.valueOf(id));
                if (chek) {
                    Toast.makeText(context, "Xóa Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
                    ArrayList<GioHang> capnhat = gioHangDao.getDS();
                    updatelist(capnhat);


                } else {
                    Toast.makeText(context, "Xóa Sản Phẩm Thất Bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Không làm gì khi người dùng hủy bỏ xóa
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int calculateTongTien() {
        int tongTien = 0;
        for (GioHang gioHang : listsp) {
            tongTien += gioHang.getGiasp() * gioHang.getSoluong();
        }
        return tongTien;
    }

    // Hàm cập nhật tổng tiền trên giao diện người dùng
    private void updateTongTien(int newTongTien) {
        // Tính tổng tiền và cập nhật nó trên giao diện
        tongTien = newTongTien;
        // txtTongTien.setText("Tổng tiền: " + formattedTongTien + " đ");
    }

}
