package com.example.app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class vaccineDashboard extends AppCompatActivity {
    BarChart mChart,mChart2;
    Spinner spinnerCountry,spinnerAge,spinnerProduct,spinnerSweden;
    Button buttonGet;
    String countryName="", AgeGroup="",vacinType="",SwedenCountis="" ;
    int[] colorClassArray = new int[]{Color.GREEN,Color.YELLOW,Color.BLUE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_dashboard);
        mChart = findViewById(R.id.bar_chart);
        buttonGet= findViewById(R.id.buttonGet);

        final Vaccineinfo VaccineUpdate= new Vaccineinfo(vaccineDashboard.this);

        spinnerCountry =  findViewById(R.id.country_spinner);
        spinnerAge =  findViewById(R.id.age_spinner);
        spinnerProduct =  findViewById(R.id.product_spinner);

        ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this,
                R.array.Country_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterAge = ArrayAdapter.createFromResource(this,
                R.array.Age_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterProuduct = ArrayAdapter.createFromResource(this,
                R.array.Product_array, android.R.layout.simple_spinner_item);


        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapterCountry);

        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapterAge);

        adapterProuduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(adapterProuduct);


        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("Country")){
                    VaccinDashInfo(VaccineUpdate,"SE","","");

                }
                else {
                    String item = adapterView.getItemAtPosition(i).toString();
                    countryName=getCountryCode(item);
                    Toast.makeText(adapterView.getContext(), "Selected " + item, Toast.LENGTH_SHORT).show();
                }
            }@Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("Product")){
                    vacinType="";
                }
                else {
                    vacinType = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(adapterView.getContext(), "Selected " + vacinType, Toast.LENGTH_SHORT).show();

                }
            }@Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("Age")){
                }
                else {
                    AgeGroup = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(adapterView.getContext(), "Selected " + AgeGroup, Toast.LENGTH_SHORT).show();
                }

            }@Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VaccinDashInfo(VaccineUpdate,countryName,AgeGroup,vacinType);
            }
        });

    }

    public void  VaccinDashInfo(Vaccineinfo VaccineUpdate,String couName,String Agge,String prodduct){
        VaccineUpdate.getVaccineData( couName,Agge,prodduct,new Vaccineinfo.VolleyResponseListener(){
            @Override
            public void onError(String message) {
                Toast.makeText(vaccineDashboard.this,"returned wrong", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(List<VaccineData> VaccineData) {
                int [] doss1, doss2;
                System.out.println("::::::::Size when choice  "+ VaccineData.size());
                    doss1=Doss1(VaccineData); //get number of doss1 for each week.
                    doss2=Doss2(VaccineData); //get number of doss1 for each week.
                    ArrayList<String> veckoName = new ArrayList<>(veckor(VaccineData)); //get weeks name
                    VaccineChart(veckoName,doss1,doss2); // chart funcktion to represent Vaccine data


            }

        });

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void VaccineChart(List<String> xAxisLabel,int[] dos1,int []dos2){
        int weekNumber=0;
        weekNumber=dos1[99];

        mChart = findViewById(R.id.bar_chart);
        mChart.getDescription().setEnabled(false);
        mChart.setMaxVisibleValueCount((int) 2.5);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);
        mChart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = mChart.getAxisLeft();
        XAxis xAxis = mChart.getXAxis();
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        mChart.getAxisRight().setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // setting data;
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(2f);
        l.setXEntrySpace(6f);

        //System.out.println("Data "+VaccineData);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        float sumval1=0,sumval2=0;

        for (int i = 0; i <weekNumber; i++) {
            float val1 = (float) (0);
            float val2 = (float) (dos1[i]);
            sumval1=sumval1+val1;

            float val3 = (float) (dos2[i]);
            sumval2=sumval2+val2;

            yVals1.add(new BarEntry(i,
                    new float[]{ val2, val3,val1},
                    getResources().getDrawable(R.drawable.aabb)));
        }

        if(sumval1==0 && sumval2==0){
            Toast.makeText(vaccineDashboard.this, "There is no data for this selected choice ", Toast.LENGTH_SHORT).show();
        }

        BarDataSet set1;
        /*
        final ArrayList<String> xAxisLabel = new ArrayList<>();
            for (int i=1;i<Vaccine.size();i++){
                for (int j=i-1;j<i;j++) {
                    if (!(Vaccine.get(j).getYearWeekISO().equals(Vaccine.get(i).getYearWeekISO()))) {
                        xAxisLabel.add(Vaccine.get(i).getYearWeekISO());
                    }
                }
            }

         */

        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "vacccine chart");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"First doss", "second doss", "Marriages"});
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);
            mChart.setData(data);

        }
        mChart.setVisibleXRangeMaximum(25);
        mChart.moveViewToX(15);
        mChart.setFitBars(true);
        mChart.invalidate();

    }

    private  int [] Doss1(List<VaccineData> Vacc){
        int [] sumDoss = new int[100];
        List<VaccineData> vaccinInfoo= new ArrayList<>(Vacc);
        System.out.println("Size i dosss 1 func  "+vaccinInfoo.size());
        int VD=0;
        for (int i=1;i<vaccinInfoo.size();i++){
            for (int j=i-1;j<i;j++) {
                if ((vaccinInfoo.get(j).getYearWeekISO().equals(vaccinInfoo.get(i).getYearWeekISO()))) {
                    sumDoss[VD] = sumDoss[VD] + (vaccinInfoo.get(j).getFirstDose());
                    System.out.println("At: "+VD+" ,Vaccine doss1: "+vaccinInfoo.get(VD).getVaccine()+" ,antal: "+vaccinInfoo.get(j).getFirstDose());
                }
                else {
                    VD++;
                }
            }
        }

        System.out.println("dosss 1 func efter lopop");
        sumDoss[99]=VD;
        return sumDoss;
    }

    private  int [] Doss2(List<VaccineData> Vacc){
        int [] sumDoss = new int[100];
        int VD=0;
        System.out.println("Size i dosss 2 func  "+Vacc.size());
        for (int i=1;i<Vacc.size();i++){
            for (int j=i-1;j<i;j++) {
                if ((Vacc.get(j).getYearWeekISO().equals(Vacc.get(i).getYearWeekISO()))) {
                    sumDoss[VD] = sumDoss[VD] + (Vacc.get(j).getSecondDose());
                    System.out.println("At: " + VD + " ,Vaccine doss 2: " + Vacc.get(VD).getVaccine()+" ,antal: "+Vacc.get(j).getSecondDose());
                }
                else {
                    VD++;
                }
            }
        }
        sumDoss[99]=VD;
        return sumDoss;
    }


    private ArrayList<String> veckor(List<VaccineData> Vaccine){
        final ArrayList<String> vekor = new ArrayList<>();
        for (int i=1;i<Vaccine.size();i++){
            for (int j=i-1;j<i;j++) {
                if (!(Vaccine.get(j).getYearWeekISO().equals(Vaccine.get(i).getYearWeekISO()))) {
                    vekor.add(Vaccine.get(i).getYearWeekISO());
                }
            }
        }
        return vekor;
    }

    public String getCountryCode(String countryName) {

        // Get all country codes in a string array.
        String[] isoCountryCodes = Locale.getISOCountries();
        String countryCode = "";
        // Iterate through all country codes:
        for (String code : isoCountryCodes) {
            // Create a locale using each country code
            Locale locale = new Locale("", code);
            // Get country name for each code.
            String name = locale.getDisplayCountry();
            if(name.equals(countryName))
            {
                countryCode = code;
                break;
            }
        }
        return countryCode;
    }
    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[3];

        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3);
        return colors;
    }

}