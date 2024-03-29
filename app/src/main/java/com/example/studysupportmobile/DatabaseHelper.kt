package com.example.studysupportmobile

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * データベースヘルパークラス
 */
class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    //クラス内のpirvate定数を宣言するためにcompanion objectブロックとする。
    companion object {
        /**
         * データベースファイル名の定数フィールド。
         */
        private const val DATABASE_NAME = "study_support.db"
        /**
         * バージョン情報の定数フィールド。
         */
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        //テーブル作成用SQL文字列の作成。
        val sb = StringBuilder()
        sb.append("CREATE TABLE qa_set (")
        sb.append("_id INTEGER PRIMARY KEY,")
        sb.append("question TEXT,")
        sb.append("answer TEXT,")
        sb.append("category1 TEXT,")
        sb.append("category2 TEXT,")
        sb.append("category3 TEXT,")
        sb.append("study_times INTEGER,")
        sb.append("register_date TEXT,")
        sb.append("last_study_date TEXT")
        sb.append(");")
        val sql = sb.toString()

        //SQLの実行。
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}
