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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.math.BigInteger;
import java.util.regex.Pattern;

public class signUpActivity extends AppCompatActivity {

    private EditText et_Fname,et_Sname, et_Email, et_Adress, et_City,et_Pnumber,et_Zcode,et_password,et_Pernumber,et_confirmation,et_country;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dbr = db.getReference().child("User");
    private ProgressBar mybar;
    private FirebaseAuth mAuth;
    private DataEncryption PasswordEncryption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et_Fname = findViewById(R.id.firstNameSignUpText);
        et_Sname = findViewById(R.id.lastNameSignUpText);
        et_Email = findViewById(R.id.emailSignUp);
        et_Adress=findViewById(R.id.signUpadress);
        et_City=findViewById(R.id.cityText);
        et_Pnumber=findViewById(R.id.mobileText);
        et_Zcode = findViewById(R.id.signUPzib);
        et_password =findViewById(R.id.passWordSignUp);
        et_Pernumber = findViewById(R.id.dateText);
        et_confirmation =findViewById(R.id.passwordconfirmation);
        et_country=findViewById(R.id.countryText);
        mybar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();


        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String Fname, Sname, Email, Adress, City, Pnumber, Zcode, Password, Pernum, Confirmation, Country;
                Fname = et_Fname.getText().toString().trim();
                Sname = et_Sname.getText().toString().trim();
                Email = et_Email.getText().toString().trim();
                Adress = et_Adress.getText().toString().trim();
                City = et_City.getText().toString().trim();
                Pnumber = et_Pnumber.getText().toString().trim();
                Zcode = et_Zcode.getText().toString().trim();
                Password = et_password.getText().toString().trim();
                Pernum = et_Pernumber.getText().toString().trim();
                Confirmation = et_confirmation.getText().toString().trim();
                Country = et_country.getText().toString().trim();
                mybar.setVisibility(view.GONE);

