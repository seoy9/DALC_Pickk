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
class RecommendMenuDBHelper(private val context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    // 홍서연 - RecommendMenuData.db를 recommendMenuData.db로 바꿈
    private val DB_NAME = "recommendMenuData.db"

    override fun onCreate(db: SQLiteDatabase?) {
        val create = "create table recommendMenu (result text primary key, menuId1 integer, menuId2 integer, menuId3 integer)"

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
    fun recommMenu(id : String):RecommendMenu{
        val strId = id
        val selectAll = "select * from recommendMenu where result = $strId"
        val db = openDatabase()
        val cursor = db.rawQuery(selectAll,null)

        var menuIdList = RecommendMenu(0, 0, 0)
        //반복문을 사용하여 list 에 데이터를 넘겨 줍시다.
        while(cursor.moveToNext()){
            val menuId1 = cursor.getInt(1)
            val menuId2 = cursor.getInt(2)
            val menuId3 = cursor.getInt(3)


            menuIdList = RecommendMenu(menuId1, menuId2, menuId3)


        }
        cursor.close()
        db.close()

        return menuIdList
    }


}