package ru.timofeysin.geministore.services.roomSqlManager

import androidx.room.*

@Dao
interface UploadManagerDAO {

    @Query("SELECT * FROM upload_manager_table WHERE NOT complete")
    fun getAllForUpload(): List<UploadManager>?

    @Query("SELECT * FROM upload_manager_table")
    fun getAll(): List<UploadManager>?


    @Query("DELETE  FROM upload_manager_table WHERE complete")
    fun deleteAllComplete()

    @Query("SELECT * FROM upload_manager_table WHERE order_number = :orderNumber AND order_type = :orderType AND NOT complete")
    fun getForOrderNumberAndType(orderNumber:String,orderType:OrderType) : UploadManager?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(uploadManager: UploadManager)

    @Update
    fun update(uploadManager: UploadManager)


}