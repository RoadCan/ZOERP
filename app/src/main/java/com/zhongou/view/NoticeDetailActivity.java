package com.zhongou.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongou.R;
import com.zhongou.base.BaseActivity;
import com.zhongou.common.MyException;
import com.zhongou.dialog.Loading;
import com.zhongou.helper.UserHelper;
import com.zhongou.inject.ViewInject;
import com.zhongou.model.NoticeListModel;

/**
 * 公告详情
 * Created by sjy on 2017/2/25.
 */

public class NoticeDetailActivity extends BaseActivity {
    //back
    @ViewInject(id = R.id.layout_back, click = "forBack")
    RelativeLayout layout_back;

    //
    @ViewInject(id = R.id.tv_title)
    TextView tv_title;

    //
    @ViewInject(id = R.id.tv_right)
    TextView tv_right;

    //通知标题
    @ViewInject(id = R.id.tv_notice_title)
    TextView tv_notice_title;

    //时间
    @ViewInject(id = R.id.tv_time)
    TextView tv_time;

    //发件人
    @ViewInject(id = R.id.tv_to)
    TextView tv_name;

    //内容
    @ViewInject(id = R.id.tv_content)
    TextView tv_content;


    private NoticeListModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apps_notification_notice_detail_common);
        initMyView();
        setShow();
    }

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.msg_noticeTitle));
        tv_right.setText("");
        //获取跳转值
        Bundle bundle = getIntent().getExtras();
        model = (NoticeListModel) bundle.getSerializable("NoticeListModel");
        readThisNotice(model);
    }

    private void setShow() {
        tv_notice_title.setText(model.getApplicationTitle());
        tv_time.setText(model.getPublishTime());
        tv_name.setText(model.getPublishDeptName()+"\t\t"+model.getEmployeeName());
        tv_content.setText(model.getAbstract());
    }

    private void readThisNotice(final NoticeListModel model){
        Loading.noDialogRun(this, new Runnable() {
            @Override
            public void run() {
                try {
                    UserHelper.postReadThisNotice(NoticeDetailActivity.this
                            ,model.getApplicationID());
                    Log.d("SJY", "成功");
                } catch (MyException e) {
                    e.printStackTrace();
                    Log.d("SJY", "已读异常="+e.getMessage());
                }
            }
        });
    }
    /**
     * back
     *
     * @param view
     */
    public void forBack(View view) {
        this.finish();
    }
}
