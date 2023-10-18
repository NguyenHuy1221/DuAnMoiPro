package com.example.duanmaupro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanmaupro.ClickItem;
import com.example.duanmaupro.R;
import com.example.duanmaupro.model.sanPham;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class showSanPhamAdapter extends RecyclerView.Adapter<showSanPhamAdapter.ViewHolder> {

    private Context mContext;
    private List<sanPham> mListSP;
    private FirebaseFirestore database;
    private ClickItem listener;


    public showSanPhamAdapter(Context mContext ,List<sanPham> mListSP, ClickItem listener) {
        this.mContext = mContext;
        this.mListSP = mListSP;
//        this.database = database;
        this.listener = listener;
    }

    public void setData (List<sanPham> list){
        this.mListSP =list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mSanPham = mListSP.get(position);

        sanPham sanPhamList =mListSP.get(position);


        String imageUrl = sanPhamList.getImagesp();
        Glide.with(mContext).load(imageUrl).into(holder.imgSP);
        holder.tenSP.setText(sanPhamList.getTensp());
        NumberFormat numberFormat = new DecimalFormat("#,###");
        double mNumer = mListSP.get(position).getGiasp();
        String formattnumber = numberFormat.format(mNumer);
        holder.giaSP.setText(formattnumber + " đ");

    }

    @Override
    public int getItemCount() {
        if (mListSP != null){
            return mListSP.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        sanPham mSanPham;
        private ImageView imgSP;
        private TextView tenSP;
        private TextView giaSP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSP = itemView.findViewById(R.id.img_SanPhamMoi);
            tenSP = itemView.findViewById(R.id.tv_TenSanPhamMoi);
            giaSP = itemView.findViewById(R.id.tv_GiaSanPhamMoi);
//            database = FirebaseFirestore.getInstance(); // khởi tạo database

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onclick(mSanPham);
                }
            });
        }
    }
}
