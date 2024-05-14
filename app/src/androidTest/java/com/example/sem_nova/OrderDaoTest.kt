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

/**
 * Testovacia trieda pre tabulku orders
 * Upravený kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */
@RunWith(AndroidJUnit4::class)
class OrderDaoTest {

    private lateinit var orderDao: OrderDao
    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: InventoryDatabase
    private val order1 = Order(1, "Apples", 1, true)
    private val order2 = Order(2, "Bananas", 2, true)
    private val item1 = Item("Apples", 10.0, 20, "A", 2.0)
    private val item2 = Item("Bananas", 15.0, 97, "B", 1.0)

    /**
     * vytvorenie databázy spolu s items
     */
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        orderDao = inventoryDatabase.orderDao()
        itemDao = inventoryDatabase.itemDao()

        // vloženie itemov predtym ako vlozime objednavku - relačne obmedzenie
        runBlocking {
            itemDao.upsert(item1)
            itemDao.upsert(item2)
        }
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
    fun daoInsert_insertsOrderIntoDB() = runBlocking {
        addOneOrderToDb()
        val allOrders = orderDao.getAllItems().first()
        Assert.assertEquals(allOrders[0], order1)
    }

    /**
     * testovanie ziskavania udajov
     */
    @Test
    @Throws(Exception::class)
    fun daoGetAllOrders_returnsAllOrdersFromDB() = runBlocking {
        addTwoOrdersToDb()
        val allOrders = orderDao.getAllItems().first()
        Assert.assertEquals(allOrders[0], order1)
        Assert.assertEquals(allOrders[1], order2)
    }

    /**
     * vlozenie a overenie objednavky do DB
     */
    @Test
    @Throws(Exception::class)
    fun daoGetOrder_returnsOrdersFromDB() = runBlocking {
        addOneOrderToDb()
        val order = orderDao.getItem(1)
        Assert.assertEquals(order.first(), order1)
    }

    /**
     * zmazanie a overenie objednavky z DB
     */
    @Test
    @Throws(Exception::class)
    fun daoDeleteOrders_deletesAllOrdersFromDB() = runBlocking {
        addTwoOrdersToDb()
        orderDao.delete(order1)
        orderDao.delete(order2)
        val allOrders = orderDao.getAllItems().first()
        Assert.assertTrue(allOrders.isEmpty())
    }

    /**
     * aktualizovanie a overenie objednavky v DB
     */
    @Test
    @Throws(Exception::class)
    fun daoUpdateOrderes_updatesOrdersInDB() = runBlocking {
        addTwoOrdersToDb()
        orderDao.update(Order(1, "Apples", 10, true))
        orderDao.update(Order(2, "Apples", 20, true))

        val allOrders = orderDao.getAllItems().first()
        Assert.assertEquals(allOrders[0], Order(1, "Apples", 10, true))
        Assert.assertEquals(allOrders[1], Order(2, "Apples", 20, true))
    }

    /**
     * vlozenie novej objednavky
     */
    private suspend fun addOneOrderToDb() {
        orderDao.insert(order1)
    }

    /**
     * vlozenie novych objednavok
     */
    private suspend fun addTwoOrdersToDb() {
        orderDao.insert(order1)
        orderDao.insert(order2)
    }
}