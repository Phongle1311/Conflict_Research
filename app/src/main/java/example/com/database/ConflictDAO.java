package example.com.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import example.com.model.Conflict;

@Dao
public interface ConflictDAO
{

    @Insert
    void insertMultiple(List<Conflict> conflicts);

    @Query("SELECT * FROM conflictEntity LIMIT :limit OFFSET :offset")
    List<Conflict> getConflictsByPage(int limit, int offset);
}
