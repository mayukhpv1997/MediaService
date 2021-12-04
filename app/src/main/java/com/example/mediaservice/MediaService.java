package com.example.mediaservice;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import AidlPackage.AidlInterface;


public class MediaService extends Service {
    public MediaService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;

    }
    AidlInterface.Stub iBinder = new AidlInterface.Stub() {
        @Override
        public int playNextSong() throws RemoteException {
            return 0;
        }

        @Override
        public int playPreviousSong() throws RemoteException {
            return 0;
        }

        @Override
        public int playPauseSong() throws RemoteException {
            return 0;
        }

        @Override
        public ArrayList<MusicFiles> getAllAudio() throws RemoteException {
            ArrayList<MusicFiles> tempAudioList =new ArrayList<>();
            Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String[] projections = {
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.ARTIST,
            };


            Cursor cursor= context.getContentResolver().query(uri,projections,null,null, null);
            if(cursor!=null){
                while (cursor.moveToNext()){
                    String album = cursor.getString(0);
                    String title = cursor.getString(1);
                    String duration = cursor.getString(2);
                    String path = cursor.getString(3);
                    String artist = cursor.getString(4);

                    MusicFiles musicFiles= new MusicFiles(path,title,artist,album,duration);
                    // take log.e for check
                    Log.e("Path : "+path, "Album: "+album);
                    tempAudioList.add(musicFiles);
                }
                cursor.close();
            }
            return tempAudioList;

        }

    };
}