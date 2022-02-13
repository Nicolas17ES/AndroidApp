package com.tek.bootstrap.chuck.firstapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tek.bootstrap.chuck.firstapp.ui.interfaces.UserApi;
import com.tek.bootstrap.chuck.firstapp.ui.model.FileModel;
import com.tek.bootstrap.chuck.firstapp.ui.model.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Upload_image extends AppCompatActivity {
    private CardView cardView;
    private ImageView imageView;
    private Button button;
    private CharSequence[] options = {"Camera", "Gallery", "Cancel"};
    private String selectedImage;
    String name;
    String email;
    String user_id;
    Toolbar mToolbar;
    MenuItem logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        cardView = findViewById(R.id.cardViewImage);
        imageView = findViewById(R.id.imageViewImage);
        button = findViewById(R.id.buttonImage);
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        user_id = intent.getStringExtra("user_id");

        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        requirePermission();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Upload_image.this);
                builder.setTitle("Select Image");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        if(options[which].equals("Camera")){
                            Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePic, 0);
                        }
                        else if(options[which].equals("Gallery")){
                            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gallery, 1);
                        }
                        else{
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFileToServer();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);
        menu = mToolbar.getMenu();
        logout = menu.getItem(2);
        logout.setTitle("Log Out");
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode !=RESULT_CANCELED){

            switch (requestCode){
                case 0:
                    if(resultCode == RESULT_OK && data !=null){
                        Bitmap image = (Bitmap) data.getExtras().get("data");
                        selectedImage = FileUtils.getPath(Upload_image.this, getImageUri(Upload_image.this,image));
                        imageView.setImageBitmap(image);
                    }
                    break;
                case 1:
                    if(resultCode == RESULT_OK && data !=null){

                        Uri image = data.getData();
                        selectedImage = FileUtils.getPath(Upload_image.this,image);
                        Picasso.get().load(image).into(imageView);
                    }
            }

        }
    }

    public Uri getImageUri(Context context, Bitmap bitmap){
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "myImage","");

        return Uri.parse(path);
    }


    public void requirePermission(){
        ActivityCompat.requestPermissions(Upload_image.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }


    public void uploadFileToServer(){

        File file = new File(Uri.parse(selectedImage).getPath());

            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);

            MultipartBody.Part filePart = MultipartBody.Part.createFormData("sendimage",file.getName(),requestBody);



            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.98:3001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            UserApi userApi = retrofit.create(UserApi.class);

            Call<FileModel> call = userApi.postImage(filePart, name, email, user_id);
            call.enqueue(new Callback<FileModel>() {
                @Override
                public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                    Log.d("devErrors", "image correct");
                    FileModel fileModel = response.body();
                    Intent intent = new Intent(Upload_image.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Upload_image.this, "Successfully Created", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<FileModel> call, Throwable t) {
                    Log.d("devErrors", "image not correct");
                    Intent intent = new Intent(Upload_image.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Upload_image.this, "Successfully Created", Toast.LENGTH_SHORT).show();
                }
            });
        }
}