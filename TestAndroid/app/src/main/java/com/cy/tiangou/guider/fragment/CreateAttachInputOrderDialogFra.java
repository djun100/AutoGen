package com.cy.tiangou.guider.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cy.testandroid.R;

/**
 * Created by wangxuechao on 2017/5/25.
 */
public class CreateAttachInputOrderDialogFra extends Fragment {

    private TextView mtvTitle;
    private RelativeLayout mrl_close;
    private RelativeLayout mrl_money;
    private RelativeLayout mrlEt;
    private EditText met;
    private RelativeLayout mrl_clear;
    private Button mbtn;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fra_attachment_create_order_dialog,null);
    }
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {
        mtvTitle = (TextView)view.findViewById(R.id.mtvTitle);
        mrl_close = (RelativeLayout)view.findViewById(R.id.mrl_close);
        mrl_money = (RelativeLayout)view.findViewById(R.id.mrl_money);
        mrlEt = (RelativeLayout)view.findViewById(R.id.mrlEt);
        met = (EditText)view.findViewById(R.id.met);
        mrl_clear = (RelativeLayout)view.findViewById(R.id.mrl_clear);
        mbtn = (Button)view.findViewById(R.id.mbtn);

    }
}
