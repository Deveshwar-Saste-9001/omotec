package com.example.attendancesystem_omotec;

import static com.example.attendancesystem_omotec.DatabaseQueries.firebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class addStudentList extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 101;
    private static final int REQUEST_PICK_EXCEL_FILE = 102;
    private TextView selectFile;
    private Button uploadFile;
    private String school;
    private InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_list);
        selectFile = findViewById(R.id.selectFile);
        uploadFile = findViewById(R.id.uploadFile);
        school=getIntent().getStringExtra("school");
        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAndRequestPermission()) {
                    openFilePicker();
                }
            }
        });
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    readExcelFile(inputStream);

                } catch (IOException e) {
                    Toast.makeText(addStudentList.this, "Error reading the Excel file.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean checkAndRequestPermission() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFilePicker();
            } else {
                Toast.makeText(this, "Permission denied. Cannot read the Excel file.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Allow all file types
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_PICK_EXCEL_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_EXCEL_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedFileUri = data.getData();

                try {
                    inputStream = getContentResolver().openInputStream(selectedFileUri);
                    selectFile.setText(selectedFileUri.toString());

                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "File not found.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void readExcelFile(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet

        for (Row row : sheet) {
            if (row.getCell(0) != null && row.getCell(1) != null&& row.getCell(2) != null && row.getCell(3) != null) {
                String rollNo = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String section = row.getCell(2).getStringCellValue();
                String day=row.getCell(3).getStringCellValue();
                final HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("name",name);
                userdataMap.put("roll_no", rollNo);
                userdataMap.put("section",section);

                firebaseFirestore.collection("Schools").document(school).collection("CLASSES").document(day).collection("STUDENTS").document(name).set(userdataMap);

            }
        }
        selectFile.setText("");

        workbook.close();
        inputStream.close();

    }


}






