package com.thebaileybrew.inventorymanager.listeners.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.data.models.Order;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Order> orderCollection;

    final private OrdersRecyclerAdapterClickHandler adapterClickHandler;

    public interface OrdersRecyclerAdapterClickHandler {
        void onClick(View view, Order order);
    }

    public OrdersRecyclerAdapter(Context context, List<Order> orderCollection, OrdersRecyclerAdapterClickHandler clicker) {
        this.layoutInflater = LayoutInflater.from(context);
        this.orderCollection = orderCollection;
        this.adapterClickHandler = clicker;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_item_upcoming_orders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Order currentOrder = orderCollection.get(position);

        holder.itemType.setText(currentOrder.getItemType());

        StringBuilder builder = new StringBuilder();
        builder.append("Ordered: ");
        builder.append(currentOrder.getOrderDate());
        holder.orderDate.setText(builder.toString());

        StringBuilder builder2 = new StringBuilder();
        builder2.append("Expected: ");
        builder2.append(currentOrder.getExpectedDate());
        holder.expectedDate.setText(builder2.toString());

        holder.orderQuantity.setText(currentOrder.getOrderQuantity());
    }

    @Override
    public int getItemCount() {
        if (orderCollection == null) {
            return 0;
        } else {
            return orderCollection.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView itemType;
        final TextView orderDate;
        final TextView expectedDate;
        final TextView orderQuantity;

        private ViewHolder(View orderView) {
            super(orderView);
            itemType = orderView.findViewById(R.id.item_type);
            orderDate = orderView.findViewById(R.id.ordered_date);
            expectedDate = orderView.findViewById(R.id.expected_date);
            orderQuantity = orderView.findViewById(R.id.quantity_ordered);
            orderView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Order currentOrder = orderCollection.get(getAdapterPosition());
            adapterClickHandler.onClick(v, currentOrder);
        }
    }
}
