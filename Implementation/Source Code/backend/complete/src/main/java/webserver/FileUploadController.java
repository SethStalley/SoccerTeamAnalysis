package webserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import teamidentifier.Video;

/**
 */
@Controller
public class FileUploadController {

	String dir = System.getProperty("user.dir");
	String ROOT = dir.substring(0, dir.lastIndexOf("backend")) + "backend/complete/src/main/webapp/videos";

	/**
	 * Method handleFileUpload.
	 * 
	 * @param file MultipartFile
	 * @param redirectAttributes RedirectAttributes
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/postFile")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		String message = "";

		if (!file.isEmpty()) {
			try {

				Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()), REPLACE_EXISTING);
				message = "Se subio " + file.getOriginalFilename() + " de forma exitosa!";

				// Process video for testing
				proccessVideo(ROOT + '/' + file.getOriginalFilename());
			} catch (FileAlreadyExistsException e) {
				message = "Archivo ya existe, cambie el nombre de archivo!";
			} catch (IOException | RuntimeException e) {
				e.printStackTrace();
				message = "Hubo un problema subiendo " + file.getOriginalFilename() + " => " + e.getMessage();
			}
		} else {
			message = "No se pudo subir " + file.getOriginalFilename() + " porque el archivo es vacio";
		}
		System.out.println(message);

		return message;
	}
	
	/**
	 * Method handleGroundTruthFile.
	 * 
	 * @param file MultipartFile
	 * @param redirectAttributes RedirectAttributes
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/postGroundTruth")
	@ResponseBody
	public String handleGroundTruthFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		String message = "";

		if (!file.isEmpty()) {
			try {

				Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()), REPLACE_EXISTING);
				message = "Se subio " + file.getOriginalFilename() + " de forma exitosa!";


			} catch (IOException | RuntimeException e) {
				e.printStackTrace();
				message = "Hubo un problema subiendo " + file.getOriginalFilename() + " => " + e.getMessage();
			}
		} else {
			message = "No se pudo subir " + file.getOriginalFilename() + " porque el archivo es vacio";
		}
		System.out.println(message);

		return message;
	}

	/**
	 * Process a soccer video and create a new video file with segmented
	 * players.
	 * 
	 * @param videoPath String
	 */
	private void proccessVideo(String videoPath) {
		Video video = new Video(videoPath);

		try {
			video.readVideo();
			video.segmentate();
			video.writeVideo(video.frames, videoPath);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}