package com.example.app;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

public class DataEncryption
{


    public DataEncryption() {
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getEncryptedText(String x)
    {   String Encrypted = Base64.getEncoder().encodeToString(x.getBytes());
        return Encrypted;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDecryotedText(String y)
    {
        String Decrypted=new String(Base64.getMimeDecoder().decode(y));
        return Decrypted;
    }
}
