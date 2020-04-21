package dandy1988.myspacelaunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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
                            Objects.requireNonNull(rv.getAdapter()).notifyDataSetChanged();
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
//        startDay = 20;startMonth = 8;startYear = 2015;
//        endDay = 20;endMonth = 9;endYear = 2015;
        int startDay; int startMonth; int startYear;
        int endDay; int endMonth; int endYear;
        String limit;

        if ((etStartDay!=null)&&(etStartMonth!=null)&&(etStartYear!=null)&&(etEndDay!=null)
        &&(etEndMonth!=null)&&(etEndYear!=null)){
            if((etStartDay.getText().toString().equals(""))||(etStartMonth.getText().toString().equals(""))
                    ||(etStartYear.getText().toString().equals(""))
                    ||(etEndDay.getText().toString().equals(""))||(etEndMonth.getText().toString().equals(""))
                    ||(etEndYear.getText().toString().equals(""))) {
                errorMassage(view, "SOME VALUES EMPTY");
            }else {

                startDay = Integer.valueOf(etStartDay.getText().toString());
                startMonth = Integer.valueOf(etStartMonth.getText().toString()) - 1;
                startYear = Integer.valueOf(etStartYear.getText().toString());
                endDay = Integer.valueOf(etEndDay.getText().toString());
                endMonth = Integer.valueOf(etEndMonth.getText().toString()) - 1;
                endYear = Integer.valueOf(etEndYear.getText().toString());
                limit = etLimit.getText().toString();

                if ((startDay>31)||(startDay==0)||(startMonth>11)||(startMonth<0)||(startYear<1961)
                    ||(endDay>31)||(endDay==0)||(endMonth>11)||(endMonth<0)||(startYear>endYear)){
                    errorMassage(view, "WRONG DATA OF PERIOD");
                }else {
                    Calendar calendarDateStart = new GregorianCalendar(startYear, startMonth, startDay);
                    Calendar calendarDateEnd = new GregorianCalendar(endYear, endMonth, endDay);
                    try {
                        getLaunchCollection(calendarDateStart, calendarDateEnd, limit);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    void errorMassage(View view, String text){
        Toast toast = Toast.makeText(view.getContext(), text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        toastContainer.setBackgroundColor(Color.YELLOW);
        toast.show();
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
