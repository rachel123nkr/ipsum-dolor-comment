package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Comment;
import org.checkerframework.checker.units.qual.C;
import play.libs.Json;
import play.mvc.*;
import play.libs.ws.*;

import javax.inject.Inject;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller implements WSBodyReadables, WSBodyWritables{
    private final WSClient ws;

    @Inject
    public HomeController(WSClient ws) {
        this.ws = ws;
    }

    public Result index() {

        List<Comment> comm_list =new ArrayList<Comment>();

        return ok(views.html.index.render(comm_list));
    }


    public Result get_comment(Http.Request request) {
        WSRequest  request_comments =  ws.url("https://jsonplaceholder.typicode.com/comments?postId=1");
        System.out.println(request_comments.getUrl());
        CompletionStage<WSResponse> response_ = request_comments.get();

        String url = "https://jsonplaceholder.typicode.com/comments?postId=1";
        CompletionStage<JsonNode> jsonPromise = ws.url(url).get()
                .thenApply(WSResponse::asJson);


//        CompletionStage<Result> res = response_.thenApply(
//                (WSResponse r) -> {
////                    System.out.println(r.asJson().toPrettyString());
//                    JsonNode response_json = r.asJson();
//                    int status = r.getStatus();
//                    if (status == 200){
//
//                        WSRequest  request_comments2 = (WSRequest) ws.url("http://localhost:8080/comments").setMethod("POST").setBody(response_json).execute();
////                        request_comments2.post("");
//                        return ok(response_json);
//
////                        return ok(views.html.list_comments.render("sssss"));
//                    }
//                    else {
//                        return badRequest();
//                    }
//                });
        JsonNode json_of_comments;
        try {
            json_of_comments = jsonPromise.toCompletableFuture().get();
//            System.out.println(json_of_comments);


//        Comment comment = new Comment(1, 1, "aaa", "aaa", "aaa");
//        JsonNode commentJson = Json.toJson(comment);
//        System.out.println(commentJson.toPrettyString());
//            @(comment_list: List[Comment])

            List<Comment> comm_list =new ArrayList<Comment>();
            for (JsonNode comm:json_of_comments) {
//                System.out.println(comm.get("postId"));

                comm_list.add(new Comment(comm.get("postId").asInt(), comm.get("id").asInt(), comm.get("name").asText(), comm.get("email").asText(), comm.get("body").asText()));
            }
            return ok(views.html.index.render(comm_list));
        }
        catch (Exception ex){
            return badRequest();

        }
    }

}

