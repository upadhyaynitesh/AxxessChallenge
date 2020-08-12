package com.example.axxesschallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.axxesschallenge.R
import com.example.axxesschallenge.databinding.FragmentMainBinding
import com.example.axxesschallenge.model.ImageResponse
import com.example.axxesschallenge.model.ImgurResponse
import com.example.axxesschallenge.networking.Status
import com.example.axxesschallenge.ui.adapter.GridViewAdapter
import com.example.axxesschallenge.viewmodel.AxxessViewModel


class MainFragment : Fragment() {
    private var axxessViewModel: AxxessViewModel? = AxxessViewModel()
    private lateinit var imgurResponse: ImgurResponse
    private lateinit var imgList: List<ImageResponse>
    private lateinit var dataBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        val view = dataBinding.root
        dataBinding.lifecycleOwner = this
        dataBinding.axxessViewModel = axxessViewModel
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val annexxviewModel = ViewModelProvider(this).get(AxxessViewModel::class.java)

        dataBinding.buttonSearch.setOnClickListener {
            dataBinding.progressBar.visibility = View.VISIBLE
            val searchString = dataBinding.editTextSearch.text.toString()
            annexxviewModel.setImgurResponse(searchString)
        }

        annexxviewModel.imgurResponseLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it.status.name, Toast.LENGTH_LONG).show()
            if (it.status == Status.SUCCESS) {
                dataBinding.progressBar.visibility = View.GONE
                imgurResponse = it.data!!
                imgList = imgurResponse.data
                setGridView()
            }
        })
    }

    private fun setGridView() {
        val gridAdapter = GridViewAdapter(imgList)
        dataBinding.gridViewMain.adapter = gridAdapter

        dataBinding.gridViewMain.setOnItemClickListener { adapterView, view, position, id ->
            val bundleArgs = Bundle().apply {
                putSerializable("imageResponse", imgList[position])
            }
            findNavController().navigate(R.id.toDetailsFragment, bundleArgs)
        }
    }
}