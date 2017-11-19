package com.grosalex.androidassistantthings

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.UnknownHostException


class SocketService() : Service() {
    private val mBinder = SocketService()

    inner class SocketService : Binder() {
        internal // Return this instance of LocalService so clients can call public methods
        val service: SocketService
            get() = SocketService()
    }
    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        openSocket()
        return mBinder
    }

    fun openSocket(){
        try {
            var end = false;
            var ss = ServerSocket(12345);
            while(!end){
                //Server is waiting for client here, if needed
                var s = ss.accept();
                var input = BufferedReader( InputStreamReader(s.getInputStream()));
                var output = PrintWriter(s.getOutputStream(),true); //Autoflush
                var st = input.readLine();
                Log.d("Tcp Example", "From client: "+st);
                output.println("Good bye and thanks for all the fish :)");
                s.close();
            }
            ss.close();


        } catch ( e : UnknownHostException) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( e : IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
