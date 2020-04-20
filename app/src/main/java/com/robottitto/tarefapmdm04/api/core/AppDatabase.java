package com.robottitto.tarefapmdm04.api.core;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.robottitto.tarefapmdm04.api.order.dao.OrderDao;
import com.robottitto.tarefapmdm04.api.order.model.Order;
import com.robottitto.tarefapmdm04.api.user.dao.UserDao;
import com.robottitto.tarefapmdm04.api.user.model.User;

@Database(entities = {Order.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public final static String NAME = "PMDM04";

    public abstract OrderDao orderDao();

    public abstract UserDao userDao();

}
