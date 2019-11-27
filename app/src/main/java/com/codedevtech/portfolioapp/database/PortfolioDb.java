package com.codedevtech.portfolioapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.codedevtech.portfolioapp.database.dao.FolioUserDao;
import com.codedevtech.portfolioapp.database.dao.UserDao;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.models.User;

//exportschema should be true for non production builds
@Database(entities = {User.class, FolioUser.class},
        version = 1,
        exportSchema = false)
public abstract class PortfolioDb extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract FolioUserDao folioUserDao();

}
