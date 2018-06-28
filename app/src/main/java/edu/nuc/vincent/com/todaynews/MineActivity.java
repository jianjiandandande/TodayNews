package edu.nuc.vincent.com.todaynews;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MineActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        mBack = (ImageView)findViewById(R.id.mine_back);
        mBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.mine_back:
                finish();
                break;

        }

    }
}
