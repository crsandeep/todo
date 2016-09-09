package com.codepath.doit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codepath.doit.R;
import com.codepath.doit.adapter.CustomItemsAdapter;
import com.codepath.doit.models.Item;
import com.codepath.doit.utils.DBUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    ArrayList<Item> items = new ArrayList<>();
    CustomItemsAdapter aToDoAdaptor;
    ListView listView;
    EditText editText;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateItems();
        listView = (ListView) findViewById(R.id.lvDisplay);
        listView.setAdapter(aToDoAdaptor);
        findViewById(R.id.spkBtn).setOnClickListener(this);
        editText = (EditText) findViewById(R.id.etAddText);
        etSearch = (EditText) findViewById(R.id.etSearch);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                new MaterialDialog.Builder(MainActivity.this)
                        .title("Confirm delete")
                        .content("Are you sure?")
                        .positiveText("Yes")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Item itemToBeDeleted = aToDoAdaptor.getItem(pos);
                                Item.delete(Item.class, itemToBeDeleted.getId());
                                aToDoAdaptor.remove(itemToBeDeleted);
                                aToDoAdaptor.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .negativeText("No")
                        .show();

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("text", aToDoAdaptor.getItem(position).subject);
                i.putExtra("position", position);
                startActivityForResult(i, 200);
            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MainActivity.this.aToDoAdaptor.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                MainActivity.this.aToDoAdaptor.notifyDataSetChanged();
            }
        });
    }

    private void populateItems() {
        items = new ArrayList<>();
        items = (ArrayList<Item>) DBUtils.readAll();
        aToDoAdaptor = new CustomItemsAdapter(this, items);
    }

    public void onAddNewItem(View view) {
        String newItem = editText.getText().toString().trim();
        if(!TextUtils.isEmpty(newItem)) {
            Item item = new Item(newItem);
            aToDoAdaptor.add(item);
            aToDoAdaptor.notifyDataSetChanged();
            editText.setText("");
            listView.smoothScrollToPosition(listView.getCount() -1);
            DBUtils.writeOne(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 200 && requestCode == 200) {
            String editedText = data.getExtras().getString("editedText");
            int position = data.getExtras().getInt("position");
            Item item = aToDoAdaptor.getItem(position);
            item.subject = editedText;
            aToDoAdaptor.notifyDataSetChanged();
            DBUtils.writeOne(item);
        }
        if (requestCode==201  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            editText.append(thingsYouSaid.get(0));
            editText.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    public void onAddFull(View view) {
        Intent i = new Intent(MainActivity.this, NewItem.class);
        startActivity(i);
    }

    public void onClick(View view) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, 201);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }
}
