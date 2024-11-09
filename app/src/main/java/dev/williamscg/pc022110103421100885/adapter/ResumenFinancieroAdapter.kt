package dev.williamscg.pc022110103421100885.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.williamscg.pc022110103421100885.R
import dev.williamscg.pc022110103421100885.model.ResumenFinancieroModel
import java.text.NumberFormat
import java.util.Locale

class ResumenFinancieroAdapter(
    private val movimientos: List<ResumenFinancieroModel>
) : RecyclerView.Adapter<ResumenFinancieroAdapter.ResumenViewHolder>() {

    inner class ResumenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFecha: TextView = view.findViewById(R.id.tvFechaResumen)
        val tvMonto: TextView = view.findViewById(R.id.tvMontoTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resumen, parent, false)
        return ResumenViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResumenViewHolder, position: Int) {
        val movimiento = movimientos[position]
        holder.tvFecha.text = movimiento.fecha

        // Formato de monto a moneda
        val formattedMonto = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(movimiento.monto)
        holder.tvMonto.text = formattedMonto
    }

    override fun getItemCount() = movimientos.size
}

