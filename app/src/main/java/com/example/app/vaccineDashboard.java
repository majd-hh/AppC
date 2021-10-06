package com.example.app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    Spinner spinnerCountry,spinnerAge,spinnerProduct;
    String countryName="", AgeGroup="",vacinType="" ;
    int[] colorClassArray = new int[]{Color.GREEN,Color.YELLOW,Color.BLUE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_dashboard);
        mChart2 = findViewById(R.id.bar_chart2);


        final Vaccineinfo VaccineUpdate= new Vaccineinfo(vaccineDashboard.this);

        spinnerCountry = (Spinner) findViewById(R.id.planets_spinner);
        spinnerAge = (Spinner) findViewById(R.id.age_spinner);
        spinnerProduct = (Spinner) findViewById(R.id.product_spinner);

        ArrayAdapter<CharSequence> adapterCountry = ArrayAdapter.createFromResource(this,
                R.array.Country_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterAge = ArrayAdapter.createFromResource(this,
                R.array.Age_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterProuduct = ArrayAdapter.createFromResource(this,
                R.array.Product_array, android.R.layout.simple_spinner_item);

        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapterCountry);

        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapterAge);

        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(adapterProuduct);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("Country")){
                    VaccinDashInfo(VaccineUpdate,countryName,AgeGroup,vacinType);
                    countryName="";

                }
                else {
                    String item = adapterView.getItemAtPosition(i).toString();
                    countryName=getCountryCode(item);
                    VaccinDashInfo(VaccineUpdate,countryName,AgeGroup,vacinType);
                    Toast.makeText(adapterView.getContext(), "Selected " + item, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
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
                    VaccinDashInfo(VaccineUpdate,countryName,AgeGroup,vacinType);
                    Toast.makeText(adapterView.getContext(), "Selected " + vacinType, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("Age")){
                    AgeGroup="";
                }
                else {
                    AgeGroup = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(adapterView.getContext(), "Selected " + AgeGroup, Toast.LENGTH_SHORT).show();
                    VaccinDashInfo(VaccineUpdate,countryName,AgeGroup,vacinType);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                System.out.println("::::::::Size when choice  "+ VaccineData.size());
                MultipalColor(VaccineData);
                //MultipalColor2(VaccineData,mChart2);

            }

        });

    }



    @SuppressLint("UseCompatLoadingForDrawables")
    public void MultipalColor(List<VaccineData> VaccineData){
        mChart = findViewById(R.id.bar_chart1);
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
        System.out.println("Data "+VaccineData);

        int [] doss1 = new int[100], doss2 = new int[100];
        int [] NDosesReceived;
        int VD=0;
        int weekNumber=0;
        NDosesReceived=NumberDosesReceived(VaccineData);
        weekNumber=NDosesReceived[99];

        //doss1=Doss1(VaccineData);
        //doss2=Doss2(VaccineData);
        /*
        for (int i = 0; i <VD; i++) {
            System.out.println("AT: "+i+" ,vecka: " +VaccineData.get(i).getYearWeekISO()+"  ,Vaccine  "+VaccineData.get(i).getVaccine()+ ",doss1: "+VaccineData.get(i).getFirstDose()+" ,doss2: "+VaccineData.get(i).getSecondDose());
        }
        
        
*/
        for (int i=1;i<VaccineData.size();i++){
            for (int j=i-1;j<i;j++) {

                if ((VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                    doss1[VD] = doss1[VD] + (VaccineData.get(j).getFirstDose());
                    System.out.println("At: "+VD+" ,Vaccine doss1: "+VaccineData.get(VD).getVaccine());
                }
                if (!(VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                    VD++;
                }
            }
        }


        for (int i=1;i<VaccineData.size();i++){
            for (int j=i-1;j<i;j++) {
                if ((VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                    doss2[VD] = doss2[VD] + (VaccineData.get(j).getSecondDose());
                    System.out.println("At: " + VD + " ,Vaccine doss 2: " + VaccineData.get(VD).getVaccine());
                }
                if (!(VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                    VD++;
                }
            }
        }
        
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        float sumval1=0,sumval2=0;

        for (int i = 0; i <weekNumber; i++) {
            float val1 = (float) (0);
            float val2 = (float) (doss1[i]);
            sumval1=sumval1+val1;

            float val3 = (float) (doss2[i]);
            sumval2=sumval2+val2;

            yVals1.add(new BarEntry(i,
                    new float[]{ val2, val3,val1},
                    getResources().getDrawable(R.drawable.aabb)));
        }

        if(sumval1==0 && sumval2==0){
            Toast.makeText(vaccineDashboard.this, "There is no data for this selected choice ", Toast.LENGTH_SHORT).show();
        }

        BarDataSet set1;

        final ArrayList<String> xAxisLabel = new ArrayList<>();
            for (int i=1;i<VaccineData.size();i++){
                for (int j=i-1;j<i;j++) {
                    if (!(VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                        xAxisLabel.add(VaccineData.get(i).getYearWeekISO());
                    }
                }
            }

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


/*
    @SuppressLint("UseCompatLoadingForDrawables")
    public void MultipalColor2(List<VaccineData> VaccineData,BarChart mChart){
        //mChart1 = findViewById(R.id.bar_chart1);

        mChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount((int) 2.5);
        mChart.setVisibleXRangeMaximum((float) 2.5);

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

        //mChart1.setDrawXLabels(false);
        // chart.setDrawYLabels(false);

        // setting data;
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(2f);
        l.setXEntrySpace(6f);
        String v1="";

        ArrayList<Integer> vlist = new ArrayList<Integer>();
        int VD=0,test = 0;
        int [] dossDistributed,doss1,doss2;
        //System.out.println("\n \n "+"VaccineData.size():::  " +VaccineData.size()+"\n \n ");
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        dossDistributed= yl3nRabHalProject(VaccineData);
        VD=dossDistributed[99];
        doss1=Doss1(VaccineData);
        doss2=Doss2(VaccineData);

        System.out.println(":::::::::::::::::VD:: :: "+VD);

        for(int i=0;i<dossDistributed.length;i++) {

            System.out.println("Vecko "+VaccineData.get(i).getYearWeekISO()+" Index:: "+i+":::::::::::::::::SUMDOSSSS:: :: " + doss1[i]);
        }



        for (int i = 0; i <VD; i++) {
            float val1 = (float) (0);
            float val2 = (float) (doss1[i]);
            float val3 = (float) (doss2[i]);

            yVals1.add(new BarEntry(i,
                    new float[]{ val2, val3,val1},
                    getResources().getDrawable(R.drawable.aabb)));
        }

        BarDataSet set1;

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        for (int i=1;i<VaccineData.size();i++){
            for (int j=i-1;j<i;j++) {
                if (!(VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                    xAxisLabel.add(VaccineData.get(i).getYearWeekISO());
                }
            }
        }

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
            //data.setValueFormatter(new MyAxisValueFormatter());
            data.setValueTextColor(Color.WHITE);


            mChart.setData(data);
        }
        mChart.setVisibleXRangeMaximum(25);
        mChart.moveViewToX(16);
        mChart.setFitBars(true);
        mChart.invalidate();
    }

*/


    private  int [] NumberDosesReceived(List<VaccineData> VaccineData){
        int [] sumDoss = new int[100];
        int VD=0;
        for (int i=1;i<VaccineData.size();i++){
            for (int j=i-1;j<i;j++) {
                    if ((VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                        sumDoss[VD] = (sumDoss[VD] + Integer.parseInt(VaccineData.get(j).getNumberDosesReceived()));
                    }
                    if (!(VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                        VD++;
                    }
                }
            }
        sumDoss[99]=VD;
    return sumDoss;
    }




    private  int [] Doss1(List<VaccineData> VaccineData){
        int [] sumDoss = new int[100];
        System.out.println("dosss 1 func");
        int VD=0;
        for (int i=1;i<VaccineData.size();i++){
            for (int j=i-1;j<i;j++) {

                    if ((VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                        sumDoss[VD] = sumDoss[VD] + (VaccineData.get(j).getFirstDose());
                        System.out.println("At: "+VD+" ,Vaccine doss1: "+VaccineData.get(VD).getVaccine());
                    }
                    if (!(VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                        VD++;
                    }
                }
            }

        System.out.println("dosss 1 func efter lopop");
        sumDoss[99]=VD;
        return sumDoss;
    }




    public  int [] Doss2(List<VaccineData> VaccineData){
        int [] sumDoss = new int[100];
        int VD=0;
        System.out.println("dosss 1 func");
        for (int i=1;i<VaccineData.size();i++){
            for (int j=i-1;j<i;j++) {
                if ((VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                    sumDoss[VD] = sumDoss[VD] + (VaccineData.get(j).getSecondDose());
                    System.out.println("At: " + VD + " ,Vaccine doss 2: " + VaccineData.get(VD).getVaccine());
                }
                if (!(VaccineData.get(j).getYearWeekISO().equals(VaccineData.get(i).getYearWeekISO()))) {
                    VD++;
                }
            }
        }
        System.out.println("dosss 2 func efter loop");
        sumDoss[99]=VD;
        return sumDoss;
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