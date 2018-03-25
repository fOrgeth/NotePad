package com.mcs.th.forge.notepad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.mcs.th.forge.notepad.R;
import com.mcs.th.forge.notepad.adapters.NoteListAdapter;
import com.mcs.th.forge.notepad.model.Note;
import com.mcs.th.forge.notepad.model.NoteManager;
import com.mcs.th.forge.notepad.model.SampleNotes;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private NoteListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Note> mNotes;
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;

    private final String TAG_LOG = "#MainActivity#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SampleNotes.fillSampleNotes();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NoteEditorActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupList();
    }

    private void setupList() {
        mRecyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final GestureDetector mGestureDetector =
                new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildLayoutPosition(child);
                    Note selectedNote = mNotes.get(position);
                    Intent editorIntent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                    editorIntent.putExtra(NoteEditorActivity.ROW_ID, selectedNote.getId());
                    Log.d(TAG_LOG, "position = " + position + ", intent.getExtra.getLong = " + editorIntent.getExtras().getLong("id"));
                    startActivity(editorIntent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        mNotes = NoteManager.getInstance().getAllNotes();
        mAdapter = new NoteListAdapter(mNotes, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setupList();
    }
}
