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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MediaService extends Service {
    public static final int REQUEST_CODE = 1;
    static MediaPlayer mediaPlayer;
    static ArrayList<MusicFiles> musicFiles;
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
        public List<String> getAllAudio() throws RemoteException {
            System.out.println("Entered int  to getAllAudio");
            musicFiles = getAllAudioFile(getApplicationContext());
            System.out.println(musicFiles);

            ArrayList<String> songTitle = new ArrayList<>(musicFiles.size());
            for(int i=0;i<musicFiles.size();i++)
            {
                songTitle.add(musicFiles.get(i).getTitle());
                System.out.println(songTitle);
            }
//            ArrayList<Integer> songDuration = new ArrayList<>(musicFiles.size());
//            for(int i=0;i<musicFiles.size();i++)
//            {
//                songDuration.add(musicFiles.get(i).getDuration());
//            }
            //return new List[]{songTitle,songDuration};
            return songTitle;

        }

        @Override
        public String getAlbum(int position) throws RemoteException {
            return null;
        }

        @Override
        public String getArtist(int position) throws RemoteException {
            return null;
        }
        @Override
        public void playSong(int position) throws RemoteException {
            System.out.println(" playSong() - call reached to service "+position);
            Uri uri = Uri.parse(String.valueOf(position));
            System.out.println("urii------------------"+uri);
            String path = musicFiles.get(position).getPath();
            mediaPlayer=new MediaPlayer();
            try {
                mediaPlayer.setDataSource(path);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                mediaPlayer.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mediaPlayer.start();
            if(mediaPlayer.isPlaying()) {
                System.out.println("playing");
            }
        }

        @Override
        public List<String> getSongDetails(int position) throws RemoteException {
            ArrayList<String> songDetails = new ArrayList<>(musicFiles.size());
            songDetails.add(musicFiles.get(position).getAlbum());
            songDetails.add(musicFiles.get(position).getArtist());
            songDetails.add(musicFiles.get(position).getDuration());

            System.out.println(songDetails);
            return songDetails;
        }

        public ArrayList<MusicFiles> getAllAudioFile(Context context) throws RemoteException {
                System.out.println(" getAllAudio() call reached to service ");
                ArrayList<MusicFiles> tempAudioList = new ArrayList<>();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] projections = {
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.ARTIST,
                };
                Cursor cursor = context.getContentResolver().query(uri, projections, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String album = cursor.getString(0);
                        String title = cursor.getString(1);
                        String duration = cursor.getString(2);
                        String path = cursor.getString(3);
                        String artist = cursor.getString(4);

                        MusicFiles musicFiles = new MusicFiles(path, title, artist, album, duration);
                        // take log.e for check
                        Log.e("Path : " + path, "Album: " + album);
                        tempAudioList.add(musicFiles);
                    }
                    cursor.close();
                }

                return tempAudioList;

        }






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