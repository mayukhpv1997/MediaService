// AidlInterface.aidl
package AidlPackage;
import java.util.List;

// Declare any non-default types here with import statements

interface AidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */


     boolean playPauseSong();
     void playSong(int position);
      List<String> getAllAudio();
      String getAlbum(int position);
      String getArtist(int position);
      List<String> getSongDetails(int position);

}