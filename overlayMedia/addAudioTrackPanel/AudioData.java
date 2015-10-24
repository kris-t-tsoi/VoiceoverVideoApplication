package overlayMedia.addAudioTrackPanel;

public class AudioData {

	private String path;
	private String name;
	private String startTime;
	private int startMiliTime;

	/**
	 * Class creates audioData objects which stores information on audio to be
	 * overlaid
	 * 
	 * @param mp3Path - path of mp3 to be added
	 * @param fileName - name of mp3
	 * @param start - start time of overlay delay in string format
	 * @param mili - start time of overlay delay in millisec
	 */
	public AudioData(String mp3Path, String fileName, String start, double mili) {
		path = mp3Path;
		name = fileName;
		startTime = start;
		startMiliTime = (int) mili;
	}

	public String getPath() {
		return path;
	}

	public String getStartTime() {
		return startTime;
	}

	public int getStartMiliTime() {
		return startMiliTime;
	}

	public String getName() {
		return name;
	}

}
