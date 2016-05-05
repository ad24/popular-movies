package com.adel.freewing.popularmovie.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSQLiteHelper extends SQLiteOpenHelper {
	private Context context;



	// database info
	private static final String DATABASE_NAME = "movies.db";
	private static final int DATABASE_VERSION = 1;


	//table movie
	public static final String MOVIE_TABLE="movies";
	public static final String MOVIE_ID="movie_id";
	public static final String MOVIE_POSTER_URL="movie_Poster_url";
	public static final String MOVIE_RELEASE_DATE="movie_release_date";
	public static final String MOVIE_TITLE="movie_title";
	public static final String MOVIE_DESCRIPTION="movie_description";
	public static final String MOVIE_REVIEWS="movie_reviews";
	public static final String MOVIE_TRAILERS="movie_trailers";
	public static final String MOVIE_RATE="movie_rate";
	public static final String MOVIE_IS_FAVOURITE="movie_favourite";

	// tables creation
	private static final String MOVIE_CREATE = "CREATE TABLE "
			+ MOVIE_TABLE + "(" + MOVIE_ID
			+ " INTEGER PRIMARY KEY , "
			+ MOVIE_POSTER_URL + " TEXT, "
			+ MOVIE_RELEASE_DATE + " TEXT, "
			+ MOVIE_DESCRIPTION + " TEXT, "
			+ MOVIE_REVIEWS + " TEXT, "
			+ MOVIE_TRAILERS + " TEXT, "
			+ MOVIE_RATE + " TEXT, "
			+ MOVIE_IS_FAVOURITE + " INTEGER DEFAULT 0, "
			+ MOVIE_TITLE + " TEXT);";
	public DatabaseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// create tables
		database.execSQL(MOVIE_CREATE);

    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);
		onCreate(db);
	}

}