                if (filedsCheck(Fname, Sname, Email, Adress, City, Pnumber, Zcode, Password, Pernum, Confirmation, Country)) {

                    String str = Password;
                    String EncryptedPassword = PasswordEncryption(str, Email);
                    User n_user = new User(Fname, Sname, Email, Adress, City, EncryptedPassword, Pnumber, Zcode, Pernum, Country);
                    //Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                    mybar.setVisibility(view.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                dbr.push().setValue(n_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "New user registered", Toast.LENGTH_SHORT).show();
                                            mybar.setVisibility(view.GONE);
                                            moveToLogIn();
                                        } else {
                                            Toast.makeText(signUpActivity.this, "Failed to register try again!", Toast.LENGTH_LONG).show();
                                            mybar.setVisibility(view.GONE);
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(signUpActivity.this, "Failed to register try again!", Toast.LENGTH_LONG).show();
                                mybar.setVisibility(view.GONE);
                            }
                        }
                    });
                } else {

                }


            }
        });
    }
    public boolean passwordlengthValidation(String password)
    {
        if( password.length()<20 && password.length() >=6)
            return true;
        else
            return false;

    }
    public boolean filedsCheck(String firstname, String secondname, String email, String adress, String city, String password,String Confirmation, String phonenumber, String zibcode, String personalnumber,String country)
    {   password=et_password.getText().toString().trim();
        Confirmation= et_confirmation.getText().toString().trim();
        phonenumber=et_Pnumber.getText().toString().trim();
        personalnumber=et_Pernumber.getText().toString().trim();
        adress=et_Adress.getText().toString().trim();
        country=et_country.getText().toString().trim();
        city=et_City.getText().toString().trim();
        zibcode=et_Zcode.getText().toString().trim();
        if(firstname.isEmpty())
        {
            et_Fname.setError("Firstname is required");
            et_Fname.requestFocus();
            return false;
        }
        else if(secondname.isEmpty()) {
            et_Sname.setError("Lastname is required");
            et_Sname.requestFocus();
            return false;
        }
        else if(!ValidEmail(email)) {

            return false;
        }


        else if(password.isEmpty())
        {
            et_password.setError("Password is required");
            et_password.requestFocus();
            return false;
        }
        else if(!passwordPattern(password))
        {
            et_password.setError("The password must contain at least 1 Capital letter" +
                    ",1 special character, 1 digit and at least 6 characters long");
            et_password.requestFocus();
            return false;
        }
        if(!passwordconfirmed(password,Confirmation))
        {
            et_confirmation.setError("Confimation does not match the password");
            et_confirmation.requestFocus();
            return false;
        }
        if(Confirmation.isEmpty())
        {
            et_confirmation.setError("Password confirmation is required");
            et_confirmation.requestFocus();
            return false;
        }
        if(!ValidPhonenumber(phonenumber))
        {
            return false;
        }
        if (!(ValidPernumber(personalnumber)))
            return false;
        if(adress.isEmpty())
        {
            et_Adress.setError("Address is required");
            et_Adress.requestFocus();
            return false;
        }

        if(country.isEmpty())
        {
            et_country.setError("City is required");
            et_country.requestFocus();
            return false;
        }
        if(city.isEmpty())
        {
            et_City.setError("City is required");
            et_City.requestFocus();
            return false;
        }

        if(!ValidZibcode(zibcode))
            return false;

        else
            return true;

    }

    public boolean ValidEmail(String email)
    {
        if(email.isEmpty())
        {
            et_Email.setError("Email is required");
            et_Email.requestFocus();
            return false;

        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            et_Email.setError("Please enter a valid email");
            et_Email.requestFocus();
            return false;
        }
        else
            return true;
    }
    public boolean ValidPhonenumber(String phonenumber)
    {
        if(phonenumber.isEmpty())
        {
            et_Pnumber.setError("Phonenumber is required");
            et_Pnumber.requestFocus();
            return false;
        }
        else if(!IsNumeric(phonenumber))
        {
            et_Pnumber.setError("Phonenumber has to be numbers");
            et_Pnumber.requestFocus();
            return false;
        }
        else
            return true;
    }
    public  boolean ValidZibcode(String zibcode)
    {
        if(zibcode.isEmpty())
        {
            et_Zcode.setError("Zipcode is required");
            et_Zcode.requestFocus();
            return false;
        }
        else if(!IsNumeric(zibcode))
        {
            et_Zcode.setError("Zipcode has to be numbers");
            et_Zcode.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean ValidPernumber(String personalnumber)
    {
        if(personalnumber.isEmpty())
        {
            et_Pernumber.setError("Please Enter Your Birthdate");
            et_Pernumber.requestFocus();
            return false;
        }
        else if(!IsNumeric(personalnumber))
        {
            et_Pernumber.setError("Please has to be numbers");
            et_Pernumber.requestFocus();
            return false;
        }


        else if(personalnumber.length()!=8 )
        {
            et_Pernumber.setError("The birthdate must follow this pattern YYYYMMDD");
            et_Pernumber.requestFocus();
            return false;
        }
        else
            return true;

    }

    public boolean IsNumeric(String str)
    {

        Pattern pattern = Pattern.compile("[0-9]*$");
        return pattern.matcher(str).matches();
    }
    public boolean IsMixed(String code)
    {
        Pattern Mypattern = Pattern.compile( "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[$@$!%*#?&])[A-Za-z\\\\d$@$!%*#?&]{6,}$");
        return Mypattern.matcher(code).matches();
    }
    public boolean passwordPattern(String str)
    {
        Pattern mypattern = Pattern.compile( "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{6,}$");
        return mypattern.matcher(str).matches();
    }
    public boolean passwordconfirmed(String x, String y)
    {
        if(x.compareTo(y)==0)
            return true;
        else
            return false;

    }

    public void moveToLogIn(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public String toHexString(byte[] hash)
    {
        /* Convert byte array of hash into digest */
        BigInteger number = new BigInteger(1, hash);

        /* Convert the digest into hex value */
        StringBuilder hexString = new StringBuilder(number.toString(16));

        /* Pad with leading zeros */
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
    public String PasswordEncryption(String str, String str2)
    {   String tmp = str + str2;
        byte [] hash = tmp.getBytes();
        String EncryptedPassword = toHexString(hash);
        return EncryptedPassword;
    }

}