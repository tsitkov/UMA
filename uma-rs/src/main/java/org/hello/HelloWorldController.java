package hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

/*
import org.mit.uma.rs.toyimpl.ToyAmRegister;
import org.mit.uma.rs.toyimpl.ToyAzServerLocator;
*/
import org.mit.uma.rs.ASConfiguration;
import org.mit.uma.rs.RegistrationResponse;
import org.mit.uma.rs.services.DiscoveryService;
import org.mit.uma.rs.services.RegistrationService;



@Controller
public class HelloWorldController {
 
	
	@Autowired
	private DiscoveryService discoverySrv;
	
	@Autowired
	private RegistrationService registrationSrv;
	
	/*
	@Autowired
	private AuthorizationService authSrv;
	*/
	
	/*
	@Autowired
	private AuthorizationService authorizationSrv;
	*/
	
	/*
	ToyAzServerLocator configService = null;
    final String confPath = "C:/tmp/rs/config.json";
	*/
	@RequestMapping("/")
	public String index( Model model) {
		return "index";
	}
	
	@RequestMapping("/hello")
	 public String hello(@RequestParam(value="as_url", required=false, defaultValue="World") String url, Model model) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
      	
		ASConfiguration api = discoverySrv.getServerAPIs(url);
		String apiAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(api);
		
		RegistrationResponse reginfo = registrationSrv.performClientRegistration(api);
		
		String reginfoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reginfo);		
        
		/* get configuration info from authorization server */
        /* 
		ToyAmRegister register = new ToyAmRegister();
		ASConfiguration cfg = register.getASConfiguration(url);
		String cliendId = register.getClientId(url, cfg);
		*/
		
		model.addAttribute("api", apiAsString);
		model.addAttribute("reginfo", reginfoAsString);
		return "helloworld";
	 }

/*
	@RequestMapping("/authorize")
	public String authorize( Model model) {
		
		authSrv.getPatToken();
		
		return null;
	}		
*/	
}

