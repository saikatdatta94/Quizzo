package com.example.saikat.quizzo;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract(){}

    public static class CategoryItemTable implements BaseColumns {
        public static final String CATEGORY_ITEM_TABLE_NAME = "category_items";
        public static final String COLUMN_CATEGORY_HEADING = "heading";
        public static final String COLUMN_CATEGORY_DESCRIPTION = "description";
        public static final String COLUMN_CATEGORY_PHOTO_RESOURCE = "resource";
        public static final String COLUMN_CATEGORY_PARENT = "parent";
    }

    public static class Questions implements BaseColumns {
        public static final String QUESTION_TABLE_NAME = "questions";
        public static final String QUESTION = "question";
        public static final String OPTION1 = "option1";
        public static final String OPTION2 = "option2";
        public static final String OPTION3 = "option3";
        public static final String OPTION4 = "option4";
        public static final String DESCRIPTION = "description";
        public static final String LEVEL = "level";
        public static final String CATEGORY = "category";
        public static final String PARENT_CATEGORY = "parent";
    }

}
