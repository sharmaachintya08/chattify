package com.example.chatroom;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class ImageUploadPreview extends AppCompatActivity {

    ImageView upload_image_container;
    EditText chat_box;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload_preview);

        imageUri = Uri.parse(getIntent().getStringExtra("image_uri"));
        upload_image_container = findViewById(R.id.upload_image_container);
        chat_box = findViewById(R.id.chat_box);

        upload_image_container.setImageURI(imageUri);
    }
    public void addMessage(View view){

    }
    public void Finish(View view){
        finish();
    }
}