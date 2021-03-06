package com.liyonglin.accounts.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liyonglin.accounts.R;
import com.liyonglin.accounts.activity.AccountAddActivity;
import com.liyonglin.accounts.activity.KeyBoardBaseActivity;

import java.util.List;

/**
 * Created by 永霖 on 2016/8/1.
 */
public class KeyboardUtils {

    private Context mContext;
    private Activity mActivity;
    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private TextView mTextView;
    private LinearLayout ll_showkeyboard;

    private boolean isRight;
    private boolean isFirst;



    private List<Keyboard.Key> keys;
    private int mHeight;

    public KeyboardUtils(Context context, Activity activity, TextView textView, LinearLayout ll_showkeyboard) {
        mContext = context;
        mActivity = activity;
        mTextView = textView;
        this.ll_showkeyboard = ll_showkeyboard;
        mHeight = this.ll_showkeyboard.getHeight();

        WindowManager wm = mActivity.getWindowManager();
        Display dp = wm.getDefaultDisplay();
        mHeight = dp.getHeight();

        mKeyboard = new Keyboard(mContext, R.xml.keyboard);
        keys = mKeyboard.getKeys();
        mKeyboardView = (KeyboardView) mActivity.findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(listener);
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            String text = mTextView.getText().toString();
            String texts[] = text.split("\\.");
            String newText = null;
            if (primaryCode == Keyboard.KEYCODE_DELETE) { // 回退
                if (!texts[1].equals("00")) {
                    char c1 = texts[1].charAt(1);
                    char c0 = texts[1].charAt(0);
                    if (c1 != '0') {
                        texts[1] = c0 + "0";
                    } else {
                        if (c0 != '0') {
                            texts[1] = "00";
                            isFirst = true;
                            if (texts[0].equals("0")) {
                                isRight = false;
                            }
                        }
                    }
                } else {
                    int textLength = texts[0].length();
                    if (textLength == 1) {
                        texts[0] = "0";
                    } else {
                        texts[0] = texts[0].substring(0, textLength - 1);
                    }
                    isRight = false;
                }
            } else if (primaryCode == 4896) {  //清空
                mTextView.setText("0.00");
                isRight = false;
                return;
            } else if (primaryCode == 43) {  //加法
                Toast.makeText(mContext, mContext.getString(R.string.noOpen), Toast.LENGTH_SHORT).show();
            } else if (primaryCode == 45) {  //减法
                Toast.makeText(mContext, mContext.getString(R.string.noOpen), Toast.LENGTH_SHORT).show();
            } else if (primaryCode == Keyboard.KEYCODE_DONE) {  //OK
                ((KeyBoardBaseActivity)mActivity).OK();
            } else { // 输入键盘值
                String key = Character.toString((char) primaryCode);
                if (primaryCode == 46) {
                    isRight = true;
                    if (texts[1].charAt(0) == '0')
                        isFirst = true;
                    return;
                }
                if (isRight) {
                    if (texts[1].charAt(1) != '0') {
                        return;
                    }
                    if (isFirst) {
                        texts[1] = key + "0";
                        isFirst = false;
                    } else {
                        texts[1] = texts[1].charAt(0) + key;
                    }
                } else {
                    if (texts[0].equals("0")) {
                        texts[0] = key;
                    } else {
                        texts[0] += key;
                    }
                }
            }
            newText = texts[0] + "." + texts[1];
            mTextView.setText(newText);
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeUp() {
        }
    };

    public void hideKeyboard() {
        int visibility = ll_showkeyboard.getVisibility();
        if (visibility == View.VISIBLE) {
            TranslateAnimation ta1 = new TranslateAnimation(0.0f, 0.0f,
                    0, mHeight);
            ta1.setDuration(500);
            ta1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    ll_showkeyboard.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            ll_showkeyboard.startAnimation(ta1);
        }
    }

    public void showKeyboard() {
        int visibility = ll_showkeyboard.getVisibility();
        if (visibility == View.GONE) {
            ll_showkeyboard.setVisibility(View.VISIBLE);
            TranslateAnimation ta = new TranslateAnimation(0.0f, 0.0f,mHeight, 0);
            ta.setDuration(500);
            ll_showkeyboard.startAnimation(ta);
        }
    }

}
