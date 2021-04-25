package booksys.domain;

public class Table {
	private int oid;
	private int number;
	private int places;
	public Table(int number, int places) {
		this.number=number;
		this.places=places;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getPlaces() {
		return places;
	}
	public void setPlaces(int places) {
		this.places = places;
	}
	public String toString() {
		return "Table [oid=" + oid + ", number=" + number + ", places=" + places + "]";
	}
	
	
}
