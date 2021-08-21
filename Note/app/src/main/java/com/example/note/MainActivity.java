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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvNote;
    EditText edtSearch;
    Button btnSearch;
    MyDatabaseHelper db=new MyDatabaseHelper(this);
    ListViewNoteAdapter lv_noteAdapter;
    ArrayList<NoteModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWitget();
        list=db.getAllNote();

        lv_noteAdapter=new ListViewNoteAdapter(list);
        lvNote.setAdapter(lv_noteAdapter);

        lvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteModel note = (NoteModel) lv_noteAdapter.getItem(position);
                Intent intent=new Intent(getApplicationContext(),Note.class);
                intent.putExtra("title",note.getTitle());
                intent.putExtra("message",note.getMessage());
                startActivity(intent);
            }
        });
        lvNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Thông báo")
                        .setMessage("Bạn có chắc chắn muốn xóa")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (list.size() > 0) {
                                    NoteModel note = (NoteModel) lv_noteAdapter.getItem(position);
                                    list.remove(position);
                                    db.deleteMonThi(note);
                                    lv_noteAdapter.notifyDataSetChanged();
                                    Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    db.updateNote(note);
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Xóa không thành công!", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();

                alertDialog.show();
                return false;
            }
        });
        search();
    }
    private void search(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list=db.searchNote(edtSearch.getText().toString());
                lv_noteAdapter=new ListViewNoteAdapter(list);
                lvNote.setAdapter(lv_noteAdapter);
            }
        });
    }
    private void getWitget(){
        lvNote=(ListView)findViewById(R.id.lv_note);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        edtSearch=(EditText)findViewById(R.id.edtSearch);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home_them:
                them();
                break;
            case R.id.nav_home_dong:
                dong();
                break;
            default:
                Toast.makeText(this, "khoong co", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void them(){
        Intent intent=new Intent(getApplicationContext(),Note.class);
        startActivity(intent);
    }
    public void dong(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Thông báo")
                .setMessage("Bạn có chắc chắn muốn đóng")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Toast.makeText(MainActivity.this, "Đóng thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Đóng không thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();

        alertDialog.show();
    }
}