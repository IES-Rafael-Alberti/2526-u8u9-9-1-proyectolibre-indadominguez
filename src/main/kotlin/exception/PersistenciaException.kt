package exception

class PersistenciaException(
    mensaje: String,
    causa: Throwable? = null
) : ExpenseTrackerException(mensaje, causa)
