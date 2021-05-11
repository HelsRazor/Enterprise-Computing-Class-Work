import java.util.ArrayList;

public class order {
	private ArrayList<item> itemsInOrder = new ArrayList<item>();
	private ArrayList<String> orderPreview = new ArrayList<String>();
	private double orderSubTotal;
	private double orderTotal;
	private double taxRate;
	
	
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	
	
	public ArrayList<String> getOrderPreview() {
		return orderPreview;
	}
	public void setOrderPreview(ArrayList<String> orderPreview) {
		this.orderPreview = orderPreview;
	}
	
	
	public ArrayList<item> getItemsInOrder() {
		return itemsInOrder;
	}
	public void setItemsInOrder(ArrayList<item> itemsInOrder) {
		this.itemsInOrder = itemsInOrder;
	}
	
	
	public double getOrderSubTotal() {
		return orderSubTotal;
	}
	public void setOrderSubTotal(double orderSubTotal) {
		this.orderSubTotal = orderSubTotal;
	}
	
	
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal() {
		this.orderTotal = getOrderSubTotal() * (1+(getTaxRate()/100));
	}
	
	
	public order()
	{
		setItemsInOrder(null);
		setOrderPreview(null);
		setOrderSubTotal(0);
		setTaxRate(6);
	}
	
	//creates an item to add to the order based off the entered ID and quantity
	public void createOrderItem(String[] s,int quant)
	{
		if(getItemsInOrder() == null)
		{
			
			item temp = new item();
			temp.setItemId(s[0]);
			temp.setItemDescription(s[1]);
			temp.setItemStockStatus(s[2]);
			temp.setItemUnitPrice(Double.parseDouble(s[3]));
			temp.setItemQuantity(quant);
			temp.setDiscount(determinDiscount(temp.getItemQuantity()));
			temp.setItemSubtotal();
			
			ArrayList <item> tList= new ArrayList <item>();
			tList.add(temp);
			setItemsInOrder(tList);
			
		}else {
			
			item temp = new item();
			temp.setItemId(s[0]);
			temp.setItemDescription(s[1]);
			temp.setItemStockStatus(s[2]);
			temp.setItemUnitPrice(Double.parseDouble(s[3]));
			temp.setItemQuantity(quant);
			temp.setDiscount(determinDiscount(temp.getItemQuantity()));
			temp.setItemSubtotal();
			
			itemsInOrder.add(temp);
			
		}
	}
	
	//figures out how much if any discount will be given
	private double determinDiscount(int iQuant)
	{
		if(iQuant <= 4)
		{
			return 0;
		}else if(iQuant<=9 && iQuant>=5)
		{
			return 10;
		}else if(iQuant<=14 && iQuant>=10)
		{
			return 15;
		}else {
			return 20;
		}
	}
	
	//creates the sub-total for the order
	public void subtotaler()
	{
		item tempItem = itemsInOrder.get(itemsInOrder.size()-1);
		if(getOrderSubTotal() == 0)
		{
			setOrderSubTotal(tempItem.getItemSubtotal());
		}else {
			setOrderSubTotal((getOrderSubTotal() + tempItem.getItemSubtotal()));
		}
	}
	
	//creates an array list of strings to create the order view button
	public void orderPreviewCreation(String s)
	{
		if(getOrderPreview() == null)
		{

			ArrayList<String> t = new ArrayList<String>();
			t.add(s);
			setOrderPreview(t);
		}else {
		
			ArrayList<String> t = getOrderPreview();
			t.add(s);
			setOrderPreview(t);
			
		}
	}
	
	//returns a formated string for use in the view order button window
	public String showPreview()
	{

		ArrayList <String> t = getOrderPreview();

		String temp = "";
		System.out.print(temp);
		for(int i = 0; i<t.size() ; i++)
		{
			if(temp.equals(""))
			{

				temp = String.format("%d. %s",i+1,t.get(i))+"\n";
			}else {

				temp = temp.concat(String.format("%d. %s",i+1,t.get(i))) + "\n";

			}
			

		}

		return temp;
	}
	
}
