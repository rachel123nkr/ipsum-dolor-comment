package utils;

import com.fasterxml.jackson.databind.JsonNode;
import models.Comment;
import play.libs.ws.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class CommentsApi implements WSBodyReadables, WSBodyWritables {
    private final WSClient ws;

    @Inject
    public CommentsApi(WSClient ws) {
        this.ws = ws;
    }

    public CompletionStage<List<Comment>> getComments(Integer postId) {
        try {
            String url = "https://jsonplaceholder.typicode.com/comments?postId=" + postId;
            WSRequest request_comments = ws.url(url);
            return request_comments.get().thenApplyAsync(res ->{
                        List<Comment> comm_list =new ArrayList<>();
                        for(JsonNode comm:res.asJson()) {
                            comm_list.add(new Comment(comm));
                        }
                        return comm_list;
                    });
        }
        catch (Exception ex){
            return null;
        }
    }
}
