package com.example.ali.chattalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity {

    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private ImageView mPhotoCapturedImageView;
    private String mImageFileLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPhotoCapturedImageView=(ImageView) findViewById(R.id.capturedPhotoImageView);
    }
    public void takePhoto(View view){
        Toast.makeText(this,"Press",Toast.LENGTH_SHORT).show();
        Intent callCameraApplication = new Intent();
        callCameraApplication.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try{
            photoFile = createImageFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        callCameraApplication.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(callCameraApplication,ACTIVITY_START_CAMERA_APP);
    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if(requestCode==ACTIVITY_START_CAMERA_APP && resultCode== RESULT_OK){
            Toast.makeText(this,"Başarılı çekim",Toast.LENGTH_SHORT).show();
            // Bundle extras = data.getExtras();
            // Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
            // mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
            // Bitmap photoCapturedBitmap = BitmapFactory.decodeFile(mImageFileLocation);
            // mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
            // mPhotoCapturedImageView.setRotation(270);

            rotateImage(setReducedImageSize());
        }
    }
    File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName,".jpg",storageDirectory);
        mImageFileLocation = image.getAbsolutePath();

        return image;
    }

    private Bitmap setReducedImageSize(){
        int targetImageViewWidth = mPhotoCapturedImageView.getWidth();
        int targetImageViewHeight = mPhotoCapturedImageView.getHeight();

        BitmapFactory.Options bmOptions= new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageFileLocation,bmOptions);
        int cameraImageWıdth = bmOptions.outWidth;
        int cameraImageHeight = bmOptions.outHeight;

        int scaleFactor = Math.min(cameraImageWıdth/targetImageViewWidth,cameraImageHeight/targetImageViewHeight);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;

        // Bitmap photoReducedSizeBitmp = BitmapFactory.decodeFile(mImageFileLocation,bmOptions);
        // mPhotoCapturedImageView.setImageBitmap(photoReducedSizeBitmp);
        return BitmapFactory.decodeFile(mImageFileLocation,bmOptions);
    }

    private void rotateImage(Bitmap bitmap){
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(mImageFileLocation);
        }catch (IOException e){
            e.printStackTrace();
        }
        int oriantation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (oriantation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(270);
                break;
            default:
        }
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        mPhotoCapturedImageView.setImageBitmap(rotatedBitmap);
    }
    @Override
    public boolean  onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent backMainTest = new Intent(this,SecimActivity.class);
        startActivity(backMainTest);
        finish();
    }
}
