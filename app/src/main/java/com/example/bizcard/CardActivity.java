package com.example.bizcard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CardActivity extends AppCompatActivity {

    TextView tvName, tvRole, tvPhone, tvWebsite;
    Button btnDial, btnWebsite, btnShare, btnBack;

    String name, role, phone, website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card);

        tvName = findViewById(R.id.tvCardName);
        tvRole = findViewById(R.id.tvCardRole);
        tvPhone = findViewById(R.id.tvCardPhone);
        tvWebsite = findViewById(R.id.tvCardWebsite);
        btnDial = findViewById(R.id.btnDial);
        btnWebsite = findViewById(R.id.btnWebsite);
        btnShare = findViewById(R.id.btnShare);
        btnBack = findViewById(R.id.btnBack);

        // קבלת הנתונים שנשלחו מ-MainActivity
        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.KEY_NAME);
        role = intent.getStringExtra(MainActivity.KEY_ROLE);
        phone = intent.getStringExtra(MainActivity.KEY_PHONE);
        website = intent.getStringExtra(MainActivity.KEY_WEBSITE);

        if (name == null) name = "";
        if (role == null) role = "";
        if (phone == null) phone = "";
        if (website == null) website = "";

        tvName.setText(name);
        tvRole.setText(role);
        tvPhone.setText("📞 " + phone);
        tvWebsite.setText("🌐 " + website);

        // מסר מרומז 1: חייגן (ACTION_DIAL) עם המספר כבר מוקלד
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.isEmpty()) {
                    Toast.makeText(CardActivity.this, R.string.toast_no_phone, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(dial);
            }
        });

        // מסר מרומז 2: דפדפן (ACTION_VIEW), כולל הוספת https:// כשחסר
        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (website.isEmpty()) {
                    Toast.makeText(CardActivity.this, R.string.toast_no_website, Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = website;
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }
                Intent view = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(view);
            }
        });

        // מסר מרומז 3: שיתוף (ACTION_SEND) עם בורר
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = name + "\n" + role + "\n" + phone + "\n" + website;
                Intent send = new Intent(Intent.ACTION_SEND);
                send.setType("text/plain");
                send.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(send, getString(R.string.share_chooser_title)));
            }
        });

        // חזרה: סוגר את המסך
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
