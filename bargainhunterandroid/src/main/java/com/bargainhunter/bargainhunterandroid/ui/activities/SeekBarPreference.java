package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.content.Context;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.R;

/**
 * Created by Veruz on 9/12/2014.
 */
public class SeekBarPreference extends Preference implements OnSeekBarChangeListener {

    private final String TAG = getClass().getName();

    private int mMaxValue = 100;
    private int mMinValue = 1;
    private int mInterval = 1;
    private int mCurrentValue;
    private SeekBar mSeekBar;

    private TextView mStatusText;

    public SeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPreference(context, attrs);
    }

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPreference(context, attrs);
    }

    private void initPreference(Context context, AttributeSet attrs) {
        mSeekBar = new SeekBar(context, attrs);
        mSeekBar.setMax(mMaxValue - mMinValue);
        mSeekBar.setOnSeekBarChangeListener(this);

        setWidgetLayoutResource(R.layout.seek_bar_preference);
    }

    @Override
    public void onBindView(@NonNull View view) {
        super.onBindView(view);

        try {
            // move our seekbar to the new view we've been given
            ViewParent oldContainer = mSeekBar.getParent();
            ViewGroup newContainer = (ViewGroup) view.findViewById(R.id.seekBarPrefBarContainer);

            if (oldContainer != newContainer) {
                // remove the seekbar from the old view
                if (oldContainer != null) {
                    ((ViewGroup) oldContainer).removeView(mSeekBar);
                }
                // remove the existing seekbar (there may not be one) and add ours
                newContainer.removeAllViews();
                newContainer.addView(mSeekBar, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error binding view: " + ex.toString());
        }

        //if dependency is false from the beginning, disable the seek bar
        if (view != null && !view.isEnabled()) {
            mSeekBar.setEnabled(false);
        }

        updateView(view);
    }

    /**
     * Update a SeekBarPreference view with our current state
     */
    protected void updateView(View view) {

        try {
            mStatusText = (TextView) view.findViewById(R.id.seekBarPrefValue);

            mStatusText.setText(String.valueOf(mCurrentValue));
            mStatusText.setMinimumWidth(30);

            mSeekBar.setProgress(mCurrentValue - mMinValue);


        } catch (Exception e) {
            Log.e(TAG, "Error updating seek bar preference", e);
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int newValue = progress + mMinValue;

        if (newValue > mMaxValue)
            newValue = mMaxValue;
        else if (newValue < mMinValue)
            newValue = mMinValue;
        else if (mInterval != 1 && newValue % mInterval != 0)
            newValue = Math.round(((float) newValue) / mInterval) * mInterval;

        // change rejected, revert to the previous value
        if (!callChangeListener(newValue)) {
            seekBar.setProgress(mCurrentValue - mMinValue);
            return;
        }

        // change accepted, store it
        mCurrentValue = newValue;
        mStatusText.setText(String.valueOf(newValue));
        persistInt(newValue);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        notifyChanged();
    }


    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {

        if (restoreValue) {
            mCurrentValue = getPersistedInt(mCurrentValue);
        } else {
            int temp = 0;
            try {
                temp = (Integer) defaultValue;
            } catch (Exception ex) {
                Log.e(TAG, "Invalid default value: " + defaultValue.toString());
            }

            persistInt(temp);
            mCurrentValue = temp;
        }

    }

    /**
     * make sure that the seekbar is disabled if the preference is disabled
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mSeekBar.setEnabled(enabled);
    }

    @Override
    public void onDependencyChanged(Preference dependency, boolean disableDependent) {
        super.onDependencyChanged(dependency, disableDependent);

        //Disable movement of seek bar when dependency is false
        if (mSeekBar != null) {
            mSeekBar.setEnabled(!disableDependent);
        }
    }
}