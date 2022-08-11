package example.com.works;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;

import java.util.List;

import example.com.database.ConflictDatabase;
import example.com.model.Conflict;
import example.com.util.Constants;

public class LoadDbWorker extends Worker {

    Context context;

    public LoadDbWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        // Get data input
        Data dataInput = getInputData();
        int offset = dataInput.getInt(Constants.KEY_OFFSET, 0);
        int limit = dataInput.getInt(Constants.KEY_LIMIT, 10);

        // do work
        // query on database
        List<Conflict> conflicts = ConflictDatabase.getInstance(context)
                .conflictDAO().getConflictsByPage(limit, offset);

        // convert data to JSON
        Gson gson = new Gson();
        String json = gson.toJson(conflicts);

        return Result.success(new Data.Builder()
                .putString(Constants.KEY_LIST_CONFLICTS, json)
                .build());
    }
}
