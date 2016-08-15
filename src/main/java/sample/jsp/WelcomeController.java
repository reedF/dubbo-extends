
package sample.jsp;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reed.dubbo.nodemap.http.TableContentByHtmlParse;

@Controller
public class WelcomeController {

	@Value("${application.dubbo.url:172.28.18.196:6060}")
	private String url;

	public static final String url_application = "/applications.html";
	public static final String url_depends = "/dependencies.html?application=";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.url);
		model.put("as",
				TableContentByHtmlParse.getApplications(url + url_application));
		return "welcome";
	}

	@RequestMapping("/{app}")
	@ResponseBody
	public String nodemap(@PathVariable String app) {
		String r = null;
		if (app != null && !app.isEmpty()) {
			String target = url + url_depends + app;
			r = TableContentByHtmlParse.getDependens(target);
		}
		return r;
	}
}
