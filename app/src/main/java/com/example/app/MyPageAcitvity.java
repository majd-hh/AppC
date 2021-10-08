package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageAcitvity extends AppCompatActivity {



    public void moveToCOVID(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public void moveToVaccineEU(){
        Intent intent = new Intent(this, vaccineDashboard.class);
        startActivity(intent);
    }


    public void moveToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void moveToVaccinSE(){
        Intent intent = new Intent(this, VaccinSeDash.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_acitvity);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected( MenuItem item){
        switch (item.getItemId()){
            case R.id.menuMyPage:
                break;
            case R.id.Dashboard:
                break;
            case R.id.Vaccine_EU:
                moveToVaccineEU();
                break;
            case R.id.Vaccine_SE:
                moveToVaccinSE();
                break;
            case R.id.Covid_state:
                moveToCOVID();
                break;
            case R.id.menuLogout:
                moveToMain();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




}