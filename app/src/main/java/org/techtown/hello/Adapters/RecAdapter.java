package org.techtown.hello.Adapters;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.hello.OnSelectListener;
import org.techtown.hello.R;
import org.techtown.hello.RecViewHolder;
import org.techtown.hello.Fragments.RecordingsFragment;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecViewHolder> {

    private Context context;
    private List<File> fileList;
    private OnSelectListener listener;

    public RecAdapter(Context context, List<File> fileList, OnSelectListener listener) {
        this.context = context;
        this.fileList = fileList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
        holder.tvName.setText(fileList.get(position).getName());
        holder.tvName.setSelected(true);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.OnSelected(fileList.get(position));
            }
        });
        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Position", String.valueOf(position));
                if (position >= fileList.size()) {
                    Log.e("Error", String.valueOf(position));
                    return;
                }

                File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/VRecorder/" + fileList.get(position).getName());
                Log.d("Path", String.valueOf(path));

                try{
                    if (path.exists()) {
                        if(path.delete()) {
                            Log.d("", "delete"+path);
                            fileList.remove(position);
                        }

                    }
                }
                catch (Exception e){

                }

                notifyDataSetChanged();


            }
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }
}
