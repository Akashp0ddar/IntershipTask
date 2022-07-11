package com.akash.intershiptask.fragments.addpost

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akash.intershiptask.R
import com.akash.intershiptask.databinding.FragmentAddPostBinding
import com.akash.intershiptask.model.User
import com.akash.intershiptask.viewmodel.UserViewModel

class AddPostFragment : Fragment() {
    private lateinit var binding: FragmentAddPostBinding
    private lateinit var mUserViewModel: UserViewModel
    private val REQUEST_IMAGE_CAPTURE = 22
    private val galleryImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
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
            galleryImage.launch("image/*")
        }

        binding.btnPost.setOnClickListener {
            insertDataToDatabase()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            try {
                if (data?.extras != null) {
                    Log.e("", "onActivityResult: ${data.extras!!.get("data")}")
                    val imageBitmap = data.extras?.get("data") as Bitmap
                    binding.ivAddPost.setImageBitmap(imageBitmap)
                }
            } catch (e: Exception) {
                Log.e("", "onActivityResult: $e")
            }
        }
    }

    private fun insertDataToDatabase() {
        val postDescription = binding.etDescription.text.toString()

        if (inputCheck(postDescription)) {
            //Create User Object
            val user = User(0,postDescription)
            //Add data to Database
            mUserViewModel.addUser(user)
            //Navigate Back
            findNavController().navigate(R.id.action_addPostFragment_to_homeScreenFragment)

            Toast.makeText(requireContext(), "Successfully Post", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(description: String): Boolean {
        return !(TextUtils.isEmpty(description))
    }
}