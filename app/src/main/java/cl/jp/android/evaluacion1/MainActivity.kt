package cl.jp.android.evaluacion1

import android.icu.text.NumberFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.jp.android.evaluacion1.modelo.CuentaMesa
import cl.jp.android.evaluacion1.modelo.ItemMenu
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtenemos vistas (elementos de la interfaz) y asigamos a variables
        val tvPlatoPastelChocloPrecio = findViewById<TextView>(R.id.tvPlatoPastelChocloPrecio)
        val tvPlatoCazuelaPrecio = findViewById<TextView>(R.id.tvPlatoCazuelaPrecio)
        val etPlatoPastelChocloCantidad = findViewById<EditText>(R.id.etPlatoPastelChocloCantidad)
        val etPlatoCazuelaCantidad = findViewById<EditText>(R.id.etPlatoCazuelaCantidad)
        val tvComidaValor = findViewById<TextView>(R.id.tvComidaValor)
        val tvPropinaValor = findViewById<TextView>(R.id.tvPropinaValor)
        val tvTotalValor = findViewById<TextView>(R.id.tvTotalValor)
        val swPropina = findViewById<Switch>(R.id.swPropina)

        // Definimos instancias de los platos incluyendo sus precios
        val itemPlatoPastelChoclo = ItemMenu("Pastel de choclo", 36000)
        val itemPlatoCazuela = ItemMenu("Cazuela", 10000)

        // Seteamos los precios definidos de los platos en la interfaz
        tvPlatoPastelChocloPrecio.text = formatearPrecio(itemPlatoPastelChoclo.precio)
        tvPlatoCazuelaPrecio.text = formatearPrecio(itemPlatoCazuela.precio)

        // Función que calcula los valores finales y los setea en la interfaz
        fun calcularValores() {
            val cuentaMesa = CuentaMesa(1)
            cuentaMesa.aceptaPropina = swPropina.isChecked
            cuentaMesa.agregarItem(itemPlatoPastelChoclo, etPlatoPastelChocloCantidad.text.toString().toIntOrNull() ?: 0)
            cuentaMesa.agregarItem(itemPlatoCazuela, etPlatoCazuelaCantidad.text.toString().toIntOrNull() ?: 0)
            tvComidaValor.text = formatearPrecio(cuentaMesa.calcularTotalSinPropina())
            tvPropinaValor.text = formatearPrecio(cuentaMesa.calcularPropina())
            tvTotalValor.text = formatearPrecio(cuentaMesa.calcularTotalConPropina())
        }

        // Evento gatillado al modificar los EditTexts de las cantidades
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calcularValores()
            }
        }
        etPlatoPastelChocloCantidad.addTextChangedListener(textWatcher)
        etPlatoCazuelaCantidad.addTextChangedListener(textWatcher)

        // Evento gatillado al momento de checkear o descheckear el switch de propina
        swPropina.setOnCheckedChangeListener { buttonView, isChecked ->
            calcularValores()
        }
    }
}

fun formatearPrecio(numero: Int): String {
    // Función que permite formatear un número a un texto precio (ej: $13.000)
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL")) // Chilean format for example
    return format.format(numero)
}