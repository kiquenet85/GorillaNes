package com.nesgorilla.feature.post.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.nesgorilla.R
import com.nesgorilla.base.BaseFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.File

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreatePostFragment : BaseFragment() {

    private lateinit var post: EditText
    private lateinit var characters: TextView
    private lateinit var addPhoto: Button
    private lateinit var image: ImageView
    private var file: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.create_post_fragment, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keyboardResizeMode()
        post = view.findViewById(R.id.post)
        characters = view.findViewById(R.id.characters)
        addPhoto = view.findViewById(R.id.button)
        image = view.findViewById(R.id.image)

        characters.text = "0/$MAX_POST_SIZE"
        addPhoto.apply {
            setOnClickListener {
                requestDevicePhoto()
            }
        }

        post.doAfterTextChanged {
            val size = it.toString().count()
            characters.text = "$size/${MAX_POST_SIZE - size}"
        }
    }

    override fun setPickedImageToImageView(file: File) {
        super.setPickedImageToImageView(file)
        this.file = file
        Picasso.get()
            .load(file)
            .into(image, object : Callback {
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
                shareData()
                true
            }
            else -> false
        }

    private fun shareData() {
        if (post.text.trim().count() > 0 || file != null) {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.text)

                putExtra(Intent.EXTRA_TITLE, getString(R.string.post_title))

                file?.let {
                    data = FileProvider.getUriForFile(
                        requireContext(),
                        requireContext().applicationContext.packageName + ".provider",
                        it
                    )
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
            }, null)
            startActivity(share)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.please_share_post_to_send),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        keyboardHidden()
    }

    companion object {
        const val MAX_POST_SIZE = 105
    }
}