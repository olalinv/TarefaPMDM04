package com.robottitto.tarefapmdm04.api.order.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.robottitto.tarefapmdm04.api.order.model.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM [order]")
    List<Order> getAll();

    @Query("SELECT * FROM [order] WHERE oid IN (:orderIds)")
    List<Order> loadAllByIds(int[] orderIds);

    @Query("SELECT * FROM [order] WHERE status = :status")
    List<Order> findByStatus(int status);

    @Query("SELECT * FROM [order] WHERE oid = :orderId LIMIT 1")
    Order findByOrderId(String orderId);

    @Query("SELECT * FROM [order] WHERE user_id = :userId")
    List<Order> findByUserId(int userId);

    @Query("SELECT * FROM [order] WHERE user_id = :userId AND status = :status")
    List<Order> findByUserIdAndStatus(int userId, int status);

    @Insert
    void insert(Order order);

    @Insert
    void insertAll(Order... orders);

    @Update
    void update(Order order);

    @Query("UPDATE [order] SET status = :status WHERE oid = :orderId")
    void updateStatus(int orderId, int status);

    @Delete
    void delete(Order order);

}