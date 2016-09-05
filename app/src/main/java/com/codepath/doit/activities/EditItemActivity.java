package com.codepath.doit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.codepath.doit.R;

public class EditItemActivity extends AppCompatActivity {

    private EditText editText;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String text = getIntent().getStringExtra("text");
        position = getIntent().getIntExtra("position", 0);
        editText = (EditText) findViewById(R.id.etEditItem);
        editText.append(text);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void onSave(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        Intent data = new Intent();
        data.putExtra("editedText", editText.getText().toString());
        data.putExtra("position", position);
        data.putExtra("code", 200);
        setResult(200, data);
        this.finish();
    }
}
