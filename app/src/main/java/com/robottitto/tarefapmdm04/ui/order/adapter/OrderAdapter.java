package com.robottitto.tarefapmdm04.ui.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.robottitto.tarefapmdm04.R;
import com.robottitto.tarefapmdm04.api.order.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<Order> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvId;
        public TextView tvProduct;
        public TextView tvCategory;
        public TextView tvQuantity;

        public MyViewHolder(View v) {
            super(v);
            tvId = v.findViewById(R.id.tvId);
            tvProduct = v.findViewById(R.id.tvProduct);
            tvCategory = v.findViewById(R.id.tvCategory);
            tvQuantity = v.findViewById(R.id.tvQuantity);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderAdapter(List<Order> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvId.setText(String.valueOf(mDataset.get(position).getOrderId()));
        holder.tvProduct.setText(mDataset.get(position).getProduct());
        holder.tvCategory.setText(mDataset.get(position).getCategory());
        holder.tvQuantity.setText(String.valueOf(mDataset.get(position).getQuantity()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
