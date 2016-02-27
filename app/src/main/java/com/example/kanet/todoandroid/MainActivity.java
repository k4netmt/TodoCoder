package com.example.kanet.todoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Adapter.TodoListMainAdapter;
import DAO.TodoListDAO;
import DataProvider.TodoListDTO;

public class MainActivity extends AppCompatActivity {
    TodoListDAO todoDAO;
    ArrayList<TodoListDTO> todoItems;
    TodoListMainAdapter aToDoAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populaterArrayItems();
        lvItems=(ListView)findViewById(R.id.lvItems);

        lvItems.setAdapter(aToDoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(todoDAO.deleteItem(todoItems.get(position))==true)
                {
                    todoItems.remove(position);
                    aToDoAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });

       lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra("index",position);
                intent.putExtra("id",todoItems.get(position).get_id());
                intent.putExtra("title",todoItems.get(position).get_titles());
                intent.putExtra("description",todoItems.get(position).get_description());
                intent.putExtra("level",todoItems.get(position).get_level());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public void populaterArrayItems(){
        todoDAO=new TodoListDAO(this);
        todoItems=new ArrayList<TodoListDTO>();
        todoItems=todoDAO.getList();
        //readItems();
 /*       todoItems.add(new TodoListDTO(0,"Items 1","Do homework",1));
        todoItems.add(new TodoListDTO(1,"Items 2","Do homework",1));
        todoItems.add(new TodoListDTO(2,"Items 3","Do homework",1));*/
        aToDoAdapter=new TodoListMainAdapter(this,todoItems);
        //aToDoAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems);
    }

    public void OnAddItems(View view) {
        //aToDoAdapter.add(etEditText.getText().toString());
        //etEditText.setText("");
        //writeItems();
        Intent intent=new Intent(MainActivity.this,EditActivity.class);
        intent.putExtra("index",-1);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            int index = data.getExtras().getInt("index", -1);
            int id=data.getExtras().getInt("id", -1);
            String title = data.getExtras().getString("title");
            String description = data.getExtras().getString("description");
            int level = data.getExtras().getInt("level", 0);
            if (index==-1) {
                TodoListDTO todo=new TodoListDTO(todoItems.size(),title,description,level);
                long idAdd=todoDAO.addItem(todo);
                if(id!=-1)
                    todo.set_id((int)idAdd);
                    todoItems.add(todo);
            }
            else {
                TodoListDTO todo=new TodoListDTO(id,title,description,level);
                if(todoDAO.updateItem(todo)==true)
                    todoItems.set(index, todo);
            }

            // Toast the name to display temporarily on screen
            //writeItems();
            aToDoAdapter.notifyDataSetChanged();
        }
    }

   /* private void readItems(){
        File filesDir=getFilesDir();

        File file=new File(filesDir,"todo.txt");
        try{
            todoItems=new ArrayList<TodoListDTO>(FileUtils.readLines(file));
        }catch (IOException e){

        }
    }*/

   /*private void writeItems(){
        File filesDir=getFilesDir();
        File file=new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(file,todoItems);
        }catch (IOException e){

        }
    }*/

}
