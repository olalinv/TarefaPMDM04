package com.robottitto.tarefapmdm04.api.order.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.robottitto.tarefapmdm04.api.address.model.Address;
import com.robottitto.tarefapmdm04.api.order.OrderUtil;
import com.robottitto.tarefapmdm04.api.user.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Entity(tableName = OrderUtil.ORDER,
        foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "uid",
        childColumns = "user_id"))
public class Order implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "oid")
    private int orderId;

    @NonNull
    @ColumnInfo(name = "category")
    private String category;

    @NonNull
    @ColumnInfo(name = "product")
    private String product;

    @NonNull
    @ColumnInfo(name = "quantity")
    private int quantity;

    @NonNull
    @ColumnInfo(name = "status")
    private int status;

    @NonNull
    @Embedded
    private Address address;

    @ColumnInfo(name = "user_id")
    private int userId;

    public Order() {
        this.setStatus(0);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Id", getOrderId());
            obj.put("Categoría", getCategory());
            obj.put("Producto", getProduct());
            obj.put("Cantidade", getQuantity());
            obj.put("Estado", getStatus());
            obj.put("Enderezo", getAddress());
            obj.put("Usuario", getUserId());
            return obj.toString(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}
