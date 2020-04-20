package com.robottitto.tarefapmdm04.api.user.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.robottitto.tarefapmdm04.api.user.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM [user]")
    List<User> getAll();

    @Query("SELECT * FROM [user] WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM [user] WHERE username = :username LIMIT 1")
    User findByUsername(String username);

    @Query("SELECT * FROM [user] WHERE uid = :userId LIMIT 1")
    User findById(int userId);

    @Insert
    long insert(User user);

    @Insert
    void insertAll(User... users);

    @Update
    int update(User user);

    @Delete
    void delete(User user);
}
