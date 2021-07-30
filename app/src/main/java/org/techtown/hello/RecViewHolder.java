package org.techtown.hello;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName;
    public LinearLayout container;
    public Button delButton;
    public RecViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.txtName);
        container = itemView.findViewById(R.id.container);
        delButton = itemView.findViewById(R.id.delButton);
    }
}
