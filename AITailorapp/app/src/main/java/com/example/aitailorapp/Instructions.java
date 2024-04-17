package com.example.aitailorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class Instructions extends AppCompatActivity {

    CheckBox mcheckBox;
    android.widget.Button mcamerabtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        mcamerabtn = findViewById(R.id.camerabtn);
        mcheckBox = findViewById(R.id.checkBox);

        mcamerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mcheckBox.isChecked())
                {
                    Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(open_camera, 100);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Select I have read the instructions", Toast.LENGTH_SHORT).show();
                }

                }
            });
        }
    }
