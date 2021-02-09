package utils;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.*;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class CommentsApi implements WSBodyReadables, WSBodyWritables {
    private final WSClient ws;

    @Inject
    public CommentsApi(WSClient ws) {
        this.ws = ws;
    }

    public CompletionStage<JsonNode> getComments(Integer postId) {
        try {
            String url = "https://jsonplaceholder.typicode.com/comments?postId=" + postId;
            WSRequest request_comments = ws.url(url);
            return  request_comments.get().thenApplyAsync(WSResponse::asJson);
        }
        catch (Exception ex){
            return null;
        }
    }
}
