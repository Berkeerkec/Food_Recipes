package com.berkeerkec.foodrecipe.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkeerkec.foodrecipe.R
import com.berkeerkec.foodrecipe.adapters.RecipesAdapter
import com.berkeerkec.foodrecipe.databinding.FragmentRecipeBinding
import com.berkeerkec.foodrecipe.util.Constant.Companion.API_KEY
import com.berkeerkec.foodrecipe.util.Resource
import com.berkeerkec.foodrecipe.viewmodel.RecipesViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeFragment @Inject constructor(
    private val adapter : RecipesAdapter
): Fragment() {

    private var fragmentBinding : FragmentRecipeBinding? = null
    lateinit var viewModel : RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRecipeBinding.bind(view)
        fragmentBinding = binding

        viewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        readDatabase()


    }

    private fun readDatabase() {

        lifecycleScope.launch {
            viewModel.readRecipes.observe(viewLifecycleOwner, Observer {database ->

                if (database.isNotEmpty()){
                    adapter.results = database[0].foodRecipe.results
                    fragmentBinding?.shimmerFrameLayout?.visibility = View.GONE
                    fragmentBinding?.recyclerView?.visibility = View.VISIBLE

                    println("Database running")
                }else{
                    observerViewModel()
                }
            })
        }
    }

    private fun observerViewModel(){
        println("RemoteData running")
        viewModel.recipe.observe(viewLifecycleOwner, Observer { response ->

            when(response){
                is Resource.Success -> {
                    fragmentBinding?.shimmerFrameLayout?.visibility = View.GONE
                    fragmentBinding?.recyclerView?.visibility = View.VISIBLE
                    response.data.let {
                        adapter.results = it?.results!!
                    }
                }
                is Resource.Error -> {
                    fragmentBinding?.shimmerFrameLayout?.visibility = View.GONE
                    fragmentBinding?.recyclerView?.visibility = View.VISIBLE
                    loadDataFromChance()
                    Toast.makeText(requireContext(), response.message?.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    fragmentBinding?.recyclerView?.visibility = View.GONE
                    fragmentBinding?.shimmerFrameLayout?.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun loadDataFromChance(){

        lifecycleScope.launch {
            viewModel.readRecipes.observe(viewLifecycleOwner, Observer { database ->
                if (database.isNotEmpty()){
                    adapter.results = database[0].foodRecipe.results
                }
            })
        }
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }



}