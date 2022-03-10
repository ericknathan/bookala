package com.ericknathan.symbian.bookala.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ericknathan.symbian.bookala.models.Address;

import java.util.ArrayList;
import java.util.List;

public class SQLiteProvider extends SQLiteOpenHelper {

    private static final String DB_NAME = "libri";
    private static final int DB_VERSION = 1;
    private static SQLiteProvider INSTANCE;

    public SQLiteProvider(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS tbl_usuario (" +
                        "cod_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome VARCHAR(500) NOT NULL," +
                        "sobrenome VARCHAR(500) NOT NULL," +
                        "login VARCHAR(100) UNIQUE NOT NULL," +
                        "senha VARCHAR(100) NOT NULL" +
                        ")"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS tbl_endereco (" +
                        "cod_endereco INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cod_usuario INTEGER NOT NULL," +
                        "cep VARCHAR(10) NOT NULL," +
                        "numero VARCHAR(10) NOT NULL," +
                        "complemento VARCHAR(500)," +
                        "FOREIGN KEY (cod_usuario) REFERENCES tbl_usuario(cod_usuario)" +
                        ")"
        );

        Log.d("SQLiteProvider", "BANCO DE DADOS CRIADO! " + DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @SuppressLint("Range")
    public boolean usernameAlreadyExists(String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM tbl_usuario WHERE login = ?",
                new String[]{username}
        );

        try {
            sqLiteDatabase.beginTransaction();
            boolean usernameExists = cursor.getCount() != 0;
            sqLiteDatabase.setTransactionSuccessful();

            return usernameExists;
        } catch(Exception error) {
            Log.e("SQLiteProvider [addBook]", error.getMessage());
            return false;
        } finally {
            if(sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }
        }
    }

    public long addUser(String name, String surname, String username, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        try {
            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("nome", name);
            values.put("sobrenome", surname);
            values.put("login", username);
            values.put("senha", password);

            long id_user = sqLiteDatabase.insertOrThrow("tbl_usuario", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return id_user;
        } catch(Exception error) {
            Log.e("SQLiteProvider [addUser]", error.getMessage());
            return 0;
        } finally {
            if(sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }
        }
    }

    public boolean registerAddress(int id_user, String cep, String number, String complement) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        try {
            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("cod_usuario", id_user);
            values.put("cep", cep);
            values.put("numero", number);
            values.put("complemento", complement);

            sqLiteDatabase.insertOrThrow("tbl_endereco", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return true;
        } catch(Exception error) {
            Log.e("SQLiteProvider [addBook]", error.getMessage());
            return false;
        } finally {
            if(sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }
        }
    }

    @SuppressLint("Range")
    public int login(String username, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM tbl_usuario WHERE login = ? AND senha = ?",
                new String[]{username, password}
        );

        int user_id = 0;
        try {
            if(cursor.moveToFirst()) {
                user_id = cursor.getInt(cursor.getColumnIndex("cod_usuario"));
                return user_id;
            }

            return user_id;
        }
        catch(Exception exception) {
            Log.e("SQLiteProvider [login]", exception.getMessage());
        }
        finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return user_id;
    }

    @SuppressLint("Range")
    public List<Address> listAddresses(int id_user) {
        List<Address> items = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM tbl_endereco WHERE cod_usuario = ?",
                new String[]{String.valueOf(id_user)}
        );

        try {
            if(cursor.moveToFirst()) {
                do {
                    String addressCEP = cursor.getString(cursor.getColumnIndex("cep"));
                    String addressNumber = cursor.getString(cursor.getColumnIndex("numero"));
                    String addressComplement = cursor.getString(cursor.getColumnIndex("complemento"));

                    Address book = new Address(addressCEP, addressNumber, addressComplement);
                    items.add(book);
                } while(cursor.moveToNext());
            }
        }
        catch(Exception exception) {
            Log.d("SQLiteProvider [listAddresses]", exception.getMessage());
        }
        finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return items;
    }

    public static SQLiteProvider getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new SQLiteProvider(context);
        }

        return INSTANCE;
    }
}
