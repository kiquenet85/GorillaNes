package com.nesgorilla.feature.post.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.nesgorilla.R
import com.nesgorilla.base.BaseFragment

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreatePostFragment : BaseFragment() {

    private lateinit var post: EditText
    private lateinit var characters: TextView
    private lateinit var addPhoto: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.create_post_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keyboardResizeMode(activity as Activity)
        post = view.findViewById(R.id.post)
        characters = view.findViewById(R.id.characters)
        addPhoto = view.findViewById(R.id.button)

        addPhoto.apply {
            setOnClickListener {

            }
        }

        post.doAfterTextChanged {
            val size = it.toString().count()
            characters.setText("$size/${105 - size}")
        }
    }
}