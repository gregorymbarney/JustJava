package com.example.duo.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JustJava extends AppCompatActivity {

    //declare and set integer for quantity of coffee to 1
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_java);
    }

    //calculate price and find toppings
    public void submitOrder(View view) {
        //get cx( aka customer) name
        EditText nameField = (EditText) findViewById(R.id.cxname);
        String name = nameField.getText().toString();
        //checkboxes to see if cx wants whip, gummy bears, or fish toppings
        CheckBox whipCheckBox = (CheckBox) findViewById(R.id.whip);
        boolean hasWhip = whipCheckBox.isChecked();
        CheckBox bearCheckBox = (CheckBox) findViewById(R.id.bear);
        boolean hasBear = bearCheckBox.isChecked();
        CheckBox fishCheckBox = (CheckBox) findViewById(R.id.fish);
        boolean hasFish = fishCheckBox.isChecked();
        //calculate pricing
        int price = calcPrice(hasWhip, hasBear, hasFish);
        String orderMessage = createOrderSummary(name, price, hasWhip, hasBear, hasFish);
        //start intent to launch email app at compose msg with order details
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:order@justjava.biz")); //mail mfer
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
    //returning the price
    private int calcPrice(boolean hasWhip, boolean hasBear, boolean hasFish) {
        int defaultPrice = 3;
        //add cost for whip
        if (hasWhip){
            defaultPrice = defaultPrice + 1;
        }
        //add cost for gummy bears
        if (hasBear){
            defaultPrice = defaultPrice + 2;
        }
        //add cost for fish
        if (hasFish){
            defaultPrice = defaultPrice + 3;
        }
        //default price return
        return quantity * defaultPrice;
    }
    //summary of order
    private String createOrderSummary(String name, int price, boolean addWhip, boolean addBear, boolean addFish) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhip;
        priceMessage += "\nAdd gummy bears? " + addBear;
        priceMessage += "\nAdd fish? " + addFish;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nEnjoy!";
        return priceMessage;
    }
    //increases amount of coffee by 1    private void displayMessage(int number){
    public void increment(View view) {
        if (quantity == 15) {
            //give notification if cx tries ordering more than 15 cups
            Toast.makeText(this, "You cannot order more than 15 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity +1;
        display(quantity);
    }
    //decreases amount of coffee by 1
    public void decrement(View view) {
        if (quantity == 1){
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }
    //displays the quantity
    private void display(int number) {
        TextView quantity = (TextView) findViewById(
                R.id.quantity);
        quantity.setText("" + number);
    }

}
