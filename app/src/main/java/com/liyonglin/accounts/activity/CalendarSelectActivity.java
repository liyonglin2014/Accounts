package com.liyonglin.accounts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.utils.FinalAttr;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.qqtheme.framework.picker.TimePicker;

public class CalendarSelectActivity extends AppCompatActivity{

    private CalendarPickerView calendar;
    private Calendar todayCal;
    private Calendar lastYear;
    private SimpleDateFormat format;
    private Date date;
    private Calendar dateCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar_select);
        final Intent intent = getIntent();
        if(intent != null){
            date = (Date) intent.getSerializableExtra("date");
        }else {
            date = new Date();
        }
        dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);

        format = new SimpleDateFormat("yyyy年MM月dd日");

        todayCal = Calendar.getInstance();
        todayCal.add(Calendar.DAY_OF_YEAR, +1);

        lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        calendar = (CalendarPickerView) findViewById(R.id.cv_calendar_select);
        calendar.init(lastYear.getTime(), todayCal.getTime()).withSelectedDate(date);
        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(final Date date) {
                TimePicker picker = new TimePicker(CalendarSelectActivity.this);
                picker.setSelectedItem(dateCalendar.get(Calendar.HOUR_OF_DAY), dateCalendar.get(Calendar.MINUTE));
                picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        dateCalendar.setTime(date);
                        dateCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
                        dateCalendar.set(Calendar.MINUTE, Integer.parseInt(minute));
                        Intent result = new Intent();
                        result.putExtra("date", dateCalendar.getTime());
                        setResult(FinalAttr.UPDATE, result);
                        finish();
                    }
                });
                picker.show();
            }

            @Override
            public void onDateUnselected(Date date) {}
        });
        calendar.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
            @Override
            public void onInvalidDateSelected(Date date) {

                Toast.makeText(CalendarSelectActivity.this, "选取的日期必须在 "
                        + format.format(lastYear.getTime()) + " 与 "
                        + format.format(new Date()) + " 之间", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
