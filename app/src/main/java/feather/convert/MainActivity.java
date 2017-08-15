package feather.convert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {
    Presenter presenter;
    RecyclerView recyclerView;
    ImageView search;
    String title_conversion = "";
    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {

        presenter = new Presenter(this);
        recyclerView = (RecyclerView) findViewById(R.id.main_RecyclerView);

        title_conversion = getResources().getString(R.string.title_conversion);

        search = (ImageView) findViewById(R.id.title_Search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enter = new Intent(MainActivity.this, SearchDialog.class);
                startActivity(enter);
            }
        });

        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        layout.setReverseLayout(true);//列表翻转
        recyclerView.setLayoutManager(layout);

        recyclerView.setAdapter(presenter.adapter);
        presenter.getData();
    }

    public void onEventMainThread(MultEeventBus multbus) {

        presenter.countMult(multbus.getItem_num(), multbus.getCount());

    }

    public void onEventMainThread(SearchEventBus searchbus) {

        presenter.sort(searchbus.getSearchname());
        presenter.sort(title_conversion);//使台幣置頂
        presenter.adapter.notifyDataSetChanged();

    }

    public void onStart() {
        super.onStart();

        eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    public void onDestroy() {
        super.onDestroy();

        eventBus.unregister(this);
    }
}
