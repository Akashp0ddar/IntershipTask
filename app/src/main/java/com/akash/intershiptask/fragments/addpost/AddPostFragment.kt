package com.akash.intershiptask.fragments.addpost

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akash.intershiptask.R
import com.akash.intershiptask.databinding.FragmentAddPostBinding
import com.akash.intershiptask.model.User
import com.akash.intershiptask.viewmodel.UserViewModel
import java.io.File
import java.net.URI


class AddPostFragment : Fragment() {
    private lateinit var binding: FragmentAddPostBinding
    private lateinit var mUserViewModel: UserViewModel

    private lateinit var imageBitmap: Bitmap
    private lateinit var video: Uri
    private var type = 0
    private val REQUEST_VIDEO_CAPTURE = 25


    private val galleryImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            imageBitmap = getImageUri(requireActivity(), it)
            binding.ivAddPost.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPostBinding.inflate(layoutInflater)
        mUserViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick() {
        binding.ivAddPost.setOnClickListener {
            showPopUp()

        }

        binding.btnPost.setOnClickListener {
            insertDataToDatabase()
        }

    }


    private fun insertDataToDatabase() {
        val postDescription = binding.etDescription.text.toString()

        if (inputCheck(postDescription)) {
            //Create User Object

            if (::imageBitmap.isInitialized || ::video.isInitialized) {
                //Add data to Database
                if (type == 0) {
                    mUserViewModel.addUser(
                        User(
                            description = postDescription,
                            imageView = imageBitmap
                        )
                    )

                } else {
                    //Add data to Database
                    mUserViewModel.addUser(User(description = postDescription, video = video.toString()))
                }
            }
            //Navigate Back
            findNavController().navigate(R.id.action_addPostFragment_to_homeScreenFragment)

            Toast.makeText(requireContext(), "Successfully Post", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill all the field", Toast.LENGTH_SHORT).show()

        }
    }

    fun getImageUri(inContext: Context, inImage: Uri): Bitmap {
        val source: ImageDecoder.Source =
            ImageDecoder.createSource(inContext.contentResolver, inImage)
        return ImageDecoder.decodeBitmap(source)
    }

    private fun inputCheck(description: String): Boolean {
        return !(TextUtils.isEmpty(description))
    }

    private fun showPopUp() {

        val popUp = PopupMenu(requireContext(), binding.ivAddPost)
        popUp.inflate(R.menu.popup)
        popUp.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.image -> {
                    galleryImage.launch("image/*")
                    type = 0
                    true
                }
                R.id.video -> {
                    videoFromGallery()
                    type = 1
                    true
                }
                else -> true
            }
        }
        popUp.show()
    }

    private fun videoFromGallery() {
        val flg: Int = Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
                Intent.FLAG_GRANT_READ_URI_PERMISSION
        val i = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        i.setFlags(flg)
        startActivityForResult(i, REQUEST_VIDEO_CAPTURE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            if (data?.data != null) {
                video = data.data!!
                binding.video.visibility = View.VISIBLE
                binding.ivAddPost.visibility = View.GONE
                binding.video.setVideoURI(video)
                binding.video.start()
            }
            Log.e("Video", "${data?.data}: ")
        }

    }

}
