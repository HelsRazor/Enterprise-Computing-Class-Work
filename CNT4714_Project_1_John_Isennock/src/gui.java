import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class gui {
	private int numOfItems = 1;
	private File in = new File("inventory.txt");
	private File out = new File("transactions.txt");
	private order customerOrder = new order();
	private JFrame frame = new JFrame("Nile Dot Com");
	
	
	public int getNumOfItems() {
		return numOfItems;
	}



	public void setNumOfItems(int numOfItems) {
		this.numOfItems = numOfItems;
	}



	public gui() {
		
		//Creates the frame called Nile Dot Com
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,250);
		
		//Creates the Panel that will host the labels and Text Fields
		JPanel centralPanel = new JPanel();
		JLabel labelNumItemsInOrder = new JLabel("                                                                        Enter number of items in this order: ");
		JTextField textFieldNumItemsInOrder = new JTextField(50);
		JLabel labelItemID = new JLabel(String.format("                                                                                            Enter item ID for item #%d: ", getNumOfItems()));
		JTextField textFieldItemID = new JTextField(50);
		JLabel labelQuantity = new JLabel(String.format("                                                                                         Enter Quantity for item #%d: ", getNumOfItems()));
		JTextField textFieldQuantity = new JTextField(50);
		JLabel labelInfo = new JLabel(String.format("                                                                                                                   Item #%d info: ",getNumOfItems()));
		JTextField textFieldInfo = new JTextField(50);
		JLabel labelSubTot = new JLabel(String.format("                                                                                      Order subtotal for %d item(s): ", getNumOfItems()-1));
		JTextField textFieldSubTot = new JTextField(50);
		
		//sets the color of each labels text to yellow
		labelNumItemsInOrder.setForeground(Color.YELLOW);
		labelItemID.setForeground(Color.YELLOW);
		labelQuantity.setForeground(Color.YELLOW);
		labelInfo.setForeground(Color.YELLOW);
		labelSubTot.setForeground(Color.YELLOW);
		
		//adds the labels and text fields to the central panel
		centralPanel.add(labelNumItemsInOrder);
		centralPanel.add(textFieldNumItemsInOrder);
		centralPanel.add(labelItemID);
		centralPanel.add(textFieldItemID);
		centralPanel.add(labelQuantity);
		centralPanel.add(textFieldQuantity);
		centralPanel.add(labelInfo);
		centralPanel.add(textFieldInfo);
		centralPanel.add(labelSubTot);
		centralPanel.add(textFieldSubTot);
		
		//sets the item info and the sub total as non-editable
		textFieldInfo.setEditable(false);
		textFieldSubTot.setEditable(false);
		
		//creates the panel that will host the buttons
		JPanel panel = new JPanel();
		JButton processItem = new JButton(String.format("Process Item #%d", getNumOfItems()));
		JButton confirmItem = new JButton(String.format("Confirm Item #%d", getNumOfItems()));
		JButton viewOrder = new JButton("View Order");
		JButton finishOrder = new JButton("Finish Order");
		JButton newOrder = new JButton("New Order");
		JButton exit = new JButton("Exit");
		
		//creates the action for Confirm Item
		confirmItem.addActionListener(e->{
			viewOrder.setEnabled(true);
			finishOrder.setEnabled(true);
			
			customerOrder.subtotaler();
			labelSubTot.setText(String.format("                                                                                      Order subtotal for %d item(s): ", getNumOfItems()-1, numOfItems));
			textFieldSubTot.setText(String.format("%.2f",customerOrder.getOrderSubTotal()));
			
			if(Integer.parseInt(textFieldNumItemsInOrder.getText().trim()) != numOfItems)
			{
				numOfItems++;
				processItem.setText(String.format("Process Item #%d", numOfItems));
				confirmItem.setText(String.format("Confirm Item #%d", numOfItems));
				textFieldItemID.setText("");
				labelItemID.setText(String.format("                                                                                            Enter item ID for item #%d: ", getNumOfItems()));
				textFieldQuantity.setText("");
				labelQuantity.setText(String.format("                                                                                         Enter Quantity for item #%d: ", getNumOfItems()));
				textFieldItemID.setEditable(true);
				textFieldQuantity.setEditable(true);
				processItem.setEnabled(true);
				
			}
			textFieldItemID.setText("");
			labelItemID.setText(String.format("                                                                                            Enter item ID for item #%d: ", getNumOfItems()));
			textFieldQuantity.setText("");
			labelQuantity.setText(String.format("                                                                                         Enter Quantity for item #%d: ", getNumOfItems()));
			confirmItem.setEnabled(false);
			
		});
		
		//Creates the action for process Item
		processItem.addActionListener(e-> {
			if( Integer.parseInt(textFieldNumItemsInOrder.getText().trim())>0) {
				textFieldNumItemsInOrder.setEditable(false);
			}
			try {
				if( Integer.parseInt(textFieldNumItemsInOrder.getText().trim())>0 && Integer.parseInt(textFieldQuantity.getText().trim())>0 && (processItemForOrder(textFieldItemID.getText().trim(),Integer.parseInt(textFieldQuantity.getText().trim())) == true))
				{
					
					
					textFieldInfo.setText(updateItemInfoField());
					labelInfo.setText(String.format("                                                                                                                   Item #%d info: ",getNumOfItems()));
					textFieldItemID.setEditable(false);
					textFieldQuantity.setEditable(false);
					confirmItem.setEnabled(true);
					processItem.setEnabled(false);
					
				}else if(Integer.parseInt(textFieldNumItemsInOrder.getText().trim())<=0)
				{
					JOptionPane.showMessageDialog(frame, "Please enter a value greater than zero for the Number of Items in your order!");
				} else if (Integer.parseInt(textFieldQuantity.getText().trim())<=0)
				{
					JOptionPane.showMessageDialog(frame, "Please enter a value greater than zero for the Quantity of Items in your order!");
				}
				
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
		});
		
		//Creates the action for view order
		viewOrder.addActionListener(e -> {
			JOptionPane.showMessageDialog(frame, String.format("%s",customerOrder.showPreview()));
			});
		
		//Creates the action for finish order
		finishOrder.addActionListener(e->{
			customerOrder.setOrderTotal();
			completeOrderFunc();
		});
		
		//Creates the action for new order
		newOrder.addActionListener(e-> {
			
			customerOrder = new order();
			
			resetLabelsAndFields(textFieldNumItemsInOrder,textFieldItemID,textFieldQuantity,textFieldInfo,textFieldSubTot,labelItemID,labelQuantity,labelInfo,labelSubTot);
			processItem.setText(String.format("Process Item #%d", numOfItems));
			confirmItem.setText(String.format("Confirm Item #%d", numOfItems));
			
			processItem.setEnabled(true);
			confirmItem.setEnabled(false);
			viewOrder.setEnabled(false);
			finishOrder.setEnabled(false);
			
			
		});
		
		//Creates the action for exit
		exit.addActionListener(e-> {
			frame.dispose();
		});
		
		//adds all buttons to the panel
		panel.add(processItem);
		panel.add(confirmItem);
		panel.add(viewOrder);
		panel.add(finishOrder);
		panel.add(newOrder);
		panel.add(exit);
		
		//disables buttons that can not be used right away
		confirmItem.setEnabled(false);
		viewOrder.setEnabled(false);
		finishOrder.setEnabled(false);
		
		//formats the frame
		JPanel spacingPanel = new JPanel(new BorderLayout());
		spacingPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		
		
		frame.getContentPane().add(BorderLayout.NORTH, spacingPanel).setBackground(Color.BLACK);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().add(BorderLayout.SOUTH, panel).setBackground(Color.BLUE);
		frame.getContentPane().add(BorderLayout.CENTER, centralPanel).setBackground(Color.BLACK);
		
		//sets the frame to be visible
		frame.setVisible(true);
	}

	//checks for the item if found returns true
	private boolean processItemForOrder(String x, int y) throws IOException
	{

	    Scanner sc = new Scanner(in);
	    String[] checker;
		while (sc.hasNextLine())
		{

			checker = sc.nextLine().split(",");
			
			
			
			if((checker[0].trim().equalsIgnoreCase(x)) && checker[2].trim().equalsIgnoreCase("true"))
			{	
				customerOrder.createOrderItem(checker,y);
				//sc.close();
				return true;
			}else if(checker[0].trim().equalsIgnoreCase(x) && checker[2].trim().equalsIgnoreCase("false"))
			{
				JOptionPane.showMessageDialog(frame,String.format("Sorry.. that item is out of stock, please try another item"));
				//sc.close();
				return false;
			}

		} 
		
			JOptionPane.showMessageDialog(frame,String.format("item ID %s not in file", x));
			//sc.close();
			return false;
		
		
	}
	
	//formats the item information for the item information field
	private String updateItemInfoField()
	{
		String newTextField;
		item t = customerOrder.getItemsInOrder().get(customerOrder.getItemsInOrder().size() - 1);
		newTextField = String.format("%s %s $%.2f %d %.1f%% $%.2f", t.getItemId(), t.getItemDescription(), t.getItemUnitPrice(), t.getItemQuantity(), t.getDiscount(), t.getItemSubtotal());
		customerOrder.orderPreviewCreation(newTextField);
		return newTextField;
	}
	
	//Creates the Message window and then calls to the outputToFile function
	private void completeOrderFunc()
	{
		TimeZone tz = TimeZone.getDefault();
		String dateFormat = "dd/MM/yy";
		String timeFormat = "hh:mm:ss a zzz";
		
		String dateOfPurchase = "Date: ";
		String numOfLineItems = String.format("Number of line items: %d", getNumOfItems());
		String keyFormat = "Item# / ID / Title / Price / Qty / Disc %% / Subtotal:";
		String ordSubTot = String.format("Order subtotal: %.2f", customerOrder.getOrderSubTotal());
		String taxRateStr = String.format("Tax Rate:   %.1f%%", customerOrder.getTaxRate());
		String amntTaxed = String.format("Tax amount:   $%.2f", customerOrder.getOrderSubTotal()*(customerOrder.getTaxRate()/100));
		String ordTotal = String.format("Order total:   $%.2f", customerOrder.getOrderTotal());
		String thanks = "Thank for shopping at Nile Dot Com!";
		
		
		Date todayDate = new Date();
		DateFormat todayDateFormat = new SimpleDateFormat(dateFormat);
		todayDateFormat.setTimeZone(tz);
		String strToday = todayDateFormat.format(todayDate);
		
		DateFormat currTimeForm = new SimpleDateFormat(timeFormat);
		Calendar cal = Calendar.getInstance(tz);
		currTimeForm.setTimeZone(cal.getTimeZone());
		String strCurrTime = currTimeForm.format(cal.getTime());
		
		dateOfPurchase = dateOfPurchase.concat(String.format("%s, %s", strToday, strCurrTime));
		
		JFrame frame = new JFrame("Nile Dot Com");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JOptionPane.showMessageDialog(frame,String.format("%s\n\n%s\n\n%s\n\n%s\n\n\n%s\n\n%s\n\n%s\n\n%s\n\n%s",dateOfPurchase,numOfLineItems,keyFormat,customerOrder.showPreview(),ordSubTot,taxRateStr,amntTaxed,ordTotal,thanks));
		
		outputToFile();
	}
	
	//function to output the finished order to the file
	private void outputToFile()
	{
		TimeZone tz = TimeZone.getDefault();
		String dateFormat = "ddMMyyyy";
		String timeFormat = "hhmm";
		
		Date today = new Date();
		DateFormat todayDateFormat = new SimpleDateFormat(dateFormat);
		todayDateFormat.setTimeZone(tz);
		String strToday = todayDateFormat.format(today);
		
		DateFormat currTimeForm = new SimpleDateFormat(timeFormat);
		Calendar cal = Calendar.getInstance(tz);
		currTimeForm.setTimeZone(cal.getTimeZone());
		String strCurrTime = currTimeForm.format(cal.getTime());
		
		String transactionId = strToday + strCurrTime;
		
		dateFormat = "MM/dd/yy";
		timeFormat = "hh:mm:ss a zzz";
		
		todayDateFormat = new SimpleDateFormat(dateFormat);
		todayDateFormat.setTimeZone(tz);
		strToday = todayDateFormat.format(today);
		
		currTimeForm = new SimpleDateFormat(timeFormat);
		cal = Calendar.getInstance(tz);
		currTimeForm.setTimeZone(cal.getTimeZone());
		strCurrTime = currTimeForm.format(cal.getTime());
		
		
		for(int i = 0; i<numOfItems;i++)
		{
			String fileOutputstr = String.format("%s, %s, %s, %.2f, %d, %.1f, %.2f, %s, %s\n",transactionId,customerOrder.getItemsInOrder().get(i).getItemId(),customerOrder.getItemsInOrder().get(i).getItemDescription(),customerOrder.getItemsInOrder().get(i).getItemUnitPrice(),customerOrder.getItemsInOrder().get(i).getItemQuantity(),customerOrder.getItemsInOrder().get(i).getDiscount()/100,customerOrder.getItemsInOrder().get(i).getItemSubtotal(),strToday,strCurrTime);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(out,true));
				writer.write(fileOutputstr);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	
	//resets the window after clicking new order
	private void resetLabelsAndFields(JTextField textFieldNumItemsInOrder,JTextField textFieldItemID,JTextField textFieldQuantity,JTextField textFieldInfo,JTextField textFieldSubTot,JLabel labelItemID,JLabel labelQuantity,JLabel labelInfo,JLabel labelSubTot)
	{			
		setNumOfItems(1);
		textFieldNumItemsInOrder.setText("");
		textFieldNumItemsInOrder.setEditable(true);
		labelItemID.setText(String.format("                                                                                            Enter item ID for item #%d: ", getNumOfItems()));
		textFieldItemID.setText("");
		textFieldItemID.setEditable(true);
		labelQuantity.setText(String.format("                                                                                         Enter Quantity for item #%d: ", getNumOfItems()));
		textFieldQuantity.setText("");
		textFieldQuantity.setEditable(true);
		labelInfo.setText(String.format("                                                                                                                   Item #%d info: ",getNumOfItems()));
		textFieldInfo.setText("");
		labelSubTot.setText(String.format("                                                                                      Order subtotal for %d item(s): ", getNumOfItems()-1));
		textFieldSubTot.setText("");
		
		
	}
}
