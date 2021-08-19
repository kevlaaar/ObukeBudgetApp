package com.example

import android.widget.Button
import com.example.obukebudgetapp.R

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class CameraPromptDialogFragment: DialogFragment() {

    lateinit var onCameraClickListener: View.OnClickListener
    lateinit var onGalleryClickListener: View.OnClickListener

    companion object {
        fun newInstance(
            onCameraClickListener: View.OnClickListener,
            onGalleryClickListener: View.OnClickListener
        ): CameraPromptDialogFragment {
            val dialogFragment = CameraPromptDialogFragment()
            dialogFragment.onGalleryClickListener = onGalleryClickListener
            dialogFragment.onCameraClickListener = onCameraClickListener
            return dialogFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = inflater.inflate(R.layout.fragment_dialog_camera_prompt, container, false)
        val galleryButton: Button = view.findViewById(R.id.galleryButton)
        val cameraButton: Button = view.findViewById(R.id.cameraButton)
        galleryButton.setOnClickListener{
            onGalleryClickListener.onClick(it)
            dismiss()
        }
        cameraButton.setOnClickListener(onCameraClickListener)
        return view
    }
}