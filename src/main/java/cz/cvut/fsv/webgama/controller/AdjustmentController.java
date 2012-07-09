package cz.cvut.fsv.webgama.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
public class AdjustmentController extends MultiActionController {

	@RequestMapping(value = "/adjustment", method = RequestMethod.GET)
	protected ModelAndView adjust(HttpServletRequest request) {

		long startTime = System.nanoTime();

		ModelAndView mav = new ModelAndView("/adjustment/adjustment");

		request.isUserInRole("ROLE_ADMIN");
		
		
		Calendar cal = Calendar.getInstance();
		
		Date date = new Date();
		mav.addObject("date", date);
		mav.addObject("cal", cal.get(Calendar.MILLISECOND));

		double time = (double) (System.nanoTime() - startTime) / 1000000;

		mav.addObject("time", time);

		return mav;

	}
	
	@RequestMapping(value = "/adjustment/xml", method = RequestMethod.GET)
	protected ModelAndView showUploadForm() {
		
		return new ModelAndView("/adjustment/xml/upload");
	}
	
	@RequestMapping(value = "/adjustment/xml", method = RequestMethod.POST)
	protected ModelAndView uploadXML(@RequestParam("file") MultipartFile file) throws IOException {
		
		if (!file.isEmpty()) {
            //byte[] bytes = file.getBytes();
        
			//TODO
			
			String orgName = file.getOriginalFilename();
			
			String path = orgName;
        
			File newFile = new File(path);
			
			file.transferTo(newFile);
			
            InputStream in = file.getInputStream();
           
            //String s = IOUtils.toString(in);
            
            
           
            in.close();
            
            
           return new ModelAndView("redirect:/adjustment/xml");
       } else {
           return new ModelAndView("/adjustment/xml/upload");
       }
	}
	

}
