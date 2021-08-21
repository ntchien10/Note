package com.example.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Note";
    private static final String TABLE_NOTE = "note";
    private static final String COLUMN_NOTE_TITLE ="Note_Title";
    private static final String COLUMN_NOTE_MESSAGE = "Note_Message";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLQuery = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_NOTE_TITLE + " TEXT PRIMARY KEY,"
                + COLUMN_NOTE_MESSAGE + " TEXT" + ")";
        // Execute script.
        db.execSQL(SQLQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(db);
    }

    // If Note table has no data
    // default, Insert 2 records.
//    public void createDefaultMonThiIfNeed()  {
//        int count = this.getCanBoCount();
//        if(count ==0 ) {
//            CanBoModel cb1 = new CanBoModel("CB01","Cán bộ 1","CNTT",5345345);
//            CanBoModel cb2 = new CanBoModel("CB02","Cán bộ 2","QTKD",134534);
//            this.addCanBo(cb1);
//            this.addCanBo(cb2);
//        }
//    }

    public void addNote(NoteModel note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getTitle());
        values.put(COLUMN_NOTE_MESSAGE, note.getMessage());

        // Inserting Row
        db.insert(TABLE_NOTE, null, values);

        // Closing database connection
        db.close();
    }


    public NoteModel getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE, new String[] {
                        COLUMN_NOTE_TITLE, COLUMN_NOTE_MESSAGE}, COLUMN_NOTE_TITLE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        NoteModel note = new NoteModel(
                cursor.getString(0),
                cursor.getString(1));
        // return note
        return note;
    }


    public ArrayList<NoteModel> getAllNote() {

        ArrayList<NoteModel> noteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NoteModel note = new NoteModel();
                note.setTitle(cursor.getString(0));
                note.setMessage(cursor.getString(1));
                // Adding note to list
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        // return monthi list
        return noteList;
    }

    public ArrayList<NoteModel> searchNote(String TenNote) {

        ArrayList<NoteModel> noteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE +" WHERE "+COLUMN_NOTE_TITLE+ " LIKE '%"+TenNote+"%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NoteModel note = new NoteModel();
                note.setTitle(cursor.getString(0));
                note.setMessage(cursor.getString(1));
                // Adding note to list
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        // return monthi list
        return noteList;
    }


//    public int getCanBoCount() {
//
//        String countQuery = "SELECT  * FROM " + TABLE_CANBO;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        int count = cursor.getCount();
//
//        cursor.close();
//
//        // return count
//        return count;
//    }

    public int updateNote(NoteModel note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_MESSAGE, note.getMessage());

        // updating row
        return db.update(TABLE_NOTE, values, COLUMN_NOTE_TITLE + " = ?",
                new String[]{String.valueOf(note.getTitle())});
    }

    public void deleteMonThi(NoteModel note) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_NOTE_TITLE + " = ?",
                new String[] { String.valueOf(note.getTitle()) });
        db.close();
    }

}
