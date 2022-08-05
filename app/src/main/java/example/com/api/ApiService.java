package example.com.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import example.com.model.Conflict;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    String BASE_URL = "https://rtlab02.rtworkspace.com/";

    Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-DD").create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    ApiService service = retrofit.create(ApiService.class);

    @GET("api/query/datamodel")
    Call<List<Conflict>> getListConflicts(@Query("dm_name") String dmName,
                                          @Query("token") String token,
                                          @Query("limit") int limit,
                                          @Query("offset") int offset);
}
