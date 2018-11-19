package com.example.pinpipo.healthy.post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.pinpipo.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private Context context;
    private List<Comment> list;
    private TextView postId, id, name, email, body;

    public CommentAdapter(@NonNull Context context,
                          int resource,
                          @NonNull List<Comment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = (ArrayList<Comment>) objects;

    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View commentItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_comment_item,
                parent,
                false);

        postId = commentItem.findViewById(R.id.comment_item_postId);
        id = commentItem.findViewById(R.id.comment_item_id);
        name = commentItem.findViewById(R.id.comment_item_name);
        email = commentItem.findViewById(R.id.comment_item_email);
        body = commentItem.findViewById(R.id.comment_item_body);

        Comment c = list.get(position);

        postId.setText(c.getPostId());
        id.setText(c.getId());
        name.setText(c.getName());
        email.setText(c.getEmail());
        body.setText(c.getBody());

        return commentItem;
    }

}
