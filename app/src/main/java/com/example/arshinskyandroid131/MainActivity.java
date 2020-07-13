package com.example.arshinskyandroid131;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText loginEdit;
    private EditText pswEdit;
    private Button logBut;
    private Button regBut;

    private String loginInp;
    private String pswInp;
    private String authFileName = "reg.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        loginEdit = findViewById(R.id.editLogin);
        pswEdit = findViewById(R.id.editPsw);
        logBut = findViewById(R.id.butLogin);
        regBut = findViewById(R.id.butReg);
        logBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInp = loginEdit.getText().toString();
                pswInp = pswEdit.getText().toString();
                if (loginInp.length() > 0 && pswInp.length() > 0) {
                    String[] logpsw = readDataFromInternalStorage(authFileName).split(" ");
                    if ((logpsw[0].equals(loginInp)) && (logpsw[1].equals(pswInp))) {
                        Toast.makeText(MainActivity.this, "Success log-in", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid login or paswd", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Fill all edits!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInp = loginEdit.getText().toString();
                pswInp = pswEdit.getText().toString();
                if (loginInp.length() > 0 && pswInp.length() > 0) {
                    saveDataToInternStorage(authFileName, loginInp + " " + pswInp);
                    Toast.makeText(MainActivity.this, "Register!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Fill all edits!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String readDataFromInternalStorage(String fname) {
        StringBuilder readedData = new StringBuilder("");
        String readedLine;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(fname)))) {
            while ((readedLine = reader.readLine()) != null) {
                readedData.append(readedLine + " ");
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "Miss file with regdata", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "File error with regdata", Toast.LENGTH_SHORT).show();
        }
        return readedData.toString();
    }

    private void saveDataToInternStorage(String fname, String dataToFile) {
        String[] datas = dataToFile.split(" ");
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fname, MODE_PRIVATE)))) {
            for (String data : datas) {
                bw.write(data + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
