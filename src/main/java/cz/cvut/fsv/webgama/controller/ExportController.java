package cz.cvut.fsv.webgama.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.exception.PermissionDeniedException;
import cz.cvut.fsv.webgama.exception.ResourceNotFoundException;
import cz.cvut.fsv.webgama.service.AdjustmentManager;

@Controller
public class ExportController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;

	private static final Logger logger = LoggerFactory.getLogger(ExportController.class);

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	protected ModelAndView export(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("/export/export");
		String username = request.getUserPrincipal().getName();

		List<Calculation> calculations = adjustmentManager.getCalculationsbyUsername(username);
		mav.addObject("calculations", calculations);

		return mav;
	}

	@RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
	protected ModelAndView exportCalculation(@PathVariable Long id, HttpServletRequest request) {

		// Check if path variable is in database otherwise throw 404 HTTP error
		if (id <= 0 || !adjustmentManager.isCalculationIdInDB(id)) {
			throw new ResourceNotFoundException();
		}
		Calculation calculation = adjustmentManager.getCalculationById(id);
		// Check if user has permission to edit
		if (!request.getUserPrincipal().getName().equals(calculation.getUser().getUsername())) {
			throw new PermissionDeniedException();
		}
		// Check if calculation is calculated
		if (calculation.getOutput() == null) {
			throw new ResourceNotFoundException();
		}

		return new ModelAndView("/export/calculation", "calculation", calculation);
	}

	@RequestMapping(value = "/export/{id}/xml", method = RequestMethod.GET)
	protected ModelAndView exportCalculationToXML(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) {

		// Check if path variable is in database otherwise throw 404 HTTP error
		if (id <= 0 || !adjustmentManager.isCalculationIdInDB(id)) {
			throw new ResourceNotFoundException();
		}
		Calculation calculation = adjustmentManager.getCalculationById(id);
		// Check if user has permission to edit
		if (!request.getUserPrincipal().getName().equals(calculation.getUser().getUsername())) {
			throw new PermissionDeniedException();
		}
		// Check if calculation is calculated
		if (calculation.getOutput() == null) {
			throw new ResourceNotFoundException();
		}
		String username = request.getUserPrincipal().getName();

		File temporary = null;
		try {
			temporary = File.createTempFile(calculation.getName(), ".xml");
			temporary.deleteOnExit();
			FileUtils.writeStringToFile(temporary, calculation.getOutput().getXmlContent());
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.setContentType("text/xml");
		response.setHeader("Content-Disposition", "attachment; filename=" + calculation.getName() + ".xml");
		response.setContentLength(Long.valueOf(temporary.length()).intValue());

		try (InputStream in = new FileInputStream(temporary); OutputStream out = response.getOutputStream();) {
			FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			temporary.delete();
		}

		logger.info(username + " downloads calculation[" + id + "] as XML.");
		return null;
	}

	@RequestMapping(value = "/export/{id}/text", method = RequestMethod.GET)
	protected ModelAndView exportCalculationToText(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) {

		// Check if path variable is in database otherwise throw 404 HTTP error
		if (id <= 0 || !adjustmentManager.isCalculationIdInDB(id)) {
			throw new ResourceNotFoundException();
		}
		Calculation calculation = adjustmentManager.getCalculationById(id);
		// Check if user has permission to edit
		if (!request.getUserPrincipal().getName().equals(calculation.getUser().getUsername())) {
			throw new PermissionDeniedException();
		}
		// Check if calculation is calculated
		if (calculation.getOutput() == null) {
			throw new ResourceNotFoundException();
		}
		String username = request.getUserPrincipal().getName();

		File temporary = null;
		try {
			temporary = File.createTempFile(calculation.getName(), ".txt");
			temporary.deleteOnExit();
			FileUtils.writeStringToFile(temporary, calculation.getOutput().getTextContent());
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment; filename=" + calculation.getName() + ".txt");
		response.setContentLength(Long.valueOf(temporary.length()).intValue());

		try (InputStream in = new FileInputStream(temporary); OutputStream out = response.getOutputStream();) {
			FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			temporary.delete();
		}

		logger.info(username + " downloads calculation[" + id + "] as TEXT.");
		return null;
	}

	@RequestMapping(value = "/export/{id}/html", method = RequestMethod.GET)
	protected ModelAndView exportCalculationToHTML(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) {

		// Check if path variable is in database otherwise throw 404 HTTP error
		if (id <= 0 || !adjustmentManager.isCalculationIdInDB(id)) {
			throw new ResourceNotFoundException();
		}
		Calculation calculation = adjustmentManager.getCalculationById(id);
		// Check if user has permission to edit
		if (!request.getUserPrincipal().getName().equals(calculation.getUser().getUsername())) {
			throw new PermissionDeniedException();
		}
		// Check if calculation is calculated
		if (calculation.getOutput() == null) {
			throw new ResourceNotFoundException();
		}
		String username = request.getUserPrincipal().getName();

		File temporary = null;
		try {
			temporary = File.createTempFile(calculation.getName(), ".html");
			temporary.deleteOnExit();
			FileUtils.writeStringToFile(temporary, calculation.getOutput().getHtmlContent());
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.setContentType("text/html");
		response.setHeader("Content-Disposition", "attachment; filename=" + calculation.getName() + ".html");
		response.setContentLength(Long.valueOf(temporary.length()).intValue());

		try (InputStream in = new FileInputStream(temporary); OutputStream out = response.getOutputStream();) {
			FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			temporary.delete();
		}

		logger.info(username + " downloads calculation[" + id + "] as HTML.");
		return null;
	}

	@RequestMapping(value = "/export/{id}/svg", method = RequestMethod.GET)
	protected ModelAndView exportCalculationToSVG(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) {

		// Check if path variable is in database otherwise throw 404 HTTP error
		if (id <= 0 || !adjustmentManager.isCalculationIdInDB(id)) {
			throw new ResourceNotFoundException();
		}
		Calculation calculation = adjustmentManager.getCalculationById(id);
		// Check if user has permission to edit
		if (!request.getUserPrincipal().getName().equals(calculation.getUser().getUsername())) {
			throw new PermissionDeniedException();
		}
		// Check if calculation is calculated
		if (calculation.getOutput() == null) {
			throw new ResourceNotFoundException();
		}
		String username = request.getUserPrincipal().getName();

		File temporary = null;
		try {
			temporary = File.createTempFile(calculation.getName(), ".svg");
			temporary.deleteOnExit();
			FileUtils.writeStringToFile(temporary, calculation.getOutput().getSvgContent());
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.setContentType("image/svg+xml");
		response.setHeader("Content-Disposition", "attachment; filename=" + calculation.getName() + ".svg");
		response.setContentLength(Long.valueOf(temporary.length()).intValue());

		try (InputStream in = new FileInputStream(temporary); OutputStream out = response.getOutputStream();) {
			FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			temporary.delete();
		}

		logger.info(username + " downloads calculation[" + id + "] as SVG.");
		return null;
	}
}
