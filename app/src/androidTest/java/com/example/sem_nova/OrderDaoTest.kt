package com.example.sem_nova

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sem_nova.Data.InventoryDatabase
import com.example.sem_nova.Data.Item
import com.example.sem_nova.Data.ItemDao
import com.example.sem_nova.Data.Order
import com.example.sem_nova.Data.OrderDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class OrderDaoTest {

    private lateinit var orderDao: OrderDao
    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: InventoryDatabase
    private val order1 = Order(1,1,1)
    private val order2 = Order(2, 2,2)
    private val item1 = Item(1, "Apples", 10.0, 20, "A", 2.0)
    private val item2 = Item(2, "Bananas", 15.0, 97, "B", 1.0)

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        orderDao = inventoryDatabase.orderDao()
        itemDao = inventoryDatabase.itemDao()

        // Insert dummy items into the Item table before inserting orders
        runBlocking {
            itemDao.insert(item1)
            itemDao.insert(item2)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        inventoryDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = orderDao.getAllItems().first()
        Assert.assertEquals(allItems[0], order1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = orderDao.getAllItems().first()
        Assert.assertEquals(allItems[0], order1)
        Assert.assertEquals(allItems[1], order2)
    }


    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        addOneItemToDb()
        val item = orderDao.getItem(1)
        Assert.assertEquals(item.first(), order1)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        orderDao.delete(order1)
        orderDao.delete(order2)
        val allItems = orderDao.getAllItems().first()
        Assert.assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateItems_updatesItemsInDB() = runBlocking {
        addTwoItemsToDb()
        orderDao.update(Order(1, 1,10))
        orderDao.update(Order(2, 1,20))

        val allItems = orderDao.getAllItems().first()
        Assert.assertEquals(allItems[0], Order(1, 1,10))
        Assert.assertEquals(allItems[1], Order(2, 1,20))
    }

    private suspend fun addOneItemToDb() {
        orderDao.insert(order1)
    }

    private suspend fun addTwoItemsToDb() {
        orderDao.insert(order1)
        orderDao.insert(order2)
    }
}