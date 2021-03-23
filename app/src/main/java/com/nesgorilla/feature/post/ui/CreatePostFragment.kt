package com.nesgorilla.feature.post.ui

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.nesgorilla.R
import com.nesgorilla.base.BaseFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.File
import java.lang.Exception

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreatePostFragment : BaseFragment() {

    private lateinit var post: EditText
    private lateinit var characters: TextView
    private lateinit var addPhoto: Button
    private lateinit var image: ImageView

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
        image = view.findViewById(R.id.image)

        addPhoto.apply {
            setOnClickListener {
                requestDevicePhoto()
            }
        }

        post.doAfterTextChanged {
            val size = it.toString().count()
            characters.setText("$size/${105 - size}")
        }
    }

    override fun setPickedImageToImageView(filePath: File) {
        super.setPickedImageToImageView(filePath)
        Picasso.get()
            .load(filePath)
            .into(image,object: Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception) {
                   errorHandler.report(e)
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.share, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_share -> {
                //viewModel.selectMultipleItems()
                true
            }
            else -> false
        }
}