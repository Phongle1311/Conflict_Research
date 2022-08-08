package example.com;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import example.com.api.ApiService;
import example.com.model.Conflict;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConflictsViewModel extends ViewModel {
    List<Conflict> conflicts = new ArrayList<>();
    final MutableLiveData<List<Conflict>> conflictsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> callApiSuccess = new MutableLiveData<>(); // use for notify UI call API fail
    boolean isLoading = false;
    final String dmName = "test_ucdp_ged181";
    final String token = "secret";
    final int limit = 10;
    int offset = 0;

    public MutableLiveData<List<Conflict>> getConflictsMutableLiveData() {
        return conflictsMutableLiveData;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void callApiGetConflicts() {
        isLoading = true;
        addLoadingItem();

        ApiService.service.getListConflicts(dmName, token, limit, offset).enqueue(new Callback<List<Conflict>>() {
            @Override
            public void onResponse(@NonNull Call<List<Conflict>> call, @NonNull Response<List<Conflict>> response) {
                removeLoadingItem();
                isLoading = false;

                List<Conflict> list = response.body();
                if (list != null){
                    conflicts.addAll(list);
                    conflictsMutableLiveData.setValue(conflicts);
                    callApiSuccess.postValue(true);
                }

                offset += limit;
            }

            @Override
            public void onFailure(@NonNull Call<List<Conflict>> call, @NonNull Throwable t) {
                callApiSuccess.postValue(false);
                isLoading = false;
                // chưa xử lý fail
                // khi load data from database thì chỉnh sửa thêm: nếu load từ mạng ko được thì
                // thì load từ database, hay khi database hết thì load từ network
                // còn cả 2 ko được thì thong báo cho user
            }
        });
    }

    public void addLoadingItem() {
        conflicts.add(new Conflict(true));
        conflictsMutableLiveData.setValue(conflicts);
    }

    public void removeLoadingItem() {
        conflicts.remove(conflicts.size() - 1);
        conflictsMutableLiveData.setValue(conflicts);
    }
}
