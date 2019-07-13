package com.example.android.pets.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class PetContract {

    /*Create a private constructor so that an object is never created
    Class is just for holding contracts constants
     */
    private PetContract() {
    }

    //Content authority for our content provider
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";

    // To make this a usable java.net.URI, we use the parse method which takes in a URI string and returns a Uri.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //This constants stores the path for each of the tables which will be appended to the base content URI
    public static final String PATH_PETS = "pets";


    public static final class PetEntry implements BaseColumns {


        /**
         * The content URI to access the pet data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;


        public static final String TABLE_NAME = "pets";


        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "Name";
        public static final String COLUMN_PET_BREED = "Breed";
        public static final String COLUMN_PET_GENDER = "Gender";
        public static final String COLUMN_PET_WEIGHT = "Weight";


        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static boolean isValidGender(Integer gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE) {
                return true;
            }
            return false;
        }


    }
}
