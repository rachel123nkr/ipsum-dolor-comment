package controllers;

import akka.http.javadsl.model.HttpRequest;
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

//    public Result index() {
//
//        List<Comment> comm_list =new ArrayList<Comment>();
//
//        return ok(views.html.comments_list.render(comm_list));
//    }


    public Result get_comment(Http.Request request) {
//        Integer postId_int = Integer.parseInt(postId);
//        WSRequest  request_comments =  ws.url("https://jsonplaceholder.typicode.com/comments?postId=1");
//        System.out.println(request_comments.getUrl());
//        CompletionStage<WSResponse> response_ = request_comments.get();

        String[] postIds = request.queryString().get("postId");
        System.out.println(postIds);
        List<Comment> comm_list =new ArrayList<Comment>();
        if(postIds == null || postIds.length<=0 ){
            return ok(views.html.comments_list.render(comm_list));
        }

        try {
            Integer postId = Integer.parseInt(postIds[0]);

        String url = "https://jsonplaceholder.typicode.com/comments?postId="+postId;
        CompletionStage<JsonNode> jsonPromise = ws.url(url).get()
                .thenApply(WSResponse::asJson);

        JsonNode json_of_comments;
//        try {
            json_of_comments = jsonPromise.toCompletableFuture().get();
            for (JsonNode comm:json_of_comments) {
                comm_list.add(new Comment(comm.get("postId").asInt(), comm.get("id").asInt(), comm.get("name").asText(), comm.get("email").asText(), comm.get("body").asText()));
            }
            return ok(views.html.comments_list.render(comm_list));
        }
        catch (NumberFormatException nfe){
            return ok(views.html.comments_list.render(comm_list));
        }
        catch (Exception ex){
            return badRequest();

        }
    }

}

