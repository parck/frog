package cn.edots.nest.ui;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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

    public static final int _18DP = R.dimen.text_size_18_dp;
    public static final int _17DP = R.dimen.text_size_17_dp;
    public static final int _16DP = R.dimen.text_size_16_dp;
    public static final int _15DP = R.dimen.text_size_15_dp;
    public static final int _14DP = R.dimen.text_size_14_dp;
    public static final int _13DP = R.dimen.text_size_13_dp;
    public static final int _12DP = R.dimen.text_size_12_dp;
    public static final int _11DP = R.dimen.text_size_11_dp;
    public static final int _10DP = R.dimen.text_size_10_dp;

    protected Toolbar toolbar;
    protected ImageView backButton;
    protected TextView leftTitle;
    protected TextView centerTitle;
    protected ImageView rightButton;
    protected TextView rightText;
    protected FrameLayout contentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_base_title_bar);
        initView();
        initListener();
        setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        backButton = (ImageView) findViewById(R.id.back_btn);
        leftTitle = (TextView) findViewById(R.id.left_title_text);
        centerTitle = (TextView) findViewById(R.id.center_title_text_view);
        rightButton = (ImageView) findViewById(R.id.right_image_btn);
        rightText = (TextView) findViewById(R.id.right_text_btn);
        contentLayout = (FrameLayout) findViewById(R.id.content_layout);
        try {
            SlugResourceProvider resourceProvider = (SlugResourceProvider) this.getApplication();
            setBackButtonImageResource(resourceProvider.getBackButtonImageResource());
        } catch (ClassCastException e) {
            throw new ClassCastException("This application has not implements \"SlugResourceProvider\"");
        }
    }

    private void initListener() {
        setOnBackButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackToExit()) onExit();
                else onBack();
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
        setLeftTitleContent(title, resId, _16DP);
    }

    protected void setLeftTitleContent(CharSequence title, @ColorRes int cresId, @DimenRes int dresId) {
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setText(title);
        leftTitle.setTextSize(getResources().getDimension(dresId));
        leftTitle.setTextColor(THIS.getResources().getColor(cresId));
    }

    protected void setCenterTitleContent(CharSequence title) {
        setCenterTitleContent(title, R.color.default_text_color);
    }

    protected void setCenterTitleContent(CharSequence title, @ColorRes int resId) {
        setCenterTitleContent(title, resId, _16DP);
    }

    protected void setCenterTitleContent(CharSequence title, @ColorRes int cresId, @DimenRes int dresId) {
        centerTitle.setVisibility(View.VISIBLE);
        centerTitle.setText(title);
        centerTitle.setTextColor(THIS.getResources().getColor(cresId));
        centerTitle.setTextSize(THIS.getResources().getDimension(dresId));
    }

    protected void setRightButtonImageResource(@DrawableRes int resId) {
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setImageResource(resId);
    }

    protected void setOnRightButtonListener(View.OnClickListener listener) {
        rightButton.setOnClickListener(listener);
    }

    protected void setRightTextContent(CharSequence text) {
        setRightTextContent(text, R.color.default_text_color);
    }

    protected void setRightTextContent(CharSequence text, @ColorRes int resId) {
        setRightTextContent(text, R.color.default_text_color, _14DP);
    }

    protected void setRightTextContent(CharSequence text, @ColorRes int cresId, @DimenRes int dresId) {
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(text);
        rightText.setTextColor(THIS.getResources().getColor(cresId));
        rightText.setTextSize(THIS.getResources().getDimension(dresId));
    }

    protected void setOnRightTextListener(View.OnClickListener listener) {
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
}
