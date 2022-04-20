//JAV-1001
//Iricher B Supera
//A00237146
package com.isupera.contactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    // create variables
    private EditText edt_name, edt_mobile, edt_email;
    private Button btn_add;
    private FloatingActionButton btn_dialog;
    private RecyclerView rv_contacts;

    // adapter class and array list
    private ContactsAdapter adapter;
    private ArrayList<Contacts> contactsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize our variables
        edt_name = findViewById(R.id.edt_name);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        btn_dialog = findViewById(R.id.btn_dialog);
        rv_contacts = findViewById(R.id.rv_contacts);

        // calling method to load data from shared prefs
        loadData();
        buildRecyclerView();

        // on click listener for calling the dialog and adding data to array list
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_update);

                edt_name = dialog.findViewById(R.id.edt_name);
                edt_mobile = dialog.findViewById(R.id.edt_mobile);
                edt_email = dialog.findViewById(R.id.edt_email);
                btn_add = dialog.findViewById(R.id.btn_action);

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edt_name.getText().toString().equals("")) {
                            Toast.makeText(getBaseContext(), "Enter name", Toast.LENGTH_SHORT).show();
                        }else if (edt_mobile.getText().toString().equals("")) {
                            Toast.makeText(getBaseContext(), "Enter mobile", Toast.LENGTH_SHORT).show();
                        }else if (edt_email.getText().toString().equals("")) {
                            Toast.makeText(getBaseContext(), "Enter email", Toast.LENGTH_SHORT).show();
                        }else {
                            // below line is use to add data to array list
                            contactsArrayList.add(new Contacts(edt_name.getText().toString(), edt_mobile.getText().toString(), edt_email.getText().toString()));
                            // notify adapter when new data added
                            adapter.notifyItemInserted(contactsArrayList.size() - 1);

                            rv_contacts.scrollToPosition(contactsArrayList.size() - 1);

                            dialog.dismiss();
                        }
                    }

                });

                dialog.show();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        // inflate menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mnu_save:
                // call method to save data in shared prefs
                saveData();
                break;
            case R.id.mnu_about:
                // call method to display about app
                displayAbout();
                break;
        }
        return true;
    }

    private void buildRecyclerView() {
        // initialize our adapter class
        adapter = new ContactsAdapter(contactsArrayList, MainActivity.this);

        // add layout manager to our recycler view
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_contacts.setHasFixedSize(true);

        // set layout manager to our recycler view
        rv_contacts.setLayoutManager(manager);

        // set adapter to our recycler view
        rv_contacts.setAdapter(adapter);
    }

    private void loadData() {
        // initialize our shared prefs with name as shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // create a variable for gson
        Gson gson = new Gson();

        // get to string present from shared prefs if not present setting it as null
        String json = sharedPreferences.getString("contacts", null);

        // below line is to get the type of our array list
        Type type = new TypeToken<ArrayList<Contacts>>() {}.getType();

        // get data from gson and saving it to our array list
        contactsArrayList = gson.fromJson(json, type);

        // check below if the array list is empty or not
        if (contactsArrayList == null) {
            // if the array list is empty, create a new array list.
            contactsArrayList = new ArrayList<>();
        }
    }

    private void saveData() {
        // create a variable for storing data in shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // create a variable for editor to store data
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // create a new variable for gson
        Gson gson = new Gson();

        // get data from gson and storing it in a string
        String json = gson.toJson(contactsArrayList);

        // save data in shared prefs in the form of string
        editor.putString("contacts", json);

        // apply changes and save data in shared prefs
        editor.apply();

        // display a toast message after saving data
        Toast.makeText(this, "Saved to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    private void displayAbout(){
        // create dialog and call about app
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.about_app);
        dialog.show();
    }
}