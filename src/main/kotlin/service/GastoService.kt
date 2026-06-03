package service

import exception.ValidacionException
import model.Gasto
import repository.IGastoRepository
import validator.GastoValidator
import java.time.LocalDate

class GastoService(private val gastoRepository: IGastoRepository) {

    fun registrar(descripcion: String, monto: Double, fecha: LocalDate, categoriaId: Long): Gasto {
        val gasto = Gasto(descripcion = descripcion, monto = monto, fecha = fecha, categoriaId = categoriaId)
        GastoValidator.validar(gasto)
        return gastoRepository.save(gasto)
    }

    fun buscarPorId(id: Long): Gasto? {
        return gastoRepository.findById(id)
    }

    fun listarTodos(): List<Gasto> {
        return gastoRepository.findAll()
    }

    fun actualizar(id: Long, descripcion: String, monto: Double, fecha: LocalDate, categoriaId: Long): Gasto {
        val existente = gastoRepository.findById(id)
            ?: throw ValidacionException("No existe gasto con id $id")
        val actualizado = existente.copy(
            descripcion = descripcion,
            monto = monto,
            fecha = fecha,
            categoriaId = categoriaId
        )
        GastoValidator.validar(actualizado)
        return gastoRepository.update(actualizado)
    }

    fun eliminar(id: Long) {
        gastoRepository.delete(id)
    }

    fun listarPorCategoria(categoriaId: Long): List<Gasto> {
        return gastoRepository.findByCategoriaId(categoriaId)
    }

    fun listarPorMontoSuperior(monto: Double): List<Gasto> {
        return gastoRepository.findByMontoGreaterThan(monto)
    }
}
