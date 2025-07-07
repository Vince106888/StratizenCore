package com.stratizen.core.repository

import com.stratizen.core.data.db.XpDao
import com.stratizen.core.data.model.Xp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

/**
 * Repository for managing XP (experience points) and leveling logic.
 * Provides a clean abstraction over [XpDao].
 *
 * @property xpDao Data access object for XP table.
 */
class XpRepository(private val xpDao: XpDao) {

    /**
     * Flow of current XP and level (can be observed by UI or ViewModel).
     */
    val xp: Flow<Xp?> = xpDao.getXp()

    /**
     * Awards XP to the user and automatically recalculates level.
     * Level increases by 1 every 100 points.
     *
     * If no XP record exists, it creates a new one.
     *
     * @param pointsToAdd Number of points to add to the current XP.
     */
    suspend fun awardXp(pointsToAdd: Int) {
        // Get current XP or default to 0 XP and level 1
        val currentXp = xpDao.getXp().firstOrNull() ?: Xp()

        // Add new points and recalculate level
        val totalPoints = currentXp.points + pointsToAdd
        val newLevel = 1 + (totalPoints / 100)

        // Save updated XP record
        xpDao.insertXp(
            Xp(points = totalPoints, level = newLevel)
        )
    }
}
