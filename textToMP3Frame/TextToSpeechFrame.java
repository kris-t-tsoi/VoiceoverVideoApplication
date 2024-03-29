package textToMP3Frame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fileChoosing.UserFileChoose;
import textToMP3Frame.buttons.CreateMP3Btn;
import textToMP3Frame.buttons.PlayTextBtn;
import textToMP3Frame.textBoxAndSliders.PitchSlider;
import textToMP3Frame.textBoxAndSliders.TextToMP3TextBox;
import textToMP3Frame.textBoxAndSliders.VoiceSpeedSlider;
import mediaMainFrame.MediaPlayerJFrame;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
/**
 * frame with all text to audio synthesis functionality
 * @author kristy
 *
 */
public class TextToSpeechFrame extends JFrame {

	// GUI Components
	final TextToSpeechFrame thisFrame = this;
	JPanel contentPane;

	// Buttons
	private PlayTextBtn playText;
	CreateMP3Btn createMP3;

	//Textbox and sliders
	TextToMP3TextBox userText;
	VoiceSpeedSlider speedSlide;
	PitchSlider pitchSlide;
	UserFileChoose fileChose;

	// Constants for the textfield - Max number of words which can be
	// played/saved, and error message
	public static final String ERROR_WORD_LIMIT_MESSAGE = "Sorry, you have exceeded the maximum word count of 50.";

	// Audio Values
	private float speed;
	private int startPitch;
	private int endPitch;
	private String text;

	/**
	 * Constructor for Text to Speech Frame Used for - playing text to audio -
	 * creating MP3
	 * 
	 * @param title
	 */
	public TextToSpeechFrame(final MediaPlayerJFrame video) {

		//intialise frame and pane
		super("Create Audio From Text");
		setBounds(500, 250, 580, 255);
		setMinimumSize(new Dimension(575, 250));
		contentPane = new JPanel();
		setContentPane(contentPane);

		//play text button
		playText = new PlayTextBtn(thisFrame);
		playText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If the text is under the allowed limit, speak the text
				if (checkTextbox()) {
					playText.sayWithFestival(speed, startPitch,
							endPitch,text);
				}
			}
		});

		//create MP3 button
		createMP3 = new CreateMP3Btn(thisFrame);
		createMP3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkTextbox()) {//check text area is not blank and is less than 50 words
					fileChose = new UserFileChoose(video);
					String name = fileChose.saveMP3();
					if (!name.equals("")) { // check user wants to create a mp3
						createMP3.createAudio(speed, startPitch,
								endPitch, text, name);
					}

				}
			}
		});

		//textbox
		userText = new TextToMP3TextBox();
		JScrollPane scrollPane = new JScrollPane(userText);

		//title labels
		JLabel titleLbl = new JLabel("Type in Text to Synthesis into MP3 Audio");
		titleLbl.setFont(video.TITLE_FONT);
		JLabel speedLbl = new JLabel("Voice Speed");
		JLabel pitchLbl = new JLabel("Vocal Tone");

		//sliders
		speedSlide = new VoiceSpeedSlider();
		pitchSlide = new PitchSlider();

		contentPane
				.setLayout(new MigLayout(
						"", // Layout Constraint
						"[4px,grow 0,shrink 0][98px,grow 0, shrink 0][2px,grow 0,shrink 0][346px,grow, shrink]"
								+ "[4px,grow 0,shrink 0][196px,grow 0, shrink 0][4px,grow ,shrink ]", // Column
																										// Constraints
						"[40px][50px,grow, shrink][40px][40px]")); // Row
																	// Constraints
		//textBox
		contentPane.add(titleLbl, "cell 1 0 5 1,grow");
		contentPane.add(scrollPane, "cell 1 1 5 1,grow");
		
		//Sliders
		contentPane.add(speedLbl, "cell 1 2,grow");
		contentPane.add(pitchLbl, "cell 1 3,grow");
		contentPane.add(speedSlide, "cell 3 2 ,grow");
		contentPane.add(pitchSlide, "cell 3 3 ,grow");
		
		//buttons
		contentPane.add(playText, "cell 5 2,grow");
		contentPane.add(createMP3, "cell 5 3,grow");

	}

	/**
	 * get the speed and pitch slider and textbox values
	 */
	private void getFestivalValues() {
		//speed value
		speed = ((float) (speedSlide.getValue()) / 10);
		
		//tone value
		int[] pitchRange = pitchSlide.findRange(pitchSlide.getValue());
		startPitch=pitchRange[0];
		endPitch=pitchRange[1];
		
		//text to be converted to audio
		text= userText.getText();
	}

	/**
	 * Check if text box is not empty and is within text limit
	 * 
	 * @return - true if valid text
	 */
	private boolean checkTextbox() {
		// get values from sliders and text box
		getFestivalValues();

		// (getText() != null) && !getText().startsWith(" ")
		if (userText.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(thisFrame, "Textbox Can Not Be Empty");
			return false;
		}

		// If the text is under the allowed limit, speak the text
		if (!userText.checkTxtLength()) {
			JOptionPane.showMessageDialog(thisFrame, ERROR_WORD_LIMIT_MESSAGE);
			return false;
		}
		return true;
	}

}
