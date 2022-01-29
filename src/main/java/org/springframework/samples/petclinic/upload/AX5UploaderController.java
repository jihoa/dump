package org.springframework.samples.petclinic.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/ax5uploader")
public class AX5UploaderController {

    @Autowired
    private FileUploadService fileUploadService;

//	@PostMapping
//	public String upload(@RequestParam MultipartFile file, @RequestParam String subPath, @RequestParam String uploadType, HttpSession session) throws IOException {
//		ArrayList<AX5File> fileList = new ArrayList<>();
//
//		String uploadTempDir = "";
//
//		if (session.getAttribute("uploadTempDir")==null) {
//			uploadTempDir = UUID.randomUUID().toString();
//			session.setAttribute("uploadTempDir", uploadTempDir);
//		}
//	}

	@PostMapping
    public AX5File upload(@RequestParam MultipartFile file) throws IOException {
        return fileUploadService.upload(file);
    }

//    @PostMapping(value = "/delete")
//    public ApiResponse delete(@RequestBody List<AX5File> files) throws IOException {
//        fileUploadService.delete(files);
//        return ok();
//    }



    @GetMapping(value = "/download")
    @ResponseBody
    public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam String id) throws IOException {
        return fileUploadService.download(request, id);
    }

    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void preview(HttpServletResponse response, @RequestParam String id) throws IOException {
        fileUploadService.preview(response, id);
    }

    @RequestMapping(value = "/thumbnail", method = RequestMethod.GET)
    public void thumbnail(HttpServletResponse response, @RequestParam String id) throws IOException {
        fileUploadService.thumbnail(response, id);
    }

    @GetMapping
    public ResponseEntity<?> files(@RequestParam String subPath, @RequestParam String uploadType, HttpSession session) {

		List<AX5File> fileList = new ArrayList<>();

		if (!uploadType.equalsIgnoreCase("NEW")) {
			String uploadTempDir = session.getAttribute("uploadTempDir").toString();
			fileList = fileUploadService.files(subPath + File.separator + uploadTempDir);
		}

		return new ResponseEntity<>(fileList, HttpStatus.OK);
		//return fileUploadService.files(fileList);
    }


//	@GetMapping("/load")
//	public List<AX5File> fileLoad() {
//		return fileUploadService.files();
//	}

//    @GetMapping(value = "/flush")
//    public ApiResponse flush() {
//        fileUploadService.flush();
//        return ok();
//    }
}
