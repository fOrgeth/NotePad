package com.mcs.th.forge.notepad.adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcs.th.forge.notepad.R;
import com.mcs.th.forge.notepad.model.Note;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private List<Note> mNotes;

    public NoteListAdapter(List<Note> notes, Context context) {
        mNotes = notes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView noteTitle, noteCreateDate;
        private CardView cardView;

        public ViewHolder(View v) {
            super(v);
//            this.cardView = v;
            noteTitle = (TextView) v.findViewById(R.id.text_view_note_title);
            noteCreateDate = (TextView) v.findViewById(R.id.text_view_note_date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_note_list, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.noteTitle.setText(mNotes.get(position).getTitle());
        holder.noteCreateDate.setText(mNotes.get(position).getDateCreated());
//        holder.noteCreateDate.setText(mNotes.get(position).getReadableCreatedDate());

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}
