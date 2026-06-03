package exception

sealed class GastoException(mensaje: String) : RuntimeException(mensaje)

class NotFoundException(entidad: String, id: Any) :
    GastoException("$entidad con id $id no encontrado")

class ValidationException(campo: String, razon: String) :
    GastoException("Error en '$campo': $razon")

class RepositoryException(mensaje: String, causa: Throwable? = null) :
    GastoException(mensaje)
