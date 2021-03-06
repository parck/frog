package cn.edots.nest.ui;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.edots.nest.R;
import cn.edots.nest.cache.Session;
import cn.edots.nest.model.view.ViewModel;
import cn.edots.nest.ui.fragment.EmptyFragment;

/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */
public abstract class TitleBarActivity<VM extends ViewModel> extends BaseActivity<VM> {

    public static final int _24SP = 24;
    public static final int _23SP = 23;
    public static final int _22SP = 22;
    public static final int _21SP = 21;
    public static final int _20SP = 20;
    public static final int _19SP = 19;
    public static final int _18SP = 18;
    public static final int _17SP = 17;
    public static final int _16SP = 16;
    public static final int _15SP = 15;
    public static final int _14SP = 14;
    public static final int _13SP = 13;
    public static final int _12SP = 12;
    public static final int _11SP = 11;
    public static final int _10SP = 10;

    protected Toolbar toolbar;
    protected RelativeLayout titleLayout;
    protected ImageView leftButton;
    protected TextView leftText;
    protected TextView leftTitle;
    protected TextView centerTitle;
    protected ImageView rightButton;
    protected TextView rightText;
    protected View bottomLine;
    protected FrameLayout contentLayout;
    protected
    @DrawableRes
    int defaultBackIconRes = R.drawable.default_back_icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_base_title_bar);
        initData();
        initView();
        initListener();
        setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = appInfo.metaData;
            if (metaData != null) {
                int resId = metaData.getInt(DEFAULT_BACK_ICON);
                if (resId != 0) defaultBackIconRes = resId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        leftButton = (ImageView) findViewById(R.id.left_button);
        leftText = (TextView) findViewById(R.id.left_button_text);
        leftTitle = (TextView) findViewById(R.id.left_title_text);
        centerTitle = (TextView) findViewById(R.id.center_title_text_view);
        rightButton = (ImageView) findViewById(R.id.right_image_btn);
        rightText = (TextView) findViewById(R.id.right_text_btn);
        bottomLine = findViewById(R.id.bottom_line);
        contentLayout = (FrameLayout) findViewById(R.id.content_layout);

        if (isHideBackButton()) {
            leftButton.setVisibility(View.GONE);
        }

        if (isHideBottomLine()) {
            bottomLine.setVisibility(View.GONE);
        }
        setLeftButtonImageResource(defaultBackIconRes);
    }

    private void initListener() {
        setOnLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackAndExit()) onExit();
                else onBack();
            }
        });

        setOnLeftTextButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackAndExit()) onExit();
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

    public void setTitleBarColor(@ColorRes int resId) {
        toolbar.setBackgroundColor(THIS.getResources().getColor(resId));
    }

    public void setTitleLayoutHeight(@DimenRes int resId) {
        ViewGroup.LayoutParams layoutParams = titleLayout.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(resId);
        titleLayout.setLayoutParams(layoutParams);
    }

    public void setTitleLayoutPixelSizeHeight(int pixel) {
        ViewGroup.LayoutParams layoutParams = titleLayout.getLayoutParams();
        layoutParams.height = pixel;
        titleLayout.setLayoutParams(layoutParams);
    }

    public void setBottomLineShapeResource(@DrawableRes int resId) {
        bottomLine.setBackgroundResource(resId);
    }

    public void showEmpty() {
        showEmpty(0);
    }

    public void showEmpty(@DrawableRes int resId) {
        showEmpty(resId, null);
    }

    public void showEmpty(@DrawableRes int resId, CharSequence text) {
        showEmpty(resId, text, null);
    }

    public void showEmpty(@DrawableRes int resId, CharSequence text, View.OnClickListener listener) {
        EmptyFragment emptyFragment = new EmptyFragment();
        emptyFragment.setResId(resId);
        emptyFragment.setText(text);
        emptyFragment.setOnClickListener(listener);
        Session.setAttribute(EmptyFragment.class.getClass().getSimpleName(), emptyFragment);
        addFragment(R.id.content_layout, (Fragment) Session.getAttribute(EmptyFragment.class.getClass().getSimpleName()));
    }

    public void hideEmpty() {
        removeFragment((Fragment) Session.getAttribute(EmptyFragment.class.getClass().getSimpleName()));
    }

    //===============================================================
    // 重载方法
    //===============================================================

    /*设置左边图片*/
    public void setLeftButtonImageResource(@DrawableRes int resId) {
        leftButton.setImageResource(resId);
    }

    public void setOnLeftButtonClickListener(View.OnClickListener listener) {
        leftButton.setOnClickListener(listener);
    }
    /*设置左边图片**/

    /*设置左边text**/
    public void setLeftTextContent(CharSequence text) {
        setLeftTextContent(text, R.color.default_text_color);
    }

    public void setLeftTextContent(CharSequence text, @ColorRes int resId) {
        setLeftTextContent(text, resId, _16SP);
    }

    public void setLeftTextContent(CharSequence text, @ColorRes int resId, int spSize) {
        leftButton.setVisibility(View.GONE);
        leftText.setVisibility(View.VISIBLE);
        leftText.setText(text);
        leftText.setTextColor(THIS.getResources().getColor(resId));
        leftText.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setOnLeftTextButtonClickListener(View.OnClickListener listener) {
        leftText.setOnClickListener(listener);
    }
    /*设置左边text*/

    /*设置左边title*/
    public void setLeftTitleContent(CharSequence title) {
        setLeftTitleContent(title, R.color.default_text_color);
    }

    public void setLeftTitleContent(CharSequence title, @ColorRes int resId) {
        setLeftTitleContent(title, resId, _18SP);
    }

    public void setLeftTitleContent(CharSequence title, @ColorRes int resId, int spSize) {
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setText(title);
        leftTitle.setTextColor(THIS.getResources().getColor(resId));
        leftTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }
    /*设置左边title*/

    /*设置中间title*/
    public void setCenterTitleContent(CharSequence title) {
        setCenterTitleContent(title, R.color.default_text_color);
    }

    public void setCenterTitleContent(CharSequence title, @ColorRes int resId) {
        setCenterTitleContent(title, resId, _18SP);
    }

    public void setCenterTitleContent(CharSequence title, @ColorRes int resId, int spSize) {
        centerTitle.setVisibility(View.VISIBLE);
        centerTitle.setText(title);
        centerTitle.setTextColor(THIS.getResources().getColor(resId));
        centerTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }
    /*设置中间title*/


    /*设置右边图片*/
    public void setRightButtonImageResource(@DrawableRes int resId) {
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setImageResource(resId);
    }

    public void setOnRightButtonListener(View.OnClickListener listener) {
        rightButton.setOnClickListener(listener);
    }
    /*设置右边图片*/

    /*设置右边text*/
    public void setRightTextContent(CharSequence text) {
        setRightTextContent(text, R.color.default_text_color);
    }

    public void setRightTextContent(CharSequence text, @ColorRes int resId) {
        setRightTextContent(text, resId, _16SP);
    }

    public void setRightTextContent(CharSequence text, @ColorRes int resId, int spSize) {
        rightButton.setVisibility(View.GONE);
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(text);
        rightText.setTextColor(THIS.getResources().getColor(resId));
        rightText.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setOnRightTextListener(View.OnClickListener listener) {
        rightText.setOnClickListener(listener);
    }
    /*设置右边text*/

    public boolean isHideBackButton() {
        return false;
    }

    public boolean isHideBottomLine() {
        return false;
    }
}
