package audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Music {
	static String randomName = "TreasureQuest";
	public static Clip clip = null;
	
	public static void playSound(String name) throws Exception{
	    if (clip != null && clip.isOpen()) clip.close();
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/music/" + name + ".wav").getAbsoluteFile());
	        clip = AudioSystem.getClip();

	        clip.open(audioInputStream);
	        FloatControl gainControl = 
	                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	            gainControl.setValue(-10f); //przyciszone o 10dB

	        System.out.println(clip.getFrameLength() + " | " + clip.getFramePosition());
	        clip.start();


	}
}
