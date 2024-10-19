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

        // Obtenemos vistas (elementos de la interfaz) y asignamos a variables
        val tvPlatoPastelChocloTotal = findViewById<TextView>(R.id.tvPlatoPastelChocloTotal)
        val tvPlatoCazuelaTotal = findViewById<TextView>(R.id.tvPlatoCazuelaTotal)
        val etPlatoPastelChocloCantidad = findViewById<EditText>(R.id.etPlatoPastelChocloCantidad)
        val etPlatoCazuelaCantidad = findViewById<EditText>(R.id.etPlatoCazuelaCantidad)
        val tvComidaValor = findViewById<TextView>(R.id.tvComidaValor)
        val tvPropinaValor = findViewById<TextView>(R.id.tvPropinaValor)
        val tvTotalValor = findViewById<TextView>(R.id.tvTotalValor)
        val swPropina = findViewById<Switch>(R.id.swPropina)

        // Definimos instancias de los platos incluyendo sus precios
        val itemPlatoPastelChoclo = ItemMenu("Pastel de choclo", 12000)
        val itemPlatoCazuela = ItemMenu("Cazuela", 10000)

        // Seteamos los precios definidos de los platos en la interfaz
        tvPlatoPastelChocloTotal.text = formatearPrecio(itemPlatoPastelChoclo.precio)
        tvPlatoCazuelaTotal.text = formatearPrecio(itemPlatoCazuela.precio)

        // Inicializar valores en 0
        tvPlatoPastelChocloTotal.text = formatearPrecio(0)
        tvPlatoCazuelaTotal.text = formatearPrecio(0)
        tvComidaValor.text = formatearPrecio(0)
        tvPropinaValor.text = formatearPrecio(0)
        tvTotalValor.text = formatearPrecio(0)

        // Función que calcula los valores finales y los setea en la interfaz
        fun calcularValores() {
            // Nueva instancia de CuentaMesa
            val cuentaMesa = CuentaMesa(1)
            // Setear propiedad aceptaPropina desde propiedad isChecked del switch
            cuentaMesa.aceptaPropina = swPropina.isChecked
            // Se agregan los platos y sus cantidades a la cuenta
            val platoPastelChocloCantidad = etPlatoPastelChocloCantidad.text.toString().toIntOrNull() ?: 0
            val platoCazuelaCantidad = etPlatoCazuelaCantidad.text.toString().toIntOrNull() ?: 0
            cuentaMesa.agregarItem(itemPlatoPastelChoclo, platoPastelChocloCantidad)
            cuentaMesa.agregarItem(itemPlatoCazuela, platoCazuelaCantidad)
            // Setear valores en los platos
            tvPlatoPastelChocloTotal.text = formatearPrecio(itemPlatoPastelChoclo.precio*platoPastelChocloCantidad)
            tvPlatoCazuelaTotal.text = formatearPrecio(itemPlatoCazuela.precio*platoCazuelaCantidad)
            // Setear valores de calculos finales
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