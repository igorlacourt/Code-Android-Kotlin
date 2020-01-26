package com.arctouch.codechallenge.extensionfunction

import android.content.Context
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.upcoming.domainobject.Details

fun Details.openYoutube(context: Context?) {
    if (context != null) {
        if (!this.videos.isNullOrEmpty() && !this.videos[0].key.isNullOrEmpty()) {
            val webIntent = android.content.Intent(
                    android.content.Intent.ACTION_VIEW,
                    android.net.Uri.parse("http://www.youtube.com/watch?v=${this.videos[0].key}")
            )
            context.startActivity(webIntent)
        } else {
            android.widget.Toast.makeText(
                    context,
                    context.getString(R.string.no_video_to_show),
                    android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }
}