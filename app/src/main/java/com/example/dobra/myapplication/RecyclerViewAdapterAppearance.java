package com.example.dobra.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapterAppearance  extends RecyclerView.Adapter<RecyclerViewAdapterAppearance.ViewHolder>{
    private List<Appearance> mAppearance = new ArrayList<>();

    private List<Consumable> mConsumable = new ArrayList<>();
    private Context mContext;
    private LinearLayout equipItemLayout;


    public RecyclerViewAdapterAppearance(Context mContext, List<Appearance> mAppearance, List<Consumable> mConsumable, LinearLayout equipItemLayout) {
        this.mAppearance = mAppearance;
        this.mConsumable = mConsumable;
        this.mContext = mContext;
        this.equipItemLayout = equipItemLayout;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterAppearance.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inventoryitem, viewGroup, false);
        RecyclerViewAdapterAppearance.ViewHolder holder = new RecyclerViewAdapterAppearance.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapterAppearance.ViewHolder viewHolder, int i) {
        if(i<mAppearance.size()) {

            if (mAppearance.get(i).getUnlocked()) {

                Glide.with(mContext).asBitmap().load(mAppearance.get(i).getImageURL()).into(viewHolder.itemImage);

                //Picasso.with(mContext).load(mFriends.get(i).getFriendImage()).into(viewHolder.friendImage);

                final int currentPos = i;

                if (mAppearance.get(i).getName().equals("CaptainHat")) {

                    viewHolder.itemName.setText("Captain's Hat");

                    viewHolder.rarity.setText("Legendary Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#FF4500"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");


                } else if (mAppearance.get(i).getName().equals("CowboyHat")) {
                    viewHolder.itemName.setText("The Fedora");

                    viewHolder.rarity.setText("Legendary Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#FF4500"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");

                } else if (mAppearance.get(i).getName().equals("FarmerHat")) {
                    viewHolder.itemName.setText("Farmer's Hat");

                    viewHolder.rarity.setText("Legendary Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#FF4500"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");

                } else if (mAppearance.get(i).getName().equals("Headphones")) {
                    viewHolder.itemName.setText("Headphones");

                    viewHolder.rarity.setText("Legendary Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#FF4500"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");

                } else if (mAppearance.get(i).getName().equals("KingCrown")) {
                    viewHolder.itemName.setText("King's Crown");

                    viewHolder.rarity.setText("Legendary Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#FF4500"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");

                } else if (mAppearance.get(i).getName().equals("NinjaHat")) {
                    viewHolder.itemName.setText("The Bandana");

                    viewHolder.rarity.setText("Legendary Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#FF4500"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");

                } else if (mAppearance.get(i).getName().equals("PirateHat")) {
                    viewHolder.itemName.setText("Pirate's Hat");

                    viewHolder.rarity.setText("Legendary Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#FF4500"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");

                } else if (mAppearance.get(i).getName().equals("SpaceHelmet")) {
                    viewHolder.itemName.setText("Space Helmet");

                    viewHolder.rarity.setText("Legendary Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#FF4500"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");

                } else if (mAppearance.get(i).getName().equals("TopHat")) {
                    viewHolder.itemName.setText("Top Hat");

                    viewHolder.rarity.setText("Legendary Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#FF4500"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");

                } else if (mAppearance.get(i).getName().equals("TrainingCap")) {
                    viewHolder.itemName.setText("Training Cap");

                    viewHolder.rarity.setText("Mythical Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#800080"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");

                } else if (mAppearance.get(i).getName().equals("WizardHat")) {
                    viewHolder.itemName.setText("Wizard's Hat");

                    viewHolder.rarity.setText("Legendary Item");
                    viewHolder.rarity.setTextColor(Color.parseColor("#FF4500"));
                    viewHolder.quantity.setText("Quantity: 1");
                    viewHolder.description.setText("Click to equip!");

                }


                viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        equipItemLayout.setVisibility(View.VISIBLE);
                        CurrentUserInformation.getInstance().setItemToBeEquipped(mAppearance.get(currentPos).getName());
                    }
                });
            }
        } else {

            int newIndex = i-mAppearance.size();

            Glide.with(mContext).asBitmap().load(mConsumable.get(newIndex).getImageURL()).into(viewHolder.itemImage);

            //Picasso.with(mContext).load(mFriends.get(i).getFriendImage()).into(viewHolder.friendImage);


            if(mConsumable.get(newIndex).getName().equals("FixerElixer")){

                viewHolder.itemName.setText("Fixer Elixer");

                viewHolder.rarity.setText("Epic Item");
                viewHolder.rarity.setTextColor(Color.parseColor("#9370DB"));
                viewHolder.quantity.setText("Quantity: "+mConsumable.get(newIndex).getQuantity().toString());
                viewHolder.description.setText("Fixes an error");


            } else if(mConsumable.get(newIndex).getName().equals("RevealingPotion")){
                viewHolder.itemName.setText("Revealing Potion");

                viewHolder.rarity.setText("Rare Item");
                viewHolder.rarity.setTextColor(Color.parseColor("#90EE90"));
                viewHolder.quantity.setText("Quantity: "+mConsumable.get(newIndex).getQuantity().toString());
                viewHolder.description.setText("Reveals an error");

            } else if(mConsumable.get(newIndex).getName().equals("HealingPotion")){
                viewHolder.itemName.setText("Healing Potion");

                viewHolder.rarity.setText("Common Item");
                viewHolder.rarity.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                viewHolder.quantity.setText("Quantity: "+mConsumable.get(newIndex).getQuantity().toString());
                viewHolder.description.setText("Heals 1 HP");

            }
        }
    }

    @Override
    public int getItemCount() {
        return mAppearance.size() + mConsumable.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;

        TextView itemName, rarity, quantity, description;

        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            rarity = itemView.findViewById(R.id.rarity);
            quantity = itemView.findViewById(R.id.quantity);
            description = itemView.findViewById(R.id.description);
            parentLayout = itemView.findViewById(R.id.friendParentLayout);

        }
    }
}
