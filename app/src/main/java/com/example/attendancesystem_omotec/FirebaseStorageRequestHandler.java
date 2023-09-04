package com.example.attendancesystem_omotec;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseStorageRequestHandler extends RequestHandler {

    @Override
    public boolean canHandleRequest(Request data) {
        return "gs".equals(data.uri.getScheme());
    }

    @Override
    public Result load(Request request, int networkPolicy) throws IOException {
        // Extract the Firebase Storage URL from the request
        String firebaseStorageUrl = request.uri.toString();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(firebaseStorageUrl);

        // Download the image from Firebase Storage as an InputStream
        StreamDownloadTask streamTask = storageRef.getStream();
        InputStream inputStream = streamTask.getResult().getStream();

        // Convert the InputStream to a Bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        return new Result(bitmap, Picasso.LoadedFrom.NETWORK);
    }
}
