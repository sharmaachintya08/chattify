package com.example.chatroom;

import static com.example.chatroom.cords.FirebaseCords.MAIN_CHAT_DATABASE;
import static com.example.chatroom.cords.FirebaseCords.mAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatroom.cords.FirebaseCords;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Toolbar toolbar;
    EditText chat_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chat_box = findViewById(R.id.chat_box);
    }

    public void addMessage(View view){
        String message = chat_box.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        if(!TextUtils.isEmpty(message)){

            Date today = new  Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String messageID = format.format(today);

            String user_image_url = "";
            Uri photoUrl = user.getPhotoUrl();
            String originalUrl = "s96-c/photo.jpg";
            String resizeImageUrl = "s400-c/photo.jpg";
            if(photoUrl!=null){
                String photoPath = photoUrl.toString();
                user_image_url = photoPath.replace(originalUrl,resizeImageUrl);
            }

            HashMap<String,Object> messageObj = new HashMap<>();
            messageObj.put("message",message);
            messageObj.put("user_name",user.getDisplayName());
            messageObj.put("timestamp", FieldValue.serverTimestamp());
            messageObj.put("messageID",messageID);
            messageObj.put("user_image_url",user_image_url);

            MAIN_CHAT_DATABASE.document(messageID).set(messageObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Message send",Toast.LENGTH_SHORT).show();
                        chat_box.setText("");
                    }else{
                        Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}