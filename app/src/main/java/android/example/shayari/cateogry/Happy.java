package android.example.shayari.cateogry;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.example.shayari.MyShayari;
import android.example.shayari.R;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Happy extends AppCompatActivity implements View.OnClickListener {
    List<String> shayari_list;
    DatabaseReference databaseReference;
    int position = 0;
    TextView shayari_txt;
    MyShayari myShayari;
    Button copybtn, previousbtn, forwardbtn, sharebtn;
    TextView contxt;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common);
        this.setTitle("Happy");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#05DAA5")));
        shayari_txt = findViewById(R.id.ShayariTxt);
        previousbtn = findViewById(R.id.previous_btn);
        copybtn = findViewById(R.id.copy_btn);
        forwardbtn = findViewById(R.id.back_btn);
        sharebtn = findViewById(R.id.share_btn);
        contxt = findViewById(R.id.con_txt);


        previousbtn.setOnClickListener(this);
        copybtn.setOnClickListener(this);
        forwardbtn.setOnClickListener(this);
        sharebtn.setOnClickListener(this);
        relativeLayout = findViewById(R.id.RelativeLayout);
        relativeLayout.setBackground(getDrawable(R.drawable.happy));


        databaseReference = FirebaseDatabase.getInstance().getReference("Happy");
        myShayari = new MyShayari();
        shayari_list = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    myShayari = ds.getValue(MyShayari.class);
                    if (myShayari != null) {
                        shayari_list.add(myShayari.getS());
                    }
                }
                shayari_txt.setText(shayari_list.get(position));
                contxt.setText(position + "/" + shayari_list.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous_btn:
                previous();
                break;
            case R.id.back_btn:
                next();
                break;
            case R.id.copy_btn:
                copy();
                break;
            case R.id.share_btn:
                share();
                break;
        }
    }

    private void previous() {
        if (position > 0) {
            position = (position - 1) % shayari_list.size();
            shayari_txt.setText(shayari_list.get(position));
            contxt.setText(position + "/" + shayari_list.size());
        }
    }

    private void next() {
        position = (position + 1) % shayari_list.size();
        shayari_txt.setText(shayari_list.get(position));
        contxt.setText(position + "/" + shayari_list.size());

    }

    private void copy() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", shayari_txt.getText());
        assert clipboardManager != null;
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(), "copied", Toast.LENGTH_SHORT).show();
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shayari_txt.getText());
        startActivity(Intent.createChooser(intent, "share by"));
    }
}
