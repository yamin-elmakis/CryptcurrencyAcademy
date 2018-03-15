package dev.yamin.cryptcurrencyacademy.alerts;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.content.AlertCoinsContent;

public class NewAlertFragmentDialog extends DialogFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private OnAlertCoinSelected mListener;

    public NewAlertFragmentDialog() {   }

    public static NewAlertFragmentDialog newInstance(int columnCount) {
        NewAlertFragmentDialog fragment = new NewAlertFragmentDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_alert, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.new_alert_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NewAlertAdapter(AlertCoinsContent.ITEMS, mListener));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAlertCoinSelected) {
            mListener = (OnAlertCoinSelected) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnAlertCoinSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnAlertCoinSelected {
        void onListFragmentInteraction(String item);
    }
}
