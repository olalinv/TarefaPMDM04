package com.robottitto.tarefapmdm04.ui.order;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.robottitto.tarefapmdm04.R;
import com.robottitto.tarefapmdm04.api.order.OrderModelService;
import com.robottitto.tarefapmdm04.api.order.enums.Status;
import com.robottitto.tarefapmdm04.api.order.model.Order;
import com.robottitto.tarefapmdm04.ui.core.ActivityUtil;
import com.robottitto.tarefapmdm04.ui.order.adapter.ModifyOrderStatusAdapter;
import com.robottitto.tarefapmdm04.ui.order.contract.OrderAction;

import java.util.ArrayList;
import java.util.List;

public class PendingOrdersActivity extends AppCompatActivity implements OrderAction {

    private final static String TAG = "PendingOrdersActivity";
    private Context context;
    private OrderModelService orderModelService;
    private List<Order> orders = new ArrayList<>();

    private RecyclerView rvOrders;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);
        context = getApplicationContext();

        // View
        rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvOrders.setLayoutManager(layoutManager);

        // Initialization
        orderModelService = OrderModelService.get(this);
        try {
            orders = getOrders();
            // Adapter
            mAdapter = new ModifyOrderStatusAdapter(orders, this);
            rvOrders.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
            ActivityUtil.showToast(context, getString(R.string.error) + ": " + e.getMessage());
        }

    }

    private List<Order> getOrders() {
        return orderModelService.findOrdersByStatus(Status.PENDING.getStatus());
    }

    @Override
    public void updateOrder(int orderId, int status) {
        try {
            orderModelService.updateOrderStatus(orderId, status);
            mAdapter.notifyDataSetChanged();
            if (Status.ACCEPTED.getStatus() == 0) {
                ActivityUtil.showToast(context, getString(R.string.result_order_accepted));
            } else if (Status.REJECTED.getStatus() == 0) {
                ActivityUtil.showToast(context, getString(R.string.result_order_rejected));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ActivityUtil.showToast(context, getString(R.string.error) + ": " + e.getMessage());
        }
    }

}
