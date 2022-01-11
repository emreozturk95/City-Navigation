package com.example.citynavigation.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.citynavigation.R
import com.example.citynavigation.databinding.FragmentHomeBinding
import com.example.citynavigation.main.view.adapter.Adapter
import com.example.citynavigation.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var cameraPermissionResultReceiver =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToQrScanFragment()
                Navigation.findNavController(requireView()).navigate(action)
            } else {
                Toast.makeText(requireActivity(), "Permission Denied ", Toast.LENGTH_SHORT).show()
            }
        }
    lateinit var adapter: Adapter
    private val viewModel by viewModels<HomeViewModel>()
    private val args: HomeFragmentArgs by navArgs()
    private var qrCode = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener()
        initRecyclerView()
        initSearchViewListener()
        viewModel.fetchCities()
    }

    private fun initRecyclerView() {
        adapter = Adapter()
        recycler_view.adapter = adapter
    }

    private fun initClickListener() {
        qr_button.setOnClickListener {
            cameraPermissionResultReceiver.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun initSearchViewListener() {
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                searchview.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }


    private fun getArgs(): Boolean {
        if (args.qr.isNotEmpty()) {
            qrCode = args.qr
            return true
        }
        return false
    }

    override fun observeLiveData() {
        viewModel.city.observe(viewLifecycleOwner) {
            adapter.addData(it)
            adapter.notifyDataSetChanged()
            initSearchViewQueryFromQrCode()
        }
    }

    private fun initSearchViewQueryFromQrCode() {
        if (getArgs()) {
            searchview.setQuery(qrCode, true)
        }
    }

}