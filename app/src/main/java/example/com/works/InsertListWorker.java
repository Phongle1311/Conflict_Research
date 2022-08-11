package example.com.works;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import example.com.database.ConflictDatabase;
import example.com.model.Conflict;
import example.com.util.Constants;

public class InsertListWorker extends Worker {
    Context context;

    public InsertListWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        String json = getInputData().getString(Constants.KEY_LIST_CONFLICTS);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Conflict>>(){}.getType();
        Collection<Conflict> list = gson.fromJson(json, collectionType);

        ConflictDatabase.getInstance(context)
                .conflictDAO().insertMultiple((List<Conflict>) list);

        return Result.success();
    }
}
