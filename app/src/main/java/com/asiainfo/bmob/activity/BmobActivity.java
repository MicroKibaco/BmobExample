package com.asiainfo.bmob.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asiainfo.bmob.R;
import com.asiainfo.bmob.bean.FeadBackBean;

import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 描述:Android Bmob云服务端 创建时间:2/7/17/20:07 作者:小木箱 邮箱:yangzy3@asiainfo.com
 */

public class BmobActivity extends Activity implements View.OnClickListener {

    //用户名名称
    private EditText mEtName;

    //用户问题反馈
    private EditText mEtFeadback;
    private EditText mEtNewName;


    private String mTextNameStr;

    private String mTextNewNameStr;

    private String mTextFeadbackStr;

    //提交服务器的按钮
    private Button mCommit;
    private Button mQuerry;
    private Button mQueryNewName;
    private Button mQueryPush;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmob);
        initView();
        initListener();
        initDatas();
    }


    private void initView() {

        mEtName = (EditText) findViewById(R.id.et_name);
        mEtNewName = (EditText) findViewById(R.id.et_new_name);
        mEtFeadback = (EditText) findViewById(R.id.et_feadback);

        mCommit = (Button) findViewById(R.id.commit);
        mQuerry = (Button) findViewById(R.id.query);
        mQueryNewName = (Button) findViewById(R.id.query_FeadBack);
        mQueryPush = (Button) findViewById(R.id.query_push);

        mTextNameStr = mEtName.getText().toString();
        mTextFeadbackStr = mEtFeadback.getText().toString();
        mTextNewNameStr = mEtNewName.getText().toString();

    }

    private void initListener() {

        mCommit.setOnClickListener(this);
        mQuerry.setOnClickListener(this);
        mQueryNewName.setOnClickListener(this);
        mQueryPush.setOnClickListener(this);

    }

/*    java.lang.UnsatisfiedLinkError: No implementation found for boolean
     cn.bmob.v3.helper.BmobNative.init(android.content.Context, java.lang.String)
     (tried Java_cn_bmob_v3_helper_BmobNative_init and
     Java_cn_bmob_v3_helper_BmobNative_init__Landroid_content_Context_2Ljava_lang_String_2)*/

    private void initDatas() {

        Bmob.initialize(this, getResources().getString(R.string.bmob_id));

        //完成pushSDK的引用
        BmobInstallation.getCurrentInstallation().save();
        BmobPush.startWork(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.commit:

                if (mEtName.equals("") || mEtFeadback.equals("")) {

                    return;

                }

                commitSQL();

                break;

            case R.id.query:

                bmobQuery(v);

                break;
            case R.id.query_FeadBack:
                if (mTextNewNameStr.equals("")) {

                    return;

                }
                bmobQuery(v);

                break;

            case R.id.query_push:

                BmobPushManager<FeadBackBean> bmobPush = new BmobPushManager<>();
                bmobPush.pushMessage("Test");

                break;

            default:
                break;

        }

    }

    private void commitSQL() {
        final FeadBackBean feadBackBean = new FeadBackBean();
        feadBackBean.setName(mTextNameStr);
        feadBackBean.setFeadback(mTextFeadbackStr);

        feadBackBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(BmobActivity.this, "commit success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BmobActivity.this, "commit failure!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bmobQuery(View v) {
        BmobQuery<FeadBackBean> query = new BmobQuery<>();

        if (v.getId() == R.id.query_FeadBack) {
            query.addWhereEqualTo("name", mTextNameStr);
        }
        query.findObjects(new FindListener<FeadBackBean>() {
            @Override
            public void done(List<FeadBackBean> list, BmobException e) {
                if (e == null) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BmobActivity.this);
                    builder.setTitle("Query");
                    String str = "";

                    for (FeadBackBean backBean : list) {

                        str += backBean.getName() + ":" + backBean.getFeadback() + "/n";

                    }

                    builder.setMessage(str);
                    builder.create().show();

                } else {
                    Toast.makeText(BmobActivity.this, "Query failure!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
