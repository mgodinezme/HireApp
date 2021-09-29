/*
 * Filename: ItemAdapter.kt
 * Description: Adapter for the RecyclerView that displays the CardViews containing the list IDs
 *              and names of the items of the provided list.
 *
 * @author Mauricio Godinez
 *
 */

package com.mgodinez.hireapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mgodinez.hireapp.databinding.ItemCardViewBinding
import com.mgodinez.hireapp.model.Constants.Companion.PRE_ID_TEXT
import com.mgodinez.hireapp.model.Constants.Companion.PRE_NAME_TEXT
import com.mgodinez.hireapp.model.Item

class ItemAdapter (private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    } // End of method onCreateViewHolder

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(items[position]){
                binding.itemIdTextView.text = PRE_ID_TEXT + this.listId.toString()
                binding.itemNameTextView.text = PRE_NAME_TEXT + this.name
            }
        }
    } // End of method onBindViewHolder

    inner class ViewHolder(val binding: ItemCardViewBinding) : RecyclerView.ViewHolder(binding.root)

} // End of class ItemAdapter