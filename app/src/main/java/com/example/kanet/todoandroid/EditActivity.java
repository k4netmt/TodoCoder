package com.example.kanet.todoandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import DataProvider.TodoListDTO;

public class EditActivity extends AppCompatActivity {
    EditText etEditTitle;
    EditText etEditDesc;
    TodoListDTO mTodo;
    Button btnSave,btnCancel;
    RadioButton rdLow,rdMedium,rdHeight;
    int mIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etEditTitle=(EditText)findViewById(R.id.etEditTitle);
        etEditDesc=(EditText)findViewById(R.id.etDescription);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnCancel=(Button)findViewById(R.id.btnCancelEdit);
        rdLow=(RadioButton)findViewById(R.id.rdLow);
        rdMedium=(RadioButton)findViewById(R.id.rdMedium);
        rdHeight=(RadioButton)findViewById(R.id.rdHeight);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent); // set result code and bundle data for response
                finish();
            }
        });

        mIndex = getIntent().getIntExtra("index", -1);
        if (mIndex != -1)
        {
            int id = getIntent().getIntExtra("id", 0);
            String title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");
            int level = getIntent().getIntExtra("level", 0);

            mTodo=new TodoListDTO(id,title,description,level);

            etEditTitle.setText(title);
            etEditDesc.setText(description);
            checkedRadion(level);
        }
        else
        {
            mTodo=new TodoListDTO();
            mTodo.set_id(-1);
        }

    }

    private void checkedRadion(int level)
    {
        switch (level)
        {
            case 0:rdLow.setChecked(true);break;
            case 1:rdMedium.setChecked(true);break;
            case 2:rdHeight.setChecked(true);break;
        }
    }

    private int getLevel()
    {
        if (rdLow.isChecked())
            return 0;
        if (rdMedium.isChecked())
            return 1;
        if (rdHeight.isChecked())
            return 2;
        return -1;
    }

    public void onDoneEdit(View view) {
        Intent intent = new Intent();
        intent.putExtra("index", mIndex); // pass arbitrary data to launched activity
        intent.putExtra("id",mTodo.get_id());
        intent.putExtra("title",etEditTitle.getText().toString());
        intent.putExtra("description", etEditDesc.getText().toString());
        int level=getLevel();
        intent.putExtra("level", level);
        setResult(RESULT_OK, intent); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }


}
