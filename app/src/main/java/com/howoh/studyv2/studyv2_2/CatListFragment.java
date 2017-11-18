package com.howoh.studyv2.studyv2_2;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.howoh.studyv2.studyv2_2.service.ContactDBSubject;
import com.howoh.studyv2.studyv2_2.service.DBHelper;
import com.howoh.studyv2.studyv2_2.view.IdNameListAdapter;
import com.howoh.studyv2.studyv2_2.vo.IdNameListView;
import com.howoh.studyv2.studyv2_2.vo.Subject;

import java.util.List;

public class CatListFragment extends BaseFragment {

    private static final String TAG = CatListFragment.class.getSimpleName();

    private FragmentManager fm;
    private DBHelper dbHelper;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_idname_list, container, false);
        fm = getActivity().getSupportFragmentManager();
        dbHelper = DBHelper.getInstance(getActivity());

        Subject sub = null;

        if(dbHelper.isExistTable(ContactDBSubject.TABLE_NAME)) {
            sub = dbHelper.getSubject(getArguments().getString("subId"));
        }

        if(sub != null) {
            setTitle(sub.getName());
            setArrayAdapter(dbHelper.getCategoriesForView(sub.getId()));
        }

        return v;
    }

    private void setArrayAdapter(List<IdNameListView> items) {

        IdNameListAdapter adapter = new IdNameListAdapter(items);
        ListView listView = (ListView) v.findViewById(R.id.frg_idname_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IdNameListView item = (IdNameListView) parent.getItemAtPosition(position);
                moveLecListFragment(((IdNameListView) parent.getItemAtPosition(position)).getId());
            }
        });
    }

    private void moveLecListFragment(String catId) {
        Bundle bundle = new Bundle();
        bundle.putString("catId", catId);

        LecListFragment lecListFragment = new LecListFragment();
        lecListFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.content_main, lecListFragment).addToBackStack(null).commit();
    }
}
