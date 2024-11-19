package joaotuneli56.github.com.joaopedro_rm93530

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import joaotuneli56.github.com.joaopedro_rm93530.model.Tipo

class MainActivity : ComponentActivity() {

    private lateinit var adapter: TipoAdapter
    private lateinit var dbHelper: TipoDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val searchView: SearchView = findViewById(R.id.search_view)
        val btnAdd: ImageButton = findViewById(R.id.btn_add)

        dbHelper = TipoDatabaseHelper(this)
        adapter = TipoAdapter(dbHelper.getTipos())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredTipos = dbHelper.getTipos().filter {
                    it.title.contains(newText ?: "", ignoreCase = true)
                }
                adapter.updateList(filteredTipos)
                return true
            }
        })

        if (dbHelper.getTipos().isEmpty()) {
            dbHelper.insertTipo("Use lâmpadas LED", "Elas consomem menos energia e duram mais.")
            dbHelper.insertTipo("Desligue aparelhos", "Evite consumo em standby.")
            adapter.updateList(dbHelper.getTipos())
        }

        btnAdd.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_tipo, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Adicionar Nova Dica")
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btn_save).setOnClickListener {
            val title = dialogView.findViewById<EditText>(R.id.et_title).text.toString()
            val description = dialogView.findViewById<EditText>(R.id.et_description).text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                dbHelper.insertTipo(title, description)
                adapter.updateList(dbHelper.getTipos())
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    fun removeTipo(tipo: Tipo) {
        dbHelper.deleteTipo(tipo.id)
        adapter.updateList(dbHelper.getTipos())
        Toast.makeText(this, "Dica removida!", Toast.LENGTH_SHORT).show()
    }
}
