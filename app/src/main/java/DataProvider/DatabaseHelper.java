package DataProvider;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {


    //Table Syntax_Syntax
    private Context context;
    SQLiteDatabase database;
    //Todo table create statement
    private static final String CREATE_TABLE_SYNTAX_SYNTAX = "CREATE TABLE " + Define.TABLE_TODOLIST_NAME
            +" ("+ Define.KEY_TODOLIST_ID+" INTEGER PRIMARY KEY  NOT NULL  DEFAULT (null) "
            +", "+Define.KEY_TODOLIST_TITLES+" TEXT DEFAULT (null) "
            +", "+Define.KEY_TODOLIST_DESCRIPTION+" TEXT DEFAULT (null) "
            +", "+Define.KEY_TODOLIST_LEVEL+" INTEGER DEFAULT (null) )";
    private String pathDatabase;


    // data/data/com.checongbinh.appkaraokelist/database/SongDB;
    public DatabaseHelper(Context context) {
        super(context, Define.DATASE_NAME, null, 1);
        this.context = context;
        pathDatabase = context.getFilesDir().getParent() + "/databases/" + Define.DATASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE_SYNTAX_SYNTAX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    public SQLiteDatabase writeDatabase(){
        return SQLiteDatabase.openDatabase(pathDatabase, null, SQLiteDatabase.OPEN_READWRITE );
    }

    public SQLiteDatabase readDatabase(){
        return SQLiteDatabase.openDatabase(pathDatabase, null, SQLiteDatabase.OPEN_READONLY);
    }

    public void createDatabase(){
        boolean kt = KiemTraDB();
        if(kt){
            Log.d("KetNoi", "Máy đã có database");
        }else{
            Log.d("KetNoi", "Máy chưa có database tiến hành copy dữ liệu");
            this.getWritableDatabase();
        }
    }


    public boolean KiemTraDB(){
        SQLiteDatabase kiemTraDB = null;
        try{
            kiemTraDB = SQLiteDatabase.openDatabase(pathDatabase, null, SQLiteDatabase.OPEN_READONLY);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(kiemTraDB !=null){
            kiemTraDB.close();
        }
        return kiemTraDB !=null ? true : false;
    }

}

