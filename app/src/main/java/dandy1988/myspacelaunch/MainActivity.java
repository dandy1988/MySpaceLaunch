package dandy1988.myspacelaunch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private LaunchCollection launchCollection;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendarDateStart = new GregorianCalendar(2015, 7 , 20);
        Calendar calendarDateEnd = new GregorianCalendar(2015, 8 , 20);


        try {
            getLaunchCollection(calendarDateStart,calendarDateEnd);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        textView = (TextView) findViewById(R.id.tvEvents);

    }

    private void getLaunchCollection(Calendar calendarDateStart, Calendar calendarDateEnd) throws IOException {

        Date dateStart = calendarDateStart.getTime();
        Date dateEnd = calendarDateEnd.getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

        NetworkService.getInstance()
                .getApiService()
                .getLaunches(formatDate.format(dateStart), formatDate.format(dateEnd))
                .enqueue(new Callback<LaunchCollection>() {
                    @Override
                    public void onResponse(Call<LaunchCollection> call, Response<LaunchCollection> response) {
                       LaunchCollection launchCollection = response.body();

                        String text = "NULL";
                        if (launchCollection != null) {
                            for (LaunchEvent launchEvent : launchCollection.getLaunches()) {
                                text = text + launchEvent.getId() + "  " + launchEvent.getNet() +
                                        "  " + launchEvent.getName() + "\n";
                            }
                        }
                        textView.setText(text);
                    }

                    @Override
                    public void onFailure(Call<LaunchCollection> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
//
//        return launchCollection;
    }





//
//    private void jsonToObjectUrl(String events) throws IOException {
//        // Create Jackson ObjectMapper instance
//        final ObjectMapper mapper = new ObjectMapper();
//
////        LaunchEventCollection launchEvents = mapper.readValue(events, LaunchEventCollection.class);
//
//        final Handler handler = new Handler();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final LaunchCollection launchEvents = mapper.readValue(
//                            new URL("https://launchlibrary.net/1.4/launch/2015-08-20/2015-09-20"),
//                            LaunchCollection.class);// read from url
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            final TextView textView = (TextView) findViewById(R.id.tvEvents);
//                            String text = "";
//                            for (LaunchEvent launchEvent : launchEvents.getLaunches()) {
//                                text = text + launchEvent.getId() + "  " + launchEvent.getNet() +
//                                        "  " + launchEvent.getName()+"\n";
//                            }
//                            textView.setText(text);
//                        }
//                    });
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

}