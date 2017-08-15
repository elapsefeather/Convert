package feather.convert;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.greenrobot.event.EventBus;

public class EnterDialog extends Activity implements View.OnClickListener {

    TextView title, cencel, check;
    EditText edit;

    String name = "", content = "";
    float count = 0;
    int item_num = 0;

    private EventBus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_enter);

        mEventBus = EventBus.getDefault();

        name = getIntent().getStringExtra("name");
        item_num = getIntent().getIntExtra("item_num", 0);

        init();

        content = "你選擇了 " + name + "\n請輸入金額";
        title.setText(content);

    }

    public void init() {

        title = (TextView) findViewById(R.id.enterdialog_title);
        cencel = (TextView) findViewById(R.id.enterdialog_cencel);
        cencel.setOnClickListener(this);
        check = (TextView) findViewById(R.id.enterdialog_check);
        check.setOnClickListener(this);
        edit = (EditText) findViewById(R.id.enterdialog_edit);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.enterdialog_cencel:
                finish();
                break;
            case R.id.enterdialog_check:

                count = Float.parseFloat(edit.getText().toString());

                MultEeventBus multbus = new MultEeventBus();
                multbus.setCount(item_num);
                multbus.setItem_num(count);
                mEventBus.post(multbus);

                finish();
                break;
        }

    }
}
