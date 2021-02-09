package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Comment;
import play.mvc.*;
import play.libs.ws.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import utils.CommentsApi;

public class HomeController extends Controller implements WSBodyReadables, WSBodyWritables{
    private final CommentsApi cws;


    @Inject
    public HomeController(CommentsApi cws) {
        this.cws = cws;
    }


    public Result index(Http.Request request) {
        String[] postIds = request.queryString().get("postId");
        if(postIds == null || postIds.length<=0 )
            return ok(views.html.index.render(null));

        try {
            List<Comment> comm_list =new ArrayList<>();
            Integer postId = Integer.parseInt(postIds[0]);
            JsonNode json_of_comments = cws.getComments(postId);
            for (JsonNode comm : json_of_comments) {
                comm_list.add(new Comment(comm));
            }
            return ok(views.html.index.render(comm_list));
        }
        catch (NumberFormatException nfe){
            return ok(views.html.index.render(null));
        }
        catch (Exception ex){
            return badRequest(views.html.error.render(ex.getMessage()));

        }
    }

}

