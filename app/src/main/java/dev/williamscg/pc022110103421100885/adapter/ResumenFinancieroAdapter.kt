package dev.williamscg.pc022110103421100885.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.williamscg.pc022110103421100885.R
import dev.williamscg.pc022110103421100885.model.ResumenFinancieroModel

class ResumenFinancieroAdapter (private val resumenFinancieroList: List<ResumenFinancieroModel>) :
    RecyclerView.Adapter<ResumenFinancieroAdapter.ViewHolder>() {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFecha: TextView = itemView.findViewById(R.id.tvFechaResumen)
            val tvMontTot: TextView = itemView.findViewById(R.id.tvMontoTotal)

    }

