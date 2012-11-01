package cz.cvut.fsv.webgama.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AboutControllerTest {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private AnnotationMethodHandlerAdapter handler;
    private AboutController aboutController;
	
	
	@Before
	public void setUp() throws Exception {
		
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		handler = new AnnotationMethodHandlerAdapter();
		aboutController = new AboutController();
		
	}

	@Test
	public void testShowAboutProject() throws Exception {
		
		request.setMethod("GET");
		ModelAndView mav = handler.handle(request, response, aboutController);
		assertTrue(mav.getViewName() == "/about/about");
	}

	@Test
	public void testShowAboutCVUT() {
		request.setMethod("GET");
	}

	@Test
	public void testShowAboutAuthor() {
		request.setMethod("GET");
	}

}
