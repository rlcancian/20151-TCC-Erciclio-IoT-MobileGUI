package br.com.soserver.controller.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import br.com.soserver.comm.ParserCommand;

/**
 * @author Ercilio Nascimento
 */
public class SOCommandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SOCommandServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*PrintWriter writer = response.getWriter();
		StringBuffer sb = new StringBuffer();
		BufferedReader bufferedReader = null;
		String ack = "";
		ParserCommand parser = new ParserCommand();

		try {
			bufferedReader = request.getReader();
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
				sb.append(charBuffer, 0, bytesRead);
			}

			ack = parser.send(sb.toString());

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		writer.write(StringEscapeUtils.escapeJava(ack));

		System.out.println(ack);*/
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		StringBuffer sb = new StringBuffer();
		BufferedReader bufferedReader = null;
		String ack = "";
		ParserCommand parser = new ParserCommand();

		try {
			bufferedReader = request.getReader();
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
				sb.append(charBuffer, 0, bytesRead);
			}

			ack = parser.send(sb.toString());

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		writer.write(StringEscapeUtils.escapeJava(ack));

		// System.out.println(sb);
	}
}
