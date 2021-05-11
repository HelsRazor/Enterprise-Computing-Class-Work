//This class will act as an Object to store an item's info when it is found
public class item {
	
	
	private String itemId;
	private String itemDescription;
	private String itemStockStatus;
	private double itemUnitPrice;
	private double discount;
	private double itemSubtotal;
	private int itemQuantity;
	
	
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}	
	
	
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	
	
	public String getItemStockStatus() {
		return itemStockStatus;
	}
	public void setItemStockStatus(String itemStockStatus) {
		this.itemStockStatus = itemStockStatus;
	}
	
	
	public double getItemUnitPrice() {
		return itemUnitPrice;
	}
	public void setItemUnitPrice(double itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}
	
	
	public double getItemSubtotal() {
		return itemSubtotal;
	}
	public void setItemSubtotal() {
		if(getDiscount()>0) {
			this.itemSubtotal = (getItemUnitPrice() * getItemQuantity())-((getItemUnitPrice() * getItemQuantity()) * (getDiscount()/100));
		}else {
			this.itemSubtotal = getItemUnitPrice() * getItemQuantity();
		}
	}
	
	
	
}
