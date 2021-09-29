/*
 * Filename: MainActivity.kt
 * Description: Main and only activity for this project. It manages the RecyclerView through a
 *              custom adapter named ItemAdapter. The RecyclerView manages Item objects
 *              displaying their list IDs and names. It also manages three actions from the
 *              action bar. These three actions are: "Sync New Data", "Sort By ID", and
 *              "Sort By Name."
 *
 * @author Mauricio Godinez
 *
 */

package com.mgodinez.hireapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mgodinez.hireapp.databinding.ActivityMainBinding
import com.mgodinez.hireapp.model.Constants
import com.mgodinez.hireapp.model.Item
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.*
import okio.IOException
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        // HTTP Client for request
        client = OkHttpClient()

        requestData()
    } // End of method onCreate

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.action_bar_menu, menu)

        return true
    } // End of method onCreateOptionsMenu

    /*
     * Method name: onSyncAction
     * Description: Requests new data from the web.
     * Arguments:   menuItem: The item clicked on the action bar.
     *
     */
    fun onSyncAction(@Suppress(Constants.UNUSED_PARAMETER) menuItem: MenuItem) {
        requestData()
    } // End of method onSyncAction

    /*
     * Method name: onSortByIdAction
     * Description: Sorts the itemsList in ascending order of list IDs.
     * Arguments:   menuItem: The item clicked on the action bar.
     *
     */
    fun onSortByIdAction(@Suppress(Constants.UNUSED_PARAMETER) menuItem: MenuItem) {
        itemAdapter = ItemAdapter(viewModel.itemsList.sortedBy { item -> item.listId })

        this@MainActivity.runOnUiThread {
            binding.hireRecyclerView.adapter = itemAdapter
        }
    } // End of method onSortByIdAction

    /*
     * Method name: onSortByNameAction
     * Description: Sorts the itemsList in alphabetical order.
     * Arguments:   menuItem: The item clicked on the action bar.
     *
     */
    fun onSortByNameAction(@Suppress(Constants.UNUSED_PARAMETER) menuItem: MenuItem) {
        itemAdapter = ItemAdapter(viewModel.itemsList.sortedBy { item -> item.name })

        this@MainActivity.runOnUiThread {
            binding.hireRecyclerView.adapter = itemAdapter
        }
    } // End of method onSortByNameAction

    /*
     * Method name: requestData
     * Description: Requests the JSON array of Items are specified in the Item data class by
     *              sending an HTTP request. When it receives the response from the client,
     *              it parses the JSON array to match the Item data class.
     * Arguments:   None
     *
     */
    private fun requestData() {
        // Request for the array of Items
        val itemsRequest = Request.Builder()
            .url(Constants.HTTP_REQUEST_PATH)
            .get()
            .build()

        // Response received by the HTTP request through a callback
        client.newCall(itemsRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@MainActivity,
                    Constants.RESPONSE_ERROR,
                    Toast.LENGTH_SHORT).show()
            } // End of method onFailure

            override fun onResponse(call: Call, response: Response) {
                // JSON string as the response of the HTTP request
                val jsonString = response.body?.string()

                // Using Moshi to parse the JSON string along with the respective data class
                val moshi: Moshi = Moshi.Builder().build()
                val listOfItemsType: Type = Types.newParameterizedType(
                    MutableList::class.java,
                    Item::class.java
                )
                val adapter: JsonAdapter<List<Item>> = moshi.adapter(listOfItemsType)
                val itemsResponse = adapter.fromJson(jsonString.toString())

                // Getting individual entries from the parsed object as well as preparing
                // them for updating the ItemAdapter
                if (itemsResponse != null) {
                    viewModel.itemsList = viewModel.filterInvalidItems(itemsResponse)

                    itemAdapter = ItemAdapter(viewModel.itemsList.sortedBy { item -> item.listId })

                    this@MainActivity.runOnUiThread {
                        binding.hireRecyclerView.adapter = itemAdapter

                        Toast.makeText(this@MainActivity,
                            Constants.RESPONSE_SUCCESS,
                            Toast.LENGTH_SHORT).show()
                    }
                }
            } // End of method onResponse
        }) // End of randomRequest callback
    } // End of method requestData

} // End of class MainActivity