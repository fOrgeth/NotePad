package com.mcs.th.forge.notepad.activities;

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
import com.mcs.th.forge.notepad.model.NoteManager;


public class NoteEditorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText titleEditText;
    private EditText bodyEditText;
    private Long curId;
    private final String TAG_LOG = "NoteEditorActivity";

    public static final String ROW_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleEditText = findViewById(R.id.edit_text_title);
        bodyEditText = findViewById(R.id.edit_text_note);
        Bundle extras = getIntent().getExtras();
        curId = extras != null ? extras.getLong(ROW_ID) : null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void populateFields() {
        if (curId != null) {
            titleEditText.setText(NoteManager.getInstance().getNote(curId).getTitle());
            bodyEditText.setText(NoteManager.getInstance().getNote(curId).getBody());
        }
    }

    private boolean isTitleBodyFilled() {
        String title = titleEditText.getText().toString();
        String body = bodyEditText.getText().toString();
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(body));
    }

    private boolean saveNote() {
        String title = titleEditText.getText().toString();
        String body = bodyEditText.getText().toString();
        Log.d(TAG_LOG, "title = " + title + ", body = " + body);
        if (curId != null) {
            Log.d(TAG_LOG, "mCurrentNote != null");
            Long id = NoteManager.getInstance().getNote(curId).getId();
            NoteManager.getInstance().update(title, body, id);
        } else {
            NoteManager.getInstance().create(title, body);
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
                if (curId != null) {
                    NoteManager.getInstance().delete(curId);
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case R.id.action_save:
                if (isTitleBodyFilled()) {
                    if (saveNote()) {
                        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Something wrong! Note was not saved", Toast.LENGTH_LONG).show();
                    }
                    finish();
                } else {
                    Toast.makeText(this, "Title and Body fill required", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
