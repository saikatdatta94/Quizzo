package com.example.saikat.quizzo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.saikat.quizzo.QuizContract.*;

import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper {

    private  static final String DATABASE_NAME = "Quizzo.db";
    private  static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORY_LIST_ITEM_TABLE = "CREATE TABLE "+
                CategoryItemTable.CATEGORY_ITEM_TABLE_NAME +
                " ( " + CategoryItemTable._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 CategoryItemTable.COLUMN_CATEGORY_HEADING + " TEXT, " +
                 CategoryItemTable.COLUMN_CATEGORY_DESCRIPTION + " TEXT, " +
                 CategoryItemTable.COLUMN_CATEGORY_PHOTO_RESOURCE + " TEXT, " +
                 CategoryItemTable.COLUMN_CATEGORY_PARENT + " TEXT " +
                " )";

//        TODO: Changes might be needed for listType ONE/TWO

        db.execSQL(SQL_CREATE_CATEGORY_LIST_ITEM_TABLE);

        fillCategoryListItemTable();

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoryItemTable.CATEGORY_ITEM_TABLE_NAME);
        onCreate(db);
    }

    private void fillCategoryListItemTable() {
        ListItem l1 = new ListItem(ListItem.ListType.ONE,"image1","Heading 1",
                "Description1","Science");
        addCategoryListItem(l1);

        ListItem l2 = new ListItem(ListItem.ListType.ONE,"image2","Heading 2",
                "Description2","Science");
        addCategoryListItem(l2);

        ListItem l3 = new ListItem(ListItem.ListType.ONE,"image3","Heading 3",
                "Description3","Science");
        addCategoryListItem(l3);

        ListItem l4 = new ListItem(ListItem.ListType.ONE,"image4","Heading 4",
                "Description4","Science");
        addCategoryListItem(l4);

        ListItem l5 = new ListItem(ListItem.ListType.ONE,"image5","Heading 5",
                "Description5","Science");
        addCategoryListItem(l5);
    }

    private void addCategoryListItem(ListItem listItem){
        ContentValues cv = new ContentValues();
        cv.put(CategoryItemTable.COLUMN_CATEGORY_HEADING,listItem.getHeading());
        cv.put(CategoryItemTable.COLUMN_CATEGORY_DESCRIPTION,listItem.getDescriptionText());
        cv.put(CategoryItemTable.COLUMN_CATEGORY_PHOTO_RESOURCE,listItem.getImageURL());
        cv.put(CategoryItemTable.COLUMN_CATEGORY_PARENT,listItem.getParentCategory());

        db.insert(CategoryItemTable.CATEGORY_ITEM_TABLE_NAME,null,cv);

    }


//   TODO:(!) Fetching Items from table
    public ArrayList<ListItem> getAllCategoryItems(){
        ArrayList<ListItem> categoryList = new ArrayList<>();
        db = getReadableDatabase();

//        We get the cursor move to the first row and keep moving it downwards as we search the database

        Cursor c = db.rawQuery("SELECT * FROM "+CategoryItemTable.CATEGORY_ITEM_TABLE_NAME,null);

        if (c.moveToFirst()){
            do{
                ListItem listItem = new ListItem();
                listItem.setHeading(c.getString(c.getColumnIndex(CategoryItemTable.COLUMN_CATEGORY_HEADING)));
                listItem.setDescriptionText(c.getString(c.getColumnIndex(CategoryItemTable.COLUMN_CATEGORY_DESCRIPTION)));
                listItem.setImageURL(c.getString(c.getColumnIndex(CategoryItemTable.COLUMN_CATEGORY_PHOTO_RESOURCE)));
                listItem.setParentCategory(c.getString(c.getColumnIndex(CategoryItemTable.COLUMN_CATEGORY_PARENT)));
                listItem.setType(ListItem.ListType.ONE);

                categoryList.add(listItem);
            }while (c.moveToNext());

        }
        c.close();

        return categoryList;

    }

}
