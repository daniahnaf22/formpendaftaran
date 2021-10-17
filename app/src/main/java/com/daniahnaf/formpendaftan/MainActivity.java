package com.daniahnaf.formpendaftan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daniahnaf.formpendaftan.adapter.Adapter;
import com.daniahnaf.formpendaftan.helper.DbHelper;
import com.daniahnaf.formpendaftan.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    DbHelper SQLite = new DbHelper(this);

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_ADDRESS = "address";
    public static final String TAG_NOHP = "nohp";
    public static final String TAG_JK = "jk";
    private String title = "Form Pendaftaran";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBarTitle(title);

        //Tambah SQLite
        SQLite = new DbHelper(getApplicationContext());

        FloatingActionButton fab = findViewById(R.id.fab);

        //Tambah ListView
        listView = findViewById(R.id.list_view);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                startActivity(intent);
            }
        });

        //Tambah adapter dan Listview
        adapter = new Adapter(MainActivity.this, itemList);
        listView.setAdapter(adapter);

        //tekan lama daftar lsitview untuk menampilkan edit dan hapus
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO Auto-generated method stub
                final String idx = itemList.get(position).getId();
                final String name = itemList.get(position).getName();
                final String address = itemList.get(position).getAddress();
                final String nohp = itemList.get(position).getNohp();
                final String jk = itemList.get(position).getJk();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                                intent.putExtra(TAG_ID, idx);
                                intent.putExtra(TAG_NAME, name);
                                intent.putExtra(TAG_ADDRESS, address);
                                intent.putExtra(TAG_NOHP, nohp);
                                intent.putExtra(TAG_JK, jk);
                                startActivity(intent);
                                break;
                            case 1:
                                SQLite.delete(Integer.parseInt(idx));
                                itemList.clear();
                                getAllData();
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
        getAllData();

    }

    private void getAllData() {
        ArrayList<HashMap<String, String>> row = SQLite.getAllData();

        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(TAG_ID);
            String poster = row.get(i).get(TAG_NAME);
            String title = row.get(i).get(TAG_ADDRESS);
            String itemnoHp = row.get(i).get(TAG_NOHP);
            String itemjk = row.get(i).get(TAG_JK);

            Data data = new Data();

            data.setId(id);
            data.setName(poster);
            data.setAddress(title);
            data.setNohp(itemnoHp);
            data.setJk(itemjk);

            itemList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        itemList.clear();
        getAllData();
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

}