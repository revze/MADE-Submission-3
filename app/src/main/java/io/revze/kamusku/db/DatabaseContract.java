package io.revze.kamusku.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String INDO_ENGLISH_TBL = "indonesia_english";
    static String ENGLISH_INDO_TBL = "english_indonesia";

    static final class IndoEnglishColumns implements BaseColumns {
        static String WORD = "word";

        static String DESCRIPTION = "description";
    }

    static final class EnglishIndoColumns implements BaseColumns {
        static String WORD = "word";

        static String DESCRIPTION = "description";
    }
}
