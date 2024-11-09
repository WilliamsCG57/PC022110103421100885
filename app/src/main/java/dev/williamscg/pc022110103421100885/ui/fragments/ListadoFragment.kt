package dev.williamscg.pc022110103421100885.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.williamscg.pc022110103421100885.R
import dev.williamscg.pc022110103421100885.adapter.ResumenFinancieroAdapter
import dev.williamscg.pc022110103421100885.model.ResumenFinancieroModel

class ListadoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ResumenFinancieroAdapter
    private lateinit var movimientos: MutableList<ResumenFinancieroModel>
    private lateinit var tvBalanceTotal: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listado, container, false)

        tvBalanceTotal = view.findViewById(R.id.tvMontoTotal)
        recyclerView = view.findViewById(R.id.rvResumenFinanciero)
        recyclerView.layoutManager = LinearLayoutManager(context)

        movimientos = mutableListOf()
        adapter = ResumenFinancieroAdapter(movimientos)
        recyclerView.adapter = adapter

        verificarUsuario()

        return view
    }

    private fun verificarUsuario() {
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid

        if (userId == null) {
            // Si el usuario no está autenticado, redirige al LoginFragment
            Toast.makeText(context, "Usuario no autenticado, redirigiendo al login...", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.nav_listado) // Aquí el ID de acción debe coincidir con el definido en el archivo nav_graph.xml
        } else {
            // Si el usuario está autenticado, carga los datos de los movimientos
            cargarMovimientos(userId)
        }
    }

    private fun cargarMovimientos(userId: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("Usuarios").document(userId).collection("Movimientos")
            .get()
            .addOnSuccessListener { documents ->
                movimientos.clear()
                var balanceTotal = 0.0  // Inicializar el balance total

                for (document in documents) {
                    val fecha = document.getString("fecha") ?: ""
                    val monto = document.getDouble("monto") ?: 0.0

                    movimientos.add(ResumenFinancieroModel(fecha, monto))
                    balanceTotal += monto  // Sumar el monto al balance total
                }

                adapter.notifyDataSetChanged()
                tvBalanceTotal.text = "Balance total: $balanceTotal"  // Mostrar el balance total
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al cargar datos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
