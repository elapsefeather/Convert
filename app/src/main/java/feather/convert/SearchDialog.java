package feather.convert;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.greenrobot.event.EventBus;

public class SearchDialog extends Activity implements View.OnClickListener {

    TextView title, cencel, check;
    EditText edit;

    String content = "";

    private EventBus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search);

        mEventBus = EventBus.getDefault();

        init();

        content = "請問要找尋哪種幣值？\n（請輸入幣別代碼）";
        title.setText(content);

    }

    public void init() {

        title = (TextView) findViewById(R.id.SearchDialog_title);
        cencel = (TextView) findViewById(R.id.SearchDialog_cencel);
        cencel.setOnClickListener(this);
        check = (TextView) findViewById(R.id.SearchDialog_check);
        check.setOnClickListener(this);
        edit = (EditText) findViewById(R.id.SearchDialog_edit);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.SearchDialog_cencel:
                finish();
                break;
            case R.id.SearchDialog_check:

                SearchEventBus searchbus = new SearchEventBus();
                searchbus.setSearchname(edit.getText().toString().trim());
                mEventBus.post(searchbus);

                finish();
                break;
        }
    }

}
