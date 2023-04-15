package com.eyyuperdogan.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.window.OnBackInvokedCallback
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.eyyuperdogan.artbooktesting.R
import com.eyyuperdogan.artbooktesting.databinding.FragmentArtDetailsBinding
import com.eyyuperdogan.artbooktesting.util.Status
import com.eyyuperdogan.artbooktesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide:RequestManager
):Fragment(R.layout.fragment_art_details) {
    private var fregmantBinding:FragmentArtDetailsBinding?=null

     lateinit var viewModel:ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding=FragmentArtDetailsBinding.bind(view)
        fregmantBinding=binding


        subscribeToObserves()

        fregmantBinding?.let {
            it.artImageViewDetail.setOnClickListener {
                findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToApiImageFragment())
            }
        }

        //aktiviteler arasında geri gelmek
        val callback=object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

      binding.saveButton.setOnClickListener {
          viewModel.makeArt(binding.nameTextDetail.text.toString()
              ,binding.artistTextDetail.text.toString()
          ,binding.yearTextDetail.text.toString())
      }
    }

    private fun subscribeToObserves(){

        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            println(url)
            fregmantBinding?.let {
                glide.load(url).into(it.artImageViewDetail)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })

    }

    //hafıza yönetimi için
    override fun onDestroyView() {
        super.onDestroyView()
        fregmantBinding=null
    }
}