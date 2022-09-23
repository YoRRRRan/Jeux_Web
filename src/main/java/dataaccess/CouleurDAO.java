package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Couleur;

public class CouleurDAO {
	public static List<Couleur> getAllCouleur() throws SQLException {
		List<Couleur> couleurList = new ArrayList<>();

		String q = "SELECT couleur_id, couleur_nom, couleur_description "
				+ "FROM couleur";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {

			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Couleur Couleur = new Couleur();

					Couleur.setCouleurId(rs.getInt("couleur_id"));
					Couleur.setCouleurNom(rs.getString("couleur_nom"));
					Couleur.setCouleurDescription(rs.getString("couleur_description"));

					couleurList.add(Couleur);
				}
			}
		}

		return couleurList;
	}

	public static void updateCouleur(Couleur couleur) throws SQLException {
		String q = "update couleur "
				+ "set couleur_nom = ?,"
				+ "couleur_description=? "
				+ "where couleur_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, couleur.getCouleurNom());
			p.setString(2, couleur.getCouleurDescription());
			p.setInt(3, couleur.getCouleurId());

			p.execute();
		}
	}

	public static void insertCouleur(Couleur Couleur) throws SQLException {
		String q = "insert couleur values(null,?,?)";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
			p.setString(1, Couleur.getCouleurNom());
			p.setString(2, Couleur.getCouleurDescription());
			int affectedRows = p.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating Couleur failed, no rows affected.");
			}

			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					Couleur.setCouleurId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating Couleur failed, no ID obtained.");
				}
			}
		}
	}

	public void deleteCouleurById(int CouleurId) throws SQLException {
		String q = "Delete from couleur where couleur_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, CouleurId);
			p.execute();
		}
	}

	public static Couleur getCouleurById(int couleurId) throws SQLException {
		String q = "SELECT couleur_Id, couleur_nom, couleur_description "
				+ "FROM couleur where couleur_id=?";
		Couleur couleur = new Couleur();

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, couleurId);
			
			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {

					couleur.setCouleurId(rs.getInt("couleur_id"));
					couleur.setCouleurNom(rs.getString("couleur_nom"));
					couleur.setCouleurDescription(rs.getString("couleur_description"));
					break;
				}
			}
		}

		return couleur;
	}
}
