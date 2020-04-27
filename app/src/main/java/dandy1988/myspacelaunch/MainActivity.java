package dandy1988.myspacelaunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
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
    private TextView tvStartDate;
    private TextView tvEndDate;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDate = new GregorianCalendar(2015, 6, 20);
        endDate = new GregorianCalendar(2015, 7, 20);

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
        EditText etLimit = findViewById(R.id.etAmountLanches);
        String limit = etLimit.getText().toString();

        try {
            getLaunchCollection(startDate, endDate, limit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tvStartDateClick(View view) {
        tvStartDate = findViewById(R.id.tvStartDate);
        new DatePickerDialog(MainActivity.this, startDateListener,
                startDate.get(Calendar.YEAR),
                startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if ((year>1961)&&(monthOfYear>0)) {
                startDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                monthOfYear++;
                String tvText = dayOfMonth + "-" + monthOfYear + "-" + year;
                tvStartDate.setText(tvText.toString());
            }else{
                errorMassage(view, "Data must be >= 01-01-1961");
            }
        }
    };

    public void tvEndDateClick(View view) {
        tvEndDate = findViewById(R.id.tvStartDate);
        new DatePickerDialog(MainActivity.this, endDateListener,
                startDate.get(Calendar.YEAR),
                startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            monthOfYear++;
            String tvText = dayOfMonth + "-" + monthOfYear + "-" + year;
            tvEndDate.setText(tvText.toString());
        }
    };

    void errorMassage(View view, String text) {
        Toast toast = Toast.makeText(view.getContext(), text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        toastContainer.setBackgroundColor(Color.YELLOW);
        toast.show();
    }
}

