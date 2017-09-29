package cn.edots.nest.ui;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edots.nest.R;
import cn.edots.nest.SlugResourceProvider;

/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */
public abstract class TitleBarActivity extends BaseActivity {

    protected AppBarLayout appBarLayout;
    protected Toolbar toolbar;
    protected ImageView backButton;
    protected TextView leftTitle;
    protected TextView centerTitle;
    protected ImageView rightButton;
    protected TextView rightText;
    protected FrameLayout contentLayout;
    private int statusPXHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_base_title_bar);
        initView();
        initListener();
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.title_bar_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        backButton = (ImageView) findViewById(R.id.back_btn);
        leftTitle = (TextView) findViewById(R.id.left_title_text);
        centerTitle = (TextView) findViewById(R.id.center_title_text_view);
        rightButton = (ImageView) findViewById(R.id.right_image_btn);
        rightText = (TextView) findViewById(R.id.right_text_btn);
        contentLayout = (FrameLayout) findViewById(R.id.content_layout);

        if (this.isTranslucentStatus()) {
            statusPXHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));
            this.setLayoutMargins(appBarLayout, 0, statusPXHeight, 0, 0);
        }

        SlugResourceProvider resourceProvider = (SlugResourceProvider) this.getApplication();
        if (resourceProvider == null)
            throw new NullPointerException("this Application has not implements SlugResourceProvider interface");
        setBackButtonImageResource(resourceProvider.getBackButtonImageResource());
    }

    private void initListener() {
        setOnBackButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackToExit())
                    onExit();
                else
                    onBack();
            }
        });
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, contentLayout.getLayoutParams());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getLayoutInflater().inflate(layoutResID, contentLayout);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        contentLayout.removeAllViews();
        contentLayout.addView(view, params);
    }

    protected void setTitleBarColor(@ColorRes int resId) {
        toolbar.setBackgroundColor(THIS.getResources().getColor(resId));
    }

    protected void setBackButtonImageResource(@DrawableRes int resId) {
        backButton.setImageResource(resId);
    }

    protected void setLeftTitleContent(CharSequence title) {
        setLeftTitleContent(title, R.color.default_text_color);
    }

    protected void setLeftTitleContent(CharSequence title, @ColorRes int resId) {
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setText(title);
        leftTitle.setTextColor(THIS.getResources().getColor(resId));
    }

    protected void setCenterTitleContent(CharSequence title) {
        setCenterTitleContent(title, R.color.default_text_color);
    }

    protected void setCenterTitleContent(CharSequence title, @ColorRes int resId) {
        centerTitle.setVisibility(View.VISIBLE);
        centerTitle.setText(title);
        centerTitle.setTextColor(THIS.getResources().getColor(resId));
    }

    protected void setRightButtonImageResource(@DrawableRes int resId) {
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setImageResource(resId);
    }

    protected void setRightButtonListener(View.OnClickListener listener) {
        rightButton.setOnClickListener(listener);
    }

    protected void setRightTextContent(CharSequence text) {
        setRightTextContent(text, R.color.default_text_color);
    }

    protected void setRightTextContent(CharSequence text, @ColorRes int resId) {
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(text);
        rightText.setTextColor(THIS.getResources().getColor(resId));
    }

    protected void setRightTextListener(View.OnClickListener listener) {
        rightText.setOnClickListener(listener);
    }

    /**
     * 返回按钮点击事件
     *
     * @param listener
     */
    protected void setOnBackButtonClickListener(View.OnClickListener listener) {
        backButton.setOnClickListener(listener);
    }

    protected void setLayoutMargins(View v, int left, int top, int right, int bottom) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            v.requestLayout();
        }
    }
}
