package com.robottitto.tarefapmdm04.ui.order;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.robottitto.tarefapmdm04.R;
import com.robottitto.tarefapmdm04.api.address.model.Address;
import com.robottitto.tarefapmdm04.api.core.AppSharedPreferences;
import com.robottitto.tarefapmdm04.api.order.OrderModelService;
import com.robottitto.tarefapmdm04.api.order.OrderUtil;
import com.robottitto.tarefapmdm04.api.order.model.Order;
import com.robottitto.tarefapmdm04.ui.core.ActivityUtil;
import com.robottitto.tarefapmdm04.ui.user.ProfileActivity;

import java.io.Serializable;

public class SelectAddressActivity extends AppCompatActivity implements Serializable {

    private final static String TAG = "SelectAddressActivity";
    private Context context;
    private Order order;
    private OrderModelService orderModelService;

    private EditText etAddress;
    private EditText etCity;
    private EditText etZipCode;
    private Button btFinishOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        context = getApplicationContext();

        // Initialization
        orderModelService = OrderModelService.get(this);

        // Intents params
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        order = (Order) bundle.getSerializable(OrderUtil.ORDER);

        // View elements
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etZipCode = findViewById(R.id.etZipCode);
        btFinishOrder = findViewById(R.id.btFinishOrder);

        // Events
        btFinishOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = etAddress.getText().toString();
                String city = etCity.getText().toString();
                String zipCode = etZipCode.getText().toString();
                // TODO Add more fields validations
                if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(zipCode)) {
                    Address newAddress = new Address();
                    newAddress.setStreet(etAddress.getText().toString());
                    newAddress.setCity(etCity.getText().toString());
                    newAddress.setZipCode(etZipCode.getText().toString());
                    order.setAddress(newAddress);
                    order.setUserId(getUserDetails("UID"));
                    openConfirmDialog(order);
                } else {
                    ActivityUtil.showToast(context, getString(R.string.error_empty_fields));
                }
            }
        });
    }

    private void openConfirmDialog(final Order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.confirm_order))
                .setCancelable(false)
                .setMessage(order.getQuantity() + " " + order.getProduct())
                .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        makeOrder(order);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void makeOrder(Order order) {
        try {
            orderModelService.addOrder(order);
            Log.d(TAG, orderModelService.getOrders().toString());
            ActivityUtil.showToast(context, getString(R.string.order_done));
            ActivityUtil.go(context, ProfileActivity.class);
        } catch (Exception e) {
            e.printStackTrace();
            ActivityUtil.showToast(context, getString(R.string.error) + ": " + e.getMessage());
        }
    }

    private int getUserDetails(String key) {
        SharedPreferences sharedPref = getSharedPreferences(AppSharedPreferences.PREFS_NAME, Context.MODE_PRIVATE);
        int uid = sharedPref.getInt(key, 0);
        return uid;
    }

}
