package sharedGUIComponets;

import javax.swing.JLabel;

import doInBackground.UseTerminalCommands;

public class TimeLabel extends JLabel {

	UseTerminalCommands termCommand = new UseTerminalCommands();

	public TimeLabel() {
		super();
		setText("00:00.00");
	}

	/**
	 * finds the duration of the video and changes label to the total duration
	 * time
	 * 
	 * @param videoPath
	 */
	public double findDuration(String path) {
		;
		String inputLine = termCommand.terminalCommandString("ffprobe -i \""
				+ path + "\" -show_format 2>&1 | grep Duration");
		if (inputLine != null) {
			String[] words = inputLine.split("[ ,]");
			for (int i = 0; i < words.length; i++) {
				if (words[i].equals("Duration:")) {
					String[] times = words[i + 1].split("[:.]");
					String minSecMilli = times[1] + ":" + times[2] + "."
							+ times[3];
					setText(minSecMilli);
					return durationStringToDouble(minSecMilli);
				}
			}

		}
		return 0;
	}
	
	public String addTimes(){
		
		return "";
	}
	
	
	/**
	 * 
	 * @param timeString
	 * @return time in milliSeconds
	 */
	private double  durationStringToDouble (String timeString){
		//00:00.00	min:sec.mil
		String[] timeSplit = timeString.split("[:.]");	
		double milli  = (Double.parseDouble(timeSplit[2]));
		double sec =  Double.parseDouble(timeSplit[1])*1000; 
		double min =  Double.parseDouble(timeSplit[0])*60000; 
		
		return (min+sec+milli);
		
	}
	
	
	public String durationDoubleToString(double duration){
		//convert into seconds
		duration = duration/1000;
		
		int min = (int)(duration/60);
		int sec = (int)(duration%60);
		int milli = (int)((duration-min-sec)*100);
		
		return min+":"+sec+"."+milli;
		
	}
	
	
}
