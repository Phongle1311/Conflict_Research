package example.com.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import example.com.model.Conflict;

@Database(entities = {Conflict.class}, version = 1)
public abstract class ConflictDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "conflict.db";
    static ConflictDatabase instance;

    public static synchronized ConflictDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ConflictDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract ConflictDAO conflictDAO();
}
