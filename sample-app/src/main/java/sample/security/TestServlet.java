package sample.security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

@WebServlet(urlPatterns="/*")
public class TestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Principal principal = req.getUserPrincipal();

		resp.getWriter().append("user=").append(principal.getName()).append('\n');

		if (req.isUserInRole("admin")) {
			resp.getWriter().append("Role: admin\n");
		}
		if (req.isUserInRole("login")) {
			resp.getWriter().append("Role: login\n");
		}

        resp.getWriter().append(String.format("\n%1$tH:%1$tM:%1$tS.%1$tL\n", new Date()));


	}
}
