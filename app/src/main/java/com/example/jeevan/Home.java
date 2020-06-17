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
import android.content.ContentResolver;
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
import android.webkit.MimeTypeMap;
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
import com.karumi.dexter.Dexter;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Home extends AppCompatActivity  {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    ImageView complaint_image_upload_camera;
    Button complaint_btn_cam_upload,complaint_btn_cam_upload_gallery;
    Button logoutButton,currentLocation;
    FloatingActionButton floatingActionButton;
    String currentPhotoPath;
    StorageReference storageReference;
    BottomNavigationView bottom_navigation_view_Home_Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottom_navigation_view_Home_Activity = findViewById(R.id.bottom_navigation_view_Home_Activity);

        bottom_navigation_view_Home_Activity.setSelectedItemId(R.id.action_lodge_complain);
        bottom_navigation_view_Home_Activity.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(),Home_screen_Activity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;
                    case R.id.action_lodge_complain:


                    case R.id.action_profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;
                }
            }
        });

        complaint_image_upload_camera = findViewById(R.id.complaint_image_upload_camera);

        complaint_btn_cam_upload = findViewById(R.id.complaint_btn_cam_upload);

        complaint_btn_cam_upload_gallery = findViewById(R.id.complaint_btn_cam_upload_gallery);

        storageReference = FirebaseStorage.getInstance().getReference();

        floatingActionButton = findViewById(R.id.floatingActionbutton);

        logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent logoutintent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(logoutintent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Chat_botActivity.class));
            }
        });

        complaint_btn_cam_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();

            }
        });
        complaint_btn_cam_upload_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });
    }
    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(Home.this, Manifest.permission.CAMERA) +
                ContextCompat.checkSelfPermission(Home.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            //when Permission not granted...
            if(ActivityCompat.shouldShowRequestPermissionRationale(Home.this,Manifest.permission.CAMERA)
            || ActivityCompat.shouldShowRequestPermissionRationale(Home.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                //Creating Alertdialog...
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Home.this);
                builder.setTitle("Grant Permissions");
                builder.setMessage("Camera, Read Storage");
                builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(
                                Home.this,
                                new String[] {
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                },CAMERA_PERM_CODE
                        );


                    }
                });
                builder.setNegativeButton("Deny",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else{
                ActivityCompat.requestPermissions(
                       Home.this,
                        new String[] {
                               Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                       },CAMERA_PERM_CODE

               );

           }

        }
        else{
            //when Permissions are already granted...
           dispatchTakePictureIntent();
            //opencamera();
        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
               File f = new File(currentPhotoPath);
               complaint_image_upload_camera.setImageURI(Uri.fromFile(f));
               Log.d("tag","Absolute image of url is:" + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                uploadImageFirebase(f.getName(),contentUri);
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(0));
               String imageFileName = "JPEG_" + timeStamp +"."+getFileExt(contentUri);
               complaint_image_upload_camera.setImageURI(contentUri);

                uploadImageFirebase(imageFileName,contentUri);
            }
        }



    }

    private void uploadImageFirebase(String name, Uri contentUri) {
        final StorageReference images = storageReference.child("IMAGES/" + name);
        images.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                images.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag","on Success Uploaded image url is:" + uri.toString());
                    }

                });
                Toast.makeText(Home.this, "Image Successfully uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Home.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(0));
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
       // File storageDire = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


   // static final int REQUEST_TAKE_PHOTO = 1;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] + grantResults[1]==PackageManager.PERMISSION_GRANTED){
            dispatchTakePictureIntent();
        }else{
            Toast.makeText(Home.this,"Camera is Required",Toast.LENGTH_SHORT).show();
        }
    }

    public void btn_current_Location(View view) {
        startActivity(new Intent(this,MapsActivity.class));
    }

    public void btn_current_RetrieveLocation(View view) {

    }
}


