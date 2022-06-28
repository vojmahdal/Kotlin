package cz.mendelu.pockettraining.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "plans")
data class Plan(
    @ColumnInfo(name = "planName")
    var planName: String){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

}