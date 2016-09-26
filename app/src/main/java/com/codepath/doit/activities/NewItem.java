package com.codepath.doit.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.doit.R;
import com.codepath.doit.models.Priority;
import com.codepath.doit.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewItem extends AppCompatActivity implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private EditText etNewTask;
    private EditText timeTextView;
    private EditText dateTextView;
    private Spinner spinner;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Priority.values()));

        // Find our View instances
        etNewTask = (EditText)findViewById(R.id.etNewTask);
        timeTextView = (EditText)findViewById(R.id.etDisplayTime);
        dateTextView = (EditText)findViewById(R.id.etDisplayDate);
        ImageView timeButton = (ImageView)findViewById(R.id.imgTime);
        ImageView dateButton = (ImageView)findViewById(R.id.imgDate);

        String subject = getIntent().getStringExtra("subject");
        position = getIntent().getIntExtra("position", -1);
        Priority priority = (Priority) getIntent().getSerializableExtra("priority");
        Date date = (Date) getIntent().getSerializableExtra("date");
        String time = getIntent().getStringExtra("time");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        if(!TextUtils.isEmpty(subject)) {
            etNewTask.append(subject);
        }

        if(priority == Priority.HIGH) {
            spinner.setSelection(2);
        } else if(priority == Priority.MEDIUM) {
            spinner.setSelection(1);
        } else {
            spinner.setSelection(0);
        }

        if(!TextUtils.isEmpty(Utils.getStringFromDate(date))) {
            dateTextView.setText(Utils.getStringFromDate(date));
        }
        if(!TextUtils.isEmpty(time)) {
            timeTextView.setText(time);
        }

        // Show a timepicker when the timeButton is clicked
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        NewItem.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });

        // Show a datepicker when the dateButton is clicked
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        NewItem.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        Drawable drawable = menu.findItem(R.id.newadd).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

        Drawable drawable1 = menu.findItem(R.id.shareNew).getIcon();
        if (drawable1 != null) {
            drawable1.mutate();
            drawable1.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
        return true;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if(tpd != null) tpd.setOnTimeSetListener(this);
        if(dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String time = hourString+":"+minuteString;
        timeTextView.setText(time);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = (++monthOfYear)+"/"+dayOfMonth+"/"+year;
        Log.w("MyApp", "onDateSet: " + date);
        dateTextView.setText(date);
    }

    public void clearDate(View view) {
        dateTextView.setText("");
    }

    public void clearTime(View view) {
        timeTextView.setText("");
    }

    public void onAddNewSaveClick(MenuItem item) {
        if(TextUtils.isEmpty(etNewTask.getText().toString().trim())) {
            Toast.makeText(NewItem.this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etNewTask.getWindowToken(), 0);
            Intent data = new Intent();
            data.putExtra("subject", etNewTask.getText().toString());
            System.out.println("NewActivity position " + position);
            data.putExtra("position", position);
            data.putExtra("date", dateTextView.getText().toString());
            data.putExtra("time", timeTextView.getText().toString());
            Priority p = (Priority) spinner.getSelectedItem();
            data.putExtra("Priority", p);
            setResult(200, data);
            this.finish();
        }
    }

    public void onDateSet(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                NewItem.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void onTimeSet(View view) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                NewItem.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    public void onSpkBtnClick(View view) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, 201);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 201 && resultCode == RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(!TextUtils.isEmpty(etNewTask.getText().toString().trim())) {
                etNewTask.append(" " + thingsYouSaid.get(0));
            } else {
                etNewTask.append(thingsYouSaid.get(0));
            }
            etNewTask.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    public void onShareClick(MenuItem view) {
        if(!TextUtils.isEmpty(etNewTask.getText().toString())) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "";
            shareBody += etNewTask.getText().toString();
            if(!TextUtils.isEmpty(dateTextView.getText().toString())) {
                shareBody += " by " + dateTextView.getText().toString();
                if(!TextUtils.isEmpty(timeTextView.getText().toString())) {
                    shareBody += " " + timeTextView.getText().toString();
                }
            }
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ToDo task");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
    }
}
