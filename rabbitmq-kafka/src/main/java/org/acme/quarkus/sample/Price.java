package org.acme.quarkus.sample;

public class Price {

	private String label;
	private Integer price;

	public Price() {}
	
	public Price(String label, Integer price) {
		this.label = label;
		this.price = price;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
