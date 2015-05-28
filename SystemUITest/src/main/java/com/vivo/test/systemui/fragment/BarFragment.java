package com.vivo.test.systemui.fragment;

import android.app.Activity;
import android.app.Notification;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.vivo.test.systemui.R;
import com.vivo.test.systemui.ui.BarTransitions;
import com.vivo.test.systemui.utils.BaseTools;
import com.vivo.test.systemui.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] mTypeArray;
    ArrayAdapter<String> mAdapter;
    private String mMode = "transparent";
    private Spinner mSpinner;
    private BarTransitions mBarTransitions;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarFragment newInstance(String param1, String param2) {
        BarFragment fragment = new BarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_bar, container, false);

        mSpinner = (Spinner)layout.findViewById(R.id.bar_type);
        mTypeArray = getResources().getStringArray(R.array.bar_mode);
        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, mTypeArray);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setVisibility(View.VISIBLE);
        mSpinner.setOnItemSelectedListener(new BarSpinnerSelectedListener());
        mSpinner.setSelected(true);

        View view = layout.findViewById(R.id.bar_result);
        mBarTransitions = new BarTransitions(view,R.drawable.status_background);

        Button button = (Button)layout.findViewById(R.id.bt_bar_mode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putCharSequence(Constants.COMMAND,Constants.COMMAND_BARS);
                bundle.putCharSequence("mode",mMode + "");
                BaseTools.sendBroadcast(getActivity(),bundle);
                transitionTo(mMode);
            }
        });

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    class BarSpinnerSelectedListener implements Spinner.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            switch (arg2){
                case 0:
                    mMode = "opaque";
                    break;
                case 1:
                    mMode = "translucent";
                    break;
                case 2:
                    mMode = "semi-transparent";
                    break;
                case 3:
                    mMode = "transparent";
                    break;
                case 4:
                    mMode = "warning";
                    break;
                default:
                    mMode = "-1";
                    break;
            }

        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void transitionTo(String mode) {
        int barMode = "opaque".equals(mode) ? BarTransitions.MODE_OPAQUE :
                "translucent".equals(mode) ? BarTransitions.MODE_TRANSLUCENT :
                        "semi-transparent".equals(mode) ? BarTransitions.MODE_SEMI_TRANSPARENT :
                                "transparent".equals(mode) ? BarTransitions.MODE_TRANSPARENT :
                                        "warning".equals(mode) ? BarTransitions.MODE_WARNING :
                                                -1;
        if (barMode != -1) {
             getBarTransitions().transitionTo(barMode, false);

        }
    }

    public BarTransitions getBarTransitions() {
        return mBarTransitions;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
