package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Vaccine extends AppCompatActivity {
    Button buttonView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);
        buttonView=(Button) findViewById(R.id.button);
        ListView listV= (ListView) findViewById(R.id.listV);

        final Vaccineinfo VaccineUpdate= new Vaccineinfo(Vaccine.this);

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*
                VaccineUpdate.getVaccineData(,new Vaccineinfo.VolleyResponseListener(){
                    @Override
                    public void onError(String message) {
                        Toast.makeText(Vaccine.this,"returned wrong", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(List<VaccineData> VaccineData) {
                        //VaccineData.get(0).getYearWeekISO();
                        //System.out.println("week för firts indesx"+VaccineData.get(0).getYearWeekISO());
                        int sizeA= VaccineData.size();
                        System.out.println("\n Size:  "+sizeA+"\n");
                        Toast.makeText(Vaccine.this,"sizeA: "+sizeA, Toast.LENGTH_SHORT).show();
                        ArrayAdapter arrayAdapter = new ArrayAdapter(Vaccine.this, android.R.layout.simple_list_item_1,VaccineData);
                        listV.setAdapter(arrayAdapter);

                    }

                });
               */
            }
        });
    }
}