package dandy1988.myspacelaunch;
import java.sql.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET("/1.4/launch")
    public Call<LaunchCollection> getLaunches(@Query("startdate")String dateStart,
                                              @Query("enddate")String dateEnd,
                                              @Query("limit") String limit);


}
