package qvcloudbell.quvii.com.testapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {
    private List<Item> itemList;
    private ItemClickListener itemClickListener;

    public SelectAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.ivIcon.setImageResource(item.getIconRes());
        holder.tvHint1.setText(item.getHint1());
        holder.tvHint2.setText(item.getHint2());
        holder.clBackground.setOnClickListener(view -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvHint1;
        TextView tvHint2;
        ConstraintLayout clBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvHint1 = itemView.findViewById(R.id.tv_hint1);
            tvHint2 = itemView.findViewById(R.id.tv_hint2);
            clBackground = itemView.findViewById(R.id.cl_background);
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public static class Item {
        private int iconRes;
        private String hint1;
        private String hint2;

        public Item() {
        }

        public Item(int iconRes, String hint1, String hint2) {
            this.iconRes = iconRes;
            this.hint1 = hint1;
            this.hint2 = hint2;
        }

        public int getIconRes() {
            return iconRes;
        }

        public void setIconRes(int iconRes) {
            this.iconRes = iconRes;
        }

        public String getHint1() {
            return hint1;
        }

        public void setHint1(String hint1) {
            this.hint1 = hint1;
        }

        public String getHint2() {
            return hint2;
        }

        public void setHint2(String hint2) {
            this.hint2 = hint2;
        }
    }
}
