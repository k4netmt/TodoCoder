package Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kanet.todoandroid.R;

import java.util.ArrayList;

import DataProvider.TodoListDTO;

/**
 * Created by Kanet on 2/27/2016.
 */
public class TodoListMainAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TodoListDTO> todoListItems;

    public TodoListMainAdapter(Context context, ArrayList<TodoListDTO> todoListItems){
        this.context = context;
        this.todoListItems = todoListItems;
    }

    @Override
    public int getCount() {
        return todoListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return todoListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_todolistmain, null);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitles);
        TextView tvLevel = (TextView) convertView.findViewById(R.id.tvLevel);

        tvTitle.setText(todoListItems.get(position).get_titles().toString());
        SetLevel(todoListItems.get(position).get_level(),tvLevel);
        return convertView;
    }

    private void SetLevel(int level,TextView tvLevel)
    {
        switch (level)
        {
            case 0:
            {
                tvLevel.setText("LOW");
                tvLevel.setTextColor(context.getResources().getColor(R.color.levelLow));
                break;
            }
            case 1:
            {
                tvLevel.setText("MEDIUM");
                tvLevel.setTextColor(context.getResources().getColor(R.color.levelMedium));
                break;
            }
            case 2:
            {
                tvLevel.setText("HIGH");
                tvLevel.setTextColor(context.getResources().getColor(R.color.levelHigh));
                break;
            }
        }
    }
}
