package com.daniahnaf.formpendaftan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daniahnaf.formpendaftan.helper.DbHelper;

public class AddEdit extends AppCompatActivity {
    DbHelper SQLite = new DbHelper(this);
    String id, name, address, nohp, jk;
    EditText txt_id, txt_name, txt_address, txt_nohp, txt_jk;
    Button btnSubmit, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = findViewById(R.id.txt_id);
        txt_name = findViewById(R.id.txt_name);
        txt_address = findViewById(R.id.txt_address);
        txt_nohp = findViewById(R.id.txt_nohp);
        txt_jk = findViewById(R.id.txt_jk);
        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);
        nohp = getIntent().getStringExtra(MainActivity.TAG_NOHP);
        jk = getIntent().getStringExtra(MainActivity.TAG_JK);


        if (id == null || id == "") {
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            txt_id.setText(id);
            txt_name.setText(name);
            txt_address.setText(address);
            txt_nohp.setText(nohp);
            txt_jk.setText(jk);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (txt_id.getText().toString().equals("")) {
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception ex) {
                    Log.e("Submit", ex.toString());
                }

                Intent intent = new Intent(AddEdit.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void blank() {
        txt_name.setText("");
        txt_id.setText("");
        txt_address.setText("");
        txt_nohp.setText("");
        txt_jk.setText("");
        txt_id.requestFocus();
    }


    private void save() {
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
                String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("") ||
                String.valueOf(txt_nohp.getText()).equals(null) || String.valueOf(txt_nohp.getText()).equals("") ||
                String.valueOf(txt_jk.getText()).equals(null) || String.valueOf(txt_jk.getText()).equals("") ) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(txt_name.getText().toString().trim(),
                    txt_address.getText().toString().trim(),
                    txt_nohp.getText().toString().trim(),
                    txt_jk.getText().toString().trim());
            blank();
            finish();
        }
    }

    private void edit() {
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
                String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("") ||
                String.valueOf(txt_nohp.getText()).equals(null) || String.valueOf(txt_nohp.getText()).equals("") ||
                String.valueOf(txt_jk.getText()).equals(null) || String.valueOf(txt_jk.getText()).equals("")){
            Toast.makeText(getApplicationContext(),
                    "Please input nam eor address ...",Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString()),
                    txt_name.getText().toString().trim(),
                    txt_address.getText().toString().trim(),
                    txt_nohp.getText().toString().trim(),
                    txt_jk.getText().toString().trim());
            blank();
            finish();
        }
    }


}