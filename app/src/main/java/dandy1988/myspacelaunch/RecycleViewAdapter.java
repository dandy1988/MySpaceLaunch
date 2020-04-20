package dandy1988.myspacelaunch;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class RecycleViewAdapter {
    //конфигурирование адаптера
    public static class LaunchAdapter extends RecyclerView.Adapter<ViewHolder>{
        //список launches
        private final LaunchCollection data;
        //конструктор адаптера
        public LaunchAdapter(LaunchCollection data) {
            this.data = data;
        }

        @NonNull
        @Override
        //Создание вьюшки и засовывание ее во вью холдер и затем возвращение ее в адаптер
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View view = layoutInflater.inflate(R.layout.item_launch, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        //Сохранение данных
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            LaunchEvent launchEvent = data.getLaunches().get(i);
            viewHolder.setData(launchEvent);
        }

        @Override
        //подсчет количества элементов
        public int getItemCount() {
            return data.getLaunches().size();
        }

    }
}
