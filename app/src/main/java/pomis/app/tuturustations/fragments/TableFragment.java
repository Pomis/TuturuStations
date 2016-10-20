package pomis.app.tuturustations.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pomis.app.tuturustations.R;
import pomis.app.tuturustations.activities.StationsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {


    private static final int REQUEST_FROM = 0x1;
    private static final int REQUEST_TO = 0x2;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_from) void openFrom(){
        startActivityForResult(new Intent(getContext(), StationsActivity.class), REQUEST_FROM);
    }

    @OnClick(R.id.tv_to) void openTo(){
        startActivityForResult(new Intent(getContext(), StationsActivity.class), REQUEST_TO);
    }

    public TableFragment() {
        // Required empty public constructor
    }

}
