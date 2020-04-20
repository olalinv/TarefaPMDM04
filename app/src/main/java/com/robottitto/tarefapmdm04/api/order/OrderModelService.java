package com.robottitto.tarefapmdm04.api.order;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import com.robottitto.tarefapmdm04.api.core.AppDatabase;
import com.robottitto.tarefapmdm04.api.order.dao.OrderDao;
import com.robottitto.tarefapmdm04.api.order.model.Order;

import java.util.List;

public class OrderModelService {
    @SuppressLint("StaticFieldLeak")
    private static OrderModelService orderModelService;
    private OrderDao orderDao;

    private OrderModelService(Context context) {
        Context appContext = context.getApplicationContext();
        AppDatabase database = Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.NAME).allowMainThreadQueries().build();
        orderDao = database.orderDao();
    }

    public static OrderModelService get(Context context) {
        if (orderModelService == null) {
            orderModelService = new OrderModelService(context);
        }
        return orderModelService;
    }

    public List<Order> getOrders() {
        return orderDao.getAll();
    }

    public Order getOrder(String orderId) {
        return orderDao.findByOrderId(orderId);
    }

    public List<Order> findOrdersByStatus(int status) {
        return orderDao.findByStatus(status);
    }

    public List<Order> findOrdersByUserId(int userId) {
        return orderDao.findByUserId(userId);
    }

    public List<Order> findOrdersByUserIdAndStatus(int userId, int status) {
        return orderDao.findByUserIdAndStatus(userId, status);
    }

    public void addOrder(Order order) {
        orderDao.insert(order);
    }

    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    public void updateOrderStatus(int orderId, int status) {
        orderDao.updateStatus(orderId, status);
    }

    public void deleteOrder(Order order) {
        orderDao.delete(order);
    }
}