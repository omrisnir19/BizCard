package com.example.bizcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // מפתחות קבועים - מוגדרים במקום אחד ומשמשים את שני המסכים
    public static final String KEY_NAME = "name";
    public static final String KEY_ROLE = "role";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_WEBSITE = "website";

    EditText etName, etRole, etPhone, etWebsite;
    Button btnCreate, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etRole = findViewById(R.id.etRole);
        etPhone = findViewById(R.id.etPhone);
        etWebsite = findViewById(R.id.etWebsite);
        btnCreate = findViewById(R.id.btnCreate);
        btnAbout = findViewById(R.id.btnAbout);

        // מסר מפורש עם נתונים: MainActivity -> CardActivity
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();

                // בדיקת קלט: שם ריק -> Toast ואין מעבר מסך
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.toast_name_required, Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                intent.putExtra(KEY_NAME, name);
                intent.putExtra(KEY_ROLE, etRole.getText().toString().trim());
                intent.putExtra(KEY_PHONE, etPhone.getText().toString().trim());
                intent.putExtra(KEY_WEBSITE, etWebsite.getText().toString().trim());
                startActivity(intent);
            }
        });

        // מסר מפורש בלי נתונים: MainActivity -> AboutActivity
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
