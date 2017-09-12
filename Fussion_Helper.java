
package com.example.Fusion_Helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.fusiontables.Fusiontables;
import com.google.api.services.fusiontables.FusiontablesScopes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;



public class Fusion_Helper {
    private Context context;
    private HttpTransport sHttpTransport;
    private final JsonFactory jsonFactory=com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance();
    private Fusiontables mFusionTable;
    private Drive mDrive;
    private boolean isDebug=true;
    private String TAG="Fusion_Helper";

    //Configuration    
    public final String MY_APP_NAME="APP"; //Your App Name
    public final String SERVICE_EMAIL="xxx@xxxxxxxgserviceaccount.com"; // Its the service email which you have setup in at cansole.google.com
    public final String P12_FILENAME="xxxxx.p12"; //it is the File name which you get After creating service account and File must be in assets folder
    


    public Fusion_Helper(Context context) {
        this.context = context;

        try {
            sHttpTransport = new com.google.api.client.http.javanet.NetHttpTransport();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            print("error while geting shttp");
        }

        GoogleCredential credential=null;
        try {
            credential = (GoogleCredential) getCredential();
        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
            print("error while getting Crededential");
        }

        if(jsonFactory==null)
            print("JasonFactry is null");

        if(sHttpTransport==null)
            print("sHTTPTransport is null");


        if(credential==null)
            print("craditdational is null");

        mFusionTable = new Fusiontables.Builder(sHttpTransport, jsonFactory, credential)
                .setApplicationName(MY_APP_NAME).build();
        mDrive = new Drive.Builder(sHttpTransport, jsonFactory, credential)
                .setApplicationName(MY_APP_NAME).build();
    }
    public Fusiontables open(){
        return mFusionTable;
    }

    String[] string={FusiontablesScopes.FUSIONTABLES, DriveScopes.DRIVE};
    private Credential getCredential() throws GeneralSecurityException, IOException {

        return new GoogleCredential.Builder()
                .setTransport(sHttpTransport)
                .setJsonFactory(jsonFactory)
                .setServiceAccountId(SERVICE_EMAIL) // TODO: Add proper email.
                .setServiceAccountScopes(Arrays.asList(string))
                .setServiceAccountPrivateKeyFromP12File(getP12File()) // Keep .p12 File in assets folder

                .build();
    }

//read .P12 File from assets
    public File getP12File(){
        File file= null;
        AssetManager assetManager=context.getAssets();
        InputStream inputStream=null;
        try {
            inputStream=assetManager.open(P12_FILENAME);
            file=new File(context.getCacheDir(),"temp.p12");
            OutputStream outputStream= new FileOutputStream(file);
            byte[] buffer= new byte[4*1024];
            int read;
            while ((read=inputStream.read(buffer))!=-1) {
                outputStream.write(buffer,0,read);
            }
            outputStream.flush();
            inputStream.close();
            print("File found");
        } catch (IOException e) {
            e.printStackTrace();
            print("File not exist");
        }

        return file;
    }


    private void print(String string){
        Log.d(TAG,string);
    }
}
