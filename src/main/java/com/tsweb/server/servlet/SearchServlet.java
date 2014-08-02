package com.tsweb.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsweb.bean.SearchReuslt;
import com.tsweb.lucene.Searcher;
import com.tsweb.util.JSONUtil;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int page = 1;
	private int limit = 20;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=UTF8");
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();

		req.setCharacterEncoding("UTF-8");
		String query = req.getParameter("query");
		if (query != null && !query.isEmpty()) {
			String word = new String(query.getBytes("ISO-8859-1"), "UTF-8");

			String textPage = req.getParameter("page");
			String textLimit = req.getParameter("limit");

			if (textPage != null && !textPage.isEmpty()) {
				page = Integer.parseInt(textPage.trim());
			}

			if (textLimit != null && !textLimit.isEmpty()) {
				limit = Integer.parseInt(textLimit.trim());
			}

			try {
				Searcher search = new Searcher();
				List<SearchReuslt> applications = search.query(word, page, limit);
				String jsonResult = JSONUtil.writeValue(applications);

				out.println("{\"result\":" + jsonResult + "}");
			} finally {
				out.close();
			}
		} else {
			out.print("query required");
		}
	}
}
