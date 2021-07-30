package org.techtown.hello.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.hello.Adapters.RecAdapter;
import org.techtown.hello.OnSelectListener;
import org.techtown.hello.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordingsFragment extends Fragment implements OnSelectListener {

    private RecyclerView recyclerView;
    private List<File> fileList;
    private RecAdapter recAdapter;
    private Button delButton;


    File path =
            new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()
                            + "/VRecorder");


    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recordings, container, false);
        delButton = view.findViewById(R.id.delButton);
//        delButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "Del Clicked", Toast.LENGTH_SHORT).show();
//                if (position >= fileList.size()) {
//                    return;
//                }
//
//                File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/VRecorder/" + fileList.get(position).getName());
//
//                if (path.exists()) {
//                    path.delete();
//                    fileList.remove(position);
//                }
//
//                recAdapter.notifyDataSetChanged();
//            }
//        });
        displayFiles();

        return view;
    }

//      holder.delButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (position >= fileList.size()) {
//                    Toast.makeText(context, "Position does not exist: " + String.valueOf(position), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/VRecorder/" + fileList.get(position).getName());
//
//                if (path.exists()) {
//                    path.delete();
//                    fileList.remove(position);
//                }
//
//
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                if (Build.VERSION.SDK_INT >= 26) {
//                    ft.setReorderingAllowed(false);
//                }
//                ft.detach(this).attach(this).commit();
//
//            }
//        });


    private void displayFiles() {
        recyclerView = view.findViewById(R.id.recycler_records);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        fileList = new ArrayList<>();
        fileList.addAll(findFile(path));
        recAdapter = new RecAdapter(getContext(), fileList, this);
        recyclerView.setAdapter(recAdapter);
    }

    public ArrayList<File> findFile(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File singleFile : files) {
            if (singleFile.getName().toLowerCase().endsWith(".amr")) {
                arrayList.add(singleFile);
            }
        }
        return arrayList;
    }

    @Override
    public void OnSelected(File file) {
        Uri uri = FileProvider.getUriForFile(getContext(),
                getContext().getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "audio/x-wav");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        getContext().startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            displayFiles();
        }
    }
}
