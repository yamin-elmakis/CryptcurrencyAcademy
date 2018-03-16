package dev.yamin.cryptcurrencyacademy.my.coins;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import dev.yamin.cryptcurrencyacademy.Adapters.CoinsAdapter;
import dev.yamin.cryptcurrencyacademy.Adapters.RecyclerViewAdapter;
import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.base.BaseFragment;
import dev.yamin.cryptcurrencyacademy.network.GsonJsonParser;
import dev.yamin.cryptcurrencyacademy.network.POJOS.Coin24Hr;
import dev.yamin.cryptcurrencyacademy.network.RequestBuilder;
import dev.yamin.cryptcurrencyacademy.utils.AppUtils;
import dev.yamin.cryptcurrencyacademy.utils.DataUtils;
import lib.yamin.easylog.EasyLog;

public class CoinsFragment extends BaseFragment implements Response.ErrorListener, Response.Listener<Coin24Hr>,
        GsonJsonParser<Coin24Hr, Object>, RecyclerViewAdapter.OnViewHolderClick<Coin24Hr> {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    ArrayList<Coin24Hr> coin24HrArrayList = new ArrayList<Coin24Hr>();

    private OnCoinSelectedListener listener;

    public CoinsFragment() {
        // Required empty public constructor
    }

    public static CoinsFragment newInstance() {
        CoinsFragment fragment = new CoinsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_coins, container, false);

        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new CoinsAdapter(getContext(), this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCoinSelectedListener) {
            listener = (OnCoinSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnCoinSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        coin24HrArrayList.clear();
        ArrayList<String> coins = DataUtils.getSupportedCoins();
        for (String item : coins) {
            RequestBuilder.getInstance(getContext()).GenerateCoin24HrRequest(DataUtils.symbolToPair(item), this, this, this);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        EasyLog.e();
    }

    @Override
    public void onResponse(Coin24Hr response) {
        coin24HrArrayList.add(response);
        mAdapter.reset();
        mAdapter.addAll(coin24HrArrayList);
    }

    @Override
    public Coin24Hr parseJsonToObj(String data) {
        Coin24Hr item = null;
        if (data != null) {
            item = AppUtils.getObjectFromStr(data, Coin24Hr.class);
        }
        return item;
    }

    @Override
    public String parseObjToJson(Object obj) {
        return null;
    }

    @Override
    public void onClick(View view, int position, Coin24Hr item) {
        if (listener != null) {
            listener.OnCoinSelected(item.getSymbol());
        }
    }

    public interface OnCoinSelectedListener {
        void OnCoinSelected(String coin);
    }
}
