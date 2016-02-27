
package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import DataProvider.DatabaseHelper;
import DataProvider.Define;
import DataProvider.TodoListDTO;


/**
 * Created by Kanet on 2/27/2016.
 */

public class TodoListDAO {
    private DatabaseHelper databaseSQLite;
    private SQLiteDatabase db;
    private Context context;
    public TodoListDAO(Context ct){
        context=ct;
        databaseSQLite=new DatabaseHelper(context);
        databaseSQLite.createDatabase();
        db=databaseSQLite.openDatabase();
    }
    public ArrayList<TodoListDTO> getList()
    {
        ArrayList<TodoListDTO> lstDTO=null;
        String strSQL="Select * From "+ Define.TABLE_TODOLIST_NAME;
        Cursor cursor=db.rawQuery(strSQL, null);
        cursor.moveToFirst();
        lstDTO= new ArrayList<TodoListDTO>();
        while (!cursor.isAfterLast()) {
            TodoListDTO dto=new TodoListDTO(cursor);
            lstDTO.add(dto);
            //Toast.makeText(context,dto.getM_sInforSyntax(),Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }
        return  lstDTO;
    }


}

