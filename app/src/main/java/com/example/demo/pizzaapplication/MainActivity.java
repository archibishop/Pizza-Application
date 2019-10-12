package com.example.demo.pizzaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView selectedPizza;
    private RadioGroup radioGrpIngredients, radioShapeGroup, selectedButton;
    private EditText customerName, customerPhoneNo;
    private Button placeOrder;
    private CheckBox pepperoni, mushrooms, veggies, anchovies;
    private ChipGroup chipGroup;
    private ArrayList<String> toppings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedPizza = findViewById(R.id.selected_pizza_image);
        radioGrpIngredients = findViewById(R.id.radio_ingredients);
        radioShapeGroup = findViewById(R.id.radio_shape);
        customerName = findViewById(R.id.customer_name);
        customerPhoneNo = findViewById(R.id.customer_phone_no);
        placeOrder = findViewById(R.id.place_order);
        pepperoni = findViewById(R.id.pepperoni);
        mushrooms = findViewById(R.id.mush_rooms);
        veggies = findViewById(R.id.veggies);
        anchovies = findViewById(R.id.anchovies);
        chipGroup = findViewById(R.id.chip_group);

        toppings = new ArrayList<String>();
        toppings.add("No Cheese");
        toppings.add("Cheese");
        toppings.add("2x Cheese");

        pepperoni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                addChipToGroup(isChecked , "pepperoni");
            }
        });

        mushrooms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                addChipToGroup(isChecked , "mushrooms");
            }
        });

        veggies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                addChipToGroup(isChecked , "veggies");
            }
        });

        anchovies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                addChipToGroup(isChecked , "anchovies");
            }
        });

        radioShapeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedRadioButton.setTextColor(Color.parseColor("#DA4A2D"));

                if (selectedRadioButton.getText().toString().equalsIgnoreCase("ROUND")) {
                    selectedPizza.setImageResource(R.drawable.ic_round_pizza);
                } else if ((selectedRadioButton.getText().toString().equalsIgnoreCase("SQUARE"))) {
                    selectedPizza.setImageResource(R.drawable.ic_squared_pizza);
                }

                int count = radioShapeGroup.getChildCount();
                int i = 0;
                while (i < count) {
                    RadioButton radioButton = (RadioButton) radioShapeGroup.getChildAt(i);
                    if (!radioButton.isChecked()) {
                        radioButton.setTextColor(Color.parseColor("#000000"));
                    }
                    i++;
                }
            }
        });

        radioGrpIngredients.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                addChipToGroup(true , selectedRadioButton.getText().toString());
            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()){
                    int id  = radioGrpIngredients.getCheckedRadioButtonId();
                    selectedButton = findViewById(id);
                    selectedButton.getText();
                    addChipToGroup(true , selectedButton.getText().toString());
                    showToast("Your order has been successfully placed." + selectedButton.getText());
                }
            }
        });
    }

    private void addChipToGroup(boolean isChecked, String add) {
        Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.chip, null, false);
        chip.setText(add);

        if (isChecked) {
            chipGroup.addView(chip);
            if (toppings.contains(add)){
                toppings.remove(add);
                for (String value: toppings) {
                    int count = chipGroup.getChildCount();
                    int i = 0;
                    while (i < count) {
                        Chip newChip = (Chip) chipGroup.getChildAt(i);
                        if (newChip.getText().toString().equalsIgnoreCase(value)) {
                            chipGroup.removeView(newChip);
                            break;
                        }
                        i++;
                    }
                }
                toppings.add(add);
            }
        } else {
            int count = chipGroup.getChildCount();
            int i = 0;
            while (i < count) {
                Chip newChip = (Chip) chipGroup.getChildAt(i);
                if (newChip.getText().toString().equalsIgnoreCase(add)) {
                    chipGroup.removeView(newChip);
                    break;
                }
                i++;
            }
        }
    }

    private boolean validateFields() {
        Boolean nameEmpty = customerName.getText().toString().isEmpty();
        Boolean phoneNumberEmpty = customerPhoneNo.getText().toString().isEmpty();
        int selectedId  = radioGrpIngredients.getCheckedRadioButtonId();
        if (nameEmpty || phoneNumberEmpty || (selectedId == -1)) {
            showToast(" All Fields have to filled.");
            return false;
        }
        if (customerPhoneNo.getText().toString().length() != 10) {
            showToast("Phone Number field should contain only 10 characters.");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
