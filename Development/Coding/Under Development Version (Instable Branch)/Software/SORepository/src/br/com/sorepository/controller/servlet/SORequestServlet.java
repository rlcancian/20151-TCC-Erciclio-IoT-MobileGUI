package br.com.sorepository.controller.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.sorepository.model.dao.RequestDAO;
import br.com.sorepository.model.pojo.SmartObjectList;

/**
 * @author Ercilio Nascimento
 */
public class SORequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SORequestServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		String out = null;
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		RequestDAO dao = null;
		SmartObjectList list = new SmartObjectList();

		StringBuffer sb = new StringBuffer();
		BufferedReader bufferedReader = null;
		String content = "";

		System.out.println(user + password);

		try {
			bufferedReader = request.getReader();
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
				sb.append(charBuffer, 0, bytesRead);
			}

			dao = new RequestDAO();
			if (dao.validateUser(user, password)) {
				list = dao.getSOList(user);
				out = list.printList();
			} else {
				out = "0Usuario ou senha invalido!";
			}

		} catch (IOException ex) {
			throw ex;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		writer.write(StringEscapeUtils.escapeJava(out));

		System.out.println(sb);
	}

}
