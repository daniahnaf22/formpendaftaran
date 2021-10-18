package com.daniahnaf.formpendaftan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daniahnaf.formpendaftan.helper.DbHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddEdit extends AppCompatActivity{
    DbHelper SQLite = new DbHelper(this);
    String id, name, address, nohp, jk, latitude, longitude;
    EditText txt_id, txt_name, txt_address, txt_nohp;
    TextView tvLokasi;
    Button btnSubmit,btnGetLocation;
    RadioButton rbLaki, rbPr;
    RadioGroup rgGroup;
    Boolean onJkSelected = false;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = findViewById(R.id.txt_id);
        txt_name = findViewById(R.id.txt_name);
        txt_address = findViewById(R.id.txt_address);
        txt_nohp = findViewById(R.id.txt_nohp);
        btnSubmit = findViewById(R.id.btn_submit);
        btnGetLocation = findViewById(R.id.btnPilihLokasi);
        rbLaki = findViewById(R.id.rbLaki);
        rbPr = findViewById(R.id.rbPerempuan);
        rgGroup = findViewById(R.id.rgGroup);
        tvLokasi = findViewById(R.id.text_lokasi_sekarang);

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
            if (jk.equals("Laki-laki")) {
                rbLaki.setSelected(true);
            } else {
                rbPr.setSelected(true);
            }
        }

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    getLocation();
                }
            }
        });

        getRadioGroupValue();

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
            }
        });

    }

    void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                tvLokasi.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
        txt_id.requestFocus();
    }


    private void save() {
        getRadioGroupValue();
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
                String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("") ||
                String.valueOf(txt_nohp.getText()).equals(null) || String.valueOf(txt_nohp.getText()).equals("") ||
                !onJkSelected) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(txt_name.getText().toString().trim(),
                    txt_address.getText().toString().trim(),
                    txt_nohp.getText().toString().trim(),
                    jk);
            Intent intent = new Intent(AddEdit.this, MainActivity.class);
            startActivity(intent);
            blank();
            finish();
        }
    }

    private void edit() {
        getRadioGroupValue();
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
                String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("") ||
                String.valueOf(txt_nohp.getText()).equals(null) || String.valueOf(txt_nohp.getText()).equals("") ||
                !onJkSelected) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString()),
                    txt_name.getText().toString().trim(),
                    txt_address.getText().toString().trim(),
                    txt_nohp.getText().toString().trim(),
                    jk);
            Intent intent = new Intent(AddEdit.this, MainActivity.class);
            startActivity(intent);
            blank();
            finish();
        }
    }

    private void getRadioGroupValue() {
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbLaki:
                        jk = rbLaki.getText().toString();
//                        Toast.makeText(getApplicationContext(), "Laki-laki", Toast.LENGTH_SHORT).show();
                        onJkSelected = true;
                        break;
                    case R.id.rbPerempuan:
//                        Toast.makeText(getApplicationContext(), "Perempuan", Toast.LENGTH_SHORT).show();
                        jk = rbPr.getText().toString();
                        onJkSelected = true;
                        break;
                }
            }
        });
    }
}