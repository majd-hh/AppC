package com.example.app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class VaccinDashboardSE extends AppCompatActivity {
    BarChart mChart,mChart2;
    LineChart mChart3;
    Spinner spinnerAge,spinnerDoss,spinnerSweden;
    Button buttonGet;
    TextView distributed,administered;
    String countryName="", AgeGroup="",DossNumber="",SwedenCountis="" ;
    int[] colorClassArray = new int[]{Color.GREEN,Color.YELLOW,Color.BLUE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccin_se_dash);
        mChart2 = findViewById(R.id.bar_chartSE2);
        mChart = findViewById(R.id.bar_chartSE1);
        mChart3=findViewById(R.id.line_chart);

        buttonGet= findViewById(R.id.buttonGetSE);
        administered=findViewById(R.id.DOSADMIN);
        distributed=findViewById(R.id.DOSdist);

        final VaccineinfoSE swedenVaccineData= new VaccineinfoSE(VaccinDashboardSE.this);

        spinnerAge = findViewById(R.id.age_spinnerSE);
        spinnerDoss = findViewById(R.id.product_spinnerSE);
        spinnerSweden= findViewById(R.id.country_spinnerSE);

        ArrayAdapter<CharSequence> adapterSweden = ArrayAdapter.createFromResource(this,
                R.array.region_sweden, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterAge = ArrayAdapter.createFromResource(this,
                R.array.Age_arraySE, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterDoss = ArrayAdapter.createFromResource(this,
                R.array.Doss_array, android.R.layout.simple_spinner_item);

        adapterSweden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSweden.setAdapter(adapterSweden);

        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapterAge);

        adapterDoss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoss.setAdapter(adapterDoss);

        spinnerSweden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                SwedenCountis=item;
            }

            @Override
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

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDoss.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DossNumber = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), "Selected " + DossNumber, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaccineDistributed( swedenVaccineData,SwedenCountis);//total doss distributed.
                vaccineAdministered(swedenVaccineData,SwedenCountis);//total doss Administered.
                vaccineDataSE(swedenVaccineData, SwedenCountis, AgeGroup, "");
                vaccineDataSEAge(swedenVaccineData, SwedenCountis, AgeGroup, "");
                vaccineDataandel( swedenVaccineData, SwedenCountis, AgeGroup, DossNumber);
            }
        });

    }
    //get the number of doss som Administered from swedebVaccineData.
    public void vaccineAdministered(VaccineinfoSE swedenVaccineData, String counties){
        swedenVaccineData.getVaccineAdministeredSE(counties, new VaccineinfoSE.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(VaccinDashboardSE.this,"returned wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<VaccineDataSE> VaccineData) {
                int antalDitributed=NumberDosesAdministered(VaccineData,SwedenCountis); //total doss distributed.
                administered.setText(String.format("%d", antalDitributed)); //textView for total doss distributed.
            }
        });

    }

    //get the number of doss som distributed from swedebVaccineData.
    public void vaccineDistributed(VaccineinfoSE swedenVaccineData, String counties){
        swedenVaccineData.getVaccineDistributedSE(counties, new VaccineinfoSE.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(VaccinDashboardSE.this,"returned wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<VaccineDataSE> VaccineData) {
                int antalDitributed=NumberDosesDistributed(VaccineData,SwedenCountis); //total doss distributed.
                distributed.setText(String.format("%d", antalDitributed)); //textView for total doss distributed.
            }
        });

    }

    public void vaccineDataSE(VaccineinfoSE swedenVaccineData, String counties, String age, String doss){
        swedenVaccineData.getVaccineDataSE(counties, age, doss, new VaccineinfoSE.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(VaccinDashboardSE.this,"returned wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<VaccineDataSE> VaccineData) {
                swedenChartWeekly(veckor(VaccineData),Doss1(VaccineData, counties),Doss2(VaccineData, counties));

            }
        });
    }

    public void vaccineDataSEAge(VaccineinfoSE swedenVaccineData, String counties, String age, String doss){
        swedenVaccineData.getVaccineDataSEAge(counties, age, doss, new VaccineinfoSE.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(VaccinDashboardSE.this,"returned wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<VaccineDataSE> VaccineData) {
                swedenChartAge( Age(VaccineData,counties),Doss1Age(VaccineData,counties),Doss2Age(VaccineData,counties));

            }
        });
    }

    public  void vaccineDataandel(VaccineinfoSE swedenVaccineData, String counties, String age, String doss){

        swedenVaccineData.getVaccineDataSE(counties, age, doss, new VaccineinfoSE.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(VaccinDashboardSE.this,"returned wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<VaccineDataSE> VaccineData) {
                List<VaccineDataSE> list=new ArrayList<>();
                if (doss.equals("Doss1")){
                    swedenLineChart(veckor(VaccineData), Doss1_andel(VaccineData, counties),Doss2_andel(list, counties));
                }
                else{
                    swedenLineChart(veckor(VaccineData), Doss1_andel(list, counties),Doss2_andel(VaccineData, counties));
                }
                //Doss2_andel(VaccineData, counties);


            }
        });
    }
    //Staked Chart.
    @SuppressLint("UseCompatLoadingForDrawables")
    public void swedenChartWeekly(List<String> xAxisLabel,int[] dos1,int []dos2){
        int weekNumber=xAxisLabel.size();

        mChart = findViewById(R.id.bar_chartSE1);
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
            Toast.makeText(VaccinDashboardSE.this, "There is no data for this selected choice ", Toast.LENGTH_SHORT).show();
        }

        BarDataSet set1;
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
       /*
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf(yVals1.get((int) value % yVals1.size()));
            }
        });

        */

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Vacccine chart");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"First doss", "second doss", ""});
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);
            mChart.setData(data);

        }
        mChart.setVisibleXRangeMaximum(20);
        mChart.moveViewToX(0);
        mChart.setFitBars(true);
        mChart.invalidate();

    }
    public void swedenChartAge(List<String> xAxisLabel,int[] dos1,int []dos2){
        int weekNumber=xAxisLabel.size();
        for (int i = 0; i <weekNumber; i++) {
            System.out.println(i+" -in chart AGE doss1 "+dos1[i]);
        }
        for (int i = 0; i <weekNumber; i++) {
            System.out.println(i+ " -in chart AGE doss2 "+dos2[i]);
        }


        mChart2 = findViewById(R.id.bar_chartSE2);
        mChart2.getDescription().setEnabled(false);
        mChart2.setMaxVisibleValueCount((int) 2.5);

        // scaling can now only be done on x- and y-axis separately
        mChart2.setPinchZoom(false);
        mChart2.setDrawGridBackground(false);
        mChart2.setDrawBarShadow(false);
        mChart2.setDrawValueAboveBar(false);
        mChart2.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = mChart2.getAxisLeft();
        XAxis xAxis = mChart2.getXAxis();
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        mChart2.getAxisRight().setEnabled(false);
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

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        float sumval1=0,sumval2=0;

        for (int i = 0; i <weekNumber-1; i++) {
            float val1 = (float) (dos1[i]);
            sumval1=sumval1+val1;

            float val2 = (float) (dos2[i]);
            sumval2=sumval2+val2;

            yVals1.add(new BarEntry(i,
                    new float[]{ val1, val2,0},
                    getResources().getDrawable(R.drawable.aabb)));
        }

        if(sumval1==0 && sumval2==0){
            Toast.makeText(VaccinDashboardSE.this, "There is no data for this selected choice ", Toast.LENGTH_SHORT).show();
        }
        xAxisLabel.remove(weekNumber-1);
        xAxisLabel.remove(weekNumber-2);
        xAxisLabel.add("90+");

        BarDataSet set1;
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

        if (mChart2.getData() != null &&
                mChart2.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart2.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart2.getData().notifyDataChanged();
            mChart2.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Vacccine chart");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"First doss", "second doss", ""});
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);
            mChart2.setData(data);

        }
        mChart2.setVisibleXRangeMaximum(6);
        mChart2.moveViewToX(0);
        mChart2.setFitBars(true);
        mChart2.invalidate();
    }
    //Liner Chart.
    @SuppressLint("UseCompatLoadingForDrawables")
    public void swedenLineChart(List<String> xAxisLabel,float[] dos1,float []dos2){

        int weekNumber=xAxisLabel.size();

        mChart3 = findViewById(R.id.line_chart);



        YAxis leftAxis = mChart3.getAxisLeft();
        XAxis xAxis = mChart3.getXAxis();
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart3.getAxisRight().setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        // setting data;
        Legend l = mChart3.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(2f);
        l.setXEntrySpace(6f);

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
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
            Toast.makeText(VaccinDashboardSE.this, "There is no data for this selected choice ", Toast.LENGTH_SHORT).show();
        }

        LineDataSet set1;
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));


        if (mChart3.getData() != null &&
                mChart3.getData().getDataSetCount() > 0) {

            mChart3.getData().notifyDataChanged();
            mChart3.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, "vacccine chart");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            //set1.setStackLabels(new String[]{"First doss", "second doss", "Marriages"});
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);
            LineData data = new LineData( dataSets);
            data.setValueTextColor(Color.WHITE);
            mChart3.setData(data);

        }
        mChart3.setVisibleXRangeMaximum(20);
        mChart3.moveViewToX(0);
        mChart3.invalidate();

    }

    //get number of Administered Doss.
    private  int NumberDosesAdministered(List<VaccineDataSE> VaccineData, String countis){
        int sum=0;
        System.out.println("countis i NumerdossssRE"+countis);
        for (int i=0;i<VaccineData.size();i++){
            if ( (VaccineData.get(i).getRegion().equals(countis)) ) {
                sum =  Integer.parseInt(VaccineData.get(i).getNumberDosesReceived());
            }
        }
        return sum;
    }
    //get number of Distributed Doss.
    private  int NumberDosesDistributed(List<VaccineDataSE> VaccineData,String countis){
        int sum=0;
        System.out.println("countis i NumerdossssRE"+countis);
        for (int i=0;i<VaccineData.size();i++){
            if ( (VaccineData.get(i).getRegion().equals(countis)) ) {
                sum =  Integer.parseInt(VaccineData.get(i).getNumberDosesReceived());
            }
        }
        return sum;
    }
    /************************ start of functions for weeks ***********************/
    //get number of doss1 for each week
    private  int [] Doss1(List<VaccineDataSE> Vacc,String countis){
        int [] sumDoss = new int[1000];
        System.out.println("Size i dosss 1 func  "+Vacc.size());
        int weekNumber=0;
        for (int i=1;i<Vacc.size();i++){
            for (int j=i-1;j<i;j++) {
                if ((Vacc.get(j).getYearWeekISO().equals(Vacc.get(i).getYearWeekISO()))) {
                    if ((Vacc.get(j).getRegion().equals(countis))) {
                        if (Vacc.get(j).getVaccinationsstatus().equals("Minst 1 dos")) {
                            sumDoss[weekNumber] = sumDoss[weekNumber] + Integer.parseInt(Vacc.get(j).getAntalVaccinerad());
                            System.out.println("AT week:"+Vacc.get(j).getYearWeekISO()+" ,Rgion: "+Vacc.get(j).getRegion()+" ,doss: "+Vacc.get(j).getVaccinationsstatus()+" ,Antal: "+Vacc.get(j).getAntalVaccinerad());
                        }
                    }
                }
                else {
                    weekNumber++;
                }

            }

        }

        System.out.println("dosss 1 func efter lopop");
        sumDoss[999]=weekNumber;
        return sumDoss;
    }

    //get number of doss2 for each week
    private  int [] Doss2(List<VaccineDataSE> Vacc,String countis){
        int [] sumDoss = new int[1000];
        System.out.println("Size i dosss 2 func  "+Vacc.size());
        int weekNumber=0;
        for (int i=1;i<Vacc.size();i++){
            for (int j=i-1;j<i;j++) {
                if ((Vacc.get(j).getYearWeekISO().equals(Vacc.get(i).getYearWeekISO()))) {
                    if ((Vacc.get(j).getRegion().equals(countis))) {
                        if (Vacc.get(j).getVaccinationsstatus().equals("Färdigvaccinerade")) {
                            sumDoss[weekNumber] = sumDoss[weekNumber] + Integer.parseInt(Vacc.get(j).getAntalVaccinerad());
                            System.out.println("AT week:"+Vacc.get(j).getYearWeekISO()+" ,Rgion: "+Vacc.get(j).getRegion()+" ,doss: "+Vacc.get(j).getVaccinationsstatus()+" ,Antal: "+Vacc.get(j).getAntalVaccinerad());
                        }
                    }
                }
                else {
                    weekNumber++;
                }

            }

        }

        System.out.println("dosss 2 func efter lopop");
        sumDoss[999]=weekNumber;
        return sumDoss;
    }

    //get andel of doss1 for each week
    private  float [] Doss1_andel(List<VaccineDataSE> Vacc,String countis){
        float [] sumDoss = new float[1000];
        System.out.println("Size i dosss 1 func  "+Vacc.size());
        int weekNumber=0;
        for (int i=1;i<Vacc.size();i++){
            for (int j=i-1;j<i;j++) {
                if ((Vacc.get(j).getYearWeekISO().equals(Vacc.get(i).getYearWeekISO()))) {
                    if ((Vacc.get(j).getRegion().equals(countis))) {
                        if (Vacc.get(j).getVaccinationsstatus().equals("Minst 1 dos")) {
                            sumDoss[weekNumber] = sumDoss[weekNumber] + Float.parseFloat(Vacc.get(j).getAndelVaccinerad());
                            System.out.println("AT week:"+Vacc.get(j).getYearWeekISO()+" ,Rgion: "+Vacc.get(j).getRegion()+" ,doss: "+Vacc.get(j).getVaccinationsstatus()+" ,Antal: "+Vacc.get(j).getAndelVaccinerad());
                        }
                    }
                }
                else {
                    weekNumber++;
                }

            }

        }

        System.out.println("dosss 1 func efter lopop");
        sumDoss[999]=weekNumber;
        return sumDoss;
    }
    //get andel of doss2 for each week
    private  float [] Doss2_andel(List<VaccineDataSE> Vacc,String countis){
        float [] sumDoss = new float[1000];
        System.out.println("Size i dosss 1 func  "+Vacc.size());
        int weekNumber=0;
        for (int i=1;i<Vacc.size();i++){
            for (int j=i-1;j<i;j++) {
                if ((Vacc.get(j).getYearWeekISO().equals(Vacc.get(i).getYearWeekISO()))) {
                    if ((Vacc.get(j).getRegion().equals(countis))) {
                        if (Vacc.get(j).getVaccinationsstatus().equals("Färdigvaccinerade")) {
                            sumDoss[weekNumber] = sumDoss[weekNumber] + Float.parseFloat(Vacc.get(j).getAndelVaccinerad());
                            System.out.println("AT week:"+Vacc.get(j).getYearWeekISO()+" ,Rgion: "+Vacc.get(j).getRegion()+" ,doss: "+Vacc.get(j).getVaccinationsstatus()+" ,Antal: "+Vacc.get(j).getAndelVaccinerad());
                        }
                    }
                }
                else {
                    weekNumber++;
                }

            }

        }

        System.out.println("dosss 1 func efter lopop");
        sumDoss[999]=weekNumber;
        return sumDoss;
    }
    /************************ Ends of functions for weeks ***********************/

