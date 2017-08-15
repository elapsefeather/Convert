package feather.convert;

import android.widget.Toast;

/**
 * Created by feather on 2017/6/20.
 */

public class Presenter {

    public MainActivity view;
    public Model model;
    public BaseAdapter adapter;
    float mult;
    Data datalist;
    String title_conversion = "";

    public Presenter(MainActivity main) {
        view = main;
        model = new Model(this);
        adapter = new BaseAdapter(view, model.list);
        title_conversion = view.getResources().getString(R.string.title_conversion);
    }

    public void getData() {
        model.getData();
    }

    public void notifyData(float mult, String name) {
//        排序
        sort(name);//改變選擇項排序
        sort(title_conversion);//使台幣置頂
//        乘上比率顯示
        adapter.setData(model.list, mult);
        adapter.notifyDataSetChanged();
    }

    public void countMult(float count, int i) {

//        計算倍數
        mult = count / model.list.get(i).getExrate();
//        獲取排序用名稱
        String name = model.list.get(i).getName();
//        更新
        notifyData(mult, name);

    }

    public void sort(String name) {
        int check = 0;
        for (int i = 0; i < model.list.size(); i++) {
            datalist = model.list.get(i);
            if (datalist.getName().equals(name)) {

                Data twcdata = new Data();
                twcdata.setName(datalist.getName());
                twcdata.setExrate(datalist.getExrate());
                model.list.add(twcdata);
//                刪除原有幣項
                model.list.remove(i);
                check = 1;
                break;
            }
        }
        if (check == 0) {
            Toast.makeText(view, "查無此項", Toast.LENGTH_SHORT).show();
        }
    }

}
