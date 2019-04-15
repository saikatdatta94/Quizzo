package com.example.saikat.quizzo;

import android.provider.BaseColumns;

public final class CategoryItemContract {

    private CategoryItemContract(){}

    public static class CategoryItemTable implements BaseColumns {
        public static final String CATEGORY_ITEM_TABLE_NAME = "category_items";
        public static final String COLUMN_CATEGORY_HEADING = "heading";
        public static final String COLUMN_CATEGORY_DESCRIPTION = "description";
        public static final String COLUMN_CATEGORY_PHOTO_RESOURCE = "resource";
        public static final String COLUMN_CATEGORY_PARENT = "parent";
    }

}
