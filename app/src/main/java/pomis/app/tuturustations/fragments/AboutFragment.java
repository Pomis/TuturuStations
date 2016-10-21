package pomis.app.tuturustations.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pomis.app.tuturustations.BuildConfig;
import pomis.app.tuturustations.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    @BindView(R.id.tv_about)
    TextView tvAbout;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);
        tvAbout.setText(getString(R.string.app_name)+"\nТестовое задание для вакансии младшего Android-разработчика в компанию Туту.ру.\n" +
                "© Роман Исмагилов.\nВерсия: " + BuildConfig.VERSION_NAME + "\nТип сборки: " + BuildConfig.BUILD_TYPE+
        "\nНомер сборки: "+BuildConfig.VERSION_CODE);
        return view;
    }

}
