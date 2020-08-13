package com.example.axxesschallenge.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        /*Set the life cycle owner for data binding*/
        dataBinding.lifecycleOwner = this
        /*Set the view model object for data binding to be set in layout file.*/
        dataBinding.axxessViewModel = axxessViewModel

        /*Get the root view using dataBinding.root*/
        val view = dataBinding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*We are observing @imgurResponseLiveData to populate data in gridview. It will be called as any change happens to @imgurResponseLiveData.*/
        axxessViewModel?.imgurResponseLiveData?.observe(viewLifecycleOwner, Observer {
            //TODO:Showing toast just for testing purpose. Should be removed before releasing the code finally.
            Toast.makeText(requireActivity(), it.status.name, Toast.LENGTH_LONG).show()
            /*Checking if the status is success and we received some data from API. Set the gridview.*/
            if (it.status == Status.SUCCESS) {
                imgurResponse = it.data!!
                imgList = imgurResponse.data
                setGridView()
            }
        })
    }

    private fun setGridView() {
        val gridAdapter = GridViewAdapter(imgList)
        dataBinding.gridViewMain.adapter = gridAdapter

        /*On item click listener used to be navigated to @DetailFragment.*/
        dataBinding.gridViewMain.setOnItemClickListener { adapterView, view, position, id ->
            /*Set the bundle object to be passed to DetailsFragment*/
            val bundleArgs = Bundle().apply {
                putSerializable("imageResponse", imgList[position])
            }
            /*Navigate to the next screen with bundleArgs(Having reference to the object to be shown on the Details screen.)*/
            findNavController().navigate(R.id.toDetailsFragment, bundleArgs)
        }
    }
}