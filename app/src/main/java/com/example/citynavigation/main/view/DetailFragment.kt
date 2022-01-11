package com.example.citynavigation.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.citynavigation.R
import com.example.citynavigation.databinding.FragmentDetailBinding
import com.example.citynavigation.main.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : BaseFragment() {
    private val viewModel by viewModels<DetailViewModel>()
    private val args: DetailFragmentArgs by navArgs()
    private var id = ""
    private var cameraPermissionResultReceiver =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {

            } else {
                Toast.makeText(requireActivity(), "Permission Denied ", Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        viewModel.fetchCityDetails()
    }

    private fun getArgs() {
        id = args.id
        viewModel.id.value = id
    }

    override fun observeLiveData() {
        viewModel.cityDetail.observe(viewLifecycleOwner) {
            name_detail.text = it.name
            population_detail.append(it.populations[0].population)
            description_detail.text = it.description
            Glide.with(this).load(it.image).into(image_detail)
        }
    }
}