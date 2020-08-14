package com.example.axxesschallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.axxesschallenge.R
import com.example.axxesschallenge.databinding.FragmentMainBinding
import com.example.axxesschallenge.model.ImageResponse
import com.example.axxesschallenge.model.ImgurResponse
import com.example.axxesschallenge.networking.Status
import com.example.axxesschallenge.ui.adapter.GridViewAdapter
import com.example.axxesschallenge.utils.Constants
import com.example.axxesschallenge.utils.Utils.hideKeyboard
import com.example.axxesschallenge.viewmodel.AxxessViewModel

class MainFragment : Fragment() {
    private lateinit var axxessViewModel: AxxessViewModel
    private lateinit var imgurResponse: ImgurResponse
    private lateinit var imgList: List<ImageResponse>
    private lateinit var dataBinding: FragmentMainBinding
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = SavedStateViewModelFactory(requireActivity().application, this)
        axxessViewModel = ViewModelProvider(requireActivity(), factory)
            .get(AxxessViewModel::class.java)

        if (rootView == null) {
            // Inflate the layout for this fragment
            dataBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false
            )
            /*Set the life cycle owner for data binding*/
            dataBinding.lifecycleOwner = this
            /*Set the view model object for data binding to be set in layout file.*/
            dataBinding.axxessViewModel = axxessViewModel

            /*Get the root view using dataBinding.root*/
            rootView = dataBinding.root
            dataBinding.editTextSearch.requestFocus()

        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        /*We are observing @imgurResponseLiveData to populate data in gridview. It will be called as any change happens to @imgurResponseLiveData.*/
        axxessViewModel.imgurResponseLiveData.observe(viewLifecycleOwner, Observer {
            //TODO:Showing toast just for testing purpose. Should be removed before releasing the code finally.
            Toast.makeText(requireActivity(), it.status.name, Toast.LENGTH_LONG).show()
            /*Checking if the status is success and we received some data from API. Set the gridview.*/
            if (it.status == Status.SUCCESS) {
                imgurResponse = it.data!!
                if (imgurResponse.data.isNotEmpty()) {
                    imgList = imgurResponse.data
                    setGridView()
                } else {
                    showAlertDialog(
                        resources.getString(R.string.error_title),
                        resources.getString(R.string.no_data_error_message),
                        resources.getString(R.string.ok_button)
                    )
                }
            }
            if (it.status == Status.ERROR) {
                if (it.message == Constants.INTERNET_ERROR) {
                    showAlertDialog(
                        resources.getString(R.string.error_title),
                        resources.getString(R.string.internet_connection_error),
                        resources.getString(R.string.ok_button)
                    )
                } else {
                    showAlertDialog(
                        resources.getString(R.string.error_title),
                        resources.getString(R.string.something_went_wrong),
                        resources.getString(R.string.ok_button)
                    )
                }
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
            dataBinding.editTextSearch.setText("")
            dataBinding.editTextSearch.hideKeyboard()
            /*Navigate to the next screen with bundleArgs(Having reference to the object to be shown on the Details screen.)*/
            findNavController().navigate(R.id.toDetailsFragment, bundleArgs)
        }
    }

    private fun showAlertDialog(
        title: String,
        message: String,
        positiveButton: String
    ) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            positiveButton
        ) { dialog, id ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }
}