package org.springframework.samples.petclinic.upload;

import io.swagger.annotations.ApiResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ax5uploader")
public class AX5UploaderController {

    @Autowired
    private FileUploadService fileUploadService;

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
    public List<AX5File> files() {
        return fileUploadService.files();
    }


	@GetMapping("/load")
	public List<AX5File> fileLoad() {
		return fileUploadService.files();
	}

//    @GetMapping(value = "/flush")
//    public ApiResponse flush() {
//        fileUploadService.flush();
//        return ok();
//    }
}
