package routing;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Fermentation;

import java.io.IOException;
import java.sql.SQLException;

import dataaccess.FermentationDAO;

/**
 * Servlet implementation class NewFermentation
 */
public class NewFermentation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewFermentation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Fermentation fermentation = null;
		String message = "";

		String fermentationId = request.getParameter("fermentationId");
		if (fermentationId == null) {
			// Create an empty fermentation so that the page is empty
			fermentation = new Fermentation();
		} else {
			// Get the fermentation from the DAO
			try {
				fermentation = FermentationDAO.getFermentationByFermentationId(Integer.parseInt(fermentationId));
			} catch (Exception e) {
				message = "oops";
			}
		}
		HttpSession session = request.getSession();
		// Put fermentation in the session for the next page
		session.setAttribute("fermentation", fermentation);
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher("/WEB-INF/newfermentation.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "";
		HttpSession session = request.getSession();
		Fermentation fermentation = (Fermentation) session.getAttribute("fermentation");
		fermentation.setFermentationNom(request.getParameter("nom"));
		fermentation.setFermentationDescription(request.getParameter("description"));

		try {
			if (fermentation.getFermentationId() > 0) {
				// We already have a fermentation_Id so do an update
				FermentationDAO.updateFermentation(fermentation);
				message = "fermentation updated";
			} else {
				FermentationDAO.insertFermentation(fermentation);
				message = "fermentation created";
			}
		} catch (SQLException e) {
			message = "Enter a new fermentation name.";
		}

		request.setAttribute("message", message);
		session.setAttribute("fermentation", fermentation);
		getServletContext().getRequestDispatcher("/WEB-INF/newfermentation.jsp").forward(request, response);
	}
}
