package com.codedevtech.portfolioapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codedevtech.portfolioapp.models.FolioUser;

@Dao
public interface FolioUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFolioUser(FolioUser folioUser);

    @Query("SELECT * FROM foliouser WHERE id = :userId")
    LiveData<FolioUser> getFolioUserFromDb(String userId);

}
