package com.robottitto.tarefapmdm04.ui.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.robottitto.tarefapmdm04.R;
import com.robottitto.tarefapmdm04.api.order.model.Order;
import com.robottitto.tarefapmdm04.api.order.OrderUtil;
import com.robottitto.tarefapmdm04.ui.core.ActivityUtil;

import java.io.Serializable;

public class MakeOrderActivity extends AppCompatActivity implements Serializable {

    private final static String TAG = "MakeOrderActivity";
    private Context context;
    private static final String[] categories = {"Informática", "Electrónica", "Móbiles"};
    private static final String[][] products = {
            {"PC de Sobremesa", "Portátil", "Monitor"},
            {"Televisión", "DVD"},
            {"Pixel", "Galaxy", "Iphone", "Xiaomi"}
    };
    private static final String[] quantities = {"1", "2", "3", "4", "5"};
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Button button;

    private String selectedCategory;
    private String selectedProduct;
    private String selectedQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        context = getApplicationContext();

        // View elements
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        button = findViewById(R.id.button);

        // Spinner options
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, quantities);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        // Events
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MakeOrderActivity.this,
                        android.R.layout.simple_spinner_item, products[position]);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = spinner1.getSelectedItem().toString();
                selectedProduct = spinner2.getSelectedItem().toString();
                selectedQuantity = spinner3.getSelectedItem().toString();
                Order order = new Order();
                order.setCategory(selectedCategory);
                order.setProduct(selectedProduct);
                order.setQuantity(Integer.parseInt(selectedQuantity));
                ActivityUtil.go(context, SelectAddressActivity.class, OrderUtil.ORDER, order);
            }
        });
    }

}
