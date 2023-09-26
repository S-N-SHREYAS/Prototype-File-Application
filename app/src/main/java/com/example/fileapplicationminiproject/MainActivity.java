package com.example.fileapplicationminiproject;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText textResult, fileNameText;
    private Button createButton, saveButton, openButton, deleteButton;

    private File file;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.text_result);
        fileNameText = findViewById(R.id.text_result2);
        createButton = findViewById(R.id.button_create);
        saveButton = findViewById(R.id.button_save);
        openButton = findViewById(R.id.button_open);
        deleteButton = findViewById(R.id.button_delete);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFile();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFile();
            }
        });
    }

    private void createFile() {
        String text = textResult.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "Please enter some text", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = fileNameText.getText().toString();
        if (fileName.isEmpty()) {
            Toast.makeText(this, "Please give a file name", Toast.LENGTH_SHORT).show();
            return;
        }

        file = new File(getFilesDir(), fileName);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(text.getBytes());
            outputStream.close();
            Toast.makeText(this, "File created successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "File creation failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToFile() {
        String text = textResult.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "Please enter some text", Toast.LENGTH_SHORT).show();
            return;
        }

        if (file == null) {
            Toast.makeText(this, "No file to save. Create or open a file first.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(text.getBytes());
            outputStream.close();
            Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "File save failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFile() {
        String fileName = fileNameText.getText().toString();
        if (fileName.isEmpty()) {
            Toast.makeText(this, "Please give a file name", Toast.LENGTH_SHORT).show();
            return;
        }

        file = new File(getFilesDir(), fileName);

        try {
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            textResult.setText(stringBuilder.toString());

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            Toast.makeText(this, "File opened successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "File open failed/ File doesn't exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFile() {
        String fileName = fileNameText.getText().toString();
        if (fileName.isEmpty()) {
            Toast.makeText(this, "Please give a file name", Toast.LENGTH_SHORT).show();
            return;
        }

        file = new File(getFilesDir(), fileName);

        if (file.delete()) {
            Toast.makeText(this, "File deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File deletion failed/ File doesn't exist", Toast.LENGTH_SHORT).show();
        }
    }
}
