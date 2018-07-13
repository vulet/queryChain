package com.querychain.mainapp;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DataBaseHandler extends SQLiteAssetHelper {
    private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "certASQLite.db";
	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	SQLiteDatabase dbs = this.getReadableDatabase();
	public List<Certifications> getAllCertifications() {
        List<Certifications> certificationsList = new ArrayList<>();
		String selectQuery = "SELECT * FROM certs";
		Cursor cursor = dbs.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
                Certifications certification = new Certifications();
				certification.setId(Integer.parseInt(cursor.getString(0)));
                certification.setName(cursor.getString(1));
                certification.setImage(cursor.getBlob(3));
                certificationsList.add(certification);
			} while (cursor.moveToNext());
		}
		cursor.close();
		dbs.close();
		return certificationsList;
	}
}


