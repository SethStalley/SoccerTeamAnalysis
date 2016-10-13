package team_identifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class Video {
	// private String videoId;
	private String path;
	private static final int CV_CAP_PROP_FRAME_COUNT = 7;
	ArrayList<Mat> frames;
	
	public Video(String path){
		this.path = path;
		this.frames = new ArrayList<Mat>();
	}
	
	/**
     * Read a video and divides it into frames
     * @return - List of frames (Mat)
     * @throws FileNotFoundException 
     */
	public void readVideo() throws FileNotFoundException{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		ArrayList<Mat> frames  = new ArrayList<Mat>();
		
		VideoCapture cap = new VideoCapture();
		cap.open(path);
		
		if(!cap.isOpened()) {
			throw new FileNotFoundException();
		}
		
		int numFrames = (int) cap.get(CV_CAP_PROP_FRAME_COUNT);
		
		for(int i = 0; i < numFrames; i++){
			Mat frame = new Mat();
			
			cap.read(frame);
			frames.add(frame);
		}	
		this.frames = frames;	
	}
	
	public void segmentate(){
		PlayerDetector playerDetector = new PlayerDetector();
		playerDetector.Detect(frames);
		ArrayList<Mat> playerFrames  = playerDetector.getProcessedPlayers();
		
		System.out.println(playerFrames.size());
		
		for(Mat frame:playerFrames){
			Imshow im2 = new Imshow("Display");
			im2.showImage(frame);
		}
        
	/*	SoccerFieldDetector fieldDetector = new SoccerFieldDetector();
		Imshow im1 = new Imshow("Display");
        im1.showImage(this.frames.get(100));
        
		fieldDetector.Detect(this.frames);
		ArrayList<Mat> fieldFrames  = fieldDetector.getProcessedFields();

		Imshow im = new Imshow("Display");
        im.showImage(fieldFrames.get(100));
	*/	
	}
	
}
