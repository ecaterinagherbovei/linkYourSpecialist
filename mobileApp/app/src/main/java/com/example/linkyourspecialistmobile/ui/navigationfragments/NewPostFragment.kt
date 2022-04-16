package com.example.linkyourspecialistmobile.ui.navigationfragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.linkyourspecialistmobile.R
import com.example.linkyourspecialistmobile.data.HomeRepository
import com.example.linkyourspecialistmobile.data.NewPostModel
import com.example.linkyourspecialistmobile.databinding.FragmentNewPostBinding


class NewPostFragment : Fragment() {

    private var homeRepository: HomeRepository? = HomeRepository()
    private lateinit var binding: FragmentNewPostBinding
    private lateinit var userSharedPreferences: SharedPreferences
    private lateinit var createPostButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPostBinding.inflate(inflater, container, false)
        userSharedPreferences = activity?.getSharedPreferences("UserData", 0)!!

        //set spinner elements
        val spinner: Spinner = binding.categoriesSpinner
        ArrayAdapter.createFromResource(
            context!!,
            R.array.posts_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        createPostButton = binding.createPostButton
        createPostButton.setOnClickListener{
            createPost(spinner)
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.newPostFragment, PostsFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        return binding.root
    }

    fun createPost(spinner: Spinner) {
        val serviceName: EditText = binding.serviceName
        val serviceCategory: String = spinner.selectedItem.toString()
        val serviceDescription: EditText = binding.serviceDescription
        val userid: String =
            userSharedPreferences.getString("id", "not found").toString()
        val accessToken: String = "Bearer " +
                userSharedPreferences.getString("access_token", "not logged in").toString()
        val newPostModel = NewPostModel()
        newPostModel.category = serviceCategory
        newPostModel.description = serviceDescription.text.toString()
        newPostModel.name = serviceName.text.toString()
        newPostModel.userID = userid
        homeRepository?.createPost(accessToken,newPostModel)
    }

}
