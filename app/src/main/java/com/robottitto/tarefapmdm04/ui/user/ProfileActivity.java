package com.robottitto.tarefapmdm04.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.robottitto.tarefapmdm04.R;
import com.robottitto.tarefapmdm04.api.core.AppSharedPreferences;
import com.robottitto.tarefapmdm04.api.user.UserModelService;
import com.robottitto.tarefapmdm04.api.user.enums.Role;
import com.robottitto.tarefapmdm04.api.user.model.User;
import com.robottitto.tarefapmdm04.ui.core.ActivityUtil;
import com.robottitto.tarefapmdm04.ui.core.ImageUtil;
import com.robottitto.tarefapmdm04.ui.order.AcceptedOrdersActivity;
import com.robottitto.tarefapmdm04.ui.order.MakeOrderActivity;
import com.robottitto.tarefapmdm04.ui.order.OrdersInProcessActivity;
import com.robottitto.tarefapmdm04.ui.order.PendingOrdersActivity;
import com.robottitto.tarefapmdm04.ui.order.PurchasesMadeActivity;
import com.robottitto.tarefapmdm04.ui.order.RejectedOrdersActivity;

public class ProfileActivity extends AppCompatActivity {

    private final static String TAG = "ProfileActivity";
    private Context context;
    private User user;
    private UserModelService userModelService;
    private Boolean isAdmin;

    private ImageView ivAvatar;
    private TextView tvName;
    private Button btEditProfile;
    private Button btLogout;

    private LinearLayout clientLayout;
    private Button btMakeOrder;
    private Button btSeeOrders;
    private Button btSeePurchases;

    private LinearLayout adminLayout;
    private Button btSeePendingOrders;
    private Button btSeeAcceptedOrders;
    private Button btSeeRejectedOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = getApplicationContext();

        // View elements
        ivAvatar = findViewById(R.id.ivAvatar);
        tvName = findViewById(R.id.tvName);
        btEditProfile = findViewById(R.id.btEditProfile);
        btLogout = findViewById(R.id.btLogout);

        // Client
        clientLayout = findViewById(R.id.clientLayout);
        btMakeOrder = findViewById(R.id.btMakeOrder);
        btSeeOrders = findViewById(R.id.btSeeOrders);
        btSeePurchases = findViewById(R.id.btSeePurchases);

        // Admin
        adminLayout = findViewById(R.id.adminLayout);
        btSeePendingOrders = findViewById(R.id.btSeePendingOrders);
        btSeeAcceptedOrders = findViewById(R.id.btSeeAcceptedOrders);
        btSeeRejectedOrders = findViewById(R.id.btSeeRejectedOrders);

        // Initialization
        userModelService = UserModelService.get(this);
        try {
            user = userModelService.findUserById(getUserDetails("UID"));
            if (null != user) {
                if (null != user.getImage()) {
                    ivAvatar.setImageBitmap(ImageUtil.arrayToImageView(user.getImage()));
                }
                tvName.setText(user.getName().concat(" ").concat(user.getSurname()));
                if (user.getRole() == Role.ADMIN.getRole()) {
                    isAdmin = true;
                    clientLayout.setVisibility(View.GONE);
                    adminLayout.setVisibility(View.VISIBLE);
                } else if (user.getRole() == Role.CUSTOMER.getRole()) {
                    isAdmin = false;
                    clientLayout.setVisibility(View.VISIBLE);
                    adminLayout.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ActivityUtil.showToast(context, getString(R.string.error) + ": " + e.getMessage());
        }

        // Events
        btEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.go(context, EditProfileActivity.class);
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.go(context, LoginActivity.class);
            }
        });

        // Client
        btMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.go(context, MakeOrderActivity.class);
            }
        });

        btSeeOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.go(context, OrdersInProcessActivity.class);
            }
        });

        btSeePurchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.go(context, PurchasesMadeActivity.class);
            }
        });

        // Admin
        btSeePendingOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.go(context, PendingOrdersActivity.class);
            }
        });

        btSeeAcceptedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.go(context, AcceptedOrdersActivity.class);
            }
        });

        btSeeRejectedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.go(context, RejectedOrdersActivity.class);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        int menuType = isAdmin ? R.menu.menu_admin : R.menu.menu_customer;
        getMenuInflater().inflate(menuType, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.makeOrder:
                btMakeOrder.performClick();
                return true;
            case R.id.seeOrders:
                btSeeOrders.performClick();
                return true;
            case R.id.seePurchases:
                btSeePurchases.performClick();
                return true;
            case R.id.seeOrdersInProcess:
                btSeePendingOrders.performClick();
                return true;
            case R.id.seeAcceptedOrders:
                btSeeAcceptedOrders.performClick();
                return true;
            case R.id.seeRejectedOrders:
                btSeeRejectedOrders.performClick();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    private int getUserDetails(String key) {
        SharedPreferences sharedPref = getSharedPreferences(AppSharedPreferences.PREFS_NAME, Context.MODE_PRIVATE);
        int uid = sharedPref.getInt(key, 0);
        return uid;
    }
}
