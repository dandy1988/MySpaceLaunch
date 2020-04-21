package dandy1988.myspacelaunch;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//вспомогательный класс для создание viewholder
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvLaunchEvent;
    private LaunchEvent launchEvent;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);// Передача Click выбранного dandy1988.myspacelaunch.ViewHolder
        tvLaunchEvent = itemView.findViewById(R.id.tvLaunchEvent);
        tvLaunchEvent.setText("NULL");
    }

    public void setData(LaunchEvent launchEvent) {
        if(launchEvent!=null) {
            tvLaunchEvent.setText("Date: " + launchEvent.getNet() + "\nName: " + launchEvent.getName());
            this.launchEvent = launchEvent;
        }
    }

    @Override
    public void onClick(View view) {
        if(launchEvent.getVidURLs()!=null){
            String text = launchEvent.getVidURLs().get(0).toString();
            Intent startVideoLaunchEvent = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
            try {
                view.getContext().startActivity(startVideoLaunchEvent);
            } catch (ActivityNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if((launchEvent.getVidURLs()==null)&&(launchEvent.getInfoURLs()!=null)){
            String text = launchEvent.getInfoURLs().get(0).toString();
            Intent startInfoLaunchEvent = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
            try {
                view.getContext().startActivity(startInfoLaunchEvent);
            } catch (ActivityNotFoundException ex) {
                ex.printStackTrace();
            }

        }
    }
}


