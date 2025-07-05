package com.stratizen.core.repository

import com.stratizen.core.data.db.XpDao
import com.stratizen.core.data.model.Xp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class XpRepository(private val xpDao: XpDao) {
    val xp: Flow<Xp?> = xpDao.getXp()

    suspend fun awardXp(pointsToAdd: Int) {
        val current = xpDao.getXp().firstOrNull() ?: Xp()
        val newPoints = current.points + pointsToAdd
        val newLevel = 1 + (newPoints / 100) // level up every 100 points
        xpDao.insertXp(Xp(points = newPoints, level = newLevel))
    }
}