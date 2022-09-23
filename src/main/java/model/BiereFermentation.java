package model;


public class BiereFermentation {
	private int biereId;
	private int fermentationId;

	public int getBiereId() {
		return biereId;
	}

	public void setBiereId(int biereId) {
		this.biereId = biereId;
	}

	public int getFermentationId() {
		return fermentationId;
	}

	public void setFermentationId(int fermentationId) {
		this.fermentationId = fermentationId;
	}

	@Override
	public String toString() {
		return "BiereFermentation [biereId=" + biereId + ", fermentationId=" + fermentationId + "]";
	}

	

}