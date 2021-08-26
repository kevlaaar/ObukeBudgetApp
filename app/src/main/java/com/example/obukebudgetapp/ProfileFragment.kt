package com.example.obukebudgetapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.CameraPromptDialogFragment
import java.io.ByteArrayOutputStream
import java.io.File


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var profileImage: ImageView
    lateinit var changeProfileImage: ImageView
    lateinit var amountOfMoneyTextView: TextView

    private var cameraPhotoUri: Uri? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImage = view.findViewById(R.id.profileImage)
        changeProfileImage = view.findViewById(R.id.changeProfileIcon)
        amountOfMoneyTextView = view.findViewById(R.id.amountOfMoneyTextView)
        changeProfileImage.setOnClickListener {
            showDialogFragment()
        }
        val amountOfMoney = 100
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(getString(R.string.shared_preference_file_name), Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("AMOUNT_OF_MONEY", 100f)
        editor.apply()
        val profileImageString = sharedPreferences.getString("PROFILE_IMAGE_URI", "")
        if(!profileImageString.isNullOrEmpty()) {
            profileImage.setImageBitmap(decodeToBase64(profileImageString))
        }
        amountOfMoneyTextView.text = "$amountOfMoney"

    }

    private fun decodeToBase64(input: String?): Bitmap? {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }

    private fun saveBitmapToSharedPreference(bitmap: Bitmap) {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(getString(R.string.shared_preference_file_name), Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val byteArray = baos.toByteArray()
        val imageByteString = Base64.encodeToString(byteArray, Base64.DEFAULT)


        editor.putString("PROFILE_IMAGE_URI", imageByteString)
        editor.apply()
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
            uri?.let {
                profileImage.setImageURI(it)
                val drawable = profileImage.drawable as BitmapDrawable
                val bitmap = drawable.bitmap
                saveBitmapToSharedPreference(bitmap)
            }
        }

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                cameraPhotoUri?.let {
                    profileImage.setImageURI(it)
                    val drawable = profileImage.drawable as BitmapDrawable
                    val bitmap = drawable.bitmap
                    saveBitmapToSharedPreference(bitmap)
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