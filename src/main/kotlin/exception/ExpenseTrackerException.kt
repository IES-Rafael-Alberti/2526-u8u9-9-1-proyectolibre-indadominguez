package exception

abstract class ExpenseTrackerException(
    mensaje: String,
    causa: Throwable? = null
) : Exception(mensaje, causa)
