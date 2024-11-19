package joaotuneli56.github.com.joaopedro_rm93530

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import joaotuneli56.github.com.joaopedro_rm93530.model.Tipo

class TipoDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "TiposDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Tipos (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Tipos")
        onCreate(db)
    }

    fun insertTipo(title: String, description: String) {
        writableDatabase.execSQL("INSERT INTO Tipos (title, description) VALUES (?, ?)", arrayOf(title, description))
    }

    fun getTipos(): List<Tipo> {
        val tipos = mutableListOf<Tipo>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM Tipos", null)
        if (cursor.moveToFirst()) {
            do {
                tipos.add(
                    Tipo(
                        id = cursor.getInt(0),
                        title = cursor.getString(1),
                        description = cursor.getString(2)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tipos
    }

    fun clearAllTipos() {
        val db = writableDatabase
        db.execSQL("DELETE FROM Tipos")
        db.close()
    }
}
