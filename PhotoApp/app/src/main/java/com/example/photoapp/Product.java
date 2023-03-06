package com.example.photoapp;
;
public class Product {
	public int getProduct_idx() {
		return product_idx;
	}

	public void setProduct_idx(int product_idx) {
		this.product_idx = product_idx;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscount(int i) {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getCategory_idx() {
		return Category_idx;
	}

	public void setCategory_idx(int category_idx) {
		Category_idx = category_idx;
	}

	private int product_idx;
	private String product_name;
	private String brand;
	private int price;
	private int discount;
	private String detail;
	private int Category_idx; //fk

	
}



