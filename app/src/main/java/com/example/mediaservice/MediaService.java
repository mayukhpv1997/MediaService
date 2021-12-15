package com.example.mediaservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;

import AidlPackage.AidlInterface;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;


public class MediaService extends Service {
    static MediaPlayer mediaPlayer;
    public MediaService() {
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();

        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        Notification notification = new NotificationCompat.Builder(this, "ChannelId1").setContentTitle("Service application")
                .setContentText("Application Running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();
        startForeground(1, notification);

        return START_STICKY;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) ;
        {
            NotificationChannel notificationChannel = new NotificationChannel(
                    "ChannelId1", "Foreground notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
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
        public void playPauseSong() throws RemoteException {
            System.out.println("call reached to service ");
            //mediaPlayer.start();

        }

        @Override
        public void getAllAudio() throws RemoteException {

        }
//
//        @Override
//        public void getAllAudio() throws RemoteException {
//
//            public ArrayList<MusicFiles> getAllAudioMethod(Context context){
//
//                System.out.println(" getAllAudio() call reached to service ");
//                ArrayList<MusicFiles> tempAudioList = new ArrayList<>();
//                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                String[] projections = {
//                        MediaStore.Audio.Media.ALBUM,
//                        MediaStore.Audio.Media.TITLE,
//                        MediaStore.Audio.Media.DURATION,
//                        MediaStore.Audio.Media.DATA,
//                        MediaStore.Audio.Media.ARTIST,
//                };
//                Cursor cursor = context.getContentResolver().query(uri, projections, null, null, null);
//                if (cursor != null) {
//                    while (cursor.moveToNext()) {
//                        String album = cursor.getString(0);
//                        String title = cursor.getString(1);
//                        String duration = cursor.getString(2);
//                        String path = cursor.getString(3);
//                        String artist = cursor.getString(4);
//
//                        MusicFiles musicFiles = new MusicFiles(path, title, artist, album, duration);
//                        // take log.e for check
//                        Log.e("Path : " + path, "Album: " + album);
//                        tempAudioList.add(musicFiles);
//                    }
//                    cursor.close();
//                }
//
//                return tempAudioList;
//            }
//
//        }






//        @Override
//        public ArrayList<MusicFiles> getAllAudio(Context context) throws RemoteException {
//            ArrayList<MusicFiles> tempAudioList =new ArrayList<>();
//            Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//            String[] projections = {
//                    MediaStore.Audio.Media.ALBUM,
//                    MediaStore.Audio.Media.TITLE,
//                    MediaStore.Audio.Media.DURATION,
//                    MediaStore.Audio.Media.DATA,
//                    MediaStore.Audio.Media.ARTIST,
//            };
//
//
//            Cursor cursor= context.getContentResolver().query(uri,projections,null,null, null);
//            if(cursor!=null){
//                while (cursor.moveToNext()){
//                    String album = cursor.getString(0);
//                    String title = cursor.getString(1);
//                    String duration = cursor.getString(2);
//                    String path = cursor.getString(3);
//                    String artist = cursor.getString(4);
//
//                    MusicFiles musicFiles= new MusicFiles(path,title,artist,album,duration);
//                    // take log.e for check
//                    Log.e("Path : "+path, "Album: "+album);
//                    tempAudioList.add(musicFiles);
//                }
//                cursor.close();
//            }
//            return tempAudioList;
//
//        }

    };
}