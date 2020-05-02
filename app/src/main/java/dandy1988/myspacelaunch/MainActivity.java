package dandy1988.myspacelaunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import dandy1988.myspacelaunch.api.NetworkService;
import dandy1988.myspacelaunch.data.LaunchCollection;
import dandy1988.myspacelaunch.view.RecycleViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private RecyclerView rv;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private EditText etLimit;
//    private CheckedTextView checkedTextView;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private RecycleViewAdapter.LaunchAdapter recycleViewAdapter;
    private LaunchCollection launchCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            startDate = new GregorianCalendar(2015, 6, 20);
            endDate = new GregorianCalendar(2015, 7, 20);
        }

        setContentView(R.layout.activity_main);

        etLimit = findViewById(R.id.etAmountLanches);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleViewAdapter = new RecycleViewAdapter.LaunchAdapter(new LaunchCollection());
    }

    @Override
    protected void onStart(){
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
                            createRecycleViewAdapter(launchCollection);
                            rv.setAdapter(recycleViewAdapter);
                            Objects.requireNonNull(rv.getAdapter()).notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<LaunchCollection> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void createRecycleViewAdapter(LaunchCollection launchCollection1){
        launchCollection = launchCollection1;
        if (recycleViewAdapter == null) {
            recycleViewAdapter = new RecycleViewAdapter.LaunchAdapter(launchCollection1);
        }else{
            recycleViewAdapter.setData(launchCollection1);
        }
    }

    public void getPeriod(View view) {
        String limit = etLimit.getText().toString();
        try {
            getLaunchCollection(startDate, endDate, limit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void StartDateClick(View view) {
        tvStartDate = findViewById(R.id.tvStartDate);
        new DatePickerDialog(MainActivity.this, startDateListener,
                startDate.get(Calendar.YEAR),
                startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (year>1960) {
                startDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                monthOfYear++;
                String tvText = dayOfMonth + "-" + monthOfYear + "-" + year;
                tvStartDate.setText(tvText.toString());
            }else{
                errorMassage(view, "Data must be >= 01-01-1961");
            }
        }
    };

    public void endDateClick(View view) {
        tvEndDate = findViewById(R.id.tvEndDate);
        new DatePickerDialog(MainActivity.this, endDateListener,
                endDate.get(Calendar.YEAR),
                endDate.get(Calendar.MONTH),
                endDate.get(Calendar.DAY_OF_MONTH))
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("launchCollection", launchCollection);
        outState.putSerializable("startDate", startDate);
        outState.putSerializable("endDate", endDate);
        outState.putString("limit", etLimit.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("launchCollection")) {
                launchCollection = (LaunchCollection) savedInstanceState.getSerializable("launchCollection");
                recycleViewAdapter = new RecycleViewAdapter.LaunchAdapter(launchCollection);
                rv.setAdapter(recycleViewAdapter);
                Objects.requireNonNull(rv.getAdapter()).notifyDataSetChanged();
            }

            if (savedInstanceState.containsKey("limit")) {
                etLimit.setText(savedInstanceState.getString("limit"));
            }

            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey("startDate")) {
                    startDate = (Calendar) savedInstanceState.getSerializable("startDate");
                    tvStartDate = findViewById(R.id.tvStartDate);
                    String text = startDate.get(Calendar.DAY_OF_MONTH)+"-"+(startDate.get(Calendar.MONTH)+1)+"-"+startDate.get(Calendar.YEAR);
                    tvStartDate.setText(text);
                }
                if (savedInstanceState.containsKey("endDate")) {//todo
                    endDate = (Calendar) savedInstanceState.getSerializable("endDate");
                    tvEndDate = findViewById(R.id.tvEndDate);
                    String text = endDate.get(Calendar.DAY_OF_MONTH)+"-"+(endDate.get(Calendar.MONTH)+1)+"-"+endDate.get(Calendar.YEAR);
                    tvEndDate.setText(text);
                }
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }


}

