Fusion Tables is an experimental data visualization web application to gather, visualize, and share data tables. You can use Fusion table as datasource for your app.Fusion REST API allow us to query Fussion table to retrive or insert data into table. Fusion_Helper.java explains how you  can easily create connection of your app with Fusion Table and send and retrive data from android app.

Usage
--------
* Go to console.cloud.google.com and create a service account, Enable Fussion Table Api and get service email and get Google Credidantiol File
* Go to Drive and create a new Fusion Table and share this table with your service email.
* Add follwoing dependencies to your project
```xml
dependencies {
    ...
    compile 'com.google.apis:google-api-services-fusiontables:v2-rev18-1.22.0'
    compile 'com.google.apis:google-api-services-drive:v3-rev81-1.22.0'
    compile 'org.apache.httpcomponents:httpcore:4.4.1'
    compile 'org.apache.httpcomponents:httpclient:4.5'
}
```
```xml
android {
    .....
    defaultConfig {
      ...
    configurations.all {
            resolutionStrategy.force 'com.google.code.findbugs:jsr305:1.3.9'
        }
    }
```
    
```xml
allprojects {
    repositories {
        jcenter()
        mavenCentral()
    }
}
```
* Copy .p12 extension file to ```src/main/assets``` folder of your project.
* Now copy ```Fusion_Helper.java``` to your project.
* in ```Fusion_Helper.java``` change the app name to your app name,change the ```service email```, change the name of ```.p12 extension file```.

* Initialize Fussion_helper.java in your activity
```java
public class exampleActivity extends AppCompatActivity {
    Fusiontables fusiontables;
    ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        fusiontables=new Fusion_Helper(this).open(); // this will return a Fussion Table instance whcih you can 
        ....
    }
```

* Example of ```fusion_table``` query in AsyncTask
```java
private class Fusion_Query extends AsyncTask {
        private Sqlresponse sqlresponse=null;
        private int flag=0;
        private String e_msg;
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                sqlresponse=fusiontables.query().sql("Select * from "+YOUR_TABLE_ID+" ;").execute();
                flag=1;
            } catch (IOException e) {
                e.printStackTrace();
                flag=-1;
                e_msg=e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(flag==1){
                //you query excuted sucsefully
                try {
                    Log.d("Fusion_query","query success"+"\n"+sqlresponse.toPrettyString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if(flag==-1){
                //error
                Log.d("Fusion_query",e_msg);
            }
        }
    }
```
Permessions
===========
* Add the following permession in your ```Manifest.xml```
```xml
<uses-permission android:name="android.permission.INTERNET"></uses-permission> 
````




