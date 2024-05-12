import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.memory.Password

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Memory.db"
        private const val TABLE_NAME = "Password"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_PASSWORD TEXT)"
        db.execSQL(createTableQuery)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertPassword(password:  Password){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, password.name)
            put(COLUMN_PASSWORD, password.password)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllPassword(): List<Password> {
        val passwordList = mutableListOf<Password>()
        val  db = readableDatabase
        val  query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))

            val pass = Password(id, name, password)
            passwordList.add(pass)
        }
        cursor.close()
        db.close()
        return passwordList
    }

    fun updatePassword(password: Password){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_NAME, password.name)
            put(COLUMN_PASSWORD, password.password)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(password.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()

    }

    fun getPasswordById(passwordId: Int): Password{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $passwordId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))


        cursor.close()
        db.close()
        return Password(id, name, password)

    }

    fun deletepassword(passwordId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs  = arrayOf(passwordId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    // Add methods for CRUD operations as needed
}