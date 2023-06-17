package com.berkeerkec.foodrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.berkeerkec.foodrecipe.databinding.RecipesRowBinding
import com.berkeerkec.foodrecipes.model.Result

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val binding : RecipesRowBinding) : ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var results : List<Result>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = RecipesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        val result = results[position]

        holder.binding.titleTextView.text = result.title
        holder.binding.descriptionTextView.text = result.summary
        holder.binding.favoriteTextView.text = result.aggregateLikes.toString()
        holder.binding.clockTextView.text = result.readyInMinutes.toString()

        if (result.vegan == true){

        }else{

        }


    }
}