package com.ketan.musicbrainzapp.timerutility

import com.google.android.gms.maps.model.Marker
import java.util.*

class Reminder {
    var timer: Timer?=null
    var marker : Marker?=null
    var onFinishListener : OnFinishListener?=null

    constructor(seconds: Long,marker:Marker,onFinishListener : OnFinishListener) {
        this.onFinishListener = onFinishListener
        timer = Timer()
        this.marker = marker
        timer?.schedule(RemindTask(), seconds * 1000)
    }

    internal inner class RemindTask : TimerTask() {
        override fun run() {
            if(marker!=null){
                cancel()
                onFinishListener?.onFinish(marker!!)
            }
        }
    }


    public interface OnFinishListener{
        fun onFinish(marker : Marker)
    }

}