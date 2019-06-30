package com.google.codelabs.appauth.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.models.Product;
import com.google.codelabs.appauth.product.ProductClient;
import com.google.codelabs.appauth.product.ProductInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subscriptions.CompositeSubscription;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class UploadProductActivity extends AppCompatActivity {
    @BindView(R.id.etProductName)
    EditText etproductName;

    @BindView(R.id.etProductPrice)
    EditText etproductPrice;

    @BindView(R.id.etProductColor)
    EditText etproductColor;

    @BindView(R.id.etProductCategory)
    EditText etproductCategory;

    @BindView(R.id.etProductImage)
    EditText etproductImage;

    @BindView(R.id.etProductSize)
    EditText etProductSize;

    @BindView(R.id.addPhotos)
    ImageView ivAddPhotos;


    @BindView(R.id.btnUpload)
    Button uploadButton;

    @BindView(R.id.btnAddImg)
    Button AddImgBtn;

    String mProductName;
    String mProductPrice;
    String mProductColor;
    String mProductCategory;
    String mProductSize;
    String mProductImage;


    ProductInterface productInterface;
    private Uri fileUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 100;
    private final static int IMAGE_RESULT = 200;
    Bitmap bitmap;
    Uri picUri;


    private static int GALLERY_REQUEST_CODE = 11;
    private static final int PICK_IMAGE_MULTIPLE = 1;


    private LinearLayout lnrImages;
    private ArrayList<String> imagesPathList;
    private Bitmap yourBitmap;
    private Bitmap resized;


    Product product;
    private static final String TAG = UploadProductActivity.class.getSimpleName();
    CompositeSubscription mSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);
        ButterKnife.bind(this);

//        uploadButton.setOnClickListener(v->initFileds());

        ivAddPhotos.setOnClickListener(v -> {
            Intent pickImage = new Intent();
            pickImage.setType("image/*");
            pickImage.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(pickImage, "Select Image"), GALLERY_REQUEST_CODE);

        });

        lnrImages = findViewById(R.id.lnrImages);
        ivAddPhotos.setOnClickListener(v -> {
            startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);

        });

        uploadButton.setOnClickListener(v -> {
            mProductName = etproductName.getText().toString().trim();
            mProductPrice = etproductPrice.getText().toString().trim();
            mProductColor = etproductColor.getText().toString().trim();
            mProductCategory = etproductCategory.getText().toString().trim();
            mProductSize = etProductSize.getText().toString().trim();
            mProductImage = etproductImage.getText().toString().trim();

            product = new Product();

            product.setProductName(mProductName);
            product.setProductPrice(mProductPrice);
            product.setProductColor(mProductColor);
            product.setProductCategory(mProductCategory);
            product.setProductSize(mProductSize);
            product.setProductImage(mProductImage);

            productInterface = ProductClient.getProductClient().create(ProductInterface.class);
            Call<ResponseBody> call = productInterface.addProduct(product);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(UploadProductActivity.this, "Uploaded", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(UploadProductActivity.this, "Failed", Toast.LENGTH_LONG).show();


                }
            });


        });

        askPermission();
//        initRetrofitClient();
        AddImgBtn.setOnClickListener(v -> {
            if (bitmap != null) {
                multipartImageUpload();
            } else {
                Toast.makeText(this, "Bitmap is null. Try again ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private ArrayList<String> findUnaskedPermission(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);

                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if
                        (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permisssion are mandator for the application. Please allow access",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new
                                                    String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;

                        }
                    }
                }
                break;

        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void askPermission() {
        permissions.add(CAMERA);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissionsToRequest = findUnaskedPermission(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

    }

    private void multipartImageUpload() {
        try {
            File filesDir = getApplicationContext().getFilesDir();
            File file = new File(filesDir, "image" + ".png");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");
            RequestBody title=RequestBody.create(MediaType.parse("multipart/form-data"),mProductName);
            RequestBody price=RequestBody.create(MediaType.parse("multipart/form-data"),mProductPrice);
            RequestBody color=RequestBody.create(MediaType.parse("multipart/form-data"),mProductColor);
            RequestBody category=RequestBody.create(MediaType.parse("multipart/form-data"),mProductCategory);
            RequestBody size=RequestBody.create(MediaType.parse("multipart/form-data"),mProductSize);


            ProductInterface productInterface = ProductClient.getProductClient().create(ProductInterface.class);
            Call<ResponseBody> req = productInterface.postImage(body, name,title,price,color,category,size);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        Toast.makeText(UploadProductActivity.this, "Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                    }
//                    Log.e(TAG, "onResponse: "+response.errorBody().toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(UploadProductActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();

                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Intent getPickImageChooserIntent() {
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);

        }
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if ((intent.getComponent().getClassName().equals("com.google.codelabs.appauth.activities.UploadProductActivity"))) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select Source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[(allIntents.size())]));
        return chooserIntent;

    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(),
                    "profile.png"));
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
//            etproductImage
            String filePath = getImageFilePath(data);
            if (filePath != null) {
                bitmap = BitmapFactory.decodeFile(filePath);
                ivAddPhotos.setImageBitmap(bitmap);
            }
        }
    }

    private String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;
        if (isCamera) {
            return getCaptureImageOutputUri().getPath();
        } else {
            return getPathFromURI(data.getData());
        }
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    @Override
    protected void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        picUri = savedInstanceState.getParcelable("pic_uri");
    }


}
