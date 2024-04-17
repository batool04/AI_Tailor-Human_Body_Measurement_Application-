package com.example.aitailorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    android.widget.Button mcontinuebtn;

    TextView mcurrentheight;
    TextView mcurrentwaist,mcurrentweight;
    ImageView mincrementwaist, mincrementweight, mdecrementweight, mdecrementwaist;
    SeekBar mseekbarforheight;
    RelativeLayout mmale, mfemale;

    int intweight = 55;
    int intwaist = 37;
    int currentprogress;
    String mintprogress = "170";
    String typeofuser = "0";
    String weight2 = "55";
    String waist2 = "37";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        

        mcontinuebtn = findViewById(R.id.continuebtn);
        mcontinuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if(typeofuser.equals("0"))
                {
                    Toast.makeText(getApplicationContext(), "Select Your Gender first", Toast.LENGTH_SHORT).show();
                }

                else if(mintprogress.equals("0"))
                {
                    Toast.makeText(getApplicationContext(), "Select Your Height first", Toast.LENGTH_SHORT).show();
                }

                else if (intwaist==0 || intwaist<0)
                {
                    Toast.makeText(getApplicationContext(), "Age is Incorrect", Toast.LENGTH_SHORT).show();
                }

                else if (intweight==0 || intweight<0)
                {
                    Toast.makeText(getApplicationContext(), "Weight is Incorrect", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Intent intent = new Intent(MainActivity.this, Instructions.class);

                    intent.putExtra("gender", typeofuser);
                    intent.putExtra("height",mintprogress);
                    intent.putExtra("weight", weight2);
                    intent.putExtra("waist", waist2);
                    startActivity(intent);

                }
                
                

            }
        });






        mcurrentwaist = findViewById(R.id.currentwaist);
        mcurrentweight = findViewById(R.id.currentweight);
        mcurrentheight = findViewById(R.id.currentheight);
        mincrementwaist = findViewById(R.id.incrementwaist);
        mdecrementwaist = findViewById(R.id.decrementwaist);
        mincrementweight = findViewById(R.id.incrementweight);
        mdecrementweight= findViewById(R.id.decrementweight);
        mseekbarforheight = findViewById(R.id.seekbarforheight);
        mmale = findViewById(R.id.male);
        mfemale = findViewById(R.id.female);


        mmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mmale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malefemalefocus));
                mfemale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malefemalenotfocus));
                typeofuser = "Male";
            }
        });

        mfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mfemale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malefemalefocus));
                mmale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malefemalenotfocus));
                typeofuser = "Female";
            }
        });

        mseekbarforheight.setMax(300);
        mseekbarforheight.setProgress(170);
        mseekbarforheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                currentprogress = progress;
                mintprogress = String.valueOf(currentprogress);
                mcurrentheight.setText(mintprogress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mincrementwaist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intwaist = intwaist+1;
                waist2= String.valueOf(intwaist);
                mcurrentwaist.setText(waist2);
            }
        });

        mdecrementwaist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intwaist = intwaist-1;
                waist2= String.valueOf(intwaist);
                mcurrentwaist.setText(waist2);
            }
        });

        mincrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intweight = intweight+1;
                weight2= String.valueOf(intweight);
                mcurrentweight.setText(weight2);
            }
        });

        mdecrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intweight = intweight-1;
                weight2= String.valueOf(intweight);
                mcurrentweight.setText(weight2);
            }
        });











    }
}