/********************************** start of functions for Ages  *****************************/
    //get number of doss1 for each Age
    private  int [] Doss1Age(List<VaccineDataSE> Vacc,String countis){
        int [] sumDoss = new int[1000];
        int AtAge=0;
        System.out.println("Size i dosss 1 AGE func  "+Vacc.size());
        for (int i=0;i<Vacc.size();i++){
            if ((Vacc.get(i).getRegion().equals(countis))) {
                if (Vacc.get(i).getVaccinationsstatus().equals("Minst 1 dos")) {
                        sumDoss[AtAge] = Integer.parseInt(Vacc.get(i).getAntalVaccinerad());
                        System.out.println(i+" -AT AGE:"+Vacc.get(i).getAge()+" ,Rgion: "+Vacc.get(i).getRegion()+" ,doss 1: "+Vacc.get(i).getVaccinationsstatus()+" ,Antal: "+Vacc.get(i).getAntalVaccinerad()+" ,sum[]: "+sumDoss[i]);
                        AtAge++;
                    }
                }
        }

        System.out.println("dosss 1 AGE func efter lopop");

        return sumDoss;
    }

    //get number of doss2 for each Age
    private  int [] Doss2Age(List<VaccineDataSE> Vacc,String countis){
        int [] sumDoss = new int[1000];
        int AtAge=0;
        System.out.println("Size i dosss 2 AGE func  "+Vacc.size());
        for (int i=0;i<Vacc.size();i++){
            if ((Vacc.get(i).getRegion().equals(countis))) {
                if (Vacc.get(i).getVaccinationsstatus().equals("Färdigvaccinerade")) {
                    sumDoss[AtAge] = Integer.parseInt(Vacc.get(i).getAntalVaccinerad());
                    System.out.println(i+" -AT AGE:"+Vacc.get(i).getAge()+" ,Rgion: "+Vacc.get(i).getRegion()+" ,doss 2: "+Vacc.get(i).getVaccinationsstatus()+" ,Antal: "+Vacc.get(i).getAntalVaccinerad()+" ,sum[]: "+sumDoss[i]);
                    AtAge++;
                }
            }
        }

        System.out.println("dosss 2 AGE func efter lopop");
        return sumDoss;
    }

    //get andel of doss1 for each Age
    private  float [] Doss1_andelAge(List<VaccineDataSE> Vacc,String countis){
        float [] sumDoss = new float[1000];
        System.out.println("Size i dosss 1 AGE ANDEL func  "+Vacc.size());
        int AtAge=0;
        for (int i=0;i<Vacc.size();i++){
            if ((Vacc.get(i).getRegion().equals(countis))) {
                if (Vacc.get(i).getVaccinationsstatus().equals("Minst 1 dos")) {
                    sumDoss[AtAge] = Float.parseFloat(Vacc.get(i).getAndelVaccinerad());
                    System.out.println(i+" -AT AGE:"+Vacc.get(i).getAge()+" ,Rgion: "+Vacc.get(i).getRegion()+" ,doss 1: "+Vacc.get(i).getVaccinationsstatus()+" ,Antal: "+Vacc.get(i).getAndelVaccinerad()+" ,sum[]: "+sumDoss[i]);
                    AtAge++;
                }
            }
        }

        System.out.println("dosss 1 AGE ANDEL func efter lopop");
        return sumDoss;
    }
    //get andel of doss2 for each Age
    private  float [] Doss2_andelAge(List<VaccineDataSE> Vacc,String countis){
        float [] sumDoss = new float[1000];
        System.out.println("Size i dosss 1 AGE ANDEL func  "+Vacc.size());
        int AtAge=0;
        for (int i=0;i<Vacc.size();i++){
            if ((Vacc.get(i).getRegion().equals(countis))) {
                if (Vacc.get(i).getVaccinationsstatus().equals("Färdigvaccinerade")) {
                    sumDoss[AtAge] = Float.parseFloat(Vacc.get(i).getAndelVaccinerad());
                    System.out.println(i+" -AT AGE:"+Vacc.get(i).getAge()+" ,Rgion: "+Vacc.get(i).getRegion()+" ,doss 2: "+Vacc.get(i).getVaccinationsstatus()+" ,Antal: "+Vacc.get(i).getAntalVaccinerad()+" ,sum[]: "+sumDoss[i]);
                    AtAge++;
                }
            }
        }

        System.out.println("dosss 1  AGE ANDEL func efter lopop");
        return sumDoss;
    }

    /********************************** Ends of functions for Ages  *****************************/

    //get array of weeks for x-Axial.
    private ArrayList<String> veckor(List<VaccineDataSE> Vaccine){
        final ArrayList<String> vekor = new ArrayList<>();
        for (int i=1;i<Vaccine.size();i++){
            for (int j=i-1;j<i;j++) {
                if (!(Vaccine.get(j).getYearWeekISO().equals(Vaccine.get(i).getYearWeekISO()))) {
                    vekor.add("W"+Vaccine.get(i).getYearWeekISO());
                }
            }
        }
        return vekor;
    }

    //get array of Ages for x-Axial.
    private ArrayList<String> Age(List<VaccineDataSE> Vaccine,String countis){
        final ArrayList<String> Age = new ArrayList<>();
        for (int i=0;i<Vaccine.size();i++){
            if ((Vaccine.get(i).getRegion().equals(countis))) {
                if (Vaccine.get(i).getVaccinationsstatus().equals("Minst 1 dos")) {
                    Age.add("Age" + Vaccine.get(i).getAge());
                }
            }

        }
        return Age;
    }


    //array of Colors for the Chart.
    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[3];

        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3);
        return colors;
    }
}