package com.codedevtech.portfolioapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.codedevtech.portfolioapp.database.dao.UserDao;
import com.codedevtech.portfolioapp.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class PortfolioDb extends RoomDatabase {

    public abstract UserDao userDao();

}
