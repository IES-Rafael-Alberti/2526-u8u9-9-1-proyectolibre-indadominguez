package repository

import model.LogEvento

interface ILogRepository : IRepository<LogEvento, String> {
    fun findByTipo(tipo: String): List<LogEvento>
}