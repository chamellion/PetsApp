/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.pets.data.PetContract.PetEntry;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

        private static final int LOADER_VERSION = 1;

    PetCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        ListView listView = findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);

        cursorAdapter = new PetCursorAdapter(getApplicationContext(), null);

        listView.setAdapter(cursorAdapter);

        listView.setEmptyView(emptyView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                Uri currentPet = ContentUris.withAppendedId(PetEntry.CONTENT_URI, id);

                intent.setData(currentPet);

                startActivity(intent);
            }
        });


        getSupportLoaderManager().initLoader(LOADER_VERSION, null, this);


    }

    @Override
    protected void onStart() {
        super.onStart();
//        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
//                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllPets() {
        int rowDeleted = getContentResolver().delete(PetEntry.CONTENT_URI, null, null);
        Log.v("Catalog Activity", rowDeleted + " rows deleted from database");
    }

    private void insertPet() {

        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 7);


        Uri uri = getContentResolver().insert(PetEntry.CONTENT_URI, values);

        Log.v("CatalogActivity", "New row ID" + uri);
    }

//    private void displayDatabaseInfo() {
//
//        String[] projection = {PetEntry._ID, PetEntry.COLUMN_PET_NAME, PetEntry.COLUMN_PET_BREED,
//                PetEntry.COLUMN_PET_GENDER, PetEntry.COLUMN_PET_WEIGHT};
//
//        Cursor cursor = getContentResolver().query(PetEntry.CONTENT_URI, projection,
//                        null,null, null);
//
//        ListView listView = findViewById(R.id.list);
//
//        View emptyView = findViewById(R.id.empty_view);
//
//        cursorAdapter = new PetCursorAdapter(getApplicationContext(), cursor);
//
//        listView.setAdapter(cursorAdapter);
//
//        listView.setEmptyView(emptyView);
//    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        String[] projection = {PetEntry._ID, PetEntry.COLUMN_PET_NAME,
                    PetEntry.COLUMN_PET_BREED};



            return new CursorLoader(getApplicationContext(), PetEntry.CONTENT_URI, projection, null,
                    null, null); }



    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            cursorAdapter.swapCursor(null);
    }
}