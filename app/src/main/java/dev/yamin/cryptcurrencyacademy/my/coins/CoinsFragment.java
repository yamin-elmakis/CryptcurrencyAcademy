package dev.yamin.cryptcurrencyacademy.my.coins;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import dev.yamin.cryptcurrencyacademy.Adapters.CoinsAdapter;
import dev.yamin.cryptcurrencyacademy.Adapters.RecyclerViewAdapter;
import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.base.BaseFragment;
import dev.yamin.cryptcurrencyacademy.network.GsonJsonParser;
import dev.yamin.cryptcurrencyacademy.network.POJOS.Coin24Hr;
import dev.yamin.cryptcurrencyacademy.network.RequestBuilder;

public class CoinsFragment extends BaseFragment implements Response.ErrorListener, Response.Listener<Coin24Hr>,GsonJsonParser<Coin24Hr,Object> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CoinsFragment() {
        // Required empty public constructor
    }

      // TODO: Rename and change types and number of parameters
    public static CoinsFragment newInstance(String param1, String param2) {
        CoinsFragment fragment = new CoinsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_coins, container, false);

        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new CoinsAdapter(getContext(),null);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   @Override
     public void onAttach(Context context) {
         super.onAttach(context);
         if (context instanceof OnFragmentInteractionListener) {
             mListener = (OnFragmentInteractionListener) context;
         } else {
             throw new RuntimeException(context.toString()
                     + " must implement OnFragmentInteractionListener");
         }
     }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        RequestBuilder.getInstance(getContext()).GenerateCoin24HrRequest("LTCUSDT",this,this,this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


    @Override
    public void onResponse(Coin24Hr response) {
        mAdapter.reset();
        ArrayList<Coin24Hr> coin24HrArrayList = new ArrayList<Coin24Hr>();
        coin24HrArrayList.add(response);
        mAdapter.addAll(coin24HrArrayList);
    }

    @Override
    public Coin24Hr parseJsonToObj(String data) {
        Coin24Hr item = null;
        if(data != null){
            JsonParser parser = new JsonParser();
            Type dataType;
            Gson gson = new Gson();
            item = gson.fromJson(data,Coin24Hr.class);
        }
        return item;
    }

    @Override
    public String parseObjToJson(Object obj) {
        return null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
