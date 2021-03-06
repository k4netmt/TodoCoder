
package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    }
    public ArrayList<TodoListDTO> getList()
    {
        db=databaseSQLite.readDatabase();
        ArrayList<TodoListDTO> lstDTO=null;
        String strSQL="Select * From "+ Define.TABLE_TODOLIST_NAME;
        Cursor cursor=db.rawQuery(strSQL, null);
        cursor.moveToFirst();
        lstDTO= new ArrayList<TodoListDTO>();
        try {
            while (!cursor.isAfterLast()) {
                TodoListDTO dto=new TodoListDTO(cursor);
                lstDTO.add(dto);
                //Toast.makeText(context,dto.getM_sInforSyntax(),Toast.LENGTH_LONG).show();
                cursor.moveToNext();
            }
        } catch (Exception e) {
            //Log.d(TAG, "Error while trying to add post to database");
        } finally {

        }
        return  lstDTO;

    }

    public ArrayList<TodoListDTO> getListLevelDesc()
    {
        db=databaseSQLite.readDatabase();
        ArrayList<TodoListDTO> lstDTO=null;
        String strSQL="Select * From "+ Define.TABLE_TODOLIST_NAME+" ORDER BY " + Define.KEY_TODOLIST_LEVEL + " DESC";
        Cursor cursor=db.rawQuery(strSQL, null);
        cursor.moveToFirst();
        lstDTO= new ArrayList<TodoListDTO>();
        try {
            while (!cursor.isAfterLast()) {
                TodoListDTO dto=new TodoListDTO(cursor);
                lstDTO.add(dto);
                //Toast.makeText(context,dto.getM_sInforSyntax(),Toast.LENGTH_LONG).show();
                cursor.moveToNext();
            }
        } catch (Exception e) {
            //Log.d(TAG, "Error while trying to add post to database");
        } finally {
            cursor.close();
            db.close();
        }
        return  lstDTO;

    }

    // Insert a post into the database
    public long addItem(TodoListDTO item) {
        // Create and/or open the database for writing
        db=databaseSQLite.writeDatabase();
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        long id=-1;
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            ContentValues values = new ContentValues();
            values.put(Define.KEY_TODOLIST_TITLES, item.get_titles());
            values.put(Define.KEY_TODOLIST_DESCRIPTION, item.get_description());
            values.put(Define.KEY_TODOLIST_LEVEL, item.get_level());
            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            id=db.insertOrThrow(Define.TABLE_TODOLIST_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
            db.close();
        }
        return id;
    }

    // Update a post into the database
    public boolean updateItem(TodoListDTO item) {
        // Create and/or open the database for writing
        db=databaseSQLite.writeDatabase();
        boolean bFlag=false;
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            ContentValues values = new ContentValues();
            values.put(Define.KEY_TODOLIST_TITLES, item.get_titles());
            values.put(Define.KEY_TODOLIST_DESCRIPTION, item.get_description());
            values.put(Define.KEY_TODOLIST_LEVEL, item.get_level());
            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.update(Define.TABLE_TODOLIST_NAME, values, Define.KEY_TODOLIST_ID + " = ?", new String[]{String.valueOf(item.get_id())});
            db.setTransactionSuccessful();
            bFlag=true;
        } catch (Exception e) {
            bFlag=false;
        } finally {
            db.endTransaction();
            db.close();
        }
        return bFlag;
    }

    public boolean deleteItem(TodoListDTO item) {
        db=databaseSQLite.writeDatabase();
        boolean bFlag=false;
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(Define.TABLE_TODOLIST_NAME, Define.KEY_TODOLIST_ID + " = ?", new String[]{String.valueOf(item.get_id())});
            db.setTransactionSuccessful();
            bFlag=true;
        } catch (Exception e) {
            bFlag= false;
        } finally {
            db.endTransaction();
            db.close();
        }
        return bFlag;
    }

}

