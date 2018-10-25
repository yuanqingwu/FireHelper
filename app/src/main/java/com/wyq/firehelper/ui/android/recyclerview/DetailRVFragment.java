package com.wyq.firehelper.ui.android.recyclerview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseFragment;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailRVFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailRVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailRVFragment extends BaseFragment implements SelectListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SelectListener selectListener;
    public boolean continueMoveToTop = false;
    public int positionToMove = 0;

    private LinearLayoutManager linearLayoutManager;
    private StickHeaderDecoration stickHeaderDecoration;

    @BindView(R.id.ui_fragment_recycler_view_detail_rv)
    public RecyclerView recyclerView;

    public DetailRVFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailRVFragment.
     */
    public static DetailRVFragment newInstance(String param1, String param2) {
        DetailRVFragment fragment = new DetailRVFragment();
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
    public int attachLayoutRes() {
        return R.layout.ui_fragment_recycler_view_detail_layout;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        stickHeaderDecoration = new StickHeaderDecoration(getContext());
        recyclerView.addItemDecoration(stickHeaderDecoration);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add("item: " + (i % 10 == 0 ? i / 10 : i));
        }
        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(list);
        adapter.enableViewType();//打开支持title
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (continueMoveToTop && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Logger.i("continue move " + positionToMove);
                    continueMoveToTop = false;
                    smoothMoveToPosition(positionToMove);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPos = linearLayoutManager.findFirstVisibleItemPosition();
                int lastPos = linearLayoutManager.findLastVisibleItemPosition();
                stickHeaderDecoration.setHeadText("item:" + firstPos / 10);
                selectListener.select(firstPos / 10, false);
            }
        });
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
//        if (context instanceof SelectListener) {
//            mListener = (OnFragmentInteractionListener) context;
//            selectListener = (SelectListener) context;
//        }
//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener and SelectListener");
//        }
    }

    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        selectListener = null;
    }

    @Override
    public void select(int position, boolean isScroll) {
        positionToMove = position * 10;
        smoothMoveToPosition(positionToMove);
        stickHeaderDecoration.setHeadText("" + position);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void smoothMoveToPosition(int pos) {
        int firstPos = linearLayoutManager.findFirstVisibleItemPosition();
        int lastPos = linearLayoutManager.findLastVisibleItemPosition();
        if (pos > firstPos && pos <= lastPos) {
            int top = recyclerView.getChildAt(pos - firstPos).getTop();
            recyclerView.smoothScrollBy(0, top);
        } else if (pos > lastPos) {
            recyclerView.smoothScrollToPosition(pos);
            continueMoveToTop = true;
        } else {
            recyclerView.smoothScrollToPosition(pos);
        }
    }
}
