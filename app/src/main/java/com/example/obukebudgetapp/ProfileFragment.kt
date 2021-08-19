package com.example.obukebudgetapp

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.CameraPromptDialogFragment
import java.io.File

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var profileImage: ImageView
    lateinit var changeProfileImage: ImageView

    private var cameraPhotoUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage = view.findViewById(R.id.profileImage)
        changeProfileImage = view.findViewById(R.id.changeProfileIcon)
        changeProfileImage.setOnClickListener {
            showDialogFragment()
        }
    }

    private fun showDialogFragment() {
        val messageDialogFragment =
            CameraPromptDialogFragment.newInstance(onCameraClickListener, onGalleryClickListener)
        messageDialogFragment.show(childFragmentManager, "dialog_fragment")
    }

    private val onGalleryClickListener = View.OnClickListener { selectImageFromGallery() }

    private val onCameraClickListener = View.OnClickListener { takeImage() }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { profileImage.setImageURI(it) }
        }

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                cameraPhotoUri?.let {
                    profileImage.setImageURI(it)
                }
            }
        }

    private fun takeImage() {
        cameraPhotoUri = getTemporaryFileUri()
        takeImageResult.launch(cameraPhotoUri)
    }

    private fun getTemporaryFileUri(): Uri {
        val tempFile = File.createTempFile("temp_image_file", ".png", requireActivity().cacheDir)
        tempFile.createNewFile()
        tempFile.deleteOnExit()

        return FileProvider.getUriForFile(
            requireContext().applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            tempFile
        )
    }
}