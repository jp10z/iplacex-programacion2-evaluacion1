package cl.jp.android.evaluacion1.modelo

class CuentaMesa (val mesa: Int) {
    // propiedades
    private val _items: MutableList<ItemMesa> = mutableListOf()
    var aceptaPropina: Boolean = true

    // constantes
    val propinaPorcentaje: Float = 10.0F

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        // Agrego directamente itemMesa con el itemMenu y cantidad proporcionada
        _items.add(ItemMesa(cantidad, itemMenu))
    }

    fun agregarItem(itemMenu: ItemMenu) {
        // Agrego el item utilizando la función anterior, pasando siempre la cantidad en 1
        agregarItem(itemMenu, 1)
    }

    fun calcularTotalSinPropina(): Int {
        // Inicializo variable para almacenar el total
        var total = 0
        // Recorro todos los items y obtengo el total de cada uno
        for(itemMesa in _items) {
            total += itemMesa.calcularSubtotal()
        }
        // Retorno el total
        return total
    }

    fun calcularPropina(): Int {
        // Obtengo el total sin propina de la función anterior y calculo la propina
        // Si no acepta propina que directamente retorne 0, en caso contrario que obtenga el calculo
        if (!aceptaPropina) return 0
        val totalSinPropina = calcularTotalSinPropina()
        return obtenerPropina(totalSinPropina)
    }

    fun calcularTotalConPropina(): Int {
        // Reutilizaré las 2 funciones anteriores y sumaré sus resultados
        val totalSinPropina: Int = calcularTotalSinPropina()
        val totalPropina: Int = obtenerPropina(totalSinPropina)
        return totalSinPropina + totalPropina
    }

    private fun obtenerPropina(valorTotal: Int): Int {
        // Recibo el valor y calculo la propina, uso esta función para no repetir la lógica
        // en las funciones de calcularPropina y calcularTotalConPropina
        // Si no acepta propina que directamente retorne 0, en caso contrario que calcule
        if (!aceptaPropina) return 0
        return (valorTotal * (propinaPorcentaje / 100)).toInt()
    }

}