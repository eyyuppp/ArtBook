package com.eyyuperdogan.artbooktesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eyyuperdogan.artbooktesting.R
import com.eyyuperdogan.artbooktesting.adapter.ArtRecyclerAdapter
import com.eyyuperdogan.artbooktesting.adapter.ImageRecyclerAdapter
import com.eyyuperdogan.artbooktesting.databinding.FragmentArtBinding
import com.eyyuperdogan.artbooktesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
) :Fragment(R.layout.fragment_art){
    private  var artBinding:FragmentArtBinding?=null
    lateinit var viewModel:ArtViewModel

    private val swipeCallBack=object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition=viewHolder.layoutPosition
            val selectedImage=artRecyclerAdapter.artsList[layoutPosition]
            viewModel.deleteArt(selectedImage)
        }



    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
       val binding=FragmentArtBinding.bind(view)
        artBinding=binding

        subscribeToObservers()

        binding.recyclerViewArt.adapter=artRecyclerAdapter
        binding.recyclerViewArt.layoutManager=LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
        println("artFragment")
    }

    private fun subscribeToObservers(){
     viewModel.artList.observe(viewLifecycleOwner, Observer {
         artRecyclerAdapter.artsList=it

     })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        artBinding=null
    }


}