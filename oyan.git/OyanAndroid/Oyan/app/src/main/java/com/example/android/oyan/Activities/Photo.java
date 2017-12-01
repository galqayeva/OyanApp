package com.example.android.oyan.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.oyan.Callbacks.PhotoCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.PhotoController;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Message.Messages;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.RequetTag;
import com.example.android.oyan.Domain.SingletonVolleyQueue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class Photo extends Activity implements PhotoCallback {

    public com.isseiaoki.simplecropview.CropImageView cropIma;
    private ProgressDialog progressDialog = null;
    private UserDomain resultDomain = null;
    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE);
        resultDomain = new UserDomain();
        resultDomain.setId(sharedPreferences.getLong(Constant.ID, 0));
        resultDomain.setFullName(sharedPreferences.getString(Constant.FULLNAME, null));
        resultDomain.setEmail(sharedPreferences.getString(Constant.EMAIL, null));
        resultDomain.setGender(sharedPreferences.getString(Constant.GENDER, null));
        resultDomain.setCountryId(sharedPreferences.getLong(Constant.COUNTRY_ID, -1));
        resultDomain.setCountryName(sharedPreferences.getString(Constant.COUNTRY_NAME, null));
        resultDomain.setPhotoName(sharedPreferences.getString(Constant.PHOTO_NAME, null));
        resultDomain.setToken(sharedPreferences.getString(Constant.TOKEN, null));
        resultDomain.setSipUsername(sharedPreferences.getLong(Constant.SIPUSERNAME, -1));
        resultDomain.setSipPassword(sharedPreferences.getString(Constant.SIPPASSWORD, null));
        resultDomain.setSipRegistrar(sharedPreferences.getString(Constant.SIPREGISTRAR, null));

        ImageView i1 = (ImageView) this.findViewById(R.id.imageView33);
        i1.setImageResource(R.drawable.userph);
        i1.buildDrawingCache(true);
        BitmapDrawable drawable = (BitmapDrawable) i1.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        cropIma = (com.isseiaoki.simplecropview.CropImageView) findViewById(R.id.myPhoto1);
        cropIma.setImageBitmap((bitmap));

        cropIma.setHandleSizeInDp(0);
        cropIma.setCropEnabled(false);

        cropIma.setFrameStrokeWeightInDp(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();

        SingletonVolleyQueue singletonVolleyQueue = SingletonVolleyQueue.getInstance(this);
        singletonVolleyQueue.getRequestQueue().cancelAll(RequetTag.LIST_REQUESTS);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {

        menu.findItem(R.id.editPhotoR).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setVisible(false);
                menu.findItem(R.id.editPhotoR).setVisible(false);
                menu.findItem(R.id.cancelPhotoR).setVisible(true);
                menu.findItem(R.id.savePhotoR).setVisible(true);
                menu.findItem(R.id.rotatePhotoR).setVisible(true);
                cropIma.setHandleSizeInDp(10);
                cropIma.setCropEnabled(true);
                cropIma.setFrameStrokeWeightInDp(1);
                selectImage();
                return true;
            }
        });
        menu.findItem(R.id.savePhotoR).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                menu.findItem(R.id.cancelPhotoR).setVisible(false);
                menu.findItem(R.id.savePhotoR).setVisible(false);
                menu.findItem(R.id.editPhotoR).setVisible(true);
                menu.findItem(R.id.rotatePhotoR).setVisible(false);
                onCropImageClick();
                return true;
            }
        });
        menu.findItem(R.id.cancelPhotoR).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                menu.findItem(R.id.cancelPhotoR).setVisible(false);
                menu.findItem(R.id.savePhotoR).setVisible(false);
                menu.findItem(R.id.editPhotoR).setVisible(true);
                menu.findItem(R.id.rotatePhotoR).setVisible(false);
                cropIma.setCropEnabled(false);
                return true;
            }
        });
        menu.findItem(R.id.rotatePhotoR).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                cropIma.rotateImage(com.isseiaoki.simplecropview.CropImageView.RotateDegrees.ROTATE_90D);
                return true;
            }
        });
        menu.findItem(R.id.skipPhotoR).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onSkip();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onCropImageClick() {

        Bitmap cropped = cropIma.getCroppedBitmap();
        //  Application.photoNameKey = UUID.randomUUID().toString() + ".jpeg";
        //  Application.mMemoryCache.put(Application.photoNameKey, cropped);
        if (cropped != null) cropIma.setImageBitmap(cropped);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cropped.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte barray[] = baos.toByteArray();
        String imageBase64 = Base64.encodeToString(barray, Base64.DEFAULT);

        SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHAREDPREFERENCES, MODE_PRIVATE);
        UserDomain userDomain = new UserDomain();
        userDomain.setPhotoString(imageBase64);
        userDomain.setPhotoName(resultDomain.getEmail());
        userDomain.setToken(sharedPreferences.getString(Constant.TOKEN, null));

        PhotoController photoController = new PhotoController();
        photoController.photoUpload(this, userDomain);
    }

    @Override
    public void onBeginPhotoUpload() {

        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onResultPhotoUpload(Object object) {

        if (progressDialog != null) progressDialog.cancel();
        UserDomain resDomain = (UserDomain) object;
        if (resDomain.getMessageId() == Messages.SUCCESFULL) {
            Log.v("this is photoName - - ", resDomain.getPhotoName());
            resultDomain.setPhotoName(resDomain.getPhotoName());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(Constant.PHOTO_NAME);
            editor.commit();
            editor.putString(Constant.PHOTO_NAME, resultDomain.getPhotoName());
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);
        } else if (resDomain.getMessageId() == Messages.AUTHORIZATION_ERROR) {
            Toast.makeText(this, "AUTHORIZATION_ERROR", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Logout.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Server error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorPhotoUpload(Object object) {

        if (progressDialog != null) progressDialog.cancel();
        Toast.makeText(this, "Network connection error", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constant.PHOTO_NAME);
        editor.commit();
        editor.putString(Constant.PHOTO_NAME, resultDomain.getPhotoName());
        this.startActivity(intent);
    }

    public void onSkip() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpeg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 0);
                } else if (items[item].equals("Choose from Library")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Choose Photo"), SELECT_FILE);
                }
            }
        });
        builder.show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA) {

                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpeg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    cropIma.setImageBitmap(bitmap);
                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        cropIma.setImageBitmap(bm);
    }
}