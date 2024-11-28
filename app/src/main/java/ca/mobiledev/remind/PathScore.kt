package ca.mobiledev.remind

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "path_score")
data class PathScore(
    @PrimaryKey(autoGenerate = true) val attemptNo: Int = 0,
    val dateTime: String, // Store as ISO 8601 string or timestamp
    val score: Int,
    val timeTaken: Long // Store in milliseconds
)
