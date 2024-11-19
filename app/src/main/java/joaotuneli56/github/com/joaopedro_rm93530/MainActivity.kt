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

class MainActivity : ComponentActivity() {

    private lateinit var adapter: TipoAdapter
    private lateinit var dbHelper: TipoDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val searchView: SearchView = findViewById(R.id.search_view)

        dbHelper = TipoDatabaseHelper(this)
        adapter = TipoAdapter(dbHelper.getTipos())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        dbHelper.clearAllTipos()
        dbHelper.insertTipo("Use lâmpadas LED", "Elas consomem menos energia e duram mais.")
        dbHelper.insertTipo("Desligue aparelhos que não estão em uso", "Aparelhos eletrônicos consomem energia mesmo em modo de espera. Desconecte quando não for usar")
        dbHelper.insertTipo("Regule o termostato", "Manter a temperatura entre 24-26°C economiza energia.")
        dbHelper.insertTipo("Utilize painéis solares", "Energia renovável reduz a conta de luz e o impacto ambiental.")
        dbHelper.insertTipo("Aproveite a luz natural", "Mantenha cortinas abertas durante o dia para economizar iluminação.")
        dbHelper.insertTipo("Desligue o computador", "Quando não estiver em uso, desligue ou utilize o modo de hibernação.")
        adapter.updateList(dbHelper.getTipos())

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
    }

}
