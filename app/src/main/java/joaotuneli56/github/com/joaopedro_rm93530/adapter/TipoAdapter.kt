package joaotuneli56.github.com.joaopedro_rm93530

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import joaotuneli56.github.com.joaopedro_rm93530.model.Tipo

class TipoAdapter(private var tipos: List<Tipo>) : RecyclerView.Adapter<TipoAdapter.TipoViewHolder>() {

    inner class TipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tipo_title)
        val description: TextView = itemView.findViewById(R.id.tipo_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tipo, parent, false)
        return TipoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipoViewHolder, position: Int) {
        val tipo = tipos[position]
        holder.title.text = tipo.title
        holder.description.text = tipo.description

        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Detalhes: ${tipo.description}",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.itemView.setOnLongClickListener {
            val context = holder.itemView.context
            AlertDialog.Builder(context)
                .setNegativeButton("Não", null)
                .show()
            true
        }
    }

    override fun getItemCount(): Int = tipos.size

    fun updateList(newTipos: List<Tipo>) {
        tipos = newTipos
        notifyDataSetChanged()
    }
}
