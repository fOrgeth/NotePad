package com.mcs.th.forge.notepad.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.mcs.th.forge.notepad.R;
import com.mcs.th.forge.notepad.model.Note;
import com.mcs.th.forge.notepad.model.NoteManager;

public class NoteEditorActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mTitleEditText;
    private EditText mBodyEditText;
    private Note mCurrentNote = null;
    private Long mCurId;
    private final String TAG_LOG = "NoteEditorActivity";

    public static final String ROW_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitleEditText = (EditText) findViewById(R.id.edit_text_title);
        mBodyEditText = (EditText) findViewById(R.id.edit_text_note);
        /*if (mCurrentNote==null){
            Bundle extras = getIntent().getExtras();
            Long curId = extras!=null?extras.getLong(NoteEditorActivity.ROW_ID):null;
            Log.d(TAG_LOG, "mCurrentNote = null, curId = "+curId);
            if(curId!=null){
                mCurrentNote=NoteManager.getInstance().getNote(curId);
                populateFields();
            }
        }*/
        if (mCurId == null) {
            Bundle extras = getIntent().getExtras();
            mCurId = extras != null ? extras.getLong(ROW_ID) : null;
            Log.d(TAG_LOG, "current id = " + mCurId);
        }
        if (mCurId != null) {
            mCurrentNote = NoteManager.getInstance().getNote(mCurId);
            Log.d(TAG_LOG, "current note = " + mCurrentNote);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void populateFields() {
        if (mCurrentNote != null) {
            mTitleEditText.setText(mCurrentNote.getTitle());
            mBodyEditText.setText(mCurrentNote.getBody());
        }
    }

    private boolean saveNote() {
        String title = mTitleEditText.getText().toString();

        if (TextUtils.isEmpty(title)) {
            mTitleEditText.setError("Title is required");
            return false;
        }

        String body = mBodyEditText.getText().toString();
        if (TextUtils.isEmpty(body)) {
            mBodyEditText.setError("Content is required");
            return false;
        }

        Log.d(TAG_LOG, "title = " + title + ", body = " + body);
        if (mCurrentNote != null) {
            Log.d(TAG_LOG, "mCurrentNote != null");
            mCurrentNote.setBody(body);
            mCurrentNote.setTitle(title);
            NoteManager.getInstance().update(mCurrentNote);
        } else {
            /** Need to rewrite?!*/
            Note note = new Note();
            note.setTitle(title);
            note.setBody(body);
            NoteManager.getInstance().create(note);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                if (mCurrentNote != null) {
                    NoteManager.getInstance().delete(mCurrentNote);
                }
                break;
            case R.id.action_save:
                if (saveNote()) {
                    Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return true;

    }
}
