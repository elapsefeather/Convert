package feather.convert;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by feather on 2017/6/20.
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    MainActivity main;
    ArrayList<Data> list;
    LayoutInflater inflater;

    String name = "", value = "";
    float exrate = 0, multiple = 1;

    public BaseAdapter(MainActivity main, ArrayList<Data> list) {
        this.main = main;
        this.list = list;
        inflater = main.getLayoutInflater();
    }

    public void setData(ArrayList<Data> list, float mult) {
        this.list = list;
        this.multiple = mult;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversion, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final Data data = list.get(i);

        name = data.getName();
        exrate = data.getExrate();
        DecimalFormat mDecimalFormat = new DecimalFormat("#,###.##");
        value = mDecimalFormat.format(exrate * multiple);

        holder.name.setText(name);
        holder.value.setText(value);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent enter = new Intent(main, EnterDialog.class);
                enter.putExtra("name", data.getName());
                enter.putExtra("item_num", i);
                main.startActivity(enter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, value;
        public LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            layout = (LinearLayout) view.findViewById(R.id.item_layout);
            name = (TextView) view.findViewById(R.id.item_name);
            value = (TextView) view.findViewById(R.id.item_value);

        }
    }
}