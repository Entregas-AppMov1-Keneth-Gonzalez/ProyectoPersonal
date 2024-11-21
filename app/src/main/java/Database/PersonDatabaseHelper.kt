package Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import Entities.Person
import java.io.ByteArrayOutputStream

class PersonDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "person_database"
        const val DATABASE_VERSION = 1

        const val TABLE_PERSON = "Person"
        const val COLUMN_ID = "Id"
        const val COLUMN_NAME = "Name"
        const val COLUMN_LASTNAME = "LastName"
        const val COLUMN_PHONE = "Phone"
        const val COLUMN_EMAIL = "Email"
        const val COLUMN_ROL = "Rol"
        const val COLUMN_PHOTO = "Photo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_PERSON (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_LASTNAME TEXT NOT NULL,
                $COLUMN_PHONE INTEGER,
                $COLUMN_EMAIL TEXT,
                $COLUMN_ROL TEXT,
                $COLUMN_PHOTO BLOB
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSON")
        onCreate(db)
    }

    fun insertPerson(person: Person): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, person.Id)
            put(COLUMN_NAME, person.Name)
            put(COLUMN_LASTNAME, person.LastName)
            put(COLUMN_PHONE, person.Phone)
            put(COLUMN_EMAIL, person.Email)
            put(COLUMN_ROL, person.Rol)
            put(COLUMN_PHOTO, person.photoBitmap?.let { bitmapToByteArray(it) })
        }
        return db.insert(TABLE_PERSON, null, values)
    }

    fun updatePerson(person: Person): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, person.Name)
            put(COLUMN_LASTNAME, person.LastName)
            put(COLUMN_PHONE, person.Phone)
            put(COLUMN_EMAIL, person.Email)
            put(COLUMN_ROL, person.Rol)
            put(COLUMN_PHOTO, person.photoBitmap?.let { bitmapToByteArray(it) })
        }
        return db.update(TABLE_PERSON, values, "$COLUMN_ID = ?", arrayOf(person.Id))
    }

    fun deletePersonById(id: String): Int {
        val db = writableDatabase
        return db.delete(TABLE_PERSON, "$COLUMN_ID = ?", arrayOf(id))
    }

    fun getPersonById(id: String): Person? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PERSON,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val person = personFromCursor(cursor)
            cursor.close()
            person
        } else {
            cursor.close()
            null
        }
    }

    fun getAllPersons(): List<Person> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PERSON,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val personList = mutableListOf<Person>()
        with(cursor) {
            while (moveToNext()) {
                personList.add(personFromCursor(this))
            }
            close()
        }
        return personList
    }

    private fun personFromCursor(cursor: android.database.Cursor): Person {
        val id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LASTNAME))
        val phone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
        val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
        val rol = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROL))
        val photoBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PHOTO))
        val photoBitmap = photoBytes?.let { byteArrayToBitmap(it) }

        return Person(id, name, lastName, phone, email, rol, photoBitmap)
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun byteArrayToBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}