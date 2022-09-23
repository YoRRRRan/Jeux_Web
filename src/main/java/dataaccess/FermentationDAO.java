package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Fermentation;

public class FermentationDAO {
	public static List<Fermentation> getAllFermentation() throws SQLException {
		return getFermentation(Optional.empty(), Optional.empty());
	}

	public static Fermentation getFermentationByFermentationId(Integer fermentationId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where fermentation.fermentation_id = ?");
		return getFermentation(whereClause, Optional.ofNullable(fermentationId))
				.stream()
				.findAny()
				.get();
	}

	private static List<Fermentation> getFermentation(Optional<String> whereClause, Optional<Integer> id)
			throws SQLException {
		List<Fermentation> fermentationList = new ArrayList<>();

		String q = "Select fermentation_id, fermentation_nom, fermentation_description "
				+ "FROM fermentation ";

		if (whereClause.isPresent()) {
			q += whereClause.get();
		}

		// try with resources PreparedStatement implements AutoCloseable
		// ConnectionFactory c'est une usine qui donne une connection
		// PreparedStatement plus securisé que statement normal (pas de SQL injection)
		// pour envoyé au BDD la requête.
		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			if (id.isPresent()) {
				p.setInt(1, id.get());
			}
			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Fermentation fermentation = new Fermentation();

					fermentation.setFermentationId(rs.getInt("fermentation_id"));
					fermentation.setFermentationNom(rs.getString("fermentation_nom"));
					fermentation.setFermentationDescription(rs.getString("fermentation_description"));

					fermentationList.add(fermentation);
				}
			}
		}

		return fermentationList;
	}

	public static void insertFermentation(Fermentation fermentation) throws SQLException {
		String q = "insert fermentation values(null,?,?)";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {

			p.setString(1, fermentation.getFermentationNom());
			p.setString(2, fermentation.getFermentationDescription());

			int affectedRows = p.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating fermentation failed, no rows affected.");
			}

			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					fermentation.setFermentationId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating fermentation failed, no ID obtained.");
				}
			}
		}
	}

	public void deleteFermentationById(int fermentationId) throws SQLException {
		String q = "Delete from fermentation where fermentation_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, fermentationId);
			p.execute();
		}
	}

	public static void updateFermentation(Fermentation fermentation) throws SQLException {
		String q = "update fermentation set fermentation_nom = ?,"
				+ "fermentation_description=? "
				+ "where fermentation_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, fermentation.getFermentationNom());
			p.setString(2, fermentation.getFermentationDescription());
			p.setInt(3, fermentation.getFermentationId());

			p.execute();
		}
	}

	public static List<Fermentation> getBiereFermentation(int biereId) throws SQLException {
		List<Fermentation> fermentationList = new ArrayList<>();
		String q = "select p.fermentation_id, p.fermentation_nom, p.fermentation_description, "
				+ "(select count(*) from bierefermentation jp where jp.biere_id=? and p.fermentation_id=jp.fermentation_id) as checked "
				+ "from fermentation p";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, biereId);
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Fermentation fermentation = new Fermentation();

					fermentation.setFermentationId(rs.getInt("fermentation_Id"));
					fermentation.setFermentationNom(rs.getString("fermentation_nom"));
					fermentation.setFermentationDescription(rs.getString("fermentation_description"));
					fermentation.setChecked(rs.getBoolean("checked"));

					fermentationList.add(fermentation);
				}
			}
		}

		return fermentationList;
	}
}
