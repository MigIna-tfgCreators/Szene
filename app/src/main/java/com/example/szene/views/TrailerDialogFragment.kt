package com.example.szene.views

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import androidx.fragment.app.DialogFragment
import com.example.szene.R

class TrailerDialogFragment(private val videoId: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.activity_detalle, container, false)
        val youTubePlayerView = view.findViewById<YouTubePlayerView>(R.id.youtubePlayerView)

        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                player.loadVideo(videoId, 0f)
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        view?.findViewById<YouTubePlayerView>(R.id.youtubePlayerView)?.release()
    }
}
