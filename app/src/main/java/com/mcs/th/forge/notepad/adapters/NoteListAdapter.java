package com.mcs.th.forge.notepad.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcs.th.forge.notepad.R;
import com.mcs.th.forge.notepad.model.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private List<Note> notes;

    public NoteListAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView noteTitle, noteCreateDate, noteModifyDate;
        private CardView cardView;

        public ViewHolder(View v) {
            super(v);
//            this.cardView = v;
            noteTitle = v.findViewById(R.id.text_view_note_title);
            noteCreateDate = v.findViewById(R.id.text_view_note_date);
            noteModifyDate = v.findViewById(R.id.text_view_note_modify_date);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_note_list, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.noteTitle.setText(notes.get(position).getTitle());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy - HH:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        sdf.setTimeZone(calendar.getTimeZone());
        holder.noteCreateDate.setText(sdf.format(notes.get(position).getDateCreated()));
        if (notes.get(position).getDataModified() != null) {
            holder.noteModifyDate.setText(sdf.format(notes.get(position).getDataModified()));
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
