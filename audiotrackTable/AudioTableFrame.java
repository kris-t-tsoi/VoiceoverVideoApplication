package audiotrackTable;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import mediaMainFrame.MediaPlayerJFrame;
import mediaMainFrame.addAudioTrackPanel.AudioData;

@SuppressWarnings("serial")
public class AudioTableFrame extends JFrame {

	//panes and frames
	private JPanel contentPane;
	AudioTableFrame thisFrame;
	MediaPlayerJFrame videoFrame;
	
	//table
	public JTable table;
	DefaultTableModel tableModel;
	JScrollPane scrollPane;

	//delete button
	JButton deleteAudioBtn;

	/**
	 * Creates a JFrame with list of all audio tracks to be overlaid with
	 * videoFrame
	 * 
	 * @param videoFrame
	 *            main Frame - where audiotrack list is stored
	 */
	public AudioTableFrame(MediaPlayerJFrame vid) {
		thisFrame = this;
		videoFrame = vid;
		setBounds(1200, 100, 350, 650);
		setMinimumSize(new Dimension(300, 250));

		// setup panes
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		scrollPane = new JScrollPane();

		// title label
		JLabel frameName = new JLabel("Audio to Be Overlaid");
		frameName.setFont(new Font("Audio To Be Overlaid", Font.BOLD, 20));

		// set up table
		setupTable();

		// setup delete and refresh button
		setUpDeleteBtn();

		// set layout of contentPane
		contentPane
				.setLayout(new MigLayout(
						"",
						"[5px,grow 0,shrink 0][90px,grow, shrink][5px,grow 0,shrink 0]",
						"[5px][50px][5px][200px,grow,shrink][3px][50px][5px]"));
		add(frameName, "cell 1 1, grow");
		add(scrollPane, "cell 1 3, grow");
		add(deleteAudioBtn, "cell 1 5, grow");

	}

	// sets up the delete button
	private void setUpDeleteBtn() {
		deleteAudioBtn = new JButton("Delete Audio");
		deleteAudioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// check if a row is selected or not
				if (table.getSelectedRow() != -1) {
					int index = 0;
					
					// remove selected audio from audio track list
					for (AudioData audData : videoFrame.getAudioTrackList()) {
						if (table.getModel()	//if name and start time is the same then delete
								.getValueAt(table.getSelectedRow(), 0)
								.equals(audData.getName())
								&& table.getModel()
										.getValueAt(table.getSelectedRow(), 1)
										.equals(audData.getStartTime())) {
							break;
						}
						index++;
					}
					//delete from table
					videoFrame.getAudioTrackList().remove(index);
					((DefaultTableModel) table.getModel()).removeRow(table
							.getSelectedRow());
				}

			}
		});
		deleteAudioBtn.setFont(new Font("Dialog", Font.BOLD, 20));
	}

	/**
	 * Gets the list of audio tracks and adds them to the table
	 * 
	 * @param arrayList
	 *            - list of Audio tracks
	 */
	private void setupTable() {
		table = new JTable(new DefaultTableModel(new Object[] { "Name",
				"Start Time" }, 0));

		table.setFont(new Font("Dialog", Font.PLAIN, 17));
		scrollPane.setViewportView(table);
		tableModel = (DefaultTableModel) table.getModel();

		// only allow selection of one row
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}

	/**
	 * Allows audio name to be added into the table
	 * 
	 * @param name
	 * @param startTime
	 */
	public void addToTable(String name, String startTime) {
		tableModel.addRow(new Object[] { name, startTime });
	}

}
