package dandy1988.myspacelaunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
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

    private RecyclerView rv;
    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTest = findViewById(R.id.checkedTextView);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void getLaunchCollection(Calendar calendarDateStart, Calendar calendarDateEnd,
                                     String limit) throws IOException {

        Date dateStart = calendarDateStart.getTime();
        Date dateEnd = calendarDateEnd.getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

        NetworkService.getInstance()
                .getApiService()
                .getLaunches(formatDate.format(dateStart), formatDate.format(dateEnd), limit)
                .enqueue(new Callback<LaunchCollection>() {
                    @Override
                    public void onResponse(Call<LaunchCollection> call, Response<LaunchCollection> response) {
                        LaunchCollection launchCollection = response.body();
                        if (launchCollection != null) {
                            rv.setAdapter(new RecycleViewAdapter.LaunchAdapter(launchCollection));
                            rv.getAdapter().notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<LaunchCollection> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void btn_getPeriod(View view) {
        EditText etStartDay = findViewById(R.id.etStartDay);
        EditText etStartMonth = findViewById(R.id.etStartMonth);
        EditText etStartYear = findViewById(R.id.etStartYear);
        EditText etEndDay = findViewById(R.id.etEndDay);
        EditText etEndMonth = findViewById(R.id.etEndMonth);
        EditText etEndYear = findViewById(R.id.etEndYear);
        EditText etLimit = findViewById(R.id.etAmountLanches);


        int startDay = Integer.valueOf(etStartDay.getText().toString());
        int startMonth = Integer.valueOf(etStartMonth.getText().toString()) - 1;
        int startYear = Integer.valueOf(etStartYear.getText().toString());
        int endDay = Integer.valueOf(etEndDay.getText().toString());
        int endMonth = Integer.valueOf(etEndMonth.getText().toString()) - 1;
        int endYear = Integer.valueOf(etEndYear.getText().toString());
        String limit = etLimit.getText().toString();

        Calendar calendarDateStart = new GregorianCalendar(startYear, startMonth, startDay);
        Calendar calendarDateEnd = new GregorianCalendar(endYear, endMonth, endDay);
        try {
            getLaunchCollection(calendarDateStart, calendarDateEnd, limit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
