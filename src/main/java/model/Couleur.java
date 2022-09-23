package model;

public class Couleur {
	private int couleurId;
	private String couleurNom;
	private String couleurDescription;

	public int getCouleurId() {
		return couleurId;
	}

	public void setCouleurId(int couleurId) {
		this.couleurId = couleurId;
	}

	public String getCouleurNom() {
		return couleurNom;
	}

	public void setCouleurNom(String couleurNom) {
		this.couleurNom = couleurNom;
	}

	public String getCouleurDescription() {
		return couleurDescription;
	}

	public void setCouleurDescription(String couleurDescription) {
		this.couleurDescription = couleurDescription;
	}

	@Override
	public String toString() {
		return "Couleur [couleurId=" + couleurId + ", couleurNom=" + couleurNom + ", couleurDescription="
				+ couleurDescription + "]";
	}
	

}