package com.example.claritus.claritus.main.Chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.claritus.claritus.R;
import com.example.claritus.claritus.di.Injectable;
import com.example.claritus.claritus.main.Chat.adapter.ChatAdapter;
import com.example.claritus.claritus.main.MainNavigationController;
import com.example.claritus.claritus.utils.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import timber.log.Timber;


public class ChatFragment extends Fragment implements Injectable {
    private RecyclerView recyclerView;
    private ImageButton button;
    private ImageButton sendImg;
    private ChatAdapter chatAdapter;
    private List<ChatModal> chatMsgs = new ArrayList<>();
    private EditText msg;
    private FirebaseUser fuser;
    private String uid;
    private String remail;
    private String userChoosenTask;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private StorageReference mStorageRef;
    private DatabaseReference msgRef;
    private Uri filePath;
    private DatabaseReference fmsgRef;

    private static final int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @Inject
    MainNavigationController mainNavigationController;
    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        uid = getArguments().getString("uid");
        remail = getArguments().getString("email");
        Objects.requireNonNull(getActivity()).setTitle(remail);
        initViews(view);
        firebaseInit();
        if(!Utility.hasPermissions(getContext(), PERMISSIONS)){
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }
        msgRef.
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<ChatModal> chatModals = new ArrayList<>();
                        for (DataSnapshot chatSnapshot: dataSnapshot.getChildren()) {
                            ChatModal chatModal = chatSnapshot.getValue(ChatModal.class);
                            chatModals.add(chatModal);
                        }
                        chatMsgs.clear();
                        chatMsgs.addAll(chatModals);
                        chatAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(chatModals.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        button.setOnClickListener(view1 -> {
            if (msg.getText()!= null && !msg.getText().toString().isEmpty()) {
                String key = msgRef.push().getKey();
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();
                ChatModal chatModal = new ChatModal(key,
                        msg.getText().toString(),
                        fuser.getEmail(),
                        remail, ts,"text","");
                if (key != null) {
                    msgRef.child(key).setValue(chatModal);
                    fmsgRef.child(key).setValue(chatModal);
                } else {
                    Toast.makeText(getContext(), "Somw error occured in sending message. Try again later", Toast.LENGTH_SHORT).show();
                }
            }
            msg.setText("");
            msg.clearFocus();
        });

        sendImg.setOnClickListener(cview -> selectImage());

    }

    private void firebaseInit() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (auth.getCurrentUser() == null) {
            logout();
        } else {
            fuser = auth.getCurrentUser();
            msgRef = database.getReference().child("Messages")
                    .child(fuser.getUid())
                    .child(uid);
            fmsgRef = database.getReference().child("Messages")
                    .child(uid)
                    .child(fuser.getUid());
            mStorageRef = FirebaseStorage.getInstance().getReference();
        }
    }

    private void logout() {
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.chatRecycler);
        button = view.findViewById(R.id.sendBtn);
        sendImg = view.findViewById(R.id.sendImg);
        msg = view.findViewById(R.id.msgInput);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(getContext(), chatMsgs, fuser.getEmail(), (v, position) -> {

            StorageReference ref = mStorageRef.child(chatMsgs.get(position).getImageRef());
            File destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    System.currentTimeMillis() + ".jpg");
            ref.getFile(destination).addOnSuccessListener(taskSnapshot -> {
                chatMsgs.get(position).setLocalPath(destination.getPath());
                msgRef.child(chatMsgs.get(position).getId()).setValue(chatMsgs.get(position));
            }).addOnFailureListener(exception -> {
                // Handle any errors
            });
        });
        recyclerView.setAdapter(chatAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void selectImage() {
        if(!Utility.hasPermissions(getContext(), PERMISSIONS)){
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), PERMISSIONS, PERMISSION_ALL);
        } else {
            final CharSequence[] items = {"Take Photo", "Choose from Library",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Add Photo!");
            builder.setItems(items, (dialog, item) -> {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                    Toast.makeText(getContext(), "Permission was denied cant send images", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                filePath = data.getData();
            } else if (requestCode == REQUEST_CAMERA) {
                if (data.getExtras() != null) {
                    filePath = onCaptureImageResult(data);
                }
            }
        }
        if (filePath != null) {
            String key = msgRef.push().getKey();
            if (key == null)
                Toast.makeText(getContext(), "Some error occured in sending. Try again later.", Toast.LENGTH_SHORT).show();
            else {
                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                String path = Utility.getPathFromUri(getContext(), filePath);
                ChatModal chatModal = new ChatModal(key,
                        "",
                        fuser.getEmail(),
                        remail, ts, "image", "", path, "images/" + System.currentTimeMillis() + ".jpg");
                msgRef.child(key).setValue(chatModal);
                StorageReference ref = mStorageRef.child("images/" + System.currentTimeMillis() + ".jpg");
                UploadTask uploadTask = ref.putFile(filePath);
                uploadTask.continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return ref.getDownloadUrl();
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Timber.d(downloadUri.toString());
                        chatModal.setUrl(downloadUri.toString());
//                    chatModal.setLocalPath(""); TODO uncomment when users added
                        fmsgRef.child(key).setValue(chatModal);
                    } else {
                        Toast.makeText(getContext(), "Cant upload files", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private Uri onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assert thumbnail != null;
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(destination);
//        ivImage.setImageBitmap(thumbnail);
    }
}