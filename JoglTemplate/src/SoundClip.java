import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


public class SoundClip {
    private byte[] mBytes;
    private DataLine.Info mInfo;
    private AudioFormat mFormat;

    private SoundClip() {}

    public static SoundClip loadFromFile(String filename) {
        try {
            SoundClip s = new SoundClip();
            File audioFile = new File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            s.mFormat = audioStream.getFormat();
            s.mInfo = new DataLine.Info(Clip.class, audioStream.getFormat());
            s.mBytes = new byte[(int)(s.mFormat.getFrameSize() * audioStream.getFrameLength())];
            audioStream.read(s.mBytes);
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void play() {
        try {
            Clip c = (Clip)AudioSystem.getLine(mInfo);
            c.open(mFormat, mBytes, 0, mBytes.length);
            c.start();
        } catch (Exception ex) {
            ;
        }
    }

    public Clip playLooping() {
        try {
            Clip c = (Clip)AudioSystem.getLine(mInfo);
            c.open(mFormat, mBytes, 0, mBytes.length);
            c.loop(Clip.LOOP_CONTINUOUSLY);
            return c;
        } catch (Exception ex) {
            return null;
        }
    }
}