package com.example.studysupportmobile

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.format.Time
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // データベースヘルパーオブジェクト。
    private val _helper = DatabaseHelper(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        val executeButton = findViewById<View>(R.id.execute_button)

        executeButton.setOnClickListener(ButtonClicker())

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        //ヘルパーオブジェクトの開放。
        _helper.close()
        super.onDestroy()
    }

    private inner class ButtonClicker : View.OnClickListener {
        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        val db = _helper.writableDatabase

        override fun onClick(view: View) {
            val sqlInsert = "INSERT INTO qa_set (_id, question, answer, category1, category2, category3, study_times, register_date, last_study_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            //SQL文字列を元にプリペアドステートメントを取得
            val stmt = db.compileStatement(sqlInsert)
            //変数のバインド
            val question = findViewById(R.id.question) as EditText
            val answer = findViewById(R.id.answer) as EditText
            val id = getLastId() + 1
            val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            val time = simpleDateFormat.format(Date(System.currentTimeMillis()))
            stmt.bindLong(1, id.toLong())
            stmt.bindString(2, "${question.text}")
            stmt.bindString(3, "${answer.text}")
            stmt.bindString(4, "プログラミング")
            stmt.bindString(5, "Kotlin")
            stmt.bindString(6, "DB")
            stmt.bindLong(7, 1)
            stmt.bindString(8, time)
            stmt.bindString(9, time)
            //SQLの実行。
            stmt.executeInsert()

//            // INSERTしたデータをSELECT
//            val sql = "SELECT * FROM qa_set WHERE _id = 2"
//            // SQLの実行
//            val cursor = db.rawQuery(sql, null)
//            var question = ""
//            var answer = ""
//            while(cursor.moveToNext()) {
//                val idxQuestion = cursor.getColumnIndex("question")
//                question = cursor.getString(idxQuestion)
//                val idxAnswer = cursor.getColumnIndex("answer")
//                answer = cursor.getString(idxAnswer)
//                Log.d("sql", "${question}　→　${answer}")
//            }
        }

        /**
         * qa_setテーブルの末尾のID(最大値)を返す
         */
        private fun getLastId(): Int {
            val cursor = db.rawQuery("SELECT MAX(_id) FROM qa_set", null)
            if (cursor.moveToNext()) {
                return cursor.getInt(0)
            } else {
                // qa_setテーブルにデータがないため、0を返す
                return 0
            }
        }
    }
}
