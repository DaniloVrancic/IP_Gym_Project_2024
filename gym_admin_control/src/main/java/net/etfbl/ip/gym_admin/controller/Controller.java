package net.etfbl.ip.gym_admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Controller")
public class Controller extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Controller() {
		super();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String address = "/WEB-INF/pages/login.jsp";
		String action = request.getParameter("action");
		
		}
	}

}
