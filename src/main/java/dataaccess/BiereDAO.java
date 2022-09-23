package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Biere;

public class BiereDAO {
	public static List<Biere> getAllBieres() throws SQLException {
		return getBiere(Optional.empty(), Optional.empty(), Optional.empty());
	}

	public static Biere getBiereById(Integer biereId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where biere.biere_id = ?");
		return getBiere(whereClause, Optional.ofNullable(biereId), Optional.empty())
				.stream()
				.findAny()
				.get();
	}

	public static List<Biere> getBiereByCouleurId(int couleurId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where biere.couleur_id = ?");
		return getBiere(whereClause, Optional.ofNullable(couleurId), Optional.empty());
	}

	public static List<Biere> getBiereByFermentationId(int fermentationId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where bierefermentation.fermentation_id = ?");
		return getBiere(whereClause, Optional.empty(), Optional.ofNullable(fermentationId));
	}

	public static List<Biere> getBiereByCouleurAndFermentation(int couleurId, int fermentationId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where biere.couleur_id = ? "
				+ "and bierefermentation.fermentation_id = ?");
		return getBiere(whereClause, Optional.ofNullable(couleurId), Optional.ofNullable(fermentationId));
	}

	private static List<Biere> getBiere(
			Optional<String> whereClause,
			Optional<Integer> id,
			Optional<Integer> fermentationId) throws SQLException {
		List<Biere> biereList = new ArrayList<>();

		String q = "SELECT biere.biere_id,biere_nom,biere_description,biere_prix,biere_datecommerce,"
				+ "biere_pays,biere_degree,biere_gout,biere.couleur_id, "
				+ "couleur.couleur_nom,"
				+ "fermentation.fermentation_nom, fermentation.fermentation_id "
				+ "FROM biere "
				+ "inner join couleur on couleur.couleur_id = biere.couleur_id "
				+ "inner join bierefermentation on biere.biere_id = bierefermentation.biere_id "
				+ "inner join fermentation on fermentation.fermentation_id = bierefermentation.fermentation_id ";

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
				if (fermentationId.isPresent()) {
					p.setInt(2, fermentationId.get());
				}
			} else {
				if (fermentationId.isPresent()) {
					p.setInt(1, fermentationId.get());
				}
			}
			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Biere jeux = new Biere();

					jeux.setBiereId(rs.getInt("biere_id"));
					jeux.setBiereNom(rs.getString("biere_nom"));
					jeux.setBiereDescription(rs.getString("biere_description"));
					jeux.setPrix(rs.getDouble("biere_prix"));
					jeux.setDateCommerce(rs.getDate("biere_datecommerce"));
					jeux.setPays(rs.getString("biere_pays"));
					jeux.setDegree(rs.getDouble("biere_degree"));
					jeux.setGout(rs.getString("biere_gout"));
					jeux.setCouleurId(rs.getInt("couleur_id"));
					jeux.setCouleurNom(rs.getString("couleur_nom"));
					jeux.setFermentationNom(rs.getString("fermentation_nom"));
					jeux.setFermentationId(rs.getInt("fermentation_id"));

					biereList.add(jeux);
				}
			}
		}

		return biereList;
	}

	public void updateNomById(int biereId, String newNom) throws SQLException {
		String q = "update biere set biere_nom = ? where biere_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, newNom);
			p.setInt(2, biereId);

			p.execute();
		}
	}

	public void insertBiere(Biere biere) throws SQLException {
		String q = "insert biere values(null,?,?,?,?,?,?,?,?)";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
			p.setString(1, biere.getBiereNom());
			p.setString(2, biere.getBiereDescription());
			p.setDouble(3, biere.getPrix());
			p.setDate(4, biere.getDateCommerce());
			p.setString(5, biere.getPays());
			p.setDouble(6, biere.getDegree());
			p.setString(7, biere.getGout());
			p.setInt(8, biere.getCouleurId());
			int affectedRows = p.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating bière failed, no rows affected.");
			}

			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					biere.setBiereId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating bière failed, no ID obtained.");
				}
			}
		}
	}

	public void deleteBiereById(int biereId) throws SQLException {
		String q = "Delete from biere where biere_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, biereId);
			p.execute();
		}
	}

	public void updateBiere(Biere biere) throws SQLException {
		String q = "update biere set biere_nom = ?,"
				+ "biere_description=?,"
				+ "biere_pays=?,"
				+ "biere_degree=?,"
				+ "biere_gout=?, "
				+ "biere_prix=?, "
				+ "couleur_id=? "
				+ "where biere_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, biere.getBiereNom());
			p.setString(2, biere.getBiereDescription());
			p.setString(3, biere.getPays());
			p.setDouble(4, biere.getDegree());
			p.setString(5, biere.getGout());
			p.setDouble(6, biere.getPrix());
			p.setInt(7, biere.getCouleurId());
			p.setInt(8, biere.getBiereId());

			p.execute();
		}
	}

	public void clearFermentation(int biereId) throws SQLException {
		String q = "Delete from bierefermentation where biere_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, biereId);
			p.execute();
		}
	}

	public void updateFermentation(int biereId, String[] fermentation) throws SQLException {
		for (String string : fermentation) {
			String q = "insert bierefermentation(biere_id, fermentation_id) values(?,?)";

			try (Connection connection = ConnectionFactory.getInstance().getConnection();
					PreparedStatement p = connection.prepareStatement(q)) {
				p.setInt(1, biereId);
				int fermentationId = Integer.parseInt(string);
				p.setInt(2, fermentationId);
				p.execute();
			}
		}
	}
}
