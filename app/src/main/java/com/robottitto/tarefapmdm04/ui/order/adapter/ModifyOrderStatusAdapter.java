package com.robottitto.tarefapmdm04.ui.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.robottitto.tarefapmdm04.R;
import com.robottitto.tarefapmdm04.api.order.enums.Status;
import com.robottitto.tarefapmdm04.api.order.model.Order;
import com.robottitto.tarefapmdm04.ui.order.contract.OrderAction;

import java.util.List;

public class ModifyOrderStatusAdapter extends RecyclerView.Adapter<ModifyOrderStatusAdapter.MyViewHolder> {

    private List<Order> mDataset;
    private OrderAction action;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ModifyOrderStatusAdapter(List<Order> myDataset, OrderAction orderAction) {
        mDataset = myDataset;
        action = orderAction;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView userId;
        public TextView tvId;
        public TextView tvProduct;
        public TextView tvCategory;
        public TextView tvQuantity;
        public Button btAcceptOrder;
        public Button btRejectOrder;

        public MyViewHolder(final View viewHolder) {
            super(viewHolder);

            userId = viewHolder.findViewById(R.id.userId);
            tvId = viewHolder.findViewById(R.id.tvId);
            tvProduct = viewHolder.findViewById(R.id.tvProduct);
            tvCategory = viewHolder.findViewById(R.id.tvCategory);
            tvQuantity = viewHolder.findViewById(R.id.tvQuantity);
            btAcceptOrder = viewHolder.findViewById(R.id.btAcceptOrder);
            btRejectOrder = viewHolder.findViewById(R.id.btRejectOrder);

            btAcceptOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order selectedOrder = (Order) viewHolder.getTag();
                    action.updateOrder(selectedOrder.getOrderId(), Status.ACCEPTED.getStatus());
                    mDataset.remove(selectedOrder);
                }
            });

            btRejectOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order selectedOrder = (Order) viewHolder.getTag();
                    action.updateOrder(selectedOrder.getOrderId(), Status.REJECTED.getStatus());
                    mDataset.remove(selectedOrder);
                }
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ModifyOrderStatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_modify_order_status, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.itemView.setTag(mDataset.get(position));
        holder.userId.setText(String.valueOf(mDataset.get(position).getUserId()));
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
