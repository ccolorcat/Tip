/*
 * Copyright 2018 cxx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.colorcat.tip;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: cxx
 * Date: 2018-07-05
 * GitHub: https://github.com/ccolorcat
 */
public final class Tip {
    public static Tip from(Activity activity, @LayoutRes int tipLayout, OnTipClickListener listener) {
        ViewGroup parent = activity.findViewById(android.R.id.content);
        return new Tip(parent, parent.getChildAt(0), tipLayout, listener);
    }

    public static Tip from(android.app.Fragment fragment, @LayoutRes int tipLayout, OnTipClickListener listener) {
        return from(fragment.getView(), tipLayout, listener);
    }

    public static Tip from(Fragment fragment, @LayoutRes int tipLayout, OnTipClickListener listener) {
        return from(fragment.getView(), tipLayout, listener);
    }

    public static Tip from(View content, @LayoutRes int tipLayout, OnTipClickListener listener) {
        ViewGroup parent = (ViewGroup) content.getParent();
        if (parent == null) {
            throw new NullPointerException("The specified content view must have a parent.");
        }
        return new Tip(parent, content, tipLayout, listener);
    }

    private final ViewGroup mParent;
    private final View mContent;
    @LayoutRes
    private final int mTipLayout;
    private OnTipClickListener mListener;

    private View mTipView;
    private int mIndex;
    private boolean mShowing;

    private Tip(ViewGroup parent, View content, @LayoutRes int tipLayout, OnTipClickListener listener) {
        mParent = parent;
        mContent = content;
        mTipLayout = tipLayout;
        mListener = listener;
        mShowing = false;
    }

    public void showTip() {
        final View tipView = getTipView();
        if (!mShowing && tipView.getParent() == null) {
            mIndex = mParent.indexOfChild(mContent);
            if (mIndex == -1) {
                throw new IllegalStateException("content view has been removed from parent");
            }
            mParent.removeViewAt(mIndex);
            mParent.addView(tipView, mIndex, mContent.getLayoutParams());
            mShowing = true;
        }
    }

    public void hideTip() {
        if (mShowing && mContent.getParent() == null) {
            mIndex = mParent.indexOfChild(mTipView);
            if (mIndex == -1) {
                throw new IllegalStateException("tip view has been removed from parent");
            }
            mParent.removeViewAt(mIndex);
            mParent.addView(mContent, mIndex);
            mShowing = false;
        }
    }

    public boolean isShowing() {
        return mShowing;
    }

    public void setOnTipClickListener(OnTipClickListener listener) {
        mListener = listener;
    }

    public OnTipClickListener getOnTipClickListener() {
        return mListener;
    }

    private View getTipView() {
        if (mTipView == null) {
            Context context = mContent.getContext();
            mTipView = LayoutInflater.from(context).inflate(mTipLayout, mParent, false);
            int clickId = context.getResources().getIdentifier("tip", "id", context.getPackageName());
            if (clickId > 0) {
                View click = mTipView.findViewById(clickId);
                if (click != null) {
                    click.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mListener != null) {
                                mListener.onTipClick();
                            }
                        }
                    });
                }
            }
        }
        return mTipView;
    }


    public interface OnTipClickListener {
        void onTipClick();
    }
}
