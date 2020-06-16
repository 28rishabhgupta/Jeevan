package com.example.jeevan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Home extends AppCompatActivity  {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    Button complaint_btn_cam_upload;
    Button logoutButton,currentLocation;
    FloatingActionButton floatingActionButton;

    String currentPhotoPath;
    StorageReference storageReference;
    TextView tv_Country, tv_State, tv_City, tv_Pin, tv_Address;
    // LocationManager locationManager;
    ImageView complaint_image_upload_camera;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //grant permission for location method...
       // grantPermission();

      // tv_Country = findViewById(R.id.tv_Country);
        //tv_City = findViewById(R.id.tv_City);
       // tv_State = findViewById(R.id.tv_State);
        //tv_Pin = findViewById(R.id.tv_Pin);
        //tv_Address = findViewById(R.id.tv_Address);
        floatingActionButton = findViewById(R.id.floatingActionbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Chat_botActivity.class));
            }
        });
        //Location enabled or not..
        //checkLocationIsEnabledOrNot();
       // getLocation();
        complaint_btn_cam_upload = findViewById(R.id.complaint_btn_cam_upload);
       // currentLocation = findViewById(R.id.currentLocation);
        complaint_image_upload_camera = findViewById(R.id.complaint_image_upload_camera);
        //Firebase Storage reference...
        storageReference = FirebaseStorage.getInstance().getReference();
       // currentLocation.setOnClickListener(new View.OnClickListener() {
           // @Override
          //  public void onClick(View v) {
          //      getLocation();
          //      checkLocationIsEnabledOrNot();
          //  }
       // });
        complaint_btn_cam_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });


        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent logoutintent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(logoutintent);
            }
        });
    }


    //private void getLocation() {
      //  try {
         //   locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         //   if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
             //   return;
           // }
         //   locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5,(LocationListener)this);
       // }catch (SecurityException e){
         //   e.printStackTrace();

       /// }
   // }

   // private void checkLocationIsEnabledOrNot() {
        //this method will redirect us to Location Settingd..
       // LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      //  boolean gpsEnabled = false;
      //  boolean networkEnabled = false;
       // try{
      //      gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
       // }catch (Exception e){
       //     e.printStackTrace();
     //   }
      //  try{
      //      networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
      //  }catch (Exception e){
        //    e.printStackTrace();
       // }
      //  if(!gpsEnabled && !networkEnabled){
        //    new AlertDialog.Builder(Home.this)
              //      .setTitle("Enable Gps Service")
                   // .setCancelable(false)
                  //  .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                   //     @Override
                   //     public void onClick(DialogInterface dialog, int which) {
                       //     startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                     //   }
                  //  }).setNegativeButton("Cancel",null)
                 //   .show();
     //   }
  //  }

  //  private void grantPermission() {
      //  if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
          //  && ActivityCompat.checkSelfPermission(getApplicationContext(),
            //    Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
          //  ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
          //  Manifest.permission.ACCESS_COARSE_LOCATION},100);
      ///  }
  //  }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }
        else{
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CAMERA_PERM_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                dispatchTakePictureIntent();
            }
            else{
                Toast.makeText(Home.this,"Camera permission required",Toast.LENGTH_SHORT).show();
            }
        }
    }




    @Override
    public void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                complaint_image_upload_camera.setImageURI(Uri.fromFile(f));
                Log.d("tag","Absolute Url image is" + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
               // f = new File(currentPhotoPath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                uploadImageToFirebase(f.getName(),contentUri);
            }


        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image = storageReference.child("images/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       Log.d("tag","onSuccess: Uploaded image url is"+ uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Home.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //images/image.jpeg
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(0));
        String imageFileName = "JPEG_" + timeStamp + "_";
      //  File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    public void btn_current_Location(View view) {
        startActivity(new Intent(this,MapsActivity.class));
    }

    public void btn_current_RetrieveLocation(View view) {

    }

    // @Override
   // public void onLocationChanged(Location location) {

      //  try {
          //  Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
          //  List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

          //  tv_Country.setText(addresses.get(0).getCountryName());
          //  tv_State.setText(addresses.get(0).getAdminArea());
           //  tv_City.setText(addresses.get(0).getLocality());
           //   tv_Pin.setText(addresses.get(0).getPostalCode());
           //   tv_Address.setText(addresses.get(0).getAddressLine(0));

      // } catch (IOException e) {
      //      e.printStackTrace();
     //   }
 //  }

   // @Override
  //  public void onStatusChanged(String provider, int status, Bundle extras) {

   // }

  //  @Override
   // public void onProviderEnabled(String provider) {

   // }

   // @Override
  //  public void onProviderDisabled(String provider) {

  //  }
}


