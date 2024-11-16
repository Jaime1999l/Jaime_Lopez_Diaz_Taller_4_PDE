package com.example.jaime_lopez_diaz_taller_4_pde.dataBase.repos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jaime_lopez_diaz_taller_4_pde.dataBase.DatabaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private FirebaseFirestore firestore;

    public UserRepository(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
        FirebaseApp.initializeApp(context.getApplicationContext());
        firestore = FirebaseFirestore.getInstance();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void saveUserName(String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);

        // Eliminar cualquier entrada existente
        database.delete(DatabaseHelper.TABLE_USERS, null, null);

        // Insertar el nuevo nombre
        database.insert(DatabaseHelper.TABLE_USERS, null, values);

        // Guardar en Firestore
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);

        firestore.collection("users").document("user1")
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    // Éxito
                })
                .addOnFailureListener(e -> {
                    // Error
                });
    }

    public String getUserName() {
        String userName = null;
        Cursor cursor = database.query(DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.COLUMN_NAME},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            cursor.close();
        }

        return userName;
    }
}
