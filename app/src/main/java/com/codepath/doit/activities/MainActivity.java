package com.codepath.doit.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codepath.doit.R;
import com.codepath.doit.adapter.CustomItemsAdapter;
import com.codepath.doit.models.Item;
import com.codepath.doit.models.Priority;
import com.codepath.doit.utils.DBUtils;

import java.util.ArrayList;
import java.util.Collections;

import values.WidgetProvider;

public class MainActivity extends AppCompatActivity implements OnClickListener, SearchView.OnQueryTextListener, CompoundButton.OnCheckedChangeListener {

    ArrayList<Item> items = new ArrayList<>();
    CustomItemsAdapter aToDoAdaptor;
    ListView listView;
    EditText editText;
    ImageView imgTick;
    ImageView spkBtn;
    FloatingActionButton fab;
    MenuItem deleteItems;
    MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateItems();
        listView = (ListView) findViewById(R.id.lvDisplay);
        listView.setAdapter(aToDoAdaptor);
        findViewById(R.id.spkBtn).setOnClickListener(this);
        imgTick = (ImageView)findViewById(R.id.imgTick);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        spkBtn = (ImageView) findViewById(R.id.spkBtn);
        editText = (EditText) findViewById(R.id.etAddText);

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
                                items.remove(itemToBeDeleted);
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
                Intent i = new Intent(MainActivity.this, NewItem.class);
                i.putExtra("subject", aToDoAdaptor.getItem(position).subject);
                i.putExtra("priority", aToDoAdaptor.getItem(position).priority);
                i.putExtra("date", aToDoAdaptor.getItem(position).dueDate);
                i.putExtra("time", aToDoAdaptor.getItem(position).dueTime);
                i.putExtra("position", position);
                startActivityForResult(i, 202);
            }
        });

        editText.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                listView.smoothScrollToPosition(listView.getCount() -1);
                if(!TextUtils.isEmpty(cs.toString().trim())) {
                    fab.setVisibility(View.INVISIBLE);
                    imgTick.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                    imgTick.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }

    private void populateItems() {
        items = (ArrayList<Item>) DBUtils.readAll();
        Collections.sort(items);
        aToDoAdaptor = new CustomItemsAdapter(this, items);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Drawable drawable = menu.findItem(R.id.search).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setTint(Color.WHITE);
        }
        Drawable drawableDelete = menu.findItem(R.id.deleteItems).getIcon();
        if (drawableDelete != null) {
            drawableDelete.mutate();
            drawableDelete.setTint(Color.WHITE);
        }
        searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.findViewById(android.support.v7.appcompat.R.id.search_plate).setBackgroundColor(Color.WHITE);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);
        searchView.setOnQueryTextListener(this);

        deleteItems = menu.findItem(R.id.deleteItems);
        deleteItems.setVisible(false);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.w("MyApp", "onMenuItemActionExpand");
                fab.setVisibility(View.INVISIBLE);
                spkBtn.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.INVISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.w("MyApp", "onMenuItemActionCollapse");
                fab.setVisibility(View.VISIBLE);
                spkBtn.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return (id == R.id.action_settings) || super.onOptionsItemSelected(item);
    }

    public void onShareClick(MenuItem view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "";
        for(int i = 0; i < items.size(); i++) {
            shareBody += i+1 + ". " + items.get(i).subject;
            if(!TextUtils.isEmpty(items.get(i).dueDate)) {
                shareBody += " by " + items.get(i).dueDate;
                if(!TextUtils.isEmpty(items.get(i).dueTime)) {
                    shareBody += " " + items.get(i).dueTime;
                }
            }
            shareBody += "\n";
        }
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ToDo tasks");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==201  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            editText.append(thingsYouSaid.get(0));
            editText.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        if (requestCode==202  && resultCode==200) {
            Item newItem;
            int position = data.getExtras().getInt("position");
            if(position >= 0) {
                System.out.println("MainActivity position " + position);
                newItem = aToDoAdaptor.getItem(position);
            } else {
                newItem = new Item();
            }
            newItem.subject = data.getExtras().getString("subject");
            newItem.dueDate = data.getExtras().getString("date");
            newItem.dueTime = data.getExtras().getString("time");
            newItem.priority = (Priority) data.getSerializableExtra("Priority");
            if(position == -1) {
                Log.w("MyApp", "position 0");
                aToDoAdaptor.add(newItem);
                items.add(newItem);
            }
            Collections.sort(items);
            aToDoAdaptor.notifyDataSetChanged();
            DBUtils.writeOne(newItem);
        }
    }

    public void onAddFull(View view) {
        Intent i = new Intent(MainActivity.this, NewItem.class);
        startActivityForResult(i, 202);
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

    public void onTickClick(View view) {
        String newItem = editText.getText().toString().trim();
        if(!TextUtils.isEmpty(newItem)) {
            Item item = new Item(newItem);
            item.priority = Priority.LOW;
            aToDoAdaptor.add(item);
            editText.setText("");
            listView.smoothScrollToPosition(listView.getCount() -1);
            Collections.sort(items);
            DBUtils.writeOne(item);
            items.add(item);
            Collections.sort(items);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.w("MyApp", "onQueryTextChange");
        MainActivity.this.aToDoAdaptor.getFilter().filter(newText);
        return true;
    }

    ArrayList<Item> deleteList = new ArrayList<>();

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.w("MyApp", "onCheckedChanged");
        int pos = listView.getPositionForView(buttonView);
        Item tempItem = aToDoAdaptor.getItem(pos);
        if(isChecked) {
            if(!deleteList.contains(tempItem)) {
                deleteList.add(tempItem);
            }
        } else {
            if(deleteList.contains(tempItem)) {
                deleteList.remove(tempItem);
            }
        }
        if(isChecked || deleteList.size() > 0) {
            Log.w("MyApp", "search disable");
            searchItem.setVisible(false);
            deleteItems.setVisible(true);
        }
        if(deleteList.size() == 0){
            Log.w("MyApp", "search enable");
            searchItem.setVisible(true);
            deleteItems.setVisible(false);
        }
    }

    public void deleteItems(MenuItem item) {
        Log.w("MyApp", "deleteItems");
        for(Item temp: deleteList) {
            Item.delete(Item.class, temp.getId());
            aToDoAdaptor.remove(temp);
            items.remove(temp);
            Collections.sort(items);
        }
        deleteList.clear();
        deleteItems.setVisible(false);
        searchItem.setVisible(true);
        updateWidgets();
    }

    private void updateWidgets() {
        int widgetIDs[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), WidgetProvider.class));
        AppWidgetManager.getInstance(getApplication()).notifyAppWidgetViewDataChanged(widgetIDs, R.id.to_do_widget);
    }
}