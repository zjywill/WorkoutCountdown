package com.comic.workoutcountdown;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishDialog extends Dialog {

    private Context mCtx;

    private TextView main_text;
    private Button btn_ok;

    private int mTextId;

    public FinishDialog(Context context, int textId) {
        super(context, R.style.Theme_dialog);
        mCtx = context;
        mTextId = textId;
    }

    public void setContentText(int textId) {
        mTextId = textId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stop_dialog);

        main_text = (TextView) findViewById(R.id.dialog_text_content);
        main_text.setText(mTextId);

        btn_ok = (Button) findViewById(R.id.dialog_ok_button);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountdownActivity) mCtx).finish();
            }
        });
    }

}
