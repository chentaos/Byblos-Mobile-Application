package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPageActivity extends AppCompatActivity {

    private EditText firsrName;
    private EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        firsrName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
    }

    public void continueRegister(View view){
        String firstName= this.firsrName.getText().toString().trim();
        String lastName = this.lastName.getText().toString().trim();
        boolean firstNameValidation = firstName.matches("^[a-zA-Z]+$");
        boolean lastNameValidation = lastName.matches("^[a-zA-Z]+$");

        if(!firstNameValidation || !lastNameValidation){
            Toast.makeText(getApplication(),"Name can't be number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(firstName.isEmpty()){
            Toast.makeText(getApplication(),"Fisrt name can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(lastName.isEmpty()){
            Toast.makeText(getApplicationContext(),"Last name can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i=getIntent();
        i.putExtra("firstName",firstName);
        i.putExtra("lastName",lastName);
        setResult(RESULT_OK, i);
        finish();
    }

}