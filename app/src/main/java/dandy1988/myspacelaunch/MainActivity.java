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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String json ="{\"launches\":[\n" +
//                "   {\n" +
//                "      \"id\":2053,\n" +
//                "      \"name\":\"Falcon 9 Block 5 | Starlink 6\",\n" +
//                "      \"windowstart\":\"2020-04-23 19:16:00\",\n" +
//                "      \"windowend\":\"2020-04-23 19:16:00\",\n" +
//                "      \"net\":\"April 23, 2020 19:16:00 UTC\",\n" +
//                "      \"status\":1,\n" +
//                "      \"tbdtime\":0,\n" +
//                "      \"tbddate\":0,\n" +
//                "      \"probability\":-1,\n" +
//                "      \"changed\":\"2020-04-14 23:09:55\",\n" +
//                "      \"lsp\":\"121\"\n" +
//                "   },\n" +
//                "   {\n" +
//                "      \"id\":1971,\n" +
//                "      \"name\":\"Soyuz 2.1a | Progress MS-14 (75P)\",\n" +
//                "      \"windowstart\":\"2020-04-25 01:51:00\",\n" +
//                "      \"windowend\":\"2020-04-25 01:51:00\",\n" +
//                "      \"net\":\"April 25, 2020 01:51:00 UTC\",\n" +
//                "      \"status\":1,\n" +
//                "      \"tbdtime\":0,\n" +
//                "      \"tbddate\":0,\n" +
//                "      \"probability\":-1,\n" +
//                "      \"changed\":\"2020-04-13 16:32:35\",\n" +
//                "      \"lsp\":\"63\"\n" +
//                "   },\n" +
//                "   {\n" +
//                "      \"id\":1359,\n" +
//                "      \"name\":\"Falcon 9 Block 5 | SAOCOM 1B\",\n" +
//                "      \"windowstart\":\"2020-04-30 00:00:00\",\n" +
//                "      \"windowend\":\"2020-04-30 00:00:00\",\n" +
//                "      \"net\":\"April 30, 2020 00:00:00 UTC\",\n" +
//                "      \"status\":2,\n" +
//                "      \"tbdtime\":1,\n" +
//                "      \"tbddate\":1,\n" +
//                "      \"probability\":-1,\n" +
//                "      \"changed\":\"2020-03-28 21:32:22\",\n" +
//                "      \"lsp\":\"121\"\n" +
//                "   },\n" +
//                "   {\n" +
//                "      \"id\":1595,\n" +
//                "      \"name\":\"Soyuz 2.1a | Bars-M No. 3\",\n" +
//                "      \"windowstart\":\"2020-04-30 00:00:00\",\n" +
//                "      \"windowend\":\"2020-04-30 00:00:00\",\n" +
//                "      \"net\":\"April 30, 2020 00:00:00 UTC\",\n" +
//                "      \"status\":2,\n" +
//                "      \"tbdtime\":1,\n" +
//                "      \"tbddate\":1,\n" +
//                "      \"probability\":-1,\n" +
//                "      \"changed\":\"2020-02-29 11:10:34\",\n" +
//                "      \"lsp\":\"193\"\n" +
//                "   },\n" +
//                "   {\n" +
//                "      \"id\":1603,\n" +
//                "      \"name\":\"LauncherOne | Test Flight\",\n" +
//                "      \"windowstart\":\"2020-04-30 00:00:00\",\n" +
//                "      \"windowend\":\"2020-04-30 00:00:00\",\n" +
//                "      \"net\":\"April 30, 2020 00:00:00 UTC\",\n" +
//                "      \"status\":2,\n" +
//                "      \"tbdtime\":1,\n" +
//                "      \"tbddate\":1,\n" +
//                "      \"probability\":-1,\n" +
//                "      \"changed\":\"2020-03-21 17:24:16\",\n" +
//                "      \"lsp\":\"199\"\n" +
//                "   },\n" +
//                "   {\n" +
//                "      \"id\":1828,\n" +
//                "      \"name\":\"Minotaur IV | NROL-129\",\n" +
//                "      \"windowstart\":\"2020-04-30 00:00:00\",\n" +
//                "      \"windowend\":\"2020-04-30 00:00:00\",\n" +
//                "      \"net\":\"April 30, 2020 00:00:00 UTC\",\n" +
//                "      \"status\":2,\n" +
//                "      \"tbdtime\":1,\n" +
//                "      \"tbddate\":1,\n" +
//                "      \"probability\":-1,\n" +
//                "      \"changed\":\"2020-02-28 08:27:01\",\n" +
//                "      \"lsp\":\"257\"\n" +
//                "   },\n" +
//                "   {\n" +
//                "      \"id\":2036,\n" +
//                "      \"name\":\"Electron | VCLS ELaNa XXXII & others (Don't Stop Me Now)\",\n" +
//                "      \"windowstart\":\"2020-04-30 00:00:00\",\n" +
//                "      \"windowend\":\"2020-04-30 00:00:00\",\n" +
//                "      \"net\":\"April 30, 2020 00:00:00 UTC\",\n" +
//                "      \"status\":2,\n" +
//                "      \"tbdtime\":1,\n" +
//                "      \"tbddate\":1,\n" +
//                "      \"probability\":-1,\n" +
//                "      \"changed\":\"2020-03-28 21:32:30\",\n" +
//                "      \"lsp\":\"147\"\n" +
//                "   },\n" +
//                "   {\n" +
//                "      \"id\":1937,\n" +
//                "      \"name\":\"Kuaizhou-11 | Maiden Flight\",\n" +
//                "      \"windowstart\":\"2020-04-30 00:00:00\",\n" +
//                "      \"windowend\":\"2020-04-30 00:00:00\",\n" +
//                "      \"net\":\"April 30, 2020 00:00:00 UTC\",\n" +
//                "      \"status\":2,\n" +
//                "      \"tbdtime\":1,\n" +
//                "      \"tbddate\":1,\n" +
//                "      \"probability\":-1,\n" +
//                "      \"changed\":\"2020-03-26 20:37:20\",\n" +
//                "      \"lsp\":\"194\"\n" +
//                "   },\n" +
//                "   {\n" +
//                "      \"id\":1938,\n" +
//                "      \"name\":\"Falcon 9 Block 5 | ANASIS-II\",\n" +
//                "      \"windowstart\":\"2020-04-30 00:00:00\",\n" +
//                "      \"windowend\":\"2020-04-30 00:00:00\",\n" +
//                "      \"net\":\"April 30, 2020 00:00:00 UTC\",\n" +
//                "      \"status\":2,\n" +
//                "      \"tbdtime\":1,\n" +
//                "      \"tbddate\":1,\n" +
//                "      \"probability\":-1,\n" +
//                "      \"changed\":\"2020-02-29 11:11:36\",\n" +
//                "      \"lsp\":\"121\"\n" +
//                "   },\n" +
//                "   {\n" +
//                "      \"id\":1944,\n" +
//                "      \"name\":\"Soyuz 2.1b\\/Fregat-M | OneWeb 4\",\n" +
//                "      \"windowstart\":\"2020-04-30 00:00:00\",\n" +
//                "      \"windowend\":\"2020-04-30 00:00:00\",\n" +
//                "      \"net\":\"April 30, 2020 00:00:00 UTC\",\n" +
//                "      \"status\":2,\n" +
//                "      \"tbdtime\":1,\n" +
//                "      \"tbddate\":1,\n" +
//                "      \"probability\":-1,\n" +
//                "      \"changed\":\"2020-03-02 18:13:14\",\n" +
//                "      \"lsp\":\"96\"\n" +
//                "   }\n" +
//                "],\n" +
//                "\"total\":208,\n" +
//                "\"offset\":0,\n" +
//                "\"count\":10\n" +
//                "}";
//
//        try {
//            jsonToObjectUrl(json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }






    }

    @Override
    protected void onStart() {
        super.onStart();

        Calendar calendarDateStart = new GregorianCalendar(2015, 7 , 20);
        Date dateStart = calendarDateStart.getTime();
        Calendar calendarDateEnd = new GregorianCalendar(2015, 8 , 20);
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

                        String text = "";
                        if (launchCollection != null){
                            for (LaunchEvent launchEvent : launchCollection.getLaunches()) {
                                text = text + launchEvent.getId() + "  " + launchEvent.getNet() +
                                        "  " + launchEvent.getName() + "\n";
                            }
                        }

                        final TextView textView = (TextView) findViewById(R.id.tvEvents);
                        textView.setText(text);

                    }

                    @Override
                    public void onFailure(Call<LaunchCollection> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

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