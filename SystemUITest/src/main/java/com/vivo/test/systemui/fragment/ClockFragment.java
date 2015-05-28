package com.vivo.test.systemui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;

import com.vivo.test.systemui.R;
import com.vivo.test.systemui.utils.BaseTools;
import com.vivo.test.systemui.utils.Constants;


public class ClockFragment extends Fragment {

    private int mHours = 0;
    private int mMinute = 0;
    private CheckBox mCheckBox;
    public ClockFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_clock, container, false);

        TimePicker timePicker = (TimePicker)layout.findViewById(R.id.timePicker);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mMinute = minute;
                mHours = hourOfDay;
            }
        });

        mCheckBox = (CheckBox)layout.findViewById(R.id.hour_type);
        Button button = (Button)layout.findViewById(R.id.clock_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putCharSequence(Constants.COMMAND,Constants.COMMAND_CLOCK);
                if (mCheckBox.isChecked()){
                    bundle.putCharSequence("millis",Integer.toString(mMinute));
                    bundle.putCharSequence("hhmm",Integer.toString(mHours));
                } else {
                    bundle.putCharSequence("millis",Integer.toString(mMinute + mHours * 60));
                }
                BaseTools.sendBroadcast(ClockFragment.this.getActivity(),bundle);
            }
        });

        return layout;
    }
}
