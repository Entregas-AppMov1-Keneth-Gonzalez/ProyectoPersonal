package Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import Entities.Comida
import java.io.ByteArrayOutputStream

class ComidaDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "comida_database"
        const val DATABASE_VERSION = 1

        const val TABLE_COMIDA = "Comida"
        const val COLUMN_ID = "Id"
        const val COLUMN_NAME = "Name"
        const val COLUMN_DESCRIPCION = "Descripcion"
        const val COLUMN_PRECIO = "Precio"
        const val COLUMN_PHOTO = "Photo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_COMIDA (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPCION TEXT NOT NULL,
                $COLUMN_PRECIO INTEGER,
                $COLUMN_PHOTO BLOB
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COMIDA")
        onCreate(db)
    }

    fun insertComida(comida: Comida): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, comida.Id)
            put(COLUMN_NAME, comida.Name)
            put(COLUMN_DESCRIPCION, comida.Descripcion)
            put(COLUMN_PRECIO, comida.Precio)
            put(COLUMN_PHOTO, comida.photoBitmap?.let { bitmapToByteArray(it) })
        }
        return db.insert(TABLE_COMIDA, null, values)
    }

    fun updateComida(comida: Comida): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, comida.Name)
            put(COLUMN_DESCRIPCION, comida.Descripcion)
            put(COLUMN_PRECIO, comida.Precio)
            put(COLUMN_PHOTO, comida.photoBitmap?.let { bitmapToByteArray(it) })
        }
        return db.update(TABLE_COMIDA, values, "$COLUMN_ID = ?", arrayOf(comida.Id))
    }

    fun deleteComidaById(id: String): Int {
        val db = writableDatabase
        return db.delete(TABLE_COMIDA, "$COLUMN_ID = ?", arrayOf(id))
    }

    fun getComidaById(id: String): Comida? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_COMIDA,
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

    fun getAllComidas(): List<Comida> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_COMIDA,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val personList = mutableListOf<Comida>()
        with(cursor) {
            while (moveToNext()) {
                personList.add(personFromCursor(this))
            }
            close()
        }
        return personList
    }

    private fun personFromCursor(cursor: android.database.Cursor): Comida {
        val id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
        val precio = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRECIO))
        val photoBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PHOTO))
        val photoBitmap = photoBytes?.let { byteArrayToBitmap(it) }

        return Comida(id, name, descripcion, precio, photoBitmap)
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