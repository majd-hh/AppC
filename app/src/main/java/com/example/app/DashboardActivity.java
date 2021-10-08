package com.example.app;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    Button buttonView;
    EditText cityInput;

    public void moveToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void moveToLogIn(){
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void moveToMyPage(){
        Intent intent = new Intent(this, MyPageAcitvity.class);
        startActivity(intent);
    }
    public void moveToVaccineEU(){
        Intent intent = new Intent(this, vaccineDashboard.class);
        startActivity(intent);
    }

    public void moveToVaccinSE(){
        Intent intent = new Intent(this, VaccinSeDash.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        buttonView=(Button) findViewById(R.id.viewVButton);
        ListView listV= (ListView) findViewById(R.id.list_view);
        cityInput=(EditText) findViewById(R.id.CityName);

        final covidState covidupdate= new covidState(DashboardActivity.this);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                covidupdate.getcovidState(cityInput.getText().toString().trim(), new covidState.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(DashboardActivity.this,"returned wrong", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(List<covidStatemodel> covidStatemodel) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(DashboardActivity.this, android.R.layout.simple_list_item_1,covidStatemodel);
                        listV.setAdapter(arrayAdapter);

                    }

                });
            }
        });
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
                //moveToCOVID();
                break;
            case R.id.menuLogout:
                moveToMain();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}