package cl.jp.android.evaluacion1.modelo

class ItemMesa (
    val cantidad: Int,
    val itemMenu: ItemMenu
) {

    fun calcularSubtotal(): Int {
        // Se calcula multiplicando la cantidad por el precio del item
        return cantidad * itemMenu.precio
    }
}