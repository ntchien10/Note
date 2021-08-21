package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Note extends AppCompatActivity {
    EditText edtTitle, edtMessage;
    ImageView btnSave;

    ArrayList<NoteModel> listNote=new ArrayList<>();
    MyDatabaseHelper db=new MyDatabaseHelper(this);
    int count=0,vtri=0;

    Intent intent=null;
    String title;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        getWitget();

        intent=this.getIntent();
        title=intent.getStringExtra("title");
        message=intent.getStringExtra("message");

        edtTitle.setText(title);
        edtMessage.setText(message);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (NoteModel note: listNote){
                    if(note.title.equals(edtTitle.getText().toString())){
                        NoteModel noteModel=new NoteModel(edtTitle.getText().toString(),edtMessage.getText().toString());
                        listNote.set(vtri,noteModel);
                        db.updateNote(noteModel);
                        Toast.makeText(Note.this, "Save successfully", Toast.LENGTH_SHORT).show();
                        count++;
                    }
                    vtri++;
                }
                if (count==0){
                    NoteModel note=new NoteModel(edtTitle.getText().toString(),edtMessage.getText().toString());
                    listNote.add(note);
                    db.addNote(note);
                    Toast.makeText(Note.this, "Save successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getWitget(){
        edtTitle=(EditText)findViewById(R.id.edt_title);
        edtMessage=(EditText)findViewById(R.id.edt_message);
        btnSave=(ImageView)findViewById(R.id.btnSave);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_note_dong:
                finish();
                break;
            default:
                Toast.makeText(this, "khoong co", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}