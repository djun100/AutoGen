package com.cy.tiangou.guider.act;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.View;
import com.cy.testandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建赠品记录
 */
public class CreateAttachRecordAct extends Activity {

    private List<String> mTabTitles = new ArrayList<>();

    /**
     * “赠品”和“抽奖”两个tab页面
     */
//    private List<CreateAttachTabFra> mFras = new ArrayList<CreateAttachTabFra>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_attach_record);
        initView();
    }

}
