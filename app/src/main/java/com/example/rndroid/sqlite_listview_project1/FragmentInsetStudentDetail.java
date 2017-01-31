package com.example.rndroid.sqlite_listview_project1;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInsetStudentDetail extends Fragment {

    EditText editTextsName, editTextsSubject;
    Button buttonInsert;
    ListView listView;
    SimpleCursorAdapter simpleCursorAdapter;
    MyDatabase myDatabase;
    Cursor cursor = null;

    public FragmentInsetStudentDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDatabase = new MyDatabase(getActivity());
        myDatabase.openDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_fragment_inset_student_detail, container, false);
        editTextsName = (EditText) v.findViewById(R.id.edittextName_InsertDetails);
        editTextsSubject = (EditText) v.findViewById(R.id.edittextSubject_InsertDetails);
        buttonInsert = (Button) v.findViewById(R.id.buttonInsert_InsertDetails);
        listView = (ListView) v.findViewById(R.id.listview1);

        // Code for Displaying Database content on ListView

        //A - Get Cursor from table
        cursor = myDatabase.queryreadStudent();
        //establish connection between cursor and cursor adapter
        simpleCursorAdapter = new SimpleCursorAdapter
                (getActivity(),R.layout.row,cursor, new String[]{"_id", "sname","ssubject"}, new int[]{R.id.textView, R.id.textView2, R.id.textView3});

        listView.setAdapter(simpleCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                int sno = cursor.getInt(0); String name = cursor.getString(1); String subject = cursor.getString(2);
                Toast.makeText(getActivity(), ""+sno+"-"+name+"-"+subject, Toast.LENGTH_SHORT).show();
            }
        });
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase.insertDetails(editTextsName.getText().toString(),editTextsSubject.getText().toString());
                cursor.requery();
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        myDatabase.closeDB();
        super.onDestroy();
    }
}
