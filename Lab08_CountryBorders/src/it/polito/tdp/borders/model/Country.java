package it.polito.tdp.borders.model;

public class Country {

	private final String abb;
	private final int code;
	private final String name;
	private int numeroStatiConfinanti;
	/**
	 * @param abb
	 * @param code
	 * @param name
	 */
	public Country(String abb, int code, String name) {
		super();
		this.abb = abb;
		this.code = code;
		this.name = name;
	}
	/**
	 * @return the numeroStatiConfinanti
	 */
	public int getNumeroStatiConfinanti() {
		return numeroStatiConfinanti;
	}
	/**
	 * @param numeroStatiConfinanti the numeroStatiConfinanti to set
	 */
	public void setNumeroStatiConfinanti(int numeroStatiConfinanti) {
		this.numeroStatiConfinanti = numeroStatiConfinanti;
	}
	/**
	 * @return the abb
	 */
	public String getAbb() {
		return abb;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abb == null) ? 0 : abb.hashCode());
		result = prime * result + code;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (abb == null) {
			if (other.abb != null)
				return false;
		} else if (!abb.equals(other.abb))
			return false;
		if (code != other.code)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getAbb() + " [" + getName() + "] - " + getNumeroStatiConfinanti() + "\n";
	}
	
	
	
}
