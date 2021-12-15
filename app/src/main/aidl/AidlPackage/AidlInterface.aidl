// AidlInterface.aidl
package AidlPackage;
import java.util.List;

// Declare any non-default types here with import statements

interface AidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    int playNextSong();
    int playPreviousSong();
    void playPauseSong();
    //ArrayList<MusicFiles> getAllAudio(Context context);
      void getAllAudio();

}