package overlayMedia;

import java.util.ArrayList;

import javax.swing.JButton;

import overlayMedia.addAudioTrackPanel.AudioData;
import sharedLabels.TimeLabel;
import mediaMainFrame.MediaPlayerJFrame;
import doInBackground.CreateInBackground;

@SuppressWarnings("serial")
public class OverlayVidAndAudioButton extends JButton {

	TimeLabel time;

	public OverlayVidAndAudioButton() {
		super();
		setText("Overlay Video with Added Audiotracks");
		setToolTipText("Create New Video By Overlaying Audiotracks and Video");
	}

	/**
	 * Overlays selected video
	 * @param listAudio - list of audiotracks to overlay
	 * @param video frame - get current video
	 * @param outName - name of mp4 file that will be created
	 */
	public void overlayVideo(ArrayList<AudioData> listAudio,
			MediaPlayerJFrame video, String outName) {

		time = new TimeLabel();
		
		//follow format: 
		//ffmpeg -i video_path -i mp3_path [1:a]adelay=500[a1];,[a1]amix=inputs=2 output_name
		

		String ffmpegVideoPath = "ffmpeg -i " + video.getVideoPath() + " ";
		String ffmpegMP3Paths = "";
		String ffmpegDelay = "";
		String ffmpegMediaNumAndChannel = "";

		int count = 1;

		for (AudioData audio : listAudio) {

			int delay = audio.getStartMiliTime();

			// add in paths of the audio files
			ffmpegMP3Paths = ffmpegMP3Paths + "-i " + audio.getPath() + " ";
			ffmpegDelay = ffmpegDelay + "[" + count+ ":a]adelay=" + delay+"[a"+count+"]";
			ffmpegMediaNumAndChannel = ffmpegMediaNumAndChannel +"[a"+count+"]";
			
			//if it is not the last audio add a semicolon
			if (count != listAudio.size()) {
				ffmpegDelay = ffmpegDelay + ";";
			}
			
			count++;
		}

		String ffmpegAmix = "amix=inputs=" + count + "\" " + outName;

		String cmd = ffmpegVideoPath + ffmpegMP3Paths + "-filter_complex \""
				+ ffmpegDelay +","+ffmpegMediaNumAndChannel+ ffmpegAmix;

		//exceute in background
		CreateInBackground back = new CreateInBackground(cmd);
		back.execute();
	
	}

}