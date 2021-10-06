package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dbr = db.getReference().child("Admin");
    private FirebaseAuth mAuth;
    private ProgressBar progressbar;
    private EditText EmailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logInButton = findViewById(R.id.logInButton);
        EmailText=(EditText) findViewById(R.id.nameLogIn);
        passwordText=(EditText) findViewById(R.id.passWordLogIn);

        // add_Admins();
        mAuth=FirebaseAuth.getInstance();
        String name=getCountryCode("Austria");
        System.out.println("\n\n ***********country Name "+name+"\n");
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToMain();
                //moveToDashboard();
                //userlogin();
            }
        });

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveToSignUp();
            }
        });

    }


    public void userlogin(){
        String email=EmailText.getText().toString().trim();
        String password=passwordText.getText().toString().trim();

        if (email.isEmpty()){
            EmailText.setError("Email is required!");
            EmailText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EmailText.setError("Please enter a valid email!");
            EmailText.requestFocus();
            return;
        }

        if (password.isEmpty()){
            passwordText.setError("Password is required!");
            passwordText.requestFocus();
            return;
        }
        if (password.length() <6){
            EmailText.setError("Min password is 6 character!");
            EmailText.requestFocus();
            return;
        }
        //progressbar.setVisibility(view.VISIBLE);
        //progressbar.setVisibility(progressbar.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(MainActivity.this,MyPageAcitvity.class));
                }else{
                    Toast.makeText(MainActivity.this,"Failed to login! please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void moveToMain(){
        Intent intent = new Intent(this, MyPageAcitvity.class);
        startActivity(intent);
    }

    public void moveToDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
    public void moveToSignUp(){
        Intent intent = new Intent(this, signUpActivity.class);
        startActivity(intent);
    }

    public void add_Admins()
    {
        Admin Yazan = new Admin("Yaazan.ab.ya94@gmail.com","Asd112233");
        Admin Farah = new Admin("max-fox11@hotmail.com","Asd112233");
        Admin Majed = new Admin("Majdhussin@hotmail.com","Asd112233");
        dbr.push().setValue(Yazan);
        dbr.push().setValue(Farah);
        dbr.push().setValue(Majed);

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



}