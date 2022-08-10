package edu.pdx.cs410J.agilston.phonebill.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import edu.pdx.cs410J.agilston.phonebill.R;

public class ReadmeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_readme);
        setTitle(getResources().getString(R.string.menu_main_readme));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        try(InputStream readmeFile = getResources().getAssets().open("README.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(readmeFile));
            StringBuilder readme = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) {
                readme.append(line)
                      .append(System.lineSeparator());
            }

            TextView textview = findViewById(R.id.textview_readme);
            textview.setText(readme);
        }
        catch(IOException e) {
            Toast toast = Toast.makeText(this, "README file not found", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
