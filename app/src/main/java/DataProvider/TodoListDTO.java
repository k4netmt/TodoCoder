package DataProvider;

import android.database.Cursor;

import java.io.Serializable;

/**
 * Created by Kanet on 2/27/2016.
 */
public class TodoListDTO implements Serializable {
    private int _id;
    private String _titles;
    private String _description;
    private int _level;
    private static final long serialVersionUID = 46543445;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_titles() {
        return _titles;
    }

    public void set_titles(String _titles) {
        this._titles = _titles;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_level() {
        return _level;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public TodoListDTO(int _id, String _titles, String _description, int _level) {
        this._id = _id;
        this._titles = _titles;
        this._description = _description;
        this._level = _level;
    }

    public TodoListDTO() {
        this._id = 0;
        this._titles = "";
        this._description = "";
        this._level = 0;
    }

    public enum TB_TODOLIST
    {
        TODOLIST_ID,TODOLIST_TITLES,TODOLIST_DESCRIPTION,TODOLIST_LEVEL
    }

    public TodoListDTO(Cursor cursor) {
        _id = cursor.getInt(getIndex(TB_TODOLIST.TODOLIST_ID));
        _titles = cursor.getString(getIndex(TB_TODOLIST.TODOLIST_TITLES));
        _description = cursor.getString(getIndex(TB_TODOLIST.TODOLIST_DESCRIPTION));
        _level=cursor.getInt(getIndex(TB_TODOLIST.TODOLIST_LEVEL));
    }

    public int getIndex(TB_TODOLIST tb)
    {
        switch (tb)
        {
            case TODOLIST_ID: return Define.INDEX_TODOLIST_ID;
            case TODOLIST_TITLES: return Define.INDEX_TODOLIST_TITLES;
            case TODOLIST_DESCRIPTION: return Define.INDEX_TODOLIST_DESCRIPTION;
            case TODOLIST_LEVEL: return Define.INDEX_TODOLIST_LEVEL;
        }
        return -1;
    }
}
