package android.example.shayari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.shayari.cateogry.Happy;
import android.example.shayari.cateogry.Motivational;
import android.example.shayari.cateogry.Romantic;
import android.example.shayari.cateogry.Sad;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button romantic_btn, sad_btn, happy_btn, motivational_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        romantic_btn = findViewById(R.id.RomanticBtn);
        sad_btn = findViewById(R.id.SadBtn);
        happy_btn = findViewById(R.id.HappyBtn);
        motivational_btn = findViewById(R.id.MotivationalBtn);

        romantic_btn.setOnClickListener(this);
        sad_btn.setOnClickListener(this);
        happy_btn.setOnClickListener(this);
        motivational_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RomanticBtn:
                Intent intent1 = new Intent(MainActivity.this, Romantic.class);
                startActivity(intent1);
                break;

            case R.id.SadBtn:
                Intent intent2 = new Intent(MainActivity.this, Sad.class);
                startActivity(intent2);
                break;

            case R.id.HappyBtn:
                Intent intent3 = new Intent(MainActivity.this, Happy.class);
                startActivity(intent3);
                break;

            case R.id.MotivationalBtn:
                Intent intent4 = new Intent(MainActivity.this, Motivational.class);
                startActivity(intent4);
                break;
        }
    }
}