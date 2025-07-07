package com.stratizen.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the user's XP (experience points) state in the Stratizen system.
 *
 * Stored in the `xp_table` Room table.
 * Used to track gamification progress — such as points earned and current level.
 */
@Entity(tableName = "xp_table")
data class Xp(
    /**
     * Singleton primary key.
     *
     * Always set to 1 — only one XP record is stored per user/device to represent overall progress.
     * Acts as a fixed row for easy retrieval and updates.
     */
    @PrimaryKey val id: Int = 1,

    /**
     * Total experience points accumulated by the user.
     *
     * Can be incremented based on completed actions, milestones, or event participation.
     */
    val points: Int = 0,

    /**
     * Current user level derived from XP thresholds.
     *
     * Can be auto-calculated from `points`, or set explicitly based on gamification rules.
     */
    val level: Int = 1
)
