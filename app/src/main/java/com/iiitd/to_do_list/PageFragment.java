package com.iiitd.to_do_list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Nayeem on 11/4/2016.
 */

public class PageFragment extends Fragment {


    public static PageFragment newInstance(Detail detail) {

        PageFragment pageFragment = new PageFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("detail", detail);
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment, container, false);
        final TextView title = (TextView) view.findViewById(R.id.pager_title);
        final TextView detail = (TextView) view.findViewById(R.id.pager_detail);

        Detail cont= (Detail) getArguments().getSerializable("detail");

        title.setText(cont.getTitle());
        detail.setText(cont.getDetail());

        return view;
    }
}
