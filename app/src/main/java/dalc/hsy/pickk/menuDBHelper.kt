package dalc.hsy.pickk

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

// SQLiteOpenHelper 상속받아 SQLite 를 사용하도록 하겠습니다.
class menuDBHelper(private val context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    private val DB_NAME = "menuData.db"

    override fun onCreate(db: SQLiteDatabase?) {
        val create = "create table menu (id integer primary key, cafe_name1 NUMERIC, menu_name text, image text, description text, calorie text, saturatedFat integer, protein integer, sugars integer, amountofcaffein integer, color integer, cafeName text, cafe_name2 text)"

        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }



    fun openDatabase(): SQLiteDatabase {
        val dbFile = context!!.getDatabasePath(DB_NAME)


        if (!dbFile.exists()) {
            try {
                val checkDB = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null)

                checkDB?.close()
                copyDatabase(dbFile)
            } catch (e: IOException) {
                throw RuntimeException("Error creating source database", e)
            }
            Log.d("#DB", "not exits")
        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    @SuppressLint("WrongConstant")
    private fun copyDatabase(dbFile: File) {
        val `is` = context!!.assets.open(DB_NAME)
        val os = FileOutputStream(dbFile)

        val buffer = ByteArray(1024)
        while (`is`.read(buffer) > 0) {
            os.write(buffer)
            Log.d("#DB", "writing>>")
        }

        os.flush()
        os.close()
        `is`.close()
        Log.d("#DB", "completed..")
    }

    //select 메소드
    fun selectMenu(id : Int):Menu{
        val strId = id.toString()
        val selectAll = "select * from menuData where id = $strId"
        val db = openDatabase()
        val cursor = db.rawQuery(selectAll,null)

        var menuInfo = Menu("","","","", 0, 0, 0, 0, 0, "")
        //반복문을 사용하여 list 에 데이터를 넘겨 줍시다.
        while(cursor.moveToNext()){
            val menuName = cursor.getString(2)
            val image = cursor.getString(3)
            val desc = cursor.getString(4)
            val calorie = cursor.getString(5)
            val saturatedFat = cursor.getInt(6)
            val protein = cursor.getInt(7)
            val sugars = cursor.getInt(8)
            val caffeine = cursor.getInt(9)
            val color = cursor.getInt(10)
            val cafeName = cursor.getString(11)

            menuInfo = Menu(menuName, image, desc, calorie, saturatedFat, protein, sugars, caffeine, color, cafeName)


        }
        cursor.close()
        db.close()

        return menuInfo
    }


}