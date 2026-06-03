package service

import model.Gasto
import repository.file.ExportacionCsvRepository

class ExportacionService(private val exportacionCsvRepository: ExportacionCsvRepository) {

    fun exportarGastosACSV(gastos: List<Gasto>, ruta: String) {
        exportacionCsvRepository.exportarGastos(gastos, ruta)
    }
}
