package dandy1988.myspacelaunch;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//вспомогательный класс для создание viewholder
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvLaunchEvent;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);// Передача Click выбранного dandy1988.myspacelaunch.ViewHolder
        tvLaunchEvent = itemView.findViewById(R.id.tvLaunchEvent);
        tvLaunchEvent.setText("NULL");
    }

    public void setData(LaunchEvent launchEvent) {//todo
        tvLaunchEvent.setText("Date:"launchEvent.getNet()+" Name: "+launchEvent.getName());
    }

    @Override
    public void onClick(View view) {
        if (tvLaunchEvent.getText().equals("2020.1.0: event#0")) {
            tvLaunchEvent.setText("Go to site:....");
            return;
        }
        tvLaunchEvent.setText("Clicked!!!!");
    }
}

