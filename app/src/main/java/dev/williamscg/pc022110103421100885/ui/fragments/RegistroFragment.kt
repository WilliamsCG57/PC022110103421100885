package dev.williamscg.pc022110103421100885.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.williamscg.pc022110103421100885.R

class RegistroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_registro, container, false)

        val etFecha: EditText = view.findViewById(R.id.etFecha)
        val etDescripcion: EditText = view.findViewById(R.id.etDescripcion)
        val etMonto: EditText = view.findViewById(R.id.etMonto)
        val btnGuardarRegistro: Button = view.findViewById(R.id.btnGuardarRegistro)
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()


        btnGuardarRegistro.setOnClickListener {
            val fecha = etFecha.text.toString()
            val descripcion = etDescripcion.text.toString()
            val montoText = etMonto.text.toString()


        if (fecha.isEmpty() || descripcion.isEmpty() || montoText.isEmpty()) {
            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }

        // Convertir el monto a Double
        val monto = montoText.toDoubleOrNull()
        if (monto == null) {
            Toast.makeText(context, "El monto debe ser un número válido", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }

        // Obtener el ID del usuario autenticado
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(context, "Error: usuario no autenticado", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }

        // Crear el objeto de datos a guardar
        val data = hashMapOf(
            "fecha" to fecha,
            "descripcion" to descripcion,
            "monto" to montoText
        )

        // Guardar el registro en la subcolección "Movimientos" del usuario
        db.collection("Usuarios").document(userId).collection("Movimientos")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(context, "Registro guardado exitosamente", Toast.LENGTH_SHORT).show()
                // Limpiar los campos después de guardar
                etFecha.text.clear()
                etDescripcion.text.clear()
                etMonto.text.clear()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    return view
}
}





