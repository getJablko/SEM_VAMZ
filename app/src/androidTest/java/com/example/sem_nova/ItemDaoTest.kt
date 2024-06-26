package com.example.sem_nova


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sem_nova.Data.InventoryDatabase
import com.example.sem_nova.Data.Item
import com.example.sem_nova.Data.ItemDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Testovacia trieda pre tabulku items
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: InventoryDatabase
    private val item1 = Item("Apples", 10.0, 0, "A", 2.0)
    private val item2 = Item("Bananas", 15.0, 0, "B", 1.0)

    /**
     * vytvorenie databázy
     */
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDao = inventoryDatabase.itemDao()
    }

    /**
     * zatvorenie databázy
     */
    @After
    @Throws(IOException::class)
    fun closeDb() {
        inventoryDatabase.close()
    }

    /**
     * testovanie vkladania
     */
    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
    }

    /**
     * testovanie ziskavania udajov
     */
    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    /**
     * vlozenie a overenie itemu/polozky do DB
     */
    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        addOneItemToDb()
        val item = itemDao.getItem("Apples")
        assertEquals(item.first(), item1)
    }

    /**
     * zmazanie a overenie itemu/polozky z DB
     */
    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.delete(item1)
        itemDao.delete(item2)
        val allItems = itemDao.getAllItems().first()
        assertTrue(allItems.isEmpty())
    }

    /**
     * aktualizovanie a overenie itemu/polozky v DB
     */
    @Test
    @Throws(Exception::class)
    fun daoUpdateItems_updatesItemsInDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.upsert2(Item("Apples", 15.0, 25, "C", 3.0),25)
        itemDao.upsert2(Item("Bananas", 5.0, 50, "D", 4.0),50)

        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], Item("Apples", 15.0, 25, "C", 3.0))
        assertEquals(allItems[1], Item("Bananas", 5.0, 50, "D", 4.0))
    }

    /**
     * vlozenie novej polozky/itemu
     */
    private suspend fun addOneItemToDb() {
        itemDao.upsert(item1)
    }

    /**
     * vlozenie novych poloziek/itemov
     */
    private suspend fun addTwoItemsToDb() {
        itemDao.upsert(item1)
        itemDao.upsert(item2)
    }
}
