package dandy1988.myspacelaunch;
import java.sql.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("/1.4/launch/{dateStart}/{dateEnd}")
    public Call<LaunchCollection> getLaunches(@Path("dateStart")String dateStart,
                                              @Path("dateEnd")String dateEnd);


}
