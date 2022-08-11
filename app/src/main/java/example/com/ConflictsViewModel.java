package example.com;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import example.com.api.ApiService;
import example.com.model.Conflict;
import example.com.util.Constants;
import example.com.works.InsertListWorker;
import example.com.works.LoadDbWorker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConflictsViewModel extends ViewModel {

    final String dmName = "test_ucdp_ged181";
    final String token = "secret";
    final int limit = 10;

    boolean isLoading = false;
    boolean shouldFetchAPI = false;
    int offset = 0;

    final MutableLiveData<List<Conflict>> conflictsMutableLiveData = new MutableLiveData<>();
    List<Conflict> conflicts = new ArrayList<>();

    public MutableLiveData<List<Conflict>> getConflictsMutableLiveData() {
        return conflictsMutableLiveData;
    }

    public boolean isLoading() {
        return isLoading;
    }

    /**
     * <h3>Offline caching</h3>
     * <p>Get data paging from room db</p>
     * <p>When room is out of data, call API</p>
     */
    public void getData(Context context) {
        addLoadingItem();

        // load from database first
        if (!shouldFetchAPI) {
            // define data input and work request
            Data data = new Data.Builder()
                    .putInt(Constants.KEY_OFFSET, offset)
                    .putInt(Constants.KEY_LIMIT, limit)
                    .build();

            WorkRequest loadDbWorkRequest =
                    new OneTimeWorkRequest.Builder(LoadDbWorker.class)
                            .setInputData(data)
                            .build();
            WorkManager workManager = WorkManager.getInstance(context);
            workManager.enqueue(loadDbWorkRequest);

            // Manage work
            workManager.getWorkInfoByIdLiveData(loadDbWorkRequest.getId())
                    .observe((LifecycleOwner) context, info -> {
                        // if work-request is succeeded, update live data
                        if (info != null && info.getState() == WorkInfo.State.SUCCEEDED) {
                            String json = info.getOutputData().getString(Constants.KEY_LIST_CONFLICTS);

                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<Collection<Conflict>>(){}.getType();
                            Collection<Conflict> list = gson.fromJson(json, collectionType);

                            if (list != null) {
                                if (list.size()>0) {
                                    removeLoadingItem();

                                    conflicts.addAll(list);
                                    conflictsMutableLiveData.setValue(conflicts);

                                    offset += list.size();
                                }
                                // if database is out of data, call API and set shouldFetchAPI true
                                else {
                                    shouldFetchAPI = true;
                                    loadFromRemote(context);
                                }
                            }
                            // if work-request is failed, load data from remote
                            else if (info.getState() == WorkInfo.State.FAILED) {
                                loadFromRemote(context);
                            }
                        }
                    });
        }
        else {
            loadFromRemote(context);
        }
    }

    void loadFromRemote(Context context) {
        ApiService.service.getListConflicts(dmName, token, limit, offset).enqueue(new Callback<List<Conflict>>() {
            @Override
            public void onResponse(@NonNull Call<List<Conflict>> call, @NonNull Response<List<Conflict>> response) {

                removeLoadingItem();

                List<Conflict> list = response.body();
                if (list != null){
                    conflicts.addAll(list);
                    conflictsMutableLiveData.setValue(conflicts);

                    offset += list.size();
                }

                Gson gson = new Gson();
                String json = gson.toJson(list);
                Data data = new Data.Builder().putString(Constants.KEY_LIST_CONFLICTS, json).build();

                WorkRequest insertListsRequest =
                        new OneTimeWorkRequest.Builder(InsertListWorker.class)
                                .setInputData(data)
                                .build();
                WorkManager.getInstance(context).enqueue(insertListsRequest);
            }

            @Override
            public void onFailure(@NonNull Call<List<Conflict>> call, @NonNull Throwable t) {
                isLoading = false;
                Toast.makeText(context, "Network error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // add loading progress
    void addLoadingItem() {
        isLoading = true;
        conflicts.add(new Conflict(true));
        conflictsMutableLiveData.setValue(conflicts);
    }

    // remove loading progress
    void removeLoadingItem() {
        isLoading = false;
        conflicts.remove(conflicts.size() - 1);
        conflictsMutableLiveData.setValue(conflicts);
    }
}
