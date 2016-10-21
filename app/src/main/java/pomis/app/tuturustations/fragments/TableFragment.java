package pomis.app.tuturustations.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pomis.app.tuturustations.R;
import pomis.app.tuturustations.activities.DrawerActivity;
import pomis.app.tuturustations.activities.StationsActivity;

/**
 * Фрагмент расписания
 */
public class TableFragment extends Fragment {


    @BindView(R.id.tv_from)
    TextView tvFrom;

    @BindView(R.id.tv_to)
    TextView tvTo;

    @BindView(R.id.tv_when)
    TextView tvWhen;

    private static final int REQUEST_FROM = 0x1;
    private static final int REQUEST_TO = 0x2;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        unbinder = ButterKnife.bind(this, view);
        selectedDay = 21;
        selectedMonth = 10;
        selectedYear = 2016;
        showInfo();
        return view;
    }

    private void showInfo() {
        if (!PreferenceManager
                .getDefaultSharedPreferences(getContext())
                .getBoolean("done", false))
            new AlertDialog
                    .Builder(getContext())
                    .setMessage("Приложению необходимо загрузить базу станций (~5Мб). Это займёт некоторое время.")
                    .setTitle("Добро пожаловать!")
                    .show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_from)
    void openFrom() {
        startActivityForResult(
                new Intent(getContext(), StationsActivity.class)
                        .putExtra("type", "from"),
                REQUEST_FROM);
    }

    @OnClick(R.id.tv_to)
    void openTo() {
        startActivityForResult(
                new Intent(getContext(), StationsActivity.class)
                        .putExtra("type", "to"),
                REQUEST_TO);
    }

    public TableFragment() {
        // Required empty public constructor
    }

    int selectedDay;
    int selectedMonth;
    int selectedYear;

    @OnClick(R.id.tv_when)
    void openWhen() {
        Log.d("kek", "lel opened");
//        getActivity().showDialog(1);
        DatePickerDialog tpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDay = dayOfMonth;
                selectedMonth = monthOfYear;
                selectedYear = year;
                tvWhen.setText(dayOfMonth + "." + monthOfYear + 1 + "." + year);
            }
        }, selectedYear, selectedMonth, selectedDay);
        tpd.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            String name = data.getStringExtra("result");
            String type = data.getStringExtra("type");
            if (type.equals("from")) {
                tvFrom.setText(name);
            } else if (type.equals("to")) {
                tvTo.setText(name);
            }
        }
    }
}
