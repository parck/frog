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

    public static final int _18SP = R.dimen.text_size_18_sp;
    public static final int _17SP = R.dimen.text_size_17_sp;
    public static final int _16SP = R.dimen.text_size_16_sp;
    public static final int _15SP = R.dimen.text_size_15_sp;
    public static final int _14SP = R.dimen.text_size_14_sp;
    public static final int _13SP = R.dimen.text_size_13_sp;
    public static final int _12SP = R.dimen.text_size_12_sp;
    public static final int _11SP = R.dimen.text_size_11_sp;
    public static final int _10SP = R.dimen.text_size_10_sp;

    protected Toolbar toolbar;
    protected ImageView leftButton;
    protected TextView leftText;
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
        leftButton = (ImageView) findViewById(R.id.left_button);
        leftText = (TextView) findViewById(R.id.left_button_text);
        leftTitle = (TextView) findViewById(R.id.left_title_text);
        centerTitle = (TextView) findViewById(R.id.center_title_text_view);
        rightButton = (ImageView) findViewById(R.id.right_image_btn);
        rightText = (TextView) findViewById(R.id.right_text_btn);
        contentLayout = (FrameLayout) findViewById(R.id.content_layout);

        if (isHideBackButton()) {
            leftButton.setVisibility(View.GONE);
        }

        try {
            SlugResourceProvider resourceProvider = (SlugResourceProvider) this.getApplication();
            setLeftButtonImageResource(resourceProvider.getBackButtonImageResource());
        } catch (ClassCastException e) {
            throw new ClassCastException("This application has not implements \"SlugResourceProvider\"");
        }
    }

    private void initListener() {
        setOnLeftButtonClickListener(new View.OnClickListener() {
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

    //===============================================================
    // 重载方法
    //===============================================================

    /*设置左边图片*/
    protected void setLeftButtonImageResource(@DrawableRes int resId) {
        leftButton.setImageResource(resId);
    }

    protected void setOnLeftButtonClickListener(View.OnClickListener listener) {
        leftButton.setOnClickListener(listener);
    }
    /*设置左边图片**/

    /*设置左边text**/
    protected void setLeftTextContent(CharSequence text) {
        setLeftTextContent(text, R.color.default_text_color);
    }

    protected void setLeftTextContent(CharSequence text, @ColorRes int resId) {
        setLeftTextContent(text, resId, _14SP);
    }

    protected void setLeftTextContent(CharSequence text, @ColorRes int cresId, @DimenRes int dresId) {
        leftButton.setVisibility(View.GONE);
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setText(text);
        leftTitle.setTextSize(getResources().getDimension(dresId));
        leftTitle.setTextColor(THIS.getResources().getColor(cresId));
    }

    protected void setOnLeftTextButtonClickListener(View.OnClickListener listener) {
        leftText.setOnClickListener(listener);
    }
    /*设置左边text*/

    /*设置左边title*/
    protected void setLeftTitleContent(CharSequence title) {
        setLeftTitleContent(title, R.color.default_text_color);
    }

    protected void setLeftTitleContent(CharSequence title, @ColorRes int resId) {
        setLeftTitleContent(title, resId, _16SP);
    }

    protected void setLeftTitleContent(CharSequence title, @ColorRes int cresId, @DimenRes int dresId) {
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setText(title);
        leftTitle.setTextSize(getResources().getDimension(dresId));
        leftTitle.setTextColor(THIS.getResources().getColor(cresId));
    }
    /*设置左边title*/

    /*设置中间title*/
    protected void setCenterTitleContent(CharSequence title) {
        setCenterTitleContent(title, R.color.default_text_color);
    }

    protected void setCenterTitleContent(CharSequence title, @ColorRes int resId) {
        setCenterTitleContent(title, resId, _16SP);
    }

    protected void setCenterTitleContent(CharSequence title, @ColorRes int cresId, @DimenRes int dresId) {
        centerTitle.setVisibility(View.VISIBLE);
        centerTitle.setText(title);
        centerTitle.setTextColor(THIS.getResources().getColor(cresId));
        centerTitle.setTextSize(THIS.getResources().getDimension(dresId));
    }
    /*设置中间title*/


    /*设置右边图片*/
    protected void setRightButtonImageResource(@DrawableRes int resId) {
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setImageResource(resId);
    }

    protected void setOnRightButtonListener(View.OnClickListener listener) {
        rightButton.setOnClickListener(listener);
    }
    /*设置右边图片*/

    /*设置右边text*/
    protected void setRightTextContent(CharSequence text) {
        setRightTextContent(text, R.color.default_text_color);
    }

    protected void setRightTextContent(CharSequence text, @ColorRes int resId) {
        setRightTextContent(text, R.color.default_text_color, _14SP);
    }

    protected void setRightTextContent(CharSequence text, @ColorRes int cresId, @DimenRes int dresId) {
        rightButton.setVisibility(View.GONE);
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(text);
        rightText.setTextColor(THIS.getResources().getColor(cresId));
        rightText.setTextSize(THIS.getResources().getDimension(dresId));
    }

    protected void setOnRightTextListener(View.OnClickListener listener) {
        rightText.setOnClickListener(listener);
    }
    /*设置右边text*/

    protected boolean isHideBackButton() {
        return false;
    }
}
