package com.example.note;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class ListViewNoteAdapter extends BaseAdapter {

    ArrayList<NoteModel> listNote;

    public ListViewNoteAdapter(ArrayList<NoteModel> listNote) {
        this.listNote = listNote;
    }

    @Override
    public int getCount() {
        return this.listNote.size();
    }

    @Override
    public Object getItem(int position) {
        return listNote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
//        if (convertView == null) {
//            view = View.inflate(parent.getContext(), R.layout.monthi_view, null);
//        } else view = convertView;
        view = View.inflate(parent.getContext(), R.layout.note_item, null);
        //Bind sữ liệu phần tử vào View
        NoteModel note =listNote.get(position);
        ((TextView) view.findViewById(R.id.tv_item_title)).setText(note.getTitle());

        return view;
    }
}
