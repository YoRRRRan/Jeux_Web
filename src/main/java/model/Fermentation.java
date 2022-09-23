package model;

public class Fermentation {
	private int fermentationId;
	private String fermentationNom;
	private String fermentationDescription;
	private boolean checked;

	public int getFermentationId() {
		return fermentationId;
	}

	public void setFermentationId(int fermentationId) {
		this.fermentationId = fermentationId;
	}

	public String getFermentationNom() {
		return fermentationNom;
	}

	public void setFermentationNom(String nom) {
		this.fermentationNom = nom;
	}

	public String getFermentationDescription() {
		return fermentationDescription;
	}

	public void setFermentationDescription(String fermentationDescription) {
		this.fermentationDescription = fermentationDescription;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "Fermentation [fermentationId=" + fermentationId + ", fermentationNom=" + fermentationNom
				+ ", fermentationDescription=" + fermentationDescription + ", checked=" + checked + "]";
	}
	
	
}