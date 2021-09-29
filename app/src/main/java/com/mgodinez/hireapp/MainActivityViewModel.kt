/*
 * Filename: MainActivityViewModel.kt
 * Description: Implements the VM in the MVVM pattern. Stores and maintains the data in the
 *              itemsList as well as providing the data method to filter out the NULL and
 *              empty names in the items in the itemsList.
 *
 * @author Mauricio Godinez
 *
 */

package com.mgodinez.hireapp

import androidx.lifecycle.ViewModel
import com.mgodinez.hireapp.model.Item

class MainActivityViewModel: ViewModel() {

    lateinit var itemsList: List<Item>

    /*
     * Method Name: filterInvalidItems
     * Description: Filters out all the NULL and empty string names in the items in the items
     *              list.
     * Arguments:   items: The list containing the elements to be filtered.
     * Return:      A list without NULL or empty string names in the items list.
     */
    fun filterInvalidItems(items: List<Item>): List<Item> {
        val validItems = arrayListOf<Item>()

        for (item in items) {
            if (!item.name.equals("") && item.name != null) validItems.add(item)
        }

        return validItems
    } // End of method filterInvalidItems

} // End of class MainActivityViewModel