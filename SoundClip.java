import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;


public class SoundClip {
	
    private Clip clip;
    private boolean playing = false;
    
    public SoundClip(String s) {      
        try {      
            File soundFile = new File(s+".wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        clip.start();
    }
    public void stop(){
        clip.stop();
    }
}
