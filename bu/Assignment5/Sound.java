//Name: Joshua Davis
//UAID: 010946462
//Date: 10/09/2020
//Assignment 4	[Sound.java]
//Description: Contains the Sound class, the play function was modified to take parameters for
//				volume and # of loops.
//================================================================================================
import java.lang.Object;
import java.io.File;
import javax.sound.sampled.*;

class Sound {
		Clip[] clips;
		int pos;

		Sound(String filename, int copies) throws Exception {
		  clips = new Clip[copies];
		  for(int i = 0; i < copies; i++) {
		    AudioInputStream inputStream = 
		    AudioSystem.getAudioInputStream(new File(filename));
		    AudioFormat format = inputStream.getFormat();
		    DataLine.Info info =
		      new DataLine.Info(Clip.class, format);
		    clips[i] = (Clip)AudioSystem.getLine(info);
		    clips[i].open(inputStream);
		  }
		  pos = 0;
		}

		void play(double volume, int loopCount)
		{
			float volConversion = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
			FloatControl volumeLevel = (FloatControl) clips[pos].getControl(FloatControl.Type.MASTER_GAIN);
			volumeLevel.setValue(volConversion);
			clips[pos].setFramePosition(0);
			clips[pos].loop(loopCount);
			if(++pos >= clips.length)
				pos = 0;
		}
